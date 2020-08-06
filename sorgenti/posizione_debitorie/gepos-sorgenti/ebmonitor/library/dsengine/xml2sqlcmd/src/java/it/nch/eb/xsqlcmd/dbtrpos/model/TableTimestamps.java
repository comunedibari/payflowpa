/**
 * 13/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.model;

/**
 * @author gdefacci
 */
public interface TableTimestamps {
	
	public java.lang.Integer getPrVersione();
	public void setPrVersione(java.lang.Integer prVersione);
	public java.lang.String getStRiga();
	public void setStRiga(java.lang.String stRiga);
	public java.lang.String getOpInserimento();
	public void setOpInserimento(java.lang.String opInserimento);
	public java.sql.Timestamp getTsInserimento();
	public void setTsInserimento(java.sql.Timestamp tsInserimento);
	public java.lang.String getOpAggiornamento();
	public void setOpAggiornamento(java.lang.String opAggiornamento);
	public java.sql.Timestamp getTsAggiornamento();
	public void setTsAggiornamento(java.sql.Timestamp tsAggiornamento);
	
}
