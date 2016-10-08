var socket, currentUser;

// @Bind view.onReady
!function(inputUser) {
	currentUser = view.get("userData");
	inputUser.set("text", currentUser);
}

// @Bind #buttonEnter.onClick
!function(inputUser, cardbookChatRoom, panelChatRoom) {
	var user = inputUser.get("text");
	if (!user) {
		throw new dorado.Exception("请输入一个有效的昵称！");
	}

	currentUser = user;
	cardbookChatRoom.set("currentIndex", 1);
	panelChatRoom.set("caption", "Chat Room - " + user);

	socket = dorado.Socket.connect({
		service : "chatRoom#connectServer",
		parameter : currentUser,
		onReceive : function(arg) {
			var messagesDiv = view.get("#messageList").getContentContainer();
			if (arg.type == "chat") {
				var sender = arg.data.user, className;
				if (sender == "System") className = "system";
				else if (sender == currentUser) className = "me";
				else className = "other";
				
				$(messagesDiv).xCreate({
					tagName : "DIV",
					className : "message message-" + className,
					content: [
						{
							tagName : "DIV",
							className : "message-author",
							contentText : sender + " - "
									+ formatDate(arg.data.time)
						}, {
							tagName : "DIV",
							className : "message-text",
							contentText : arg.data.text
						}
					]
				});
				messagesDiv.scrollTop = messagesDiv.scrollHeight
						- messagesDiv.clientHeight;
			}
		}
	});
}

// @Bind #inputMessage.onTextEdit
!function(self, buttonSend) {
	buttonSend.set("disabled", self.get("text") == "");
}

// @Bind #buttonSend.onClick
!function(self, inputMessage) {
	socket.send("chat", {
		user : currentUser,
		text : inputMessage.get("text")
	});
	inputMessage.set("text", "");
	self.set("disabled", true);
}

function formatDate(time) {
	console.log(time + "");
	var date = Date.parseDate(time, "Y-m-d\\TH:i:s\\Z");
	return date.formatDate("n-d H:i:s");
}