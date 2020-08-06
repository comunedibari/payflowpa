package it.nch.fwk.fo.web.resources.cache.timeout.impl;

import it.nch.fwk.fo.web.resources.cache.CacheConfiguration;

public class TimeOutCacheConfiguration implements CacheConfiguration {

	private String cacheName;

	private long timeout;

	public String getCacheName() {
		return cacheName;
	}

	public long getTimeout() {
		return timeout;
	}

	public String getCacheType() {
		return "it.nch.fwk.fo.web.resources.cache.timeout.impl.TimeOutCache";
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setCacheName(String name) {
		cacheName = name;
	}
}