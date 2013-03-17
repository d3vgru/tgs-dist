package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSEvent;

public class TGSCommunityEvent extends TGSEvent<TGSCommunity> {
	// verbs:
	// new - created new square (java)
	// joined - joined a square (java)
	// left - left a square (java)
	
	private TGSCommunity subject;

	@Override
	public TGSCommunity getSubject() {
		return subject;
	}

	@Override
	public void setSubject(TGSCommunity subject) {
		this.subject = subject;
	}

}
