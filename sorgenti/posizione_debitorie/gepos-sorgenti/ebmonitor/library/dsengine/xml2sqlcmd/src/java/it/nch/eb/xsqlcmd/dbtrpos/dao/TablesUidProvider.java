/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.dao.UIdProvider;

/**
 * @author gdefacci
 */
public interface TablesUidProvider {
	
	public UIdProvider getAllegato();
	public UIdProvider getAllineamenti();
	public UIdProvider getCondizioniPagamento();
	public UIdProvider getDestinatari();
	public UIdProvider getFlussi();
	public UIdProvider getPendenze();
	public UIdProvider getVociBilancio();
	public UIdProvider getTributiStrutturati();
	
	public UIdProvider getEsitiPendenza();
	public UIdProvider getErroriEsitiPendenza();
	
	
}
