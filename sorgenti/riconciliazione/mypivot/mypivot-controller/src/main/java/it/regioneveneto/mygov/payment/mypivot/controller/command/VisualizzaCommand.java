/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller.command;


/**
 * @author Giorgio Vallini
 * 
 */
public class VisualizzaCommand {

	private String codice_iud;
	private String codice_iuv;
	private String denominazione_attestante;
	private String identificativo_univoco_riscossione;
	private String codice_identificativo_univoco_versante;
	private String anagrafica_versante;
	private String codice_identificativo_univoco_pagatore;
	private String anagrafica_pagatore;
	private String causale_versamento;
	private String data_esito_singolo_pagamento_da;
	private String data_esito_singolo_pagamento_a;
	private String identificativo_flusso_rendicontazione;
	private String identificativo_univoco_regolamento;
	private String data_regolamento_da;
	private String data_regolamento_a;
	private String cod_tipo_dovuto;
	private String visualizzaNascosti; 
	private int page;
	private int pageSize;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public VisualizzaCommand() {
		super();
	}

	public String getCodice_iud() {
		return codice_iud;
	}

	public void setCodice_iud(String codice_iud) {
		this.codice_iud = codice_iud;
	}

	public String getCodice_iuv() {
		return codice_iuv;
	}

	public void setCodice_iuv(String codice_iuv) {
		this.codice_iuv = codice_iuv;
	}

	public String getDenominazione_attestante() {
		return denominazione_attestante;
	}

	public void setDenominazione_attestante(String denominazione_attestante) {
		this.denominazione_attestante = denominazione_attestante;
	}

	public String getIdentificativo_univoco_riscossione() {
		return identificativo_univoco_riscossione;
	}

	public void setIdentificativo_univoco_riscossione(
			String identificativo_univoco_riscossione) {
		this.identificativo_univoco_riscossione = identificativo_univoco_riscossione;
	}

	public String getCodice_identificativo_univoco_versante() {
		return codice_identificativo_univoco_versante;
	}

	public void setCodice_identificativo_univoco_versante(
			String codice_identificativo_univoco_versante) {
		this.codice_identificativo_univoco_versante = codice_identificativo_univoco_versante;
	}

	public String getAnagrafica_versante() {
		return anagrafica_versante;
	}

	public void setAnagrafica_versante(String anagrafica_versante) {
		this.anagrafica_versante = anagrafica_versante;
	}

	public String getCodice_identificativo_univoco_pagatore() {
		return codice_identificativo_univoco_pagatore;
	}

	public void setCodice_identificativo_univoco_pagatore(
			String codice_identificativo_univoco_pagatore) {
		this.codice_identificativo_univoco_pagatore = codice_identificativo_univoco_pagatore;
	}

	public String getAnagrafica_pagatore() {
		return anagrafica_pagatore;
	}

	public void setAnagrafica_pagatore(String anagrafica_pagatore) {
		this.anagrafica_pagatore = anagrafica_pagatore;
	}

	public String getCausale_versamento() {
		return causale_versamento;
	}

	public void setCausale_versamento(String causale_versamento) {
		this.causale_versamento = causale_versamento;
	}

	public String getData_esito_singolo_pagamento_da() {
		return data_esito_singolo_pagamento_da;
	}

	public void setData_esito_singolo_pagamento_da(
			String data_esito_singolo_pagamento_da) {
		this.data_esito_singolo_pagamento_da = data_esito_singolo_pagamento_da;
	}

	public String getData_esito_singolo_pagamento_a() {
		return data_esito_singolo_pagamento_a;
	}

	public void setData_esito_singolo_pagamento_a(
			String data_esito_singolo_pagamento_a) {
		this.data_esito_singolo_pagamento_a = data_esito_singolo_pagamento_a;
	}

	public String getIdentificativo_flusso_rendicontazione() {
		return identificativo_flusso_rendicontazione;
	}

	public void setIdentificativo_flusso_rendicontazione(
			String identificativo_flusso_rendicontazione) {
		this.identificativo_flusso_rendicontazione = identificativo_flusso_rendicontazione;
	}

	public String getIdentificativo_univoco_regolamento() {
		return identificativo_univoco_regolamento;
	}

	public void setIdentificativo_univoco_regolamento(
			String identificativo_univoco_regolamento) {
		this.identificativo_univoco_regolamento = identificativo_univoco_regolamento;
	}

	public String getData_regolamento_da() {
		return data_regolamento_da;
	}

	public void setData_regolamento_da(String data_regolamento_da) {
		this.data_regolamento_da = data_regolamento_da;
	}

	public String getData_regolamento_a() {
		return data_regolamento_a;
	}

	public void setData_regolamento_a(String data_regolamento_a) {
		this.data_regolamento_a = data_regolamento_a;
	}

	public String getCod_tipo_dovuto() {
		return cod_tipo_dovuto;
	}

	public void setCod_tipo_dovuto(String cod_tipo_dovuto) {
		this.cod_tipo_dovuto = cod_tipo_dovuto;
	}

	public String getVisualizzaNascosti() {
		return visualizzaNascosti;
	}

	public void setVisualizzaNascosti(String visualizzaNascosti) {
		this.visualizzaNascosti = visualizzaNascosti;
	}

}
