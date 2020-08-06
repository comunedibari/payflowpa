/**
 * 13/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.model;

/**
 * @author gdefacci
 */
public interface TableCancellableTimestamps extends TableTimestamps {
	
	public java.lang.String getOpAnnullamento();
	public void setOpAnnullamento(java.lang.String opAnnullamento);
	public java.sql.Timestamp getTsAnnullamento();
	public void setTsAnnullamento(java.sql.Timestamp tsAnnullamento);
	
}
