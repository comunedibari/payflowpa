package it.nch.fwk.fo.web.resources.cache;

import it.nch.fwk.fo.web.resources.cache.exception.CacheCreateException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class CacheManager {

	private static HashMap cacheMap = new HashMap();

	private CacheManager() {
	}

	public static Cache getCache(String cacheName) {
		if (cacheMap == null) {
			cacheMap = new HashMap();
		}
		return (Cache) cacheMap.get(cacheName);
	}

	public static Cache getCache(CacheConfiguration config)
			throws CacheCreateException {
		if (cacheMap == null) {
			cacheMap = new HashMap();
		}
		if (cacheMap.get(config.getCacheName()) == null) {
			return createCache(config);
		}
		return (Cache) cacheMap.get(config.getCacheName());
	}

	public static Cache createCache(CacheConfiguration config)
			throws CacheCreateException {
		Cache cache = null;
		synchronized (cacheMap) {
			if (cacheMap == null) {
				cacheMap = new HashMap();
			}
			try {
				Object obj = Class.forName(config.getCacheType()).newInstance();
				cache = ((Cache) obj).configure(config);
				removeCache(config.getCacheName());
				cacheMap.put(config.getCacheName(), cache);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CacheCreateException(e);
			}
		}
		return cache;
	}

	public static void removeCache(String cacheName) {
		cacheMap.remove(cacheName);
	}

	public static void removeCache(CacheConfiguration config) {
		cacheMap.remove(config.getCacheName());
	}

	public static void resetAllCaches() {
		Set s = cacheMap.keySet();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			((Cache) cacheMap.get(iter.next())).reset();
		}
	}
}