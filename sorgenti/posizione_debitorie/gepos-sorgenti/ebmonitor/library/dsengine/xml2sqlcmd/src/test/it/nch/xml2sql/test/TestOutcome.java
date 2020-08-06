/**
 * 26/giu/2009
 */
package it.nch.xml2sql.test;


import java.util.List;

public class TestOutcome {
	final List/*<FullDbTest>*/ successTests;
	final List/*<FullDbTest>*/ failTests;
	final long spentTime;
	public TestOutcome(List successTests, List failTests, long spentTime) {
		super();
		this.successTests = successTests;
		this.failTests = failTests;
		this.spentTime = spentTime;
	}
	public FullDbTest[] getSuccessTests() {
		return (FullDbTest[]) successTests.toArray(new FullDbTest[0]);
	}
	public FailingTest[] getFailTests() {
		return (FailingTest[]) failTests.toArray(new FailingTest[0]);
	}
	public long getSpentTime() {
		return spentTime;
	}
	
	
}