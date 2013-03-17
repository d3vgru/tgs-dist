package org.theglobalsquare.framework;

public abstract class TGSEvent<T> {
	// what kind of event is this?
	protected String type = "abstract";
	public String getType() { return type; }
	
	// what is doing it?
	public abstract T getSubject();
	public abstract void setSubject(T subject);
	
	// what is going on?
	private String verb = "chilling";
	public String getVerb() {
		return verb;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}

	// to what is it happening?
	private TGSObject object = null;
	public TGSObject getObject() {
		return object;
	}
	public void setObject(TGSObject object) {
		this.object = object;
	}
	
	// route to python?
	private boolean toPy = false;
	
	
	public boolean send() {
		return TGSEventProxy.getInstance().sendEvent(this, true);
	}
}
