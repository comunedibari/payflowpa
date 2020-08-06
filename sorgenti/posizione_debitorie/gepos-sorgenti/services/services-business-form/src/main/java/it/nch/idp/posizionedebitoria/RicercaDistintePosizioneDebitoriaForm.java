package it.nch.idp.posizionedebitoria;

import org.apache.struts.validator.ValidatorForm;


public class RicercaDistintePosizioneDebitoriaForm extends ValidatorForm
{
	private String dataPagamentoDaGG;
	private String dataPagamentoDaMM;
	private String dataPagamentoDaYY;
	private String dataPagamentoAGG;
	private String dataPagamentoAMM;
	private String dataPagamentoAYY;
	
	private String importoDa;
	private String importoA;
	
	private String idEnte;
	private String descEnte;
	private String idTributo;
	private String descTributo;
	private String idTipoTributo;
	private String descTipoTributo;
	
	private String mezzoPagamento;
	private String modalita;
	private String stato;
	
	private String ordinamentoColonna;
	private String ordinamentoTipo;

	public RicercaDistintePosizioneDebitoriaForm(){}

	public String getIdEnte() {
		return idEnte;
	}

	public String getIdTipoTributo() {
		return idTipoTributo;
	}

	public String getImportoA() {
		return importoA;
	}

	/**
	 * @return
	 */
	public String getImportoDa() {
		return importoDa;
	}

	/**
	 * @return
	 */
	public String getModalita() {
		return modalita;
	}

	/**
	 * @return
	 */
	public String getOrdinamentoColonna() {
		return ordinamentoColonna;
	}

	/**
	 * @return
	 */
	public String getOrdinamentoTipo() {
		return ordinamentoTipo;
	}

	/**
	 * @return
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param string
	 */
	public void setIdEnte(String string) {
		idEnte = string;
	}

	/**
	 * @param string
	 */
	public void setIdTipoTributo(String string) {
		idTipoTributo = string;
	}

	/**
	 * @param string
	 */
	public void setImportoA(String string) {
		importoA = string;
	}

	/**
	 * @param string
	 */
	public void setImportoDa(String string) {
		importoDa = string;
	}

	/**
	 * @param string
	 */
	public void setModalita(String string) {
		modalita = string;
	}

	/**
	 * @param string
	 */
	public void setOrdinamentoColonna(String string) {
		ordinamentoColonna = string;
	}

	/**
	 * @param string
	 */
	public void setOrdinamentoTipo(String string) {
		ordinamentoTipo = string;
	}

	/**
	 * @param string
	 */
	public void setStato(String string) {
		stato = string;
	}

	public String getIdTributo() {
		return idTributo;
	}

	public void setIdTributo(String string) {
		idTributo = string;
	}

	public String getDescEnte() {
		return descEnte;
	}

	public void setDescEnte(String string) {
		descEnte = string;
	}

	public String getDescTipoTributo() {
		return descTipoTributo;
	}

	public String getDescTributo() {
		return descTributo;
	}

	public void setDescTipoTributo(String string) {
		descTipoTributo = string;
	}

	public void setDescTributo(String string) {
		descTributo = string;
	}

	public String getDataPagamentoAGG() {
		return dataPagamentoAGG;
	}

	public String getDataPagamentoAMM() {
		return dataPagamentoAMM;
	}

	public String getDataPagamentoAYY() {
		return dataPagamentoAYY;
	}

	public String getDataPagamentoDaGG() {
		return dataPagamentoDaGG;
	}

	public String getDataPagamentoDaMM() {
		return dataPagamentoDaMM;
	}

	public String getDataPagamentoDaYY() {
		return dataPagamentoDaYY;
	}

	public void setDataPagamentoAGG(String string) {
		dataPagamentoAGG = string;
	}

	public void setDataPagamentoAMM(String string) {
		dataPagamentoAMM = string;
	}

	public void setDataPagamentoAYY(String string) {
		dataPagamentoAYY = string;
	}

	public void setDataPagamentoDaGG(String string) {
		dataPagamentoDaGG = string;
	}

	public void setDataPagamentoDaMM(String string) {
		dataPagamentoDaMM = string;
	}

	public void setDataPagamentoDaYY(String string) {
		dataPagamentoDaYY = string;
	}

	public String getMezzoPagamento() {
		return mezzoPagamento;
	}

	public void setMezzoPagamento(String string) {
		mezzoPagamento = string;
	}

}
