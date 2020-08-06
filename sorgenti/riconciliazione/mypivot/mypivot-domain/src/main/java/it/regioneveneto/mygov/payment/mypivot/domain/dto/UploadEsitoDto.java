package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class UploadEsitoDto {

	private String esito;
	private String nomeFile;
	private Long dimensioneFile;
	private String codRequestToken;

	private String cod_identificativo_flusso;
	private String dt_data_ora_flusso;
	private String cod_identificativo_univoco_regolamento;
	private String dt_data_regolamento;
	private String num_importo_totale_pagamenti;
	
	private String de_anno_bolletta;
	private String cod_bolletta;
	private String dt_contabile;
	private String de_denominazione;
	private String de_causale;
	private String num_importo;
	private String dt_valuta;

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Long getDimensioneFile() {
		return dimensioneFile;
	}

	public void setDimensioneFile(Long dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}

	public String getCodRequestToken() {
		return codRequestToken;
	}

	public void setCodRequestToken(String codRequestToken) {
		this.codRequestToken = codRequestToken;
	}

	public String getCod_identificativo_flusso() {
		return cod_identificativo_flusso;
	}

	public void setCod_identificativo_flusso(String cod_identificativo_flusso) {
		this.cod_identificativo_flusso = cod_identificativo_flusso;
	}

	public String getDt_data_ora_flusso() {
		return dt_data_ora_flusso;
	}

	public void setDt_data_ora_flusso(String dt_data_ora_flusso) {
		this.dt_data_ora_flusso = dt_data_ora_flusso;
	}

	public String getCod_identificativo_univoco_regolamento() {
		return cod_identificativo_univoco_regolamento;
	}

	public void setCod_identificativo_univoco_regolamento(String cod_identificativo_univoco_regolamento) {
		this.cod_identificativo_univoco_regolamento = cod_identificativo_univoco_regolamento;
	}

	public String getDt_data_regolamento() {
		return dt_data_regolamento;
	}

	public void setDt_data_regolamento(String dt_data_regolamento) {
		this.dt_data_regolamento = dt_data_regolamento;
	}

	public String getNum_importo_totale_pagamenti() {
		return num_importo_totale_pagamenti;
	}

	public void setNum_importo_totale_pagamenti(String num_importo_totale_pagamenti) {
		this.num_importo_totale_pagamenti = num_importo_totale_pagamenti;
	}

	public String getDe_anno_bolletta() {
		return de_anno_bolletta;
	}

	public void setDe_anno_bolletta(String de_anno_bolletta) {
		this.de_anno_bolletta = de_anno_bolletta;
	}

	public String getCod_bolletta() {
		return cod_bolletta;
	}

	public void setCod_bolletta(String cod_bolletta) {
		this.cod_bolletta = cod_bolletta;
	}

	public String getDt_contabile() {
		return dt_contabile;
	}

	public void setDt_contabile(String dt_contabile) {
		this.dt_contabile = dt_contabile;
	}

	public String getDe_denominazione() {
		return de_denominazione;
	}

	public void setDe_denominazione(String de_denominazione) {
		this.de_denominazione = de_denominazione;
	}

	public String getDe_causale() {
		return de_causale;
	}

	public void setDe_causale(String de_causale) {
		this.de_causale = de_causale;
	}

	public String getNum_importo() {
		return num_importo;
	}

	public void setNum_importo(String num_importo) {
		this.num_importo = num_importo;
	}

	public String getDt_valuta() {
		return dt_valuta;
	}

	public void setDt_valuta(String dt_valuta) {
		this.dt_valuta = dt_valuta;
	}

}
