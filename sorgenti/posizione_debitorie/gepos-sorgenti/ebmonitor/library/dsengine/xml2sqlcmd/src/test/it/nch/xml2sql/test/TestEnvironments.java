/**
 * 05/ago/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.xsqlcmd.utils.Environment;
import it.nch.eb.xsqlcmd.utils.Environments;

/**
 * @author gdefacci
 */
public class TestEnvironments {

	protected static final TestConfigurationFactory colltasTestConfigurationFactory =
			new TestConfigurationFactory() {

				public PendenzeTestConfiguration create(Environment env) {
					return new CollTasTestConfiguration(env);
				}
			
			};	
	
	protected static final TestConfigurationFactory h2TestConfigurationFactory =
		new TestConfigurationFactory() {

			public PendenzeTestConfiguration create(Environment env) {
				return new H2TestConfiguration(env);
			}
		
		};
		
	protected static final TestConfigurationFactory mysqlTestConfigurationFactory =
		new TestConfigurationFactory() {

			public PendenzeTestConfiguration create(Environment env) {
				return new MysqlTestConfiguration(env);
			}
		
		};
		
		protected static final TestConfigurationFactory db2TestConfigurationFactory =
			new TestConfigurationFactory() {

				public PendenzeTestConfiguration create(Environment env) {
					return new DB2TestConfiguration(env);
				}
			
			};

//	public static final TestEnvironment h2 = new TestEnvironment(Environments.h2.withApplicationContextNamed("test-application-context.xml"), h2TestConfigurationFactory);
//	public static final TestEnvironment h2 = new TestEnvironment(Environments.h2, h2TestConfigurationFactory);

	public static final TestEnvironment colltas() {
		return new TestEnvironment(Environments.colltas, colltasTestConfigurationFactory);
	}
	
	public static final TestEnvironment colltas(String appCtxNm) {
		return new TestEnvironment(Environments.colltas.withApplicationContextNamed(appCtxNm), colltasTestConfigurationFactory);
	}
	
	public static final TestEnvironment db2() {
		return new TestEnvironment(Environments.db2, db2TestConfigurationFactory);
	}
	
	public static final TestEnvironment db2(String appCtxNm) {
		return new TestEnvironment(Environments.db2.withApplicationContextNamed(appCtxNm), db2TestConfigurationFactory);
	}	
			
	public static final TestEnvironment h2() {
		return new TestEnvironment(Environments.h2, h2TestConfigurationFactory);
	}
	
	public static final TestEnvironment h2(String appCtxNm) {
		return new TestEnvironment(Environments.h2.withApplicationContextNamed(appCtxNm), h2TestConfigurationFactory);
	}
	
	public static final TestEnvironment mysql() {
		return new TestEnvironment(Environments.mysql, mysqlTestConfigurationFactory);
	}
	
	public static final TestEnvironment mysql(String appCtxNm) {
		return new TestEnvironment(Environments.mysql.withApplicationContextNamed(appCtxNm), mysqlTestConfigurationFactory);
	}
	
	public static final TestEnvironment centos() {
		Environment env = Environments.newEnvironment("centos");
		return new TestEnvironment(env, db2TestConfigurationFactory);
	}
	
	public static final TestEnvironment centos(String appCtxNm) {
		Environment h2_iris_env = Environments.newEnvironment("centos");
		return new TestEnvironment(h2_iris_env.withApplicationContextNamed(appCtxNm), h2TestConfigurationFactory);
	}

//	private static Environment h2_iris_env = Environments.newEnvironment("h2_iris");
	
//	public static final TestEnvironment h2_iris = new TestEnvironment(h2_iris_env, h2TestConfigurationFactory);
//	public static final TestEnvironment db2 = new TestEnvironment(Environments.db2, db2TestConfigurationFactory);
//	public static final TestEnvironment mysql = new TestEnvironment(Environments.mysql.withApplicationContextNamed("test-application-context.xml"), mysqlTestConfigurationFactory);
//	public static final TestEnvironment mysql = new TestEnvironment(Environments.mysql, mysqlTestConfigurationFactory);
}
