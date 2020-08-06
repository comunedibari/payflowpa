/**
 * 26/feb/2010
 */
package it.nch.eb.xsqlcmd.jms;

/**
 * @author gdefacci
 */
public class Lazy<T> implements Function0<T>{

	private T cache;
	private final Function0<T> f;
	
	public Lazy(Function0<T> f) {
		this.f = f;
	}
	
	public static <T> Lazy<T> lazy(Function0<T> f) {
		return new Lazy<T>(f);
	}

	private synchronized T init() {
		if (cache == null) {
			cache = f.apply();
		}
		return cache;
	}
	
	public T apply() {
		return init();
	}

}
