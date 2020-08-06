package it.tasgroup.idp.util;

import java.util.LinkedHashMap;

public class LRUCache<K,V> extends LinkedHashMap<K,V> {

	private final int cacheSize;
	
	public LRUCache (int cacheSize) {
		if (cacheSize < 1) throw new IllegalArgumentException("cacheSize");
		this.cacheSize = cacheSize;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() >= cacheSize;
	}
}
