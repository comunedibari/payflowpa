/**
 * 26/giu/2009
 */
package it.nch.xml2sql.test;

public class FailingTest {
	private final FullDbTest test;
	private final Throwable cause;
	public FailingTest(FullDbTest test, Throwable cause) {
		this.test = test;
		this.cause = cause;
	}
	public FullDbTest getTest() {
		return test;
	}
	public Throwable getCause() {
		return cause;
	}
	public String toString() {
		return "test " + test.toString() + " failed cause : " + cause.getMessage();
	}
	
}