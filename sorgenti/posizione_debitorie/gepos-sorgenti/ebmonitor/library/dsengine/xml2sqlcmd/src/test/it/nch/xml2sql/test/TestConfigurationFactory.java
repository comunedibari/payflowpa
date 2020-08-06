/**
 * 05/ago/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.xsqlcmd.utils.Environment;

public interface TestConfigurationFactory {
	PendenzeTestConfiguration create(Environment env);
}