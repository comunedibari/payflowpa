package it.nch.idp.gestorecomunicazioni;



import it.nch.utility.iban.IbanHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.ValidatorForm;


public class GestoreComunicazioniContainerForm extends ValidatorForm{
	
	private String email;
	private String flagComunicazioni;
	
	// selezione da checkbox
	private String[] ids;
	private String[] selectedIds;
	private Integer isAllUnsubsribed;
	
	
	private Object comunicazione;
	private String utenteMapHid;
	private Map<String, Object> utenteMap = new HashMap<String, Object>();
	
	
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getIsAllUnsubsribed() {
		return isAllUnsubsribed;
	}
	
	public void setIsAllUnsubsribed(Integer isAllUnsubsribed) {
		this.isAllUnsubsribed = isAllUnsubsribed;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		return super.validate(mapping, request);
		
	}
	
	public Object getComunicazione() {
		return comunicazione;
	}
	public void setComunicazione(Object comunicazione) {
		this.comunicazione = comunicazione;
	}
	
	@Override
		public void reset(ActionMapping mapping, HttpServletRequest request) {
			// TODO Auto-generated method stub
			super.reset(mapping, request);
			this.email= ""; 
		}
	public String getFlagComunicazioni() {
		return flagComunicazioni;
	}
	public void setFlagComunicazioni(String flagComunicazioni) {
		this.flagComunicazioni = flagComunicazioni;
	}
	public Map<String, Object> getUtenteMap() {
		return utenteMap;
	}
	
	public void setUtenteMap(Map<String, Object> utenteMap) {
		this.utenteMap = utenteMap;
		this.utenteMapHid = utenteMap.toString();
	}
	
	public String getUtenteMapHid() {
		return utenteMapHid;
	}
	
	public void setUtenteMapHid(String utenteMapHid) {
		this.utenteMapHid = utenteMapHid;

		if(utenteMapHid != null){		
			String[] map = utenteMapHid.replace("{", "").replace("}", "").replace(" ", "").split(",");
			getUtenteMap().clear();
			for (String string : map) {
				String[] items = string.split("=");
				String key = items[0];
				Object value = null;
				
				if("irisId".equalsIgnoreCase(key)){
					value = items[1];
				} else {
					value = Boolean.valueOf(items[1]);
				}
				
				getUtenteMap().put(key, value);
			}
		}

	}
	
	
}
