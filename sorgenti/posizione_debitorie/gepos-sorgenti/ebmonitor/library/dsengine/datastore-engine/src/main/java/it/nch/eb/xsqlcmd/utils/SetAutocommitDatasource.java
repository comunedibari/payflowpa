/**
 * 27/ago/2009
 */
package it.nch.eb.xsqlcmd.utils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * @author gdefacci
 */
public class SetAutocommitDatasource implements DataSource {
	
	private final DataSource delegate;
	private final boolean autocommit;

	public SetAutocommitDatasource(DataSource delegate, boolean autocommit) {
		this.delegate = delegate;
		this.autocommit = autocommit;
	}

	protected Connection configured(Connection conn) {
		try {
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			throw new RuntimeException("could not configure", e);
		}
		return conn;
	}
	
	public Connection getConnection() throws SQLException {
		return configured( delegate.getConnection() );
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return configured( delegate.getConnection(username, password) );
	}

	public int getLoginTimeout() throws SQLException {
		return delegate.getLoginTimeout();
	}

	public PrintWriter getLogWriter() throws SQLException {
		return delegate.getLogWriter();
	}


	public void setLoginTimeout(int seconds) throws SQLException {
		delegate.setLoginTimeout(seconds);
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		delegate.setLogWriter(out);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return delegate.isWrapperFor(iface);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return delegate.unwrap(iface);
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
