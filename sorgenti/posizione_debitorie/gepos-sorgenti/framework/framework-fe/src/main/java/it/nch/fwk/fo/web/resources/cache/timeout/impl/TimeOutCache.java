package it.nch.fwk.fo.web.resources.cache.timeout.impl;

import it.nch.fwk.fo.web.resources.cache.Cache;
import it.nch.fwk.fo.web.resources.cache.CacheConfiguration;

import java.util.HashMap;

public class TimeOutCache implements Cache {

	private long timeout;

	private HashMap cache = null;

	public TimeOutCache() {
		cache = new HashMap();
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public Object get(Object key) {
		Object retVal = null;
		TimeoutCacheObject cacheVal = (TimeoutCacheObject) cache.get(key);
		if (cacheVal != null && cacheVal.isValid(timeout)) {
			return cacheVal.getValue();
		} else if (cacheVal != null) {
			cache.remove(key);
		}
		return retVal;
	}

	public void put(Object key, Object value) {
		long enterTime = System.currentTimeMillis();
		TimeoutCacheObject obj = new TimeoutCacheObject(enterTime, value);
		cache.remove(key);
		cache.put(key, obj);
	}

	public Cache configure(CacheConfiguration config) {
		try {
			TimeOutCacheConfiguration toc = (TimeOutCacheConfiguration) config;
			timeout = toc.getTimeout();
		} catch (Exception e) {
			return null;
		}
		return this;
	}

	public void reset() {
		cache = null;
		cache = new HashMap();
	}
}