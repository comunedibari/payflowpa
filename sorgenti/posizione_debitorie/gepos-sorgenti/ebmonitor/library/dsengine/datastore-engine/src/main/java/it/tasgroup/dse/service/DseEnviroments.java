/**
 * 05/ago/2009
 */
package it.tasgroup.dse.service;

import it.nch.eb.xsqlcmd.jms.Function0;
import it.nch.eb.xsqlcmd.jms.Lazy;
import it.nch.eb.xsqlcmd.utils.Environments;
import it.nch.eb.xsqlcmd.utils.Environment;
import it.tasgroup.dse.service.SpringNames.SystemProperties;

/**
 * @author gdefacci
 */
public class DseEnviroments extends Environments {
	
	private static Environment defaultEnvironment() {
		try {
			String propValue = System.getProperty(SystemProperties.ENV_CONFIG_PROPERTY_NAME);
			if (propValue==null) throw new NullPointerException(SystemProperties.ENV_CONFIG_PROPERTY_NAME);
			Environment res = (Environment) DseEnviroments.class.getField(propValue).get(null);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("errror creating default environment " + e.getMessage(), e);
		} 
	}
	
	public static Lazy<Environment> DEFAULT_ENVIROMENT = Lazy.lazy(new Function0<Environment>() {

		public Environment apply() {
			return defaultEnvironment();
		}
		
	});
	
	public static final Environment db2 = newEnvironment("db2");
	public static final Environment prod = newEnvironment("prod");
	public static final Environment centos = newEnvironment("centos");
	public static final Environment sviluppo = newEnvironment("sviluppo");
	public static final Environment collaudo = newEnvironment("collaudo");
	public static final Environment testdb2 = newEnvironment("testdb2");
	public static final Environment ppel = newEnvironment("ppel");
	public static final Environment oracle = newEnvironment("oracle");
	public static final Environment eapOracle = newEnvironment("eapOracle");
	public static final Environment eapOracleNoJms = newEnvironment("eapOracleNoJms");
	public static final Environment eapdb2 = newEnvironment("eapdb2");
	public static final Environment eapmysql = newEnvironment("eapmysql");

}
