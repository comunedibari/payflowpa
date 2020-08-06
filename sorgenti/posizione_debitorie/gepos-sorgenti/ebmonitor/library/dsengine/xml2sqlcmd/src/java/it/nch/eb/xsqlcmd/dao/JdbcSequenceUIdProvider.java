/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dao;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * @author gdefacci
 */
public abstract class JdbcSequenceUIdProvider implements UIdProvider {
	
	public long nextVal(Connection conn) {
		String stm = createFetchNextValStatement();
		try {
			ResultSet rs = conn.createStatement().executeQuery(stm);
			if (rs.next()) return rs.getLong(1);
			else throw new RuntimeException("error executing \n" + stm);
		} catch (Exception e) {
			throw new RuntimeException("error executing \n" + stm, e);
		}

	}

	public String stringNextVal(Connection conn) {
		return String.valueOf(nextVal(conn));
	}

	protected abstract String createFetchNextValStatement();

}
