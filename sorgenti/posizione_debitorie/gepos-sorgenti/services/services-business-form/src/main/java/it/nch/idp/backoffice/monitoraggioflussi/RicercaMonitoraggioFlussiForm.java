package it.nch.idp.backoffice.monitoraggioflussi;

import it.nch.erbweb.web.util.ComboOption;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class RicercaMonitoraggioFlussiForm extends ValidatorActionForm{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String dataCreazioneDaGG;
    private String dataCreazioneDaMM;
    private String dataCreazioneDaYY;
    private String dataCreazioneAGG;
    private String dataCreazioneAMM;
    private String dataCreazioneAYY;
    
    private String importoDa;
    private String importoA;
    
    private String stato;
    private String tipoflusso;
    
    private String nomeSupporto;
    private Integer dimensioneDa;
    private Integer dimensioneA;
    
    private String mittente;
    private String ricevente;
    
    private String tipoesito;
    private String esitianomali;
    private String canaleCbill;
    
    //Campi del form Lista Operazioni per Esito
    private String tsInserimentoDaGG;
    private String tsInserimentoDaMM;
    private String tsInserimentoDaYY;
    private String tsInserimentoAGG;
    private String tsInserimentoAMM;
    private String tsInserimentoAYY;
    
    private String systemId; //SenderId
    private String applicationId;
    private String tiOperazione;
    private String codicePagamento;
    private String sessionIdToken;
    private String esito;
    private String utenteCreatore;
    private String codErrore;
    private String emailVersante;

    private List<ComboOption> systemIds;
    private List<ComboOption> applicationIds;
    private List<ComboOption> tiOperazioni;
    
    private List<ComboOption> mittenti;
    private List<ComboOption> riceventi;

    
	public void resetFields(ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
    	super.reset(mapping, request);
        dataCreazioneDaGG = "";
        dataCreazioneDaMM = "";
        dataCreazioneDaYY = "";
        dataCreazioneAGG = "";
        dataCreazioneAMM = "";
        dataCreazioneAYY = "";
        importoDa = "";
        importoA = "";
        stato = "";
        tipoflusso = "";
        nomeSupporto = "";
        dimensioneDa = null;
        dimensioneA = null;
        mittente = "";
        ricevente = "";
        tipoesito = "";
        esitianomali = "";
    }
    

    public String getUtenteCreatore() {
        return utenteCreatore;
    }

    public void setUtenteCreatore(String utenteCreatore) {
        this.utenteCreatore = utenteCreatore;
    }
    
    public String getNomeSupporto() {
        return nomeSupporto;
    }
    public void setNomeSupporto(String nomeSupporto) {
        this.nomeSupporto = nomeSupporto;
    }
    public Integer getDimensioneDa() {
        return dimensioneDa;
    }
    public void setDimensioneDa(Integer dimensioneDa) {
        this.dimensioneDa = dimensioneDa;
    }
    public Integer getDimensioneA() {
        return dimensioneA;
    }
    public void setDimensioneA(Integer dimensioneA) {
        this.dimensioneA = dimensioneA;
    }
    public String getTipoesito() {
        return tipoesito;
    }
    public void setTipoesito(String tipoesito) {
        this.tipoesito = tipoesito;
    }
    public String getStato() {
        return stato;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }
    public String getTipoflusso() {
        return tipoflusso;
    }
    public void setTipoflusso(String tipoflusso) {
        this.tipoflusso = tipoflusso;
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
    public String getDataCreazioneDaGG() {
        return dataCreazioneDaGG;
    }
    public void setDataCreazioneDaGG(String dataCreazioneDaGG) {
        this.dataCreazioneDaGG = dataCreazioneDaGG;
    }
    public String getDataCreazioneDaMM() {
        return dataCreazioneDaMM;
    }
    public void setDataCreazioneDaMM(String dataCreazioneDaMM) {
        this.dataCreazioneDaMM = dataCreazioneDaMM;
    }
    public String getDataCreazioneDaYY() {
        return dataCreazioneDaYY;
    }
    public void setDataCreazioneDaYY(String dataCreazioneDaYY) {
        this.dataCreazioneDaYY = dataCreazioneDaYY;
    }
    public String getDataCreazioneAGG() {
        return dataCreazioneAGG;
    }
    public void setDataCreazioneAGG(String dataCreazioneAGG) {
        this.dataCreazioneAGG = dataCreazioneAGG;
    }
    public String getDataCreazioneAMM() {
        return dataCreazioneAMM;
    }
    public void setDataCreazioneAMM(String dataCreazioneAMM) {
        this.dataCreazioneAMM = dataCreazioneAMM;
    }
    public String getDataCreazioneAYY() {
        return dataCreazioneAYY;
    }
    public void setDataCreazioneAYY(String dataCreazioneAYY) {
        this.dataCreazioneAYY = dataCreazioneAYY;
    }
    
    public String getMittente() {
        return mittente;
    }
    
    public void setMittente(String mittente) {
        this.mittente = mittente;
    }
    
    public String getRicevente() {
        return ricevente;
    }
    
    public void setRicevente(String ricevente) {
        this.ricevente = ricevente;
    }
    
    public List<ComboOption> getMittenti() {
        return mittenti;
    }
    
    public void setMittenti(List<ComboOption> mittenti) {
        this.mittenti = mittenti;
    }
    
    public List<ComboOption> getRiceventi() {
        return riceventi;
    }
    
    public void setRiceventi(List<ComboOption> riceventi) {
        this.riceventi = riceventi;
    }
    
    public String getEsitianomali() {
        return esitianomali;
    }
    public void setEsitianomali(String esitianomali) {
        this.esitianomali = esitianomali;
    }
    
    public String getCanaleCbill() {
        return canaleCbill;
    }
    
    public void setCanaleCbill(String canaleCbill) {
        this.canaleCbill = canaleCbill;
    }
    
    public String getTsInserimentoDaGG() {
        return tsInserimentoDaGG;
    }
    
    public void setTsInserimentoDaGG(String tsInserimentoDaGG) {
        this.tsInserimentoDaGG = tsInserimentoDaGG;
    }
    
    public String getTsInserimentoDaMM() {
        return tsInserimentoDaMM;
    }
    
    public void setTsInserimentoDaMM(String tsInserimentoDaMM) {
        this.tsInserimentoDaMM = tsInserimentoDaMM;
    }
    
    public String getTsInserimentoDaYY() {
        return tsInserimentoDaYY;
    }
    
    public void setTsInserimentoDaYY(String tsInserimentoDaYY) {
        this.tsInserimentoDaYY = tsInserimentoDaYY;
    }
    
    public String getTsInserimentoAGG() {
        return tsInserimentoAGG;
    }
    
    public void setTsInserimentoAGG(String tsInserimentoAGG) {
        this.tsInserimentoAGG = tsInserimentoAGG;
    }
    
    public String getTsInserimentoAMM() {
        return tsInserimentoAMM;
    }
    
    public void setTsInserimentoAMM(String tsInserimentoAMM) {
        this.tsInserimentoAMM = tsInserimentoAMM;
    }
    
    public String getTsInserimentoAYY() {
        return tsInserimentoAYY;
    }
    
    public void setTsInserimentoAYY(String tsInserimentoAYY) {
        this.tsInserimentoAYY = tsInserimentoAYY;
    }
    
    public String getSystemId() {
        return systemId;
    }
    
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    public String getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
    
    public String getTiOperazione() {
        return tiOperazione;
    }
    
    public void setTiOperazione(String tiOperazione) {
        this.tiOperazione = tiOperazione;
    }
    
    public String getCodicePagamento() {
        return codicePagamento;
    }
    
    public void setCodicePagamento(String codicePagamento) {
        this.codicePagamento = codicePagamento;
    }
    
    public String getSessionIdToken() {
        return sessionIdToken;
    }
    
    public void setSessionIdToken(String sessionIdToken) {
        this.sessionIdToken = sessionIdToken;
    }
    
    public List<ComboOption> getSystemIds() {
        return systemIds;
    }
    
    public void setSystemIds(List<ComboOption> systemIds) {
        this.systemIds = systemIds;
    }
    
    public List<ComboOption> getApplicationIds() {
        return applicationIds;
    }
    
    public void setApplicationIds(List<ComboOption> applicationIds) {
        this.applicationIds = applicationIds;
    }
    
    public List<ComboOption> getTiOperazioni() {
        return tiOperazioni;
    }
    
    public void setTiOperazioni(List<ComboOption> tiOperazioni) {
        this.tiOperazioni = tiOperazioni;
    }
    
    public String getEsito() {
        return esito;
    }
    
    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getCodErrore() {
        return codErrore;
    }

    public void setCodErrore(String codErrore) {
        this.codErrore = codErrore;
    }
    
    public String getEmailVersante() {
        return emailVersante;
    }

    public void setEmailVersante(String emailVersante) {
        this.emailVersante = emailVersante;
    }
    
    public Map<String, String> getMittentiMap(){
    	
    
        Map<String, String> map = new HashMap<String, String>();
        
        for (ComboOption comboOption : mittenti) 
            map.put(comboOption.getValue(), comboOption.getLabel());
        
        
        return  map;
    }
    
    public Map<String, String> getRiceventiMap(){
    	
    
        Map<String, String> map = new HashMap<String, String>();
        
        for (ComboOption comboOption : riceventi) 
            map.put(comboOption.getValue(), comboOption.getLabel());
        
        
        return  map;
    }
    
    public Map<String, String> getSystemIdsMap(){
    	
    
        Map<String, String> map = new HashMap<String, String>();
        
        for (ComboOption comboOption : systemIds) 
            map.put(comboOption.getValue(), comboOption.getLabel());
        
        
        return  map;
    }
    
    public Map<String, String> getApplicationIdsMap(){
    	
    
        Map<String, String> map = new HashMap<String, String>();
        
        for (ComboOption comboOption : applicationIds) 
            map.put(comboOption.getValue(), comboOption.getLabel());
        
        
        return  map;
    }
    
    public Map<String, String> getTiOperazioniMap(){
    	
    
        Map<String, String> map = new HashMap<String, String>();
        
        for (ComboOption comboOption : tiOperazioni)
            map.put(comboOption.getValue(), comboOption.getLabel());
        
        
        return  map;
    }
    
    public String getEsitoCompleto(){
    	
    	if(EnumBusinessReturnCodes.OK.getDescrizione().equals(getEsito()))
            return getEsito();
        
        return getEsito() + "/" + getCodErrore();
        
    }
    
    public Boolean checkRequiredFieldsWSTracking() {
		
		if (StringUtils.isEmpty(codicePagamento) && StringUtils.isEmpty(utenteCreatore) && StringUtils.isEmpty(importoA) && StringUtils.isEmpty(importoDa) && StringUtils.isEmpty(this.sessionIdToken) && StringUtils.isEmpty(tsInserimentoAGG) && StringUtils.isEmpty(tsInserimentoAMM) && StringUtils.isEmpty(tsInserimentoDaGG) && StringUtils.isEmpty(tsInserimentoDaMM) && StringUtils.isEmpty(tsInserimentoDaYY))
		
			return false;
					
		return true;
		
	}
    
    public Boolean checkRequiredFieldsFlussi() {
		
		if (StringUtils.isEmpty(nomeSupporto) && StringUtils.isEmpty(dataCreazioneAGG) && StringUtils.isEmpty(dataCreazioneAMM) && StringUtils.isEmpty(dataCreazioneAYY) && StringUtils.isEmpty(dataCreazioneDaGG) && StringUtils.isEmpty(dataCreazioneDaMM) && StringUtils.isEmpty(dataCreazioneDaYY))
		
			return false;
					
		return true;
		
	}
}
