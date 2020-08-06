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
package it.tasgroup.idp.rctEnti;


/**
 * @author Battaglil
 *
 * Mapping del messaggio di rct enti
 * 
 */
public class RendicontazioneContoModel  {
	
	//chiave
	private String idRendicontoEnte;
	//rif. ibanAccredito
	private String ibanAccredito;
	private String importoTotaleAccredito;
	//rif. DettaglioImportoTotaleAccredito
	private String importoVoceAccredito;
	private String capitoloBilancio;
	//rif. AccreditoPagamento
		private String idPendenza;
		private String tipoPendenza;
		private String idPagamento;
		private String canalePagamento;
		private String descrizioneCanale;
		private String idDestinatario;
		private String descrizioneDestinatario;
		private String idPagante;
		private String descrizionePagante;
		//rif. rifAccredito
		private String dataOperazione;
		private String dataValuta;
		private String causale;
		private String riferimentoBanca;
		private String importoRendicontoConto;
		//rif. DettaglioImporto
		private String codiceVoce;
		private String descrizioneVoce;
		private String importoVoce;
		private String capitoloBilancioVoce;
		private String accertamentoVoce;
	//rif. note
	private String note;
	
	public String getIdRendicontoEnte() {
		return idRendicontoEnte;
	}
	public void setIdRendicontoEnte(String idRendicontoEnte) {
		this.idRendicontoEnte = idRendicontoEnte;
	}
	public String getIbanAccredito() {
		return ibanAccredito;
	}
	public void setIbanAccredito(String ibanAccredito) {
		this.ibanAccredito = ibanAccredito;
	}
	public String getImportoTotaleAccredito() {
		return importoTotaleAccredito;
	}
	public void setImportoTotaleAccredito(String importoTotaleAccredito) {
		this.importoTotaleAccredito = importoTotaleAccredito;
	}
	public String getImportoVoceAccredito() {
		return importoVoceAccredito;
	}
	public void setImportoVoceAccredito(String importoVoceAccredito) {
		this.importoVoceAccredito = importoVoceAccredito;
	}
	public String getCapitoloBilancio() {
		return capitoloBilancio;
	}
	public void setCapitoloBilancio(String capitoloBilancio) {
		this.capitoloBilancio = capitoloBilancio;
	}
	public String getTipoPendenza() {
		return tipoPendenza;
	}
	public void setTipoPendenza(String tipoPendenza) {
		this.tipoPendenza = tipoPendenza;
	}
	public String getDescrizioneCanale() {
		return descrizioneCanale;
	}
	public void setDescrizioneCanale(String descrizioneCanale) {
		this.descrizioneCanale = descrizioneCanale;
	}
	public String getIdDestinatario() {
		return idDestinatario;
	}
	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}
	public String getDescrizioneDestinatario() {
		return descrizioneDestinatario;
	}
	public void setDescrizioneDestinatario(String descrizioneDestinatario) {
		this.descrizioneDestinatario = descrizioneDestinatario;
	}
	public String getDataOperazione() {
		return dataOperazione;
	}
	public void setDataOperazione(String dataOperazione) {
		this.dataOperazione = dataOperazione;
	}
	public String getDataValuta() {
		return dataValuta;
	}
	public void setDataValuta(String dataValuta) {
		this.dataValuta = dataValuta;
	}
	public String getRiferimentoBanca() {
		return riferimentoBanca;
	}
	public void setRiferimentoBanca(String riferimentoBanca) {
		this.riferimentoBanca = riferimentoBanca;
	}
	public String getImportoRendicontoConto() {
		return importoRendicontoConto;
	}
	public void setImportoRendicontoConto(String importoRendicontoConto) {
		this.importoRendicontoConto = importoRendicontoConto;
	}
	public String getCodiceVoce() {
		return codiceVoce;
	}
	public void setCodiceVoce(String codiceVoce) {
		this.codiceVoce = codiceVoce;
	}
	public String getCapitoloBilancioVoce() {
		return capitoloBilancioVoce;
	}
	public void setCapitoloBilancioVoce(String capitoloBilancioVoce) {
		this.capitoloBilancioVoce = capitoloBilancioVoce;
	}
	public String getAccertamentoVoce() {
		return accertamentoVoce;
	}
	public void setAccertamentoVoce(String accertamentoVoce) {
		this.accertamentoVoce = accertamentoVoce;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public String getIdPagante() {
		return idPagante;
	}
	public void setIdPagante(String idPagante) {
		this.idPagante = idPagante;
	}
	public String getDescrizionePagante() {
		return descrizionePagante;
	}
	public void setDescrizionePagante(String descrizionePagante) {
		this.descrizionePagante = descrizionePagante;
	}
	public String getDescrizioneVoce() {
		return descrizioneVoce;
	}
	public void setDescrizioneVoce(String descrizioneVoce) {
		this.descrizioneVoce = descrizioneVoce;
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
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	public String getCanalePagamento() {
		return canalePagamento;
	}
	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}	
}

