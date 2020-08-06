package it.nch.fwk.fo.web.resources.cache;

public interface Cache {

	public Object get(Object key);

	public void put(Object key, Object value);

	public Cache configure(CacheConfiguration config);

	public void reset();
}