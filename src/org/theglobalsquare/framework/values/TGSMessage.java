package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSObject;

public class TGSMessage extends TGSObject {
	// who sent it?
	private TGSUser from;
	
	public TGSUser getFrom() {
		return from;
	}

	public void setFrom(TGSUser from) {
		this.from = from;
	}

	// what does it say?
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	// what community is this in?
	private TGSCommunity community;

	public TGSCommunity getCommunity() {
		return community;
	}

	public void setCommunity(TGSCommunity community) {
		this.community = community;
	}
}
