package com.koleksinaia.rest.controller.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseObject<T> {

	private List<ResponseMessage> messages = new ArrayList<ResponseMessage>();
	
	private T content;

	public List<ResponseMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ResponseMessage> messages) {
		this.messages = messages;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
	
	public void addMessage(ResponseMessage msg) {
		this.messages.add(msg);
	}
}
