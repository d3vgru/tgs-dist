package org.theglobalsquare.framework;

import java.util.*;
import java.util.concurrent.*;

// A queue of events passing between the java (UI) and python (dispersy) layers.
public class TGSEventProxy {
	private static TGSEventProxy _instance = null;
	private Queue<TGSEvent<? extends TGSObject>> qFromPy, qToPy;
	private TGSEventProxy() {
		qFromPy = new ConcurrentLinkedQueue<TGSEvent<? extends TGSObject>>();
		qToPy = new ConcurrentLinkedQueue<TGSEvent<? extends TGSObject>>();
	}
	public static TGSEventProxy getInstance() {
		if(_instance == null)
			_instance = new TGSEventProxy();
		return _instance;
	}
	public static boolean sendEvent(TGSEvent<? extends TGSObject> e) {
		// python calls sendEvent(e) to send to java
		// java calls sendEvent(e, true); to send to python
		// returns false if it can't send
		return sendEvent(e, false);
	}
	public static boolean sendEvent(TGSEvent e, boolean toPy) {
		TGSEventProxy proxy = TGSEventProxy.getInstance();
		Queue<TGSEvent<? extends TGSObject>> dest = proxy.qFromPy;
		if(toPy)
			dest = proxy.qToPy;
		return dest.offer(e);
	}
	public static TGSEvent<? extends TGSObject> nextEvent() {
		// python calls nextEvent() to get from java
		// java calls nextEvent(false); to get from python
		// returns null if no events
		return nextEvent(true);
	}
	public static TGSEvent<? extends TGSObject> nextEvent(boolean toPy) {
		TGSEventProxy proxy = TGSEventProxy.getInstance();
		Queue<TGSEvent<? extends TGSObject>> src = proxy.qFromPy;
		if(toPy)
			src = proxy.qToPy;
		return src.poll();
	}
}
