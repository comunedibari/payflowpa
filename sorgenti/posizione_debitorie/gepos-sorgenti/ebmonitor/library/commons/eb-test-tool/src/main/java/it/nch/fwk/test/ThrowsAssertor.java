/**
 * Created on 09/lug/07
 */
package it.nch.fwk.test;

import junit.framework.AssertionFailedError;

/**
 * @author gdefacci
 */
public class ThrowsAssertor {
	private Class	expecetedExcpetionClass;
	public ThrowsAssertor(Class klass) {
		this.expecetedExcpetionClass = klass;
	}
	public void shouldFail(Testable task) {
		try {
			task.test();
			throw new AssertionFailedError("Expeceting an exception " + expecetedExcpetionClass.getName() + " but a non exception has been thrown");
		} catch (Throwable e) { // throwable is correct: we want to catch even the OutOfMemoryError & the co.
			if (AssertionFailedError.class.isInstance(e)) {
				throw (AssertionFailedError) e;
			}
			if (!expecetedExcpetionClass.isInstance(e)) {
				throw new AssertionFailedError("Expeceting an " + expecetedExcpetionClass.getName() + " but a " + e.getClass().getName() + " has been thrown");
			}
		}
	}
	public static void assertThrow(Class exceptionClass, Testable task) {
		ThrowsAssertor assertor = new ThrowsAssertor(exceptionClass);
		assertor.shouldFail(task);
	}
	
}
