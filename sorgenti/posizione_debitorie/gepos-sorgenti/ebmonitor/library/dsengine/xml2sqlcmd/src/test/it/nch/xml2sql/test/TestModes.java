/**
 * 15/giu/2009
 */
package it.nch.xml2sql.test;

/**
 * @author gdefacci
 */
public class TestModes {

	public static final TestMode VERIFY = new TestMode(1, "execute tests and verify results");
	public static final TestMode FREEZE = new TestMode(2, "execute tests and freeze results");
	public static final TestMode EXECUTE_NO_VERIFY = new TestMode(4, "execute tests but does not verify results");

}
