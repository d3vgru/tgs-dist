package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSEvent;

public class TGSUserEvent extends TGSEvent<TGSUser> {
	// verbs:
	// name - the user changed name

	private TGSUser subject;

	@Override
	public TGSUser getSubject() {
		return subject;
	}

	@Override
	public void setSubject(TGSUser subject) {
		this.subject = subject;
	}
	
	public TGSUserEvent() {
		type = "user";
	}
}
