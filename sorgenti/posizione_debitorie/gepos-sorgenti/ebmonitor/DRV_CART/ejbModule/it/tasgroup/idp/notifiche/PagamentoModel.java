/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.notifiche;

import it.tasgroup.iris2.pagamenti.Pagamenti;


/**
 * @author Battaglil
 *
 * Mapping del messaggio di notifica pagamento
 * 
 */
public class PagamentoModel  {
	
	// Riferimento Pagamento:
	private String tipoPagamento;
	private String idPagamento;//OK
	
	
	private String dataOraPagamento;//OK
	private String dataScadenzaPagamento;//OK
	private String importo;	//OK
	
	//  Riferimento Debitore
	private String idPagante;//OK
	
	private String esito;//OK
	
	// Pagante
	private String tipoVersante;
	private String idVersante;
	private String descrizioneVersante;
	
	// Transazione
	private String tipoCanalePagamento; //OK
	private String descrizioneCanalePagamento;
	private String mezzoPagamento; //OK
	private String idTransazione;//OK
	private String dataOraTransazione;//OK
	private String  codiceAutorizzazione; 
	private String dataOraAutorizzazione;//OK
	
	// Transazione.importo transato
	private String importoTransato;
	private String importoVoce;//OK
	private String importoVoceCommissioni;//OK
	
	// Riferimento Debito
	private String tipoDebito;//OK
	private String idPendenzaEnte;//OK
	
	private String note;
	private String deCausale;//OK
	private boolean flagCausale;//OK	
	
	//chiave
	private String idNotifica;	
	private String idPendenza;
	
	private int versionNotifichePagamento; // contiene il valore corrente della version della riga della tabella 
	                                       // notifiche_pagamento in esame
	private String iuv;
	
	private PagamentoModelTyped pagamentoModelTyped;
	
	private String cdEnte;
	private String cdTrbEnte;
		
	public String getDeCausale() {
		return deCausale;
	}
	public void setDeCausale(String deCausale) {
		this.deCausale = deCausale;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public String getDataOraPagamento() {
		return dataOraPagamento;
	}
	public void setDataOraPagamento(String dataOraPagamento) {
		this.dataOraPagamento = dataOraPagamento;
	}
	public String getDataScadenzaPagamento() {
		return dataScadenzaPagamento;
	}
	public void setDataScadenzaPagamento(String dataScadenzaPagamento) {
		this.dataScadenzaPagamento = dataScadenzaPagamento;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getIdPagante() {
		return idPagante;
	}
	public void setIdPagante(String idPagante) {
		this.idPagante = idPagante;
	}
	public String getTipoCanalePagamento() {
		return tipoCanalePagamento;
	}
	public void setTipoCanalePagamento(String tipoCanalePagamento) {
		this.tipoCanalePagamento = tipoCanalePagamento;
	}
	public String getMezzoPagamento() {
		return mezzoPagamento;
	}
	public void setMezzoPagamento(String mezzoPagamento) {
		this.mezzoPagamento = mezzoPagamento;
	}
	public String getIdTransazione() {
		return idTransazione;
	}
	public void setIdTransazione(String idTransazione) {
		this.idTransazione = idTransazione;
	}
	public String getDataOraTransazione() {
		return dataOraTransazione;
	}
	public void setDataOraTransazione(String dataOraTransazione) {
		this.dataOraTransazione = dataOraTransazione;
	}
	public String getDataOraAutorizzazione() {
		return dataOraAutorizzazione;
	}
	public void setDataOraAutorizzazione(String dataOraAutorizzazione) {
		this.dataOraAutorizzazione = dataOraAutorizzazione;
	}
	public String getImportoTransato() {
		return importoTransato;
	}
	public void setImportoTransato(String importoTransato) {
		this.importoTransato = importoTransato;
	}
	public String getImportoVoce() {
		return importoVoce;
	}
	public void setImportoVoce(String importoVoce) {
		this.importoVoce = importoVoce;
	}
	public String getIdPendenza() {
		return idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}
	public String getTipoDebito() {
		return tipoDebito;
	}
	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}
	public String getIdNotifica() {
		return idNotifica;
	}
	public void setIdNotifica(String idNotifica) {
		this.idNotifica = idNotifica;
	}
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}
	public String getImportoVoceCommissioni() {
		return importoVoceCommissioni;
	}
	public void setImportoVoceCommissioni(String importoVoceCommissioni) {
		this.importoVoceCommissioni = importoVoceCommissioni;
	}
	public boolean isFlagCausale() {
		return flagCausale;
	}
	public void setFlagCausale(boolean flagCausale) {
		this.flagCausale = flagCausale;
	}
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public String getTipoVersante() {
		return tipoVersante;
	}
	public void setTipoVersante(String tipoVersante) {
		this.tipoVersante = tipoVersante;
	}
	public String getIdVersante() {
		return idVersante;
	}
	public void setIdVersante(String idVersante) {
		this.idVersante = idVersante;
	}
	public String getDescrizioneVersante() {
		return descrizioneVersante;
	}
	public void setDescrizioneVersante(String descrizioneVersante) {
		this.descrizioneVersante = descrizioneVersante;
	}
	public int getVersionNotifichePagamento() {
		return versionNotifichePagamento;
	}
	public void setVersionNotifichePagamento(int versionNotifichePagamento) {
		this.versionNotifichePagamento = versionNotifichePagamento;
	}
	public String getDescrizioneCanalePagamento() {
		return descrizioneCanalePagamento;
	}
	public void setDescrizioneCanalePagamento(String descrizioneCanalePagamento) {
		this.descrizioneCanalePagamento = descrizioneCanalePagamento;
	}
	public String getCodiceAutorizzazione() {
		return codiceAutorizzazione;
	}
	public void setCodiceAutorizzazione(String codiceAutorizzazione) {
		this.codiceAutorizzazione = codiceAutorizzazione;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public PagamentoModelTyped getPagamentoModelTyped() {
		return pagamentoModelTyped;
	}
	public void setPagamentoModelTyped(PagamentoModelTyped pagamentoModelTyped) {
		this.pagamentoModelTyped = pagamentoModelTyped;
	}
	public String getCdEnte() {
		return cdEnte;
	}
	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}
	
}

