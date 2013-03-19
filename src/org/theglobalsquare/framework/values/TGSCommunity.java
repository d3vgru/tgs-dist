package org.theglobalsquare.framework.values;

import java.util.*;

import org.theglobalsquare.framework.TGSObject;

public class TGSCommunity extends TGSObject {
	private List<TGSMessage> messages;
	
	public List<TGSMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<TGSMessage> messages) {
		this.messages = messages;
	}

	public TGSCommunity() {
		this.messages = new ArrayList<TGSMessage>();
	}
}
