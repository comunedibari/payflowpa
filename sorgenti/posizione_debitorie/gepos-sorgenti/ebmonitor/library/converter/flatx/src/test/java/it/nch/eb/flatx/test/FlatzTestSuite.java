/**
 * Created on 05/lug/07
 */
package it.nch.eb.flatx.test;

import it.nch.eb.flatx.files.tests.inputfileimpl.LineNumberInputFileTest;
import it.nch.eb.flatx.files.tokeniners.tests.DelimitedTokenizerTest;
import it.nch.eb.flatx.files.tokeniners.tests.ReaderTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * smoke test suite
 * @author gdefacci
 */
public class FlatzTestSuite extends TestCase {
	
	private static Class[]	testsClasses = new Class[] {
		DelimitedTokenizerTest.class,
		ReaderTest.class,
		it.nch.eb.flatx.files.tests.inputfileimpl.CachedInputFileBorderTests.class,
		it.nch.eb.flatx.files.tests.inputfileimpl.InputFileTest.class,
		it.nch.eb.flatx.files.tests.inputfileimpl.InputFileTest1.class,
		it.nch.eb.flatx.files.tests.inputfileimpl.InputFileWithBufferTest1.class,
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
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		String suiteDescription = FlatzTestSuite.class.getName() + " suite " + dateFormat.format(new Date());
		return suiteDescription;
	}

}
