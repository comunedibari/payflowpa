/**
 * 26/mag/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.xsqlcmd.utils.Environment;
import it.nch.eb.xsqlcmd.utils.SpringXml2SqlConfiguration;

import java.sql.Connection;

import org.dbunit.database.DatabaseConnection;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author gdefacci
 */
public class DB2TestConfiguration extends SpringXml2SqlConfiguration implements PendenzeTestConfiguration {
	
	public DB2TestConfiguration(Environment env) {
		super(env);
	}
	protected DB2TestConfiguration(BeanFactory bf) {
		super(bf);
	}
	
	public DatabaseConnection create(Connection conn) {
		try {
			return new org.dbunit.ext.mysql.MySqlConnection(conn, this.getSchemaName());
		} catch (Exception e) {
			throw new RuntimeException("error wrapping dbunit connection", e);
		}
	}
	
	public String getSchemaName() {
		return "idp";
	}
	
	public synchronized void initializeDB() {
	}

}
