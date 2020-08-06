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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.Allegato;


/**
 * @author Battaglil
 *
 * Mapping del messaggio di notifica pagamento
 * 
 */
public class PagamentoModelTyped  {
	
	// Riferimento Pagamento:
	private String tipoPagamento;
	private String idPagamento;//OK
	
	private Timestamp dataOraPagamento;//OK
	private Date dataScadenzaPagamento;//OK
	private BigDecimal importo;	//OK
	
	//  Riferimento Debitore
	private String idPagante;//OK
	
	private String esito;//OK
	
	// Pagante
	private String tipoVersante;
	private String idVersante;
	private String descrizioneVersante; // Contirne la email..
	private String emailVersante;
	
	
	// Transazione
	private String tipoCanalePagamento; //OK
	private String descrizioneCanalePagamento;
	private String mezzoPagamento; //OK
	private String idTransazione;//OK
	private Timestamp dataOraTransazione;//OK
	private String  codiceAutorizzazione; 
	private Timestamp dataOraAutorizzazione;//OK
	
	// Transazione.importo transato
	private BigDecimal importoTransato;
	private BigDecimal importoVoce;//OK
	private BigDecimal importoVoceCommissioni;//OK
	
	// Riferimento Debito
	private String tipoDebito;//OK
	private String idPendenzaEnte;//OK
	
	private String deCausale;//OK
	private boolean flagCausale;//OK	
	
	private String iuv; 
	
	//chiave
	private String idNotifica;	
	private String idPendenza;
	
	
	
	private String idPsp;
	private String idCanale;
	private String descrizionePsp;
	private String tipoVersamento;

	
	// Dati incasso [per notifiche nuove]
	private String flagIncasso;
	private String idPspTransazioneIncasso;
	private String idFlussoIncasso;
	private Date  dataEsecuzioneIncasso;
	private BigDecimal totaleTransazioneIncasso;
	private String trnIncasso; 
	
	
	//Dati pagamento [per notifiche nuove]
	private String idPspTransazionePagamento;
	private String idCanaleTransazionePagamento;
	private String tipoVersamentoTransazionePagamento;
	private String descrizioneCanaleTransazionePagamento;
	private String iurTransazionePagamento;
	private Date dataOraTransazionePagamento;
	private BigDecimal importoTransazionePagamento;
	private BigDecimal importoCommissioniTransazionePagamento;
	private Long numeroPagamentiTransazionePagamento;
	
	private Allegato allegato;
	
	private Date dataOraInizioPagamento;
	

	private String riferimentoDebito;
	private String notePagamento;
	



	public void setAllegato(Allegato allegato) {
        this.allegato = allegato;
    }
	
	public Allegato getAllegato() {
        return allegato;
    }
	
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public Timestamp getDataOraPagamento() {
		return dataOraPagamento;
	}
	public void setDataOraPagamento(Timestamp dataOraPagamento) {
		this.dataOraPagamento = dataOraPagamento;
	}
	public Date getDataScadenzaPagamento() {
		return dataScadenzaPagamento;
	}
	public void setDataScadenzaPagamento(Date dataScadenzaPagamento) {
		this.dataScadenzaPagamento = dataScadenzaPagamento;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getIdPagante() {
		return idPagante;
	}
	public void setIdPagante(String idPagante) {
		this.idPagante = idPagante;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
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
	public Timestamp getDataOraTransazione() {
		return dataOraTransazione;
	}
	public void setDataOraTransazione(Timestamp dataOraTransazione) {
		this.dataOraTransazione = dataOraTransazione;
	}
	public Timestamp getDataOraAutorizzazione() {
		return dataOraAutorizzazione;
	}
	public void setDataOraAutorizzazione(Timestamp dataOraAutorizzazione) {
		this.dataOraAutorizzazione = dataOraAutorizzazione;
	}
	public BigDecimal getImportoTransato() {
		return importoTransato;
	}
	public void setImportoTransato(BigDecimal importoTransato) {
		this.importoTransato = importoTransato;
	}
	public BigDecimal getImportoVoce() {
		return importoVoce;
	}
	public void setImportoVoce(BigDecimal importoVoce) {
		this.importoVoce = importoVoce;
	}
	public BigDecimal getImportoVoceCommissioni() {
		return importoVoceCommissioni;
	}
	public void setImportoVoceCommissioni(BigDecimal importoVoceCommissioni) {
		this.importoVoceCommissioni = importoVoceCommissioni;
	}
	public String getTipoDebito() {
		return tipoDebito;
	}
	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}
	public String getDeCausale() {
		return deCausale;
	}
	public void setDeCausale(String deCausale) {
		this.deCausale = deCausale;
	}
	public boolean isFlagCausale() {
		return flagCausale;
	}
	public void setFlagCausale(boolean flagCausale) {
		this.flagCausale = flagCausale;
	}
	public String getIdNotifica() {
		return idNotifica;
	}
	public void setIdNotifica(String idNotifica) {
		this.idNotifica = idNotifica;
	}
	public String getIdPendenza() {
		return idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
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
	public String getIdPsp() {
		return idPsp;
	}
	public void setIdPsp(String idPsp) {
		this.idPsp = idPsp;
	}
	public String getIdCanale() {
		return idCanale;
	}
	public void setIdCanale(String idCanale) {
		this.idCanale = idCanale;
	}
	public String getDescrizionePsp() {
		return descrizionePsp;
	}
	public void setDescrizionePsp(String descrizionePsp) {
		this.descrizionePsp = descrizionePsp;
	}
	public String getTipoVersamento() {
		return tipoVersamento;
	}
	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}
	public String getEmailVersante() {
		return emailVersante;
	}
	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}
	public String getIdPspTransazioneIncasso() {
		return idPspTransazioneIncasso;
	}
	public void setIdPspTransazioneIncasso(String idPspTransazioneIncasso) {
		this.idPspTransazioneIncasso = idPspTransazioneIncasso;
	}
	public String getIdFlussoIncasso() {
		return idFlussoIncasso;
	}
	public void setIdFlussoIncasso(String idFlussoIncasso) {
		this.idFlussoIncasso = idFlussoIncasso;
	}
	public Date getDataEsecuzioneIncasso() {
		return dataEsecuzioneIncasso;
	}
	public void setDataEsecuzioneIncasso(Date dataEsecuzioneIncasso) {
		this.dataEsecuzioneIncasso = dataEsecuzioneIncasso;
	}
	public BigDecimal getTotaleTransazioneIncasso() {
		return totaleTransazioneIncasso;
	}
	public void setTotaleTransazioneIncasso(BigDecimal totaleTransazioneIncasso) {
		this.totaleTransazioneIncasso = totaleTransazioneIncasso;
	}
	public String getTrnIncasso() {
		return trnIncasso;
	}
	public void setTrnIncasso(String trnIncasso) {
		this.trnIncasso = trnIncasso;
	}
	public String getFlagIncasso() {
		return flagIncasso;
	}
	public void setFlagIncasso(String flagIncasso) {
		this.flagIncasso = flagIncasso;
	}
	public String getIdPspTransazionePagamento() {
		return idPspTransazionePagamento;
	}
	public void setIdPspTransazionePagamento(String idPspTransazionePagamento) {
		this.idPspTransazionePagamento = idPspTransazionePagamento;
	}
	public String getIdCanaleTransazionePagamento() {
		return idCanaleTransazionePagamento;
	}
	public void setIdCanaleTransazionePagamento(String idCanaleTransazionePagamento) {
		this.idCanaleTransazionePagamento = idCanaleTransazionePagamento;
	}
	public String getTipoVersamentoTransazionePagamento() {
		return tipoVersamentoTransazionePagamento;
	}
	public void setTipoVersamentoTransazionePagamento(
			String tipoVersamentoTransazionePagamento) {
		this.tipoVersamentoTransazionePagamento = tipoVersamentoTransazionePagamento;
	}
	public String getDescrizioneCanaleTransazionePagamento() {
		return descrizioneCanaleTransazionePagamento;
	}
	public void setDescrizioneCanaleTransazionePagamento(
			String descrizioneCanaleTransazionePagamento) {
		this.descrizioneCanaleTransazionePagamento = descrizioneCanaleTransazionePagamento;
	}
	public String getIurTransazionePagamento() {
		return iurTransazionePagamento;
	}
	public void setIurTransazionePagamento(String iurTransazionePagamento) {
		this.iurTransazionePagamento = iurTransazionePagamento;
	}
	public Date getDataOraTransazionePagamento() {
		return dataOraTransazionePagamento;
	}
	public void setDataOraTransazionePagamento(Date dataOraTransazionePagamento) {
		this.dataOraTransazionePagamento = dataOraTransazionePagamento;
	}
	public BigDecimal getImportoTransazionePagamento() {
		return importoTransazionePagamento;
	}
	public void setImportoTransazionePagamento(
			BigDecimal importoTransazionePagamento) {
		this.importoTransazionePagamento = importoTransazionePagamento;
	}
	public BigDecimal getImportoCommissioniTransazionePagamento() {
		return importoCommissioniTransazionePagamento;
	}
	public void setImportoCommissioniTransazionePagamento(
			BigDecimal importoCommissioniTransazionePagamento) {
		this.importoCommissioniTransazionePagamento = importoCommissioniTransazionePagamento;
	}
	public Long getNumeroPagamentiTransazionePagamento() {
		return numeroPagamentiTransazionePagamento;
	}
	public void setNumeroPagamentiTransazionePagamento(
			Long numeroPagamentiTransazionePagamento) {
		this.numeroPagamentiTransazionePagamento = numeroPagamentiTransazionePagamento;
	}
	public Date getDataOraInizioPagamento() {
		return dataOraInizioPagamento;
	}
	public void setDataOraInizioPagamento(Date dataOraInizioPagamento) {
		this.dataOraInizioPagamento = dataOraInizioPagamento;
	}
	public String getRiferimentoDebito() {
		return riferimentoDebito;
	}
	public void setRiferimentoDebito(String riferimentoDebito) {
		this.riferimentoDebito = riferimentoDebito;
	}
	public String getNotePagamento() {
		return notePagamento;
	}
	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}
}

