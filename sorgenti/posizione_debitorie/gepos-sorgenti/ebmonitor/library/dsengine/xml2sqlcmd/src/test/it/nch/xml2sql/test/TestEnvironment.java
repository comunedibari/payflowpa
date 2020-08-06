/**
 * 05/ago/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.xsqlcmd.utils.Environment;

/**
 * @author gdefacci
 */
public class TestEnvironment extends Environment {

	private final PendenzeTestConfiguration testConfiguration;
	public TestEnvironment(Environment delegate,
			TestConfigurationFactory testConfigurationFactory) {
		this(delegate, testConfigurationFactory.create(delegate), delegate.getApplicationContextName());
	}
	
	public TestEnvironment(Environment delegate,
			TestConfigurationFactory testConfigurationFactory,
			String appCtxNm) {
		this(delegate, testConfigurationFactory.create(delegate), appCtxNm);
	}
	
	public TestEnvironment(Environment delegate,
			PendenzeTestConfiguration testConfiguration,
			String appCtxNm) {
		super(delegate.getName(), delegate.getFolder(), appCtxNm, delegate.getLoader());
		this.testConfiguration = testConfiguration;
	}

	public PendenzeTestConfiguration getTestConfiguration() {
		return testConfiguration;
	}

	public Environment withApplicationContextNamed(String newName) {
		return new TestEnvironment(this, testConfiguration, newName);
	}
}
