package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSObject;

public class TGSMessageSearchEvent extends TGSSearchEvent {
	private TGSMessage subject;
	
	@Override
	public TGSMessage getSubject() {
		return subject;
	}

	@Override
	public void setSubject(TGSObject subject) {
		assert subject instanceof TGSMessage;
		subject = (TGSMessage)subject;
	}
	
	public TGSMessageSearchEvent() {
		type += ".user";
	}
}
