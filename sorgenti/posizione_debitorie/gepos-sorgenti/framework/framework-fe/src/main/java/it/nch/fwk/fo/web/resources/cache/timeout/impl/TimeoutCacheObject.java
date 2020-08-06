package it.nch.fwk.fo.web.resources.cache.timeout.impl;

public class TimeoutCacheObject {

	private long enterTime;

	private Object value;

	public TimeoutCacheObject(long enterTime, Object value) {
		this.enterTime = enterTime;
		this.value = value;
	}

	public boolean isValid(long timeout) {
		if (timeout == 0) {
			return true;
		}
		long curTime = System.currentTimeMillis();
		long validTime = enterTime + timeout;
		if (validTime >= curTime) {
			return true;
		} else {
			return false;
		}
	}

	public long getEnterTime() {
		return enterTime;
	}

	public Object getValue() {
		return value;
	}

	public void setEnterTime(long enterTime) {
		this.enterTime = enterTime;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}