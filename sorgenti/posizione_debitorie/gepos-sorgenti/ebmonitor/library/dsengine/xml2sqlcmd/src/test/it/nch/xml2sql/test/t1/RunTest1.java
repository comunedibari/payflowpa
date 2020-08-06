/**
 * 15/giu/2009
 */
package it.nch.xml2sql.test.t1;

import it.nch.xml2sql.test.ClasspathTestRunner;
import it.nch.xml2sql.test.FailingTest;
import it.nch.xml2sql.test.FullDbTest;
import it.nch.xml2sql.test.PendenzeTestConfiguration;
import it.nch.xml2sql.test.TestEnvironments;
import it.nch.xml2sql.test.TestModes;
import it.nch.xml2sql.test.TestOutcome;

/**
 * @author gdefacci
 */
public class RunTest1 {
	
	public static void main(String[] args) {
//		PendenzeTestConfiguration testConfig = MysqlTestConfiguration.instance;
		PendenzeTestConfiguration testConfig = TestEnvironments.mysql().getTestConfiguration();
		ClasspathTestRunner runner = new ClasspathTestRunner(
				TestModes.VERIFY,
				testConfig, 
				"resources");

		// runner.compareOptions().setQuery(testConfig.getTableNames().getDestinatari(),
		// "select * from jltdepd ");

		TestOutcome outcome = runner.runTests("testsv3");
		
		if (outcome.getFailTests().length > 0) {
			System.out.println("failing tests");
			FailingTest[] ftest = outcome.getFailTests();
			for (int i = 0; i < ftest.length; i++) {
				FailingTest failingTest = ftest[i];
				System.out.println(failingTest.toString());
			}
		} else {
			System.out.println("All tests completed sucessfully");
			FullDbTest[] sTests = outcome.getSuccessTests();
			for (int i = 0; i < sTests.length; i++) {
				FullDbTest fullDbTest = sTests[i];
				System.out.println(fullDbTest);
			}
		}
		System.out.println("time elapsed : " + outcome.getSpentTime() + "ms.");

	}
	
}
