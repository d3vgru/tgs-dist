package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSEvent;

public class TGSConfigEvent extends TGSEvent<TGSConfig> {
	// verbs:
	// update - set a config parameter 
	
	public TGSConfigEvent() {
		type = "config";
	}

	private TGSConfig subject;

	@Override
	public TGSConfig getSubject() {
		return subject;
	}

	@Override
	public void setSubject(TGSConfig subject) {
		this.subject = subject;
	}
	
	// what is the param called?
	private String paramName;
	
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	// what is the param being set to?
	private String paramValue;

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
