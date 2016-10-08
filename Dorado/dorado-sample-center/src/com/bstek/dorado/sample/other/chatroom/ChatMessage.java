package com.bstek.dorado.sample.other.chatroom;

import java.util.Date;

public class ChatMessage {
	private long id = System.currentTimeMillis();
	private String user;
	private String text;
	private Date time = new Date();

	public ChatMessage() {
	}

	public ChatMessage(String user, String text) {
		this.user = user;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
