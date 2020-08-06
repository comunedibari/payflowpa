package it.regioneveneto.mygov.payment.mypivot.controller.command;

import java.util.List;

public class VisualizzaFlussoRicevutaCommand {

	private Boolean dataEsitoSingoloPagamentoCheck;
	private String dataEsitoSingoloPagamentoDa;
	private String dataEsitoSingoloPagamentoA;
	private String iud;
	private String iuv;
	private String denominazioneAttestante;
	private String identificativoUnivocoRiscossione;
	private String codiceIdentificativoUnivocoPagatore;
	private String anagraficaPagatore;
	private String codiceIdentificativoUnivocoVersante;
	private String anagraficaVersante;
	private String codTipoDovuto;
	private String iuf;
	private List<String> listaIUD;
	private int page;
	private int pageSize;

	public Boolean getDataEsitoSingoloPagamentoCheck() {
		return dataEsitoSingoloPagamentoCheck;
	}

	public void setDataEsitoSingoloPagamentoCheck(Boolean dataEsitoSingoloPagamentoCheck) {
		this.dataEsitoSingoloPagamentoCheck = dataEsitoSingoloPagamentoCheck;
	}

	public String getDataEsitoSingoloPagamentoDa() {
		return dataEsitoSingoloPagamentoDa;
	}

	public void setDataEsitoSingoloPagamentoDa(String dataEsitoSingoloPagamentoDa) {
		this.dataEsitoSingoloPagamentoDa = dataEsitoSingoloPagamentoDa;
	}

	public String getDataEsitoSingoloPagamentoA() {
		return dataEsitoSingoloPagamentoA;
	}

	public void setDataEsitoSingoloPagamentoA(String dataEsitoSingoloPagamentoA) {
		this.dataEsitoSingoloPagamentoA = dataEsitoSingoloPagamentoA;
	}

	public String getIud() {
		return iud;
	}

	public void setIud(String iud) {
		this.iud = iud;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getDenominazioneAttestante() {
		return denominazioneAttestante;
	}

	public void setDenominazioneAttestante(String denominazioneAttestante) {
		this.denominazioneAttestante = denominazioneAttestante;
	}

	public String getIdentificativoUnivocoRiscossione() {
		return identificativoUnivocoRiscossione;
	}

	public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
		this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
	}

	public String getCodiceIdentificativoUnivocoPagatore() {
		return codiceIdentificativoUnivocoPagatore;
	}

	public void setCodiceIdentificativoUnivocoPagatore(String codiceIdentificativoUnivocoPagatore) {
		this.codiceIdentificativoUnivocoPagatore = codiceIdentificativoUnivocoPagatore;
	}

	public String getAnagraficaPagatore() {
		return anagraficaPagatore;
	}

	public void setAnagraficaPagatore(String anagraficaPagatore) {
		this.anagraficaPagatore = anagraficaPagatore;
	}

	public String getCodiceIdentificativoUnivocoVersante() {
		return codiceIdentificativoUnivocoVersante;
	}

	public void setCodiceIdentificativoUnivocoVersante(String codiceIdentificativoUnivocoVersante) {
		this.codiceIdentificativoUnivocoVersante = codiceIdentificativoUnivocoVersante;
	}

	public String getAnagraficaVersante() {
		return anagraficaVersante;
	}

	public void setAnagraficaVersante(String anagraficaVersante) {
		this.anagraficaVersante = anagraficaVersante;
	}

	public String getCodTipoDovuto() {
		return codTipoDovuto;
	}

	public void setCodTipoDovuto(String codTipoDovuto) {
		this.codTipoDovuto = codTipoDovuto;
	}

	public String getIuf() {
		return iuf;
	}

	public void setIuf(String iuf) {
		this.iuf = iuf;
	}

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

	/**
	 * @return the listaIUD
	 */
	public List<String> getListaIUD() {
		return listaIUD;
	}

	/**
	 * @param listaIUD2 the listaIUD to set
	 */
	public void setListaIUD(List<String> listaIUD2) {
		this.listaIUD = listaIUD2;
	}
}