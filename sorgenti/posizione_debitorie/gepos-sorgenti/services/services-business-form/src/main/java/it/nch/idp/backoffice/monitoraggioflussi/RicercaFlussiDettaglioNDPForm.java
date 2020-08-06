package it.nch.idp.backoffice.monitoraggioflussi;

import it.tasgroup.services.util.enumeration.EnumTipoAccredito;

import org.apache.struts.validator.ValidatorActionForm;

public class RicercaFlussiDettaglioNDPForm extends ValidatorActionForm{
    
    private static final long serialVersionUID = 1L;
    
    private String dataPagamentoDaGG;
    
    private String dataPagamentoDaMM;
    
    private String dataPagamentoDaYY;
    
    private String dataPagamentoAGG;
    
    private String dataPagamentoAMM;
    
    private String dataPagamentoAYY;
    
    private String importoDa;
    
    private String importoA;
     
    private String iuv;
    
    private String idPSP;
    
    private String transRefNumber;
    
    private String selectedId;
    
    private String tipoRiconciliazione;
    
    private EnumTipoAccredito tipoRiconcEnum;
    
    private String contoTecnico;
    
    private String cfContoTecnico;
    
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

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getIdPSP() {
		return idPSP;
	}

	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getTransRefNumber() {
		return transRefNumber;
	}

	public void setTransRefNumber(String transRefNumber) {
		this.transRefNumber = transRefNumber;
	}

	public String getTipoRiconciliazione() {
		return tipoRiconciliazione;
	}

	public void setTipoRiconciliazione(String tipoRiconciliazione) {
		
		this.tipoRiconciliazione = tipoRiconciliazione;
		
		this.tipoRiconcEnum = EnumTipoAccredito.getByKey(tipoRiconciliazione);
	}

	public EnumTipoAccredito getTipoRiconcEnum() {
		return tipoRiconcEnum;
	}

	public void setTipoRiconcEnum(EnumTipoAccredito tipoRiconcEnum) {
		this.tipoRiconcEnum = tipoRiconcEnum;
	}

	public String getContoTecnico() {
		return contoTecnico;
	}

	public void setContoTecnico(String contoTecnico) {
		this.contoTecnico = contoTecnico;
	}

	public String getCfContoTecnico() {
		return cfContoTecnico;
	}

	public void setCfContoTecnico(String cfContoTecnico) {
		this.cfContoTecnico = cfContoTecnico;
	}
      
}
