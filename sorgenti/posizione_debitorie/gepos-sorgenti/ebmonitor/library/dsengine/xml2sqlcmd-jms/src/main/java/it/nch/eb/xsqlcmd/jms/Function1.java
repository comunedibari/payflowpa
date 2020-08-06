/**
 * 03/set/2009
 */
package it.nch.eb.xsqlcmd.jms;

/**
 * @author gdefacci
 */
public interface Function1<T,R> {
	R apply(T t);
}
