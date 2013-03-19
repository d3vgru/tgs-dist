package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSObject;

public class TGSUser extends TGSObject {
	public TGSUser() {
		setName("Anon");
	}
	
	public static TGSUser _me = null;
	
	public static TGSUser getMe() {
		if(_me == null)
			_me = new TGSUser();
		return _me;
	}
}
