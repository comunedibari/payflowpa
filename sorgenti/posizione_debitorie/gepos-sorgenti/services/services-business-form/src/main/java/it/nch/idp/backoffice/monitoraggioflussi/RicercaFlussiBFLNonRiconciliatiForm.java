package it.nch.idp.backoffice.monitoraggioflussi;

import it.tasgroup.services.util.enumeration.EnumStatoRiversamento;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.validator.ValidatorActionForm;

public class RicercaFlussiBFLNonRiconciliatiForm extends ValidatorActionForm{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String dataAccreditoDaGG;
    
    private String dataAccreditoDaMM;
    
    private String dataAccreditoDaYY;
    
    private String dataAccreditoAGG;
    
    private String dataAccreditoAMM;
    
    private String dataAccreditoAYY;
    
    private String importoDa;
    
    private String importoA;
    
    private EnumStatoRiversamento statoEnum;
    
    private String stato;
    
    private String chiusura;
    
    private String note;
    
    private String codRifOperazione;
    
    private String transRefNumber;
    
    private String tipoAnomalia;
    
    private String idRiconciliazione;
    
    private String[] selectedIds;
    
    private String selectedIBAN = "";
    
    private String IBANTributo = "";
    
    private String chiusuraDettaglio = "";
    
    private String ibanAccredito;
    
    
    public EnumStatoRiversamento getStatoEnum() {
        return statoEnum;
    }
    
    public void setStatoEnum(EnumStatoRiversamento statoEnum) {
		this.statoEnum = statoEnum;
	}
    
    public void setStato(String stato) {
    	
    	if(stato != null & !stato.isEmpty())
    		this.statoEnum = EnumStatoRiversamento.getByKey(stato);
    	
    	else this.statoEnum = null;
    	
    	this.stato = stato;
    }
    
    public String getStato() {
		return stato;
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
   
	public String getDataAccreditoDaGG() {
		return dataAccreditoDaGG;
	}
	
	public void setDataAccreditoDaGG(String dataAccreditoDaGG) {
		this.dataAccreditoDaGG = dataAccreditoDaGG;
	}
	
	public String getDataAccreditoDaMM() {
		return dataAccreditoDaMM;
	}
	
	public void setDataAccreditoDaMM(String dataAccreditoDaMM) {
		this.dataAccreditoDaMM = dataAccreditoDaMM;
	}
	
	public String getDataAccreditoDaYY() {
		return dataAccreditoDaYY;
	}
	
	public void setDataAccreditoDaYY(String dataAccreditoDaYY) {
		this.dataAccreditoDaYY = dataAccreditoDaYY;
	}
	
	public String getDataAccreditoAGG() {
		return dataAccreditoAGG;
	}
	
	public void setDataAccreditoAGG(String dataAccreditoAGG) {
		this.dataAccreditoAGG = dataAccreditoAGG;
	}
	
	public String getDataAccreditoAMM() {
		return dataAccreditoAMM;
	}
	
	public void setDataAccreditoAMM(String dataAccreditoAMM) {
		this.dataAccreditoAMM = dataAccreditoAMM;
	}
	
	public String getDataAccreditoAYY() {
		return dataAccreditoAYY;
	}
	
	public void setDataAccreditoAYY(String dataAccreditoAYY) {
		this.dataAccreditoAYY = dataAccreditoAYY;
	}
	
	public String getCodRifOperazione() {
		return codRifOperazione;
	}
	
	public void setCodRifOperazione(String codRifOperazione) {
		this.codRifOperazione = codRifOperazione;
	}

	public String getTipoAnomalia() {
		return tipoAnomalia;
	}

	public void setTipoAnomalia(String tipoAnomalia) {
		this.tipoAnomalia = tipoAnomalia;
	}

	public String getTransRefNumber() {
		return transRefNumber;
	}

	public void setTransRefNumber(String transRefNumber) {
		this.transRefNumber = transRefNumber;
	}

	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}

	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}

	public String getChiusura() {
		return chiusura;
	}

	public void setChiusura(String chiusura) {
		this.chiusura = chiusura;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String getSelectedIBAN() {
		return selectedIBAN;
	}

	public void setSelectedIBAN(String selectedIBAN) {
		this.selectedIBAN = selectedIBAN;
	}

	public String getIBANTributo() {
		return IBANTributo;
	}

	public void setIBANTributo(String iBANTributo) {
		IBANTributo = iBANTributo;
	}

	public String getChiusuraDettaglio() {
		return chiusuraDettaglio;
	}

	public void setChiusuraDettaglio(String chiusuraDettaglio) {
		this.chiusuraDettaglio = chiusuraDettaglio;
	}
	
	public String getIbanAccredito() {
		return ibanAccredito;
	}

	public void setIbanAccredito(String ibanAccredito) {
		this.ibanAccredito = ibanAccredito;
	}

	public Boolean checkRequiredFields() {
		
		if (StringUtils.isEmpty(idRiconciliazione) && StringUtils.isEmpty(transRefNumber) && StringUtils.isEmpty(importoA) && StringUtils.isEmpty(importoDa) && StringUtils.isEmpty(codRifOperazione) && StringUtils.isEmpty(dataAccreditoAGG) && StringUtils.isEmpty(dataAccreditoAMM) && StringUtils.isEmpty(dataAccreditoAYY) && StringUtils.isEmpty(dataAccreditoDaGG) && StringUtils.isEmpty(dataAccreditoDaMM) && StringUtils.isEmpty(dataAccreditoDaYY))
		
			return false;
					
		return true;
		
	}
      
}
