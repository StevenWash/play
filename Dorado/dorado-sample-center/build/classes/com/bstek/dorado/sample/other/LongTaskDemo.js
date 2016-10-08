// @Bind #buttonReload.onClick
!function() {
	location.reload();
}

// @Bind #longTaskCloneProject.beforeExecute
!function(progressCloneProject) {
	progressCloneProject.set("value", 0);
}

// @Bind #longTaskCloneProject.onStateChange
!function(self, arg, progressCloneProject, labelCloneProgress, buttonSuspendCloning, buttonResumeCloning, buttonAbortCloning) {
	var text = "[" + $resource("dorado.baseWidget.LongTask." + arg.state) + "] "; 
	if (arg.data) {
		progressCloneProject.set({
			maxValue: arg.data.total,
			value: arg.data.completed
		});
		text += arg.text
	}
	labelCloneProgress.set("text", text);
	
	buttonSuspendCloning.set("disabled", arg.state != "running");
	buttonResumeCloning.set("disabled", arg.state != "suspended");
	buttonAbortCloning.set("disabled", !self.get("alive"));
}

// @Bind #longTaskCloneProject.onSuccess
!function(arg, progressCloneProject, labelCloneProgress) {
	labelCloneProgress.set("text", "");
	progressCloneProject.set("value", progressCloneProject.get("maxValue"));
	dorado.widget.NotifyTipManager.notify("复制项目完成。");
}

// @Bind #buttonSuspendCloning.onClick
!function(longTaskCloneProject) {
	longTaskCloneProject.suspend();
}

// @Bind #buttonResumeCloning.onClick
!function(longTaskCloneProject) {
	longTaskCloneProject.resume();
}

// @Bind #buttonAbortCloning.onClick
!function(longTaskCloneProject) {
	longTaskCloneProject.abort();
}

// @Bind #longTaskCheckOrders.onLog
!function(arg, messageList) {
	var messagesDiv = messageList.getContentContainer();
	
	if (arg.state == "waiting") {
		$(messagesDiv).empty();
	}
	
	$(messagesDiv).xCreate({
		tagName: "DIV",
		className: "log",
		contentText: arg.text
	});
	messagesDiv.scrollTop = messagesDiv.scrollHeight - messagesDiv.clientHeight;
}

// @Bind #longTaskCheckOrders.onStateChange
!function(arg, messageList) {
	var messagesDiv = messageList.getContentContainer();
	$(messagesDiv).xCreate({
		tagName: "DIV",
		className: "state",
		contentText: ("[" + $resource("dorado.baseWidget.LongTask." + arg.state) + "] " + (arg.text || ''))
	});
	messagesDiv.scrollTop = messagesDiv.scrollHeight - messagesDiv.clientHeight;
}

// @Bind #longTaskCheckOrders.onSuccess
!function(arg) {
	dorado.widget.NotifyTipManager.notify("本次共核查了" + arg.result + "笔订单。");
}