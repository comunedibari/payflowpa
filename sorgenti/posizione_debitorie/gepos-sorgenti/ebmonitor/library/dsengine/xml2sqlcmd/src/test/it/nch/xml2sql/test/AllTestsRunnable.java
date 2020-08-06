package it.nch.xml2sql.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdefacci
 */
final class AllTestsRunnable {
	private final FullDbTest[] tests;
	AllTestsRunnable(FullDbTest[] tests) {
		this.tests = tests;
	}

	public TestOutcome run() {
		long now = System.currentTimeMillis();
		final List/*<FullDbTest>*/ successTests = new ArrayList();
		final List/*<FailingTest>*/ failTests = new ArrayList();

		for (int i = 0; i < tests.length; i++) {
			FullDbTest tst = tests[i];
			try {
				tst.getRun().run();
				successTests.add(tst);
			} catch (Throwable e) {
				e.printStackTrace();
				failTests.add(new FailingTest(tst, e));
			}
		}
		return new TestOutcome(successTests, failTests, System.currentTimeMillis() - now);
	}

}