/**
 * Created on 30/ago/07
 */
package it.nch.fwk.xml;


import it.nch.fwk.xml.tests.NamespacesAdaptingTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author gdefacci
 */
public class XmlTestSuite extends TestCase {
	
	private static Class[]	testsClasses = new Class[] {
		it.nch.fwk.xml.tests.BindingManagerTest.class,
		it.nch.fwk.xml.tests.ChildrenIteratorTest.class,
		it.nch.fwk.xml.tests.TemplateErrorsTest.class,
		NamespacesAdaptingTest.class,
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
		String suiteDescription = XmlTestSuite.class.getName() + " suite " + dateFormat.format(new Date());
		return suiteDescription;
	}
	
}
