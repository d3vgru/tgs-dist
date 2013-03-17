package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSEvent;

public class TGSMessageEvent extends TGSEvent<TGSMessage> {
	// verbs:
	// received - a message has been received
	// send - send this message
	
	public TGSMessageEvent() {
		type = "message";
	}

	private TGSMessage subject;

	@Override
	public TGSMessage getSubject() {
		return subject;
	}

	@Override
	public void setSubject(TGSMessage subject) {
		this.subject = subject;
	}
}
