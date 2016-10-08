package com.bstek.dorado.sample.other.chatroom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.bstek.dorado.view.socket.Message;
import com.bstek.dorado.view.socket.Socket;
import com.bstek.dorado.view.socket.SocketConnectionListener;
import com.bstek.dorado.view.socket.SocketReceiveListener;

@Component
public class ChatServer implements SocketConnectionListener,
		SocketReceiveListener {
	private static final Log logger = LogFactory.getLog(ChatServer.class);

	public final static String SYSTEM_USER = "System";
	public final static String MSG_TYPE_CHAT = "chat";

	private List<Socket> sockets = new ArrayList<Socket>();
	private MessageBase messageBase = new MessageBase();

	public ChatServer() {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUser(SYSTEM_USER);
		chatMessage.setText("欢迎来到Dorado聊天室！");
		chatMessage.setTime(new Date());
		boardcast(new Message(MSG_TYPE_CHAT, chatMessage));
	}

	public void registerSocket(Socket socket) {
		socket.addConnectionListener(this);
		socket.addReceiveListener(this);
		sockets.add(socket);

		try {
			for (ChatMessage chatMessage : messageBase.getMessages()) {
				Message message = new Message();
				message.setType(MSG_TYPE_CHAT);
				message.setData(chatMessage);
				socket.send(message);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@SuppressWarnings("unchecked")
	public void onReceive(Socket socket, Message message) {
		String type = message.getType();
		if (MSG_TYPE_CHAT.equals(type)) {

			Map<String, String> data = (Map<String, String>) message.getData();
			ChatMessage chatMessage = new ChatMessage(data.get("user"),
					data.get("text"));
			boardcast(new Message(MSG_TYPE_CHAT, chatMessage));
		}
	}

	public void boardcast(Message message) {
		messageBase.addMessage((ChatMessage) message.getData());

		for (Socket otherSocket : sockets) {
			try {
				otherSocket.send(message);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
	}

	public void onDisconnect(Socket socket) {
		socket.removeConnectionListener(this);
		socket.removeReceiveListener(this);
		sockets.remove(socket);
	}
}