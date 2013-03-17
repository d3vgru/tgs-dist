package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSObject;

public class TGSConfig extends TGSObject {
	private boolean proxyEnabled;
	
	public boolean isProxyEnabled() {
		return proxyEnabled;
	}

	public void setProxyEnabled(boolean proxyEnabled) {
		this.proxyEnabled = proxyEnabled;
	}

	private String proxyAddress;
	
	public String getProxyAddress() {
		return proxyAddress;
	}

	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}

	private Integer proxyPort;

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}	
}
