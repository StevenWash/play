package com.bstek.dorado.sample.other.chatroom;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class MessageBase {
	public static final int MAX_MESSAGE_COUNT = 100;
	private List<ChatMessage> messages = new Vector<ChatMessage>(
			MAX_MESSAGE_COUNT);

	public List<ChatMessage> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public synchronized void addMessage(ChatMessage message) {
		if (messages.size() > MAX_MESSAGE_COUNT - 1) {
			int sz = messages.size(), gap = sz - MAX_MESSAGE_COUNT - 1;
			for (int i = 0; i < gap; i++) {
				messages.remove(0);
			}
		}
		messages.add(message);
	}
}
