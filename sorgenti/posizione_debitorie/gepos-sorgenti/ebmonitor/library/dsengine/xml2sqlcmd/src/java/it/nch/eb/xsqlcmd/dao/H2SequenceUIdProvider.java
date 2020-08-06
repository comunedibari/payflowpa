/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dao;


/**
 * @author gdefacci
 */
public class H2SequenceUIdProvider extends JdbcSequenceUIdProvider {
	
	private final String sequenceName;

	public H2SequenceUIdProvider(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	protected String createFetchNextValStatement() {
		return "select " + sequenceName + ".nextVal";
	}

}
