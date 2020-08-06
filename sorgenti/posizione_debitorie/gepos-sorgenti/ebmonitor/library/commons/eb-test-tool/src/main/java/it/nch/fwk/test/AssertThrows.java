/**
 * Created on 10/dic/07
 */
package it.nch.fwk.test;

import junit.framework.AssertionFailedError;

/**
 * @author gdefacci
 */
public class AssertThrows {
	final Class expecetedExcpetionClass;
	
	public AssertThrows(Class klass) {
		assertIsException(klass);
		this.expecetedExcpetionClass = klass;
	}
	
	public void assertIsException(Class klass) {
		if (klass==null) throw new NullPointerException();	// early null detection
		if (klass.equals(Exception.class)) return;
		if (klass.isAssignableFrom(Exception.class)) throw new IllegalStateException("expeceting a subclass of Exception but got " + klass.getName());
	}
	
	public void shouldFail(Testable task) {
		if (task==null) throw new NullPointerException(); // early null detection
		try {
			task.test();
			throw new AssertionFailedError("Expeceting an exception " + expecetedExcpetionClass.getName() + " but a non exception has been thrown");
		} catch (Throwable e) {		// throwable is correct: we want to catch even the OutOfMemoryError & the co.
			if (AssertionFailedError.class.isInstance(e)) {
				throw (AssertionFailedError)e;
			}
			if (!expecetedExcpetionClass.isInstance(e)) {
				throw new AssertionFailedError("Expeceting an " + expecetedExcpetionClass.getName() + " but a " + e.getClass().getName() + " has been thrown");
			}
		}
	}
//	shortcut
	public static void shouldFail(Class/*<T extends Exception>*/ klass, Testable task) {
		if (klass==null || task==null) throw new NullPointerException(); // early null detection
		new AssertThrows(klass).shouldFail(task);
	}
}