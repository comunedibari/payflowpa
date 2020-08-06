/**
 * Created on 28/set/07
 */
package it.nch.eb.flatx.files.tests.inputfileimpl;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author gdefacci
 */
public class CachedInputFileSuite extends TestCase {
		
	private static Class[]	testsClasses = new Class[] {
		it.nch.eb.flatx.files.tests.inputfileimpl.CachedInputFileBorderTests.class,
		it.nch.eb.flatx.files.tests.inputfileimpl.InputFileTest.class,
		it.nch.eb.flatx.files.tests.inputfileimpl.InputFileTest1.class,
		LineNumberInputFileTest.class,
	};

	public static Test suite() { 
		String suiteDescription = getSuiteDescription();
		TestSuite testSuite = new TestSuite(suiteDescription);
		for (int i = 0; i < testsClasses.length; i++) {
			Class testClass = testsClasses[i];
			testSuite.addTestSuite(testClass);
		}
		return testSuite;
	}

	private static String getSuiteDescription() {
		return CachedInputFileSuite.class.getName() + " : " + new Date();
	}

}
