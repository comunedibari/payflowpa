package it.nch.fwk.fo.web.resources.cache.exception;

public class CacheCreateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 541183500039495868L;

	public CacheCreateException(String msg) {
		super(msg);
	}

	public CacheCreateException(Exception e) {
		super(e.getLocalizedMessage());
	}
}