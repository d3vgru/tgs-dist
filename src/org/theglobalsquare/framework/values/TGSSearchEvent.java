package org.theglobalsquare.framework.values;

import org.theglobalsquare.framework.TGSEvent;
import org.theglobalsquare.framework.TGSObject;

public abstract class TGSSearchEvent extends TGSEvent<TGSObject> {
	// verbs:
	// start - request a search to start [java]
	// cancel - cancel search [java]
	// update - report result(s) [python]
	
	// various types
	class ForCommunity extends TGSSearchEvent {
		private TGSCommunity subject;
		
		@Override
		public TGSCommunity getSubject() {
			return subject;
		}

		@Override
		public void setSubject(TGSObject subject) {
			assert subject instanceof TGSCommunity;
			subject = (TGSCommunity)subject;
		}
		
		public ForCommunity() {
			type += ".community";
		}
	}
	
	class ForMessage extends TGSSearchEvent {
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
		
		public ForMessage() {
			type += ".message";
		}
	}
	
	class ForUser extends TGSSearchEvent {
		private TGSUser subject;
		
		@Override
		public TGSUser getSubject() {
			return subject;
		}

		@Override
		public void setSubject(TGSObject subject) {
			assert subject instanceof TGSUser;
			subject = (TGSUser)subject;
		}
		
		public ForUser() {
			type += ".user";
		}
	}
	
	public TGSSearchEvent() {
		type = "search";
	}
}
