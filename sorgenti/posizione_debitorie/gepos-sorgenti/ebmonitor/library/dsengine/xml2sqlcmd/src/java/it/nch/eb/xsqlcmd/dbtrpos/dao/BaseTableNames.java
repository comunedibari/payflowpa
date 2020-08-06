/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gdefacci
 */
public class BaseTableNames implements TableNames {
	
	private final String allineamenti		  	  ;
	private final String pendenze               ;
	private final String destinatari            ;
	private final String condizioniPagamento    ;
	private final String vociBilancio           ;
	private final String allegato               ;
	private final String flussi             	  ;
	private final String erroriEsitiPendenza;
	private final String esitiPendenza;
	private final String pendenzeCart;

	public BaseTableNames(String allineamenti, String pendenze,
			String destinatari, String condizioniPagamento,
			String vociBilancio, String allegato, String flussi, 
			String esitiPend, String errEsitiPend, String pendenzeCart) {
		super();
		this.allineamenti = allineamenti.trim();
		this.pendenze = pendenze.trim();
		this.destinatari = destinatari.trim();
		this.condizioniPagamento = condizioniPagamento.trim();
		this.vociBilancio = vociBilancio.trim();
		this.allegato = allegato.trim();
		this.flussi = flussi.trim();
		this.esitiPendenza = esitiPend.trim();
		this.erroriEsitiPendenza = errEsitiPend.trim();
		this.pendenzeCart = pendenzeCart.trim();
	}
	/* @Override */
	public String getAllineamenti() {
		return allineamenti;
	}
	/* @Override */
	public String getPendenze() {
		return pendenze;
	}
	/* @Override */
	public String getDestinatari() {
		return destinatari;
	}
	/* @Override */
	public String getCondizioniPagamento() {
		return condizioniPagamento;
	}
	/* @Override */
	public String getVociBilancio() {
		return vociBilancio;
	}
	/* @Override */
	public String getAllegato() {
		return allegato;
	}
	/* @Override */
	public String getFlussi() {
		return flussi;
	}
	public String getErroriEsitiPendenza() {
		return erroriEsitiPendenza;
	}
	public String getEsitiPendenza() {
		return esitiPendenza;
	}
	public String getPendenzeCart() {
		return pendenzeCart;
	}
	
	public static String[] getAllTableNames(TableNames tn) {
		String[] res = new String[] {
				tn.getAllegato(),
				tn.getAllineamenti(),
				tn.getCondizioniPagamento(),
				tn.getDestinatari(),
				tn.getEsitiPendenza(),
				tn.getErroriEsitiPendenza(),
				tn.getFlussi(),
				tn.getPendenze(),
				tn.getVociBilancio(),
				tn.getPendenzeCart(),
			};
		
		return res;
	}
	
	public Map entries() {
		Map res = new HashMap();
		String[] allTableNames = getAllTableNames();
		for (int i = 0; i < allTableNames.length; i++) {
			String tbNm = allTableNames[i];
			putEntry(res, tbNm);
		}
		return res;
	}
	
	public String[] getAllTableNames() {
		return getAllTableNames(this);
	}
	
	private void putEntry(Map map, String tabName) {
		map.put(tabName, tabName);
	}

}
