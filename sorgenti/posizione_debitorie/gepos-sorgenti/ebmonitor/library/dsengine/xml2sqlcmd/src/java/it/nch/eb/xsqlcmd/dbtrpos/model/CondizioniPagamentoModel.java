/**
 * 11/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.xsqlcmd.dbtrpos.gen.model.AllegatoModel;

/**
 * @author gdefacci
 */
public class CondizioniPagamentoModel extends it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel {
	
	private static final long serialVersionUID = 8590968063793692094L;
//	private AllegatoModel allegato;
	private java.util.Collection allegati;
	private java.util.Collection vociImporto;

//	public AllegatoModel getAllegato() {
//		return allegato;
//	}
//
//	public void setAllegato(AllegatoModel allegato) {
//		this.allegato = allegato;
//	}

	public java.util.Collection getVociImporto() {
		return vociImporto;
	}

	public void setVociImporto(java.util.Collection vociImporto) {
		this.vociImporto = vociImporto;
	}
	
	public java.util.Collection getAllegati() {
		return allegati;
	}

	public void setAllegati(java.util.Collection allegati) {
		this.allegati = allegati;
	}		
}
