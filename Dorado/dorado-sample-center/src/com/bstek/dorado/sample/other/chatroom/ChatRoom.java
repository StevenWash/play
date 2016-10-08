package com.bstek.dorado.sample.other.chatroom;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.socket.Message;
import com.bstek.dorado.view.socket.Socket;

@Component
public class ChatRoom {
	@Resource
	private ChatServer chatServer;

	private static int SerialNo = 1;

	public synchronized void onReady(View view) {
		view.setUserData("User" + (SerialNo++));
	}

	@Expose
	public void connectServer(Socket socket, String user) {
		chatServer.registerSocket(socket);
		chatServer.boardcast(new Message(ChatServer.MSG_TYPE_CHAT,
				new ChatMessage(ChatServer.SYSTEM_USER, "\"" + user
						+ "\"刚刚进入了聊天室。")));
	}
}