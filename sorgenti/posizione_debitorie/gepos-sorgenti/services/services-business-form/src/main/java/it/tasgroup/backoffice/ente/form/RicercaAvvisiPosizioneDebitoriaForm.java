package it.tasgroup.backoffice.ente.form;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.validator.ValidatorForm;

import java.util.ArrayList;

public class RicercaAvvisiPosizioneDebitoriaForm extends ValidatorForm   {

	private static final long serialVersionUID = 9009464743760458386L;
	
	private String dataEmissioneDaGG;
	private String dataEmissioneDaMM;
	private String dataEmissioneDaYY;
	private String dataEmissioneAGG;
	private String dataEmissioneAMM;
	private String dataEmissioneAYY;
	
	private String dataScadenzaDaGG;
	private String dataScadenzaDaMM;
	private String dataScadenzaDaYY;
	private String dataScadenzaAGG;
	private String dataScadenzaAMM;
	private String dataScadenzaAYY;
	
	private String dataPagamentoDaGG;
	private String dataPagamentoDaMM;
	private String dataPagamentoDaYY;
	private String dataPagamentoAGG;
	private String dataPagamentoAMM;
	private String dataPagamentoAYY;
	
	private String canale;

	private String importoDa;
	private String importoA;
	
	private String idEnte;
	private String descEnte;
	private String idTributo;
	private String descTributo;
	private String idTipoTributo;
	private String descTipoTributo;
	private String causale;	
	private String idPendenzaEnte;	
	private String idPagamento;
	
	private ArrayList<String> descTributoList = new ArrayList<String>();
	


	private String cfIntestatario;
	
	private String modalita;
	private String stato;
	
	private String ordinamentoColonna;
	private String ordinamentoTipo;

	private String iuv;
	
	private String riscossore;
	private String riferimento;
	
	public RicercaAvvisiPosizioneDebitoriaForm(){}

	/**
	 * @return
	 */
	public String getCausale() {
		return causale;
	}

	/**
	 * @return
	 */
	public String getCfIntestatario() {
		return cfIntestatario;
	}

	/**
	 * @return
	 */
	public String getIdEnte() {
		return idEnte;
	}

	/**
	 * @return
	 */
	public String getIdTipoTributo() {
		return idTipoTributo;
	}

	/**
	 * @return
	 */
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
	public void setCausale(String string) {
		causale = string;
	}

	/**
	 * @param string
	 */
	public void setCfIntestatario(String string) {
		cfIntestatario = string;
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

	/**
	 * @return
	 */
	public String getDataEmissioneAGG() {
		return dataEmissioneAGG;
	}

	/**
	 * @return
	 */
	public String getDataEmissioneAMM() {
		return dataEmissioneAMM;
	}

	/**
	 * @return
	 */
	public String getDataEmissioneAYY() {
		return dataEmissioneAYY;
	}

	/**
	 * @return
	 */
	public String getDataEmissioneDaGG() {
		return dataEmissioneDaGG;
	}

	/**
	 * @return
	 */
	public String getDataEmissioneDaMM() {
		return dataEmissioneDaMM;
	}

	/**
	 * @return
	 */
	public String getDataEmissioneDaYY() {
		return dataEmissioneDaYY;
	}

	/**
	 * @param string
	 */
	public void setDataEmissioneAGG(String string) {
		dataEmissioneAGG = string;
	}

	/**
	 * @param string
	 */
	public void setDataEmissioneAMM(String string) {
		dataEmissioneAMM = string;
	}

	/**
	 * @param string
	 */
	public void setDataEmissioneAYY(String string) {
		dataEmissioneAYY = string;
	}

	/**
	 * @param string
	 */
	public void setDataEmissioneDaGG(String string) {
		dataEmissioneDaGG = string;
	}

	/**
	 * @param string
	 */
	public void setDataEmissioneDaMM(String string) {
		dataEmissioneDaMM = string;
	}

	/**
	 * @param string
	 */
	public void setDataEmissioneDaYY(String string) {
		dataEmissioneDaYY = string;
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

	public ArrayList<String> getDescTributoList() {
		return descTributoList;
	}

	public void setDescTributoList(ArrayList<String> descTributoList) {
		this.descTributoList = descTributoList;
	}

	public void setDescTipoTributo(String string) {
		descTipoTributo = string;
	}

	public void setDescTributo(String string) {
		descTributo = string;
	}
	
	public void addDescTributo(String string) {
		descTributoList.add(string);
	}

	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}

	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}
	
	
	
	
	
	public String getDataScadenzaDaGG() {
		return dataScadenzaDaGG;
	}

	public void setDataScadenzaDaGG(String dataScadenzaDaGG) {
		this.dataScadenzaDaGG = dataScadenzaDaGG;
	}

	public String getDataScadenzaDaMM() {
		return dataScadenzaDaMM;
	}

	public void setDataScadenzaDaMM(String dataScadenzaDaMM) {
		this.dataScadenzaDaMM = dataScadenzaDaMM;
	}

	public String getDataScadenzaDaYY() {
		return dataScadenzaDaYY;
	}

	public void setDataScadenzaDaYY(String dataScadenzaDaYY) {
		this.dataScadenzaDaYY = dataScadenzaDaYY;
	}

	public String getDataScadenzaAGG() {
		return dataScadenzaAGG;
	}

	public void setDataScadenzaAGG(String dataScadenzaAGG) {
		this.dataScadenzaAGG = dataScadenzaAGG;
	}

	public String getDataScadenzaAMM() {
		return dataScadenzaAMM;
	}

	public void setDataScadenzaAMM(String dataScadenzaAMM) {
		this.dataScadenzaAMM = dataScadenzaAMM;
	}

	public String getDataScadenzaAYY() {
		return dataScadenzaAYY;
	}

	public void setDataScadenzaAYY(String dataScadenzaAYY) {
		this.dataScadenzaAYY = dataScadenzaAYY;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getDataPagamentoDaGG() {
		return dataPagamentoDaGG;
	}

	public void setDataPagamentoDaGG(String dataPagamentoDaGG) {
		this.dataPagamentoDaGG = dataPagamentoDaGG;
	}

	public String getDataPagamentoDaMM() {
		return dataPagamentoDaMM;
	}

	public void setDataPagamentoDaMM(String dataPagamentoDaMM) {
		this.dataPagamentoDaMM = dataPagamentoDaMM;
	}

	public String getDataPagamentoDaYY() {
		return dataPagamentoDaYY;
	}

	public void setDataPagamentoDaYY(String dataPagamentoDaYY) {
		this.dataPagamentoDaYY = dataPagamentoDaYY;
	}

	public String getDataPagamentoAGG() {
		return dataPagamentoAGG;
	}

	public void setDataPagamentoAGG(String dataPagamentoAGG) {
		this.dataPagamentoAGG = dataPagamentoAGG;
	}

	public String getDataPagamentoAMM() {
		return dataPagamentoAMM;
	}

	public void setDataPagamentoAMM(String dataPagamentoAMM) {
		this.dataPagamentoAMM = dataPagamentoAMM;
	}

	public String getDataPagamentoAYY() {
		return dataPagamentoAYY;
	}

	public void setDataPagamentoAYY(String dataPagamentoAYY) {
		this.dataPagamentoAYY = dataPagamentoAYY;
	}

	public String getCanale() {
		return canale;
	}

	public void setCanale(String canale) {
		this.canale = canale;
	}
	
	public Boolean checkRequiredFields() {
		
		if (StringUtils.isEmpty(iuv) && StringUtils.isEmpty(importoDa) &&  StringUtils.isEmpty(importoA) && StringUtils.isEmpty(idPagamento) &&  StringUtils.isEmpty(this.cfIntestatario) && StringUtils.isEmpty(causale) && StringUtils.isEmpty(idPendenzaEnte) && StringUtils.isEmpty(dataEmissioneAGG) && StringUtils.isEmpty(dataEmissioneAMM) && StringUtils.isEmpty(dataEmissioneAYY) && StringUtils.isEmpty(dataEmissioneDaGG) && StringUtils.isEmpty(dataEmissioneDaMM) && StringUtils.isEmpty(dataEmissioneDaYY)&& StringUtils.isEmpty(dataScadenzaAGG) && StringUtils.isEmpty(dataScadenzaAMM) && StringUtils.isEmpty(dataScadenzaAYY) && StringUtils.isEmpty(dataScadenzaDaGG) && StringUtils.isEmpty(dataScadenzaDaMM) && StringUtils.isEmpty(dataScadenzaDaYY))
		
			return false;
					
		return true;
		
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getRiscossore() {
		return riscossore;
	}

	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	
}
