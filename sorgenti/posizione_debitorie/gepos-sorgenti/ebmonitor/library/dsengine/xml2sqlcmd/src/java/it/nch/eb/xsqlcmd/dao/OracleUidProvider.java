/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dao;


/**
 * @author gdefacci
 */
public class OracleUidProvider extends JdbcSequenceUIdProvider {
	
	private final String sequenceName;
	
	public OracleUidProvider(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	/* @Override */
	protected String createFetchNextValStatement() {
		return "select " + sequenceName + ".nextval from dual ";
//		select JLTPEND_ID_PENDENZA_SEQ.nextval from dual
	}

}
