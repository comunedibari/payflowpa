/*
 * Created on 5-giu-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.pagamenti;

import it.tasgroup.services.util.enumeration.EnumTipoStrumentoDiPagamento;

import java.io.Serializable;

/**
 * @author Simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MezzoDiPagamentoVO implements Serializable {
	
	public static final String CREDITCARD = EnumTipoStrumentoDiPagamento.CREDITCARD.getChiave(); 
	public static final String RID = EnumTipoStrumentoDiPagamento.RID.getChiave(); 
	public static final String BONIFICO = EnumTipoStrumentoDiPagamento.BONIFICO.getChiave(); 
	public static final String BOLLETTINO = EnumTipoStrumentoDiPagamento.DDP.getChiave(); 
	public static final String MULTA = EnumTipoStrumentoDiPagamento.MULTA.getChiave(); 
	public static final String WEB = EnumTipoStrumentoDiPagamento.WEB.getChiave(); 
	public static final String BOLLETTINO_FRECCIA = EnumTipoStrumentoDiPagamento.BOLLETTINOFRECCIA.getChiave();
	
	private String descrizione;
	private String codice;
	private String note;

	/**
	 * 
	 */
	public MezzoDiPagamentoVO(String codice, String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public MezzoDiPagamentoVO(String codice, String descrizione, String note) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
		this.note = note;
	}

	/**
	 * @return
	 */
	public String getCodice() {
		return codice;
	}

	/**
	 * @return
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param string
	 */
	public void setCodice(String string) {
		codice = string;
	}

	/**
	 * @param string
	 */
	public void setDescrizione(String string) {
		descrizione = string;
	}

	/**
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param string
	 */
	public void setNote(String string) {
		note = string;
	}

}
