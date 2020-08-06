/**
 * 26/feb/2010
 */
package it.nch.eb.xsqlcmd.utils;

/**
 * @author gdefacci
 */
public abstract class Jdk14Lazy {

	private Object cache;
	
	public final synchronized Object apply() {
		if (cache == null) {
			cache = getValue();
		}
		return cache;
	}

	protected abstract Object getValue();

}
