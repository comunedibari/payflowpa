package it.nch.idp.gestionecanali;



import it.nch.utility.iban.IbanHelper;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.ValidatorForm;


public class GestioneCanaliContainerForm extends ValidatorForm{
	
	private Long id;
	private String configurazione;
	private String denominazione;
	private Integer numTentativi;
	private Integer tempoRetry;
	private String stato;
	
	// selezione da checkbox
	private String[] ids;
	private String[] selectedIds;
	private Integer isAllUnsubsribed;
	
	private Object canale;
	
	public String[] getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
		
	public Integer getIsAllUnsubsribed() {
		return isAllUnsubsribed;
	}
	
	public void setIsAllUnsubsribed(Integer isAllUnsubsribed) {
		this.isAllUnsubsribed = isAllUnsubsribed;
	}
	
	public Object getCanale() {
		return canale;
	}
	
	public void setCanale(Object canale) {
		this.canale = canale;
	} 
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getConfigurazione() {
		return configurazione;
	}
	
	public void setConfigurazione(String configurazione) {
		this.configurazione = configurazione;
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public Integer getNumTentativi() {
		return numTentativi;
	}
	
	public void setNumTentativi(Integer numTentativi) {
		this.numTentativi = numTentativi;
	}
	
	public Integer getTempoRetry() {
		return tempoRetry;
	}
	
	public void setTempoRetry(Integer tempoRetry) {
		this.tempoRetry = tempoRetry;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
		
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		return super.validate(mapping, request);		
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);
		this.id = null;
		this.configurazione = null;
		this.denominazione = null;
		this.numTentativi = null;
		this.tempoRetry = null;
		this.stato = null;
	}
		
}
