package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;
import java.util.List;

public class AnonymousEntiDTO implements Serializable{
	

	private String idEnte; 
	private String denominazione; 
	private String stato;
    private Integer maxNumTributi;
	private String cdEnte;
    private List<AnonymousTributoEnteDTO> tributiList;
	
    public String getIdEnte() {
		return idEnte;
	}
    
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getCdEnte() {
		return cdEnte;
	}

	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public Integer getMaxNumTributi() {
		return maxNumTributi;
	}
	
	public void setMaxNumTributi(Integer maxNumTributi) {
		this.maxNumTributi = maxNumTributi;
	}

	public String getIstruzioniPagamento(){
		return "";
	}
	
	public List<AnonymousTributoEnteDTO> getTributiList() {
		return tributiList;
	}
	
	public void setTributiList(List<AnonymousTributoEnteDTO> tributiList) {
		this.tributiList = tributiList;
	}
	

}