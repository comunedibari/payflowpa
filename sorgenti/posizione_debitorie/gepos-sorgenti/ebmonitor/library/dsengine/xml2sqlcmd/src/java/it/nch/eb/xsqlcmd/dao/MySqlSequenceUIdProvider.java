/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dao;


/**
 * @author battaglil
 */
public class MySqlSequenceUIdProvider extends JdbcSequenceUIdProvider {
	
	private final String sequenceName;

	public MySqlSequenceUIdProvider(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	protected String createFetchNextValStatement() {
		//SELECT NEXTVAL('JLTPEND_ID_PENDENZA_SEQ')
		return "select nextval('" + sequenceName + "')";
	}

}
