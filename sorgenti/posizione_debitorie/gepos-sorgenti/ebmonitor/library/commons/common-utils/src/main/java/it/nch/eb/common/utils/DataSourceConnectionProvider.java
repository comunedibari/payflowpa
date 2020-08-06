/**
 * 19/giu/2009
 */
package it.nch.eb.common.utils;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * @author gdefacci
 */
public class DataSourceConnectionProvider implements ConnectionProvider {
	
	private final DataSource dataSource;
	public DataSourceConnectionProvider(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() {
		try {
			Connection conn = dataSource.getConnection();
			return conn;
		} catch (Exception e) {
			throw new RuntimeException("error retrieving connection ");
		}
	}

}
