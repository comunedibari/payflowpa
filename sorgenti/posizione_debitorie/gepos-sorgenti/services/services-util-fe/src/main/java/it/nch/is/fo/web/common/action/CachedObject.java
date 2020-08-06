package it.nch.is.fo.web.common.action;

import java.util.HashMap;

public class CachedObject {

	private Object attribute;

	private long secsToLive;

	private long timeEnd;

	private static HashMap mappa = new HashMap();

	public static void setAttribute(Object name, Object attribute, long secsToLive) {
		CachedObject cached = new CachedObject();
		cached.setAttribute(attribute, secsToLive);
		mappa.put(name, cached);
	}

	public static Object getAttribute(Object name) {
		CachedObject cached = (CachedObject) mappa.get(name);
		if (cached==null || cached.isExpired()) {
			mappa.remove(name);
			return null;
		} else {
			return cached.getAttribute();
		}
	}

	public static boolean isExpired(Object name) {
		CachedObject cached = (CachedObject) mappa.get(name);
		return cached.isExpired();
	}

	private void reset() {
		timeEnd = System.currentTimeMillis() + (secsToLive * 1000);
	}

	private void setSecsToLive(long secsToLive) {
		this.secsToLive = secsToLive;
	}

	public boolean isExpired() {
		long now = System.currentTimeMillis();
		if (now > timeEnd) {
			return true;
		} else {
			return false;
		}
	}

	private Object getAttribute() {
		return attribute;
	}

	private void setAttribute(Object attribute, long secsToLive) {
		this.attribute = attribute;
		this.setSecsToLive(secsToLive);
		this.reset();
	}

	public long getSecsToLive() {
		return secsToLive;
	}

}
