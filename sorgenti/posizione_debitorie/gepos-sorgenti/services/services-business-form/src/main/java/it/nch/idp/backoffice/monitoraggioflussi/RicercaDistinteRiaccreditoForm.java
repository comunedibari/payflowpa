package it.nch.idp.backoffice.monitoraggioflussi;

import org.apache.commons.lang.StringUtils;

import it.nch.erbweb.common.PreferenzeEsportazioneForm;


public class RicercaDistinteRiaccreditoForm extends PreferenzeEsportazioneForm{
	
	private String dataDisposizioneDaGG;
	private String dataDisposizioneDaMM;
	private String dataDisposizioneDaYY;
	private String dataDisposizioneAGG;
	private String dataDisposizioneAMM;
	private String dataDisposizioneAYY;
	
	private String dataRicezioneEsitoDaGG;
    private String dataRicezioneEsitoDaMM;
    private String dataRicezioneEsitoDaYY;
    private String dataRicezioneEsitoAGG;
    private String dataRicezioneEsitoAMM;
    private String dataRicezioneEsitoAYY;
	
	private String importoDa;
	private String importoA;
	
	private String stato;
	private String statoDesc;
	
	private String ragioneSocialeBeneficiario;
	private String ragioneSocialeBeneficiarioDesc;
    private String tipoDebito;
    private String tipoDebitoDesc;
    private String idPagamentoEnte;
    private String codicePagamentoIris;
    	
    	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getImportoDa() {
		return importoDa;
	}
	public void setImportoDa(String importoDa) {
		this.importoDa = importoDa;
	}
	public String getImportoA() {
		return importoA;
	}
	public void setImportoA(String importoA) {
		this.importoA = importoA;
	}
    public String getDataDisposizioneDaGG() {
        return dataDisposizioneDaGG;
    }
    public void setDataDisposizioneDaGG(String dataDisposizioneDaGG) {
        this.dataDisposizioneDaGG = dataDisposizioneDaGG;
    }
    public String getDataDisposizioneDaMM() {
        return dataDisposizioneDaMM;
    }
    public void setDataDisposizioneDaMM(String dataDisposizioneDaMM) {
        this.dataDisposizioneDaMM = dataDisposizioneDaMM;
    }
    public String getDataDisposizioneDaYY() {
        return dataDisposizioneDaYY;
    }
    public void setDataDisposizioneDaYY(String dataDisposizioneDaYY) {
        this.dataDisposizioneDaYY = dataDisposizioneDaYY;
    }
    public String getDataDisposizioneAGG() {
        return dataDisposizioneAGG;
    }
    public void setDataDisposizioneAGG(String dataDisposizioneAGG) {
        this.dataDisposizioneAGG = dataDisposizioneAGG;
    }
    public String getDataDisposizioneAMM() {
        return dataDisposizioneAMM;
    }
    public void setDataDisposizioneAMM(String dataDisposizioneAMM) {
        this.dataDisposizioneAMM = dataDisposizioneAMM;
    }
    public String getDataDisposizioneAYY() {
        return dataDisposizioneAYY;
    }
    public void setDataDisposizioneAYY(String dataDisposizioneAYY) {
        this.dataDisposizioneAYY = dataDisposizioneAYY;
    }
    public String getDataRicezioneEsitoDaGG() {
        return dataRicezioneEsitoDaGG;
    }
    public void setDataRicezioneEsitoDaGG(String dataRicezioneEsitoDaGG) {
        this.dataRicezioneEsitoDaGG = dataRicezioneEsitoDaGG;
    }
    public String getDataRicezioneEsitoDaMM() {
        return dataRicezioneEsitoDaMM;
    }
    public void setDataRicezioneEsitoDaMM(String dataRicezioneEsitoDaMM) {
        this.dataRicezioneEsitoDaMM = dataRicezioneEsitoDaMM;
    }
    public String getDataRicezioneEsitoDaYY() {
        return dataRicezioneEsitoDaYY;
    }
    public void setDataRicezioneEsitoDaYY(String dataRicezioneEsitoDaYY) {
        this.dataRicezioneEsitoDaYY = dataRicezioneEsitoDaYY;
    }
    public String getDataRicezioneEsitoAGG() {
        return dataRicezioneEsitoAGG;
    }
    public void setDataRicezioneEsitoAGG(String dataRicezioneEsitoAGG) {
        this.dataRicezioneEsitoAGG = dataRicezioneEsitoAGG;
    }
    public String getDataRicezioneEsitoAMM() {
        return dataRicezioneEsitoAMM;
    }
    public void setDataRicezioneEsitoAMM(String dataRicezioneEsitoAMM) {
        this.dataRicezioneEsitoAMM = dataRicezioneEsitoAMM;
    }
    public String getDataRicezioneEsitoAYY() {
        return dataRicezioneEsitoAYY;
    }
    public void setDataRicezioneEsitoAYY(String dataRicezioneEsitoAYY) {
        this.dataRicezioneEsitoAYY = dataRicezioneEsitoAYY;
    }
    public String getRagioneSocialeBeneficiario() {
        return ragioneSocialeBeneficiario;
    }
    public void setRagioneSocialeBeneficiario(String ragioneSocialeBeneficiario) {
        this.ragioneSocialeBeneficiario = ragioneSocialeBeneficiario;
    }
    public String getTipoDebito() {
        return tipoDebito;
    }
    public void setTipoDebito(String tipoDebito) {
        this.tipoDebito = tipoDebito;
    }
    public String getIdPagamentoEnte() {
        return idPagamentoEnte;
    }
    public void setIdPagamentoEnte(String idPagamentoEnte) {
        this.idPagamentoEnte = idPagamentoEnte;
    }
    public String getCodicePagamentoIris() {
        return codicePagamentoIris;
    }
    public void setCodicePagamentoIris(String codicePagamentoIris) {
        this.codicePagamentoIris = codicePagamentoIris;
    }
    public String getStatoDesc() {
        return statoDesc;
    }
    public void setStatoDesc(String statoDesc) {
        this.statoDesc = statoDesc;
    }
   
    public String getTipoDebitoDesc() {
        return tipoDebitoDesc;
    }
    public void setTipoDebitoDesc(String tipoDebitoDesc) {
        this.tipoDebitoDesc = tipoDebitoDesc;
    }
    public String getRagioneSocialeBeneficiarioDesc() {
        return ragioneSocialeBeneficiarioDesc;
    }
    public void setRagioneSocialeBeneficiarioDesc(String ragioneSocialeBeneficiarioDesc) {
        this.ragioneSocialeBeneficiarioDesc = ragioneSocialeBeneficiarioDesc;
    }
	
    public Boolean checkRequiredFields() {
		
		if (StringUtils.isEmpty(codicePagamentoIris) && StringUtils.isEmpty(idPagamentoEnte) && StringUtils.isEmpty(importoA) && StringUtils.isEmpty(importoDa) && StringUtils.isEmpty(dataDisposizioneAGG) && StringUtils.isEmpty(dataDisposizioneAMM) && StringUtils.isEmpty(dataDisposizioneAYY) && StringUtils.isEmpty(dataDisposizioneDaGG) && StringUtils.isEmpty(dataDisposizioneDaMM) && StringUtils.isEmpty(dataDisposizioneDaYY)&& StringUtils.isEmpty(dataRicezioneEsitoAGG) && StringUtils.isEmpty(dataRicezioneEsitoAMM) && StringUtils.isEmpty(dataRicezioneEsitoAYY) && StringUtils.isEmpty(dataRicezioneEsitoDaGG) && StringUtils.isEmpty(dataRicezioneEsitoDaMM) && StringUtils.isEmpty(dataRicezioneEsitoDaYY))
		
			return false;
					
		return true;
		
	}
}
