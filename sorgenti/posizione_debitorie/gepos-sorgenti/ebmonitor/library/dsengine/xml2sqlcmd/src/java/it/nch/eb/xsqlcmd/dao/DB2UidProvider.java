/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dao;


/**
 * @author gdefacci
 */
public class DB2UidProvider extends JdbcSequenceUIdProvider {
	
	private final String sequenceName;
	
	public DB2UidProvider(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	/* @Override */
	protected String createFetchNextValStatement() {
		return "VALUES next value for " + sequenceName;
	}

}
