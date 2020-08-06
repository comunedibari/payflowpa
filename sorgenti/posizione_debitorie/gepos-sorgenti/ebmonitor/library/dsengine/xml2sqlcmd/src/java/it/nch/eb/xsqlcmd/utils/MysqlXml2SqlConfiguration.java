/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.utils;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gdefacci
 */
public class MysqlXml2SqlConfiguration extends SpringXml2SqlConfiguration {
	
	protected static final String APPLICATION_CONTEXT_CLASSAPATH_LOCATION =  "test/assembly/mysql/mysql-application-context.xml";
	
	public static final Xml2SqlAppConfiguration instance = new MysqlXml2SqlConfiguration();
	
	public MysqlXml2SqlConfiguration() {
		this(new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_CLASSAPATH_LOCATION));
	}
	
	public MysqlXml2SqlConfiguration(BeanFactory bf) {
		super(bf);
	}
	
	public Connection getConnection() {
		try {
			DataSource ds = getDataSource();
			Connection connection = ds.getConnection();
			connection.setAutoCommit(false);
			return connection;
		} catch (Exception e) {
			throw new RuntimeException("errror retieving connection ", e);
		} 
	}
	
}
