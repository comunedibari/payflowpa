/**
 * 14/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import java.util.Map;

/**
 * @author gdefacci
 */
public interface TableNames {
	
	static final TableNames locical = 
		new BaseTableNames("ALLINEAMENTI",
			"PENDENZE", "DESTINATARI", "CONDIZIONI_PAGAMENTO", "VOCI_BILANCIO",
			"ALLEGATO", "FLUSSI", "ESITI_PENDENZA", "ERRORI_ESITI_PENDENZA", "PENDENZE_CART");

	String getAllineamenti();
	String getPendenze();
	String getDestinatari();
	String getCondizioniPagamento();
	String getVociBilancio();
	String getAllegato();
	String getFlussi();
	String getEsitiPendenza();
	String getErroriEsitiPendenza();
	String getPendenzeCart();
	
	public String[] getAllTableNames();

	public Map entries();
	
}