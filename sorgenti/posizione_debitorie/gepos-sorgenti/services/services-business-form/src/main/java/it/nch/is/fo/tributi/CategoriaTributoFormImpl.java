package it.nch.is.fo.tributi;

import it.nch.fwk.fo.base.BaseForm;

import it.tasgroup.services.util.enumeration.EnumTassonomiaDebito; 
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;

import java.util.Iterator;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class CategoriaTributoFormImpl extends BaseForm implements ICategoriaTributoForm {

	private String idTributo;
	private String deTrb;
	private String cdAde;
	private String tpEntrata;
	private String flPredeterm;
	private String flIniziativa;
	private String cdPagamentoSpontaneo;
	private String dePagamentoSpontaneo;
	private String stato;
	private String soggEsclusi;
	private String [] selectedItems;
	private String [] tributiAssociati;
	private String isNew;

    private String tassonomia; 
	
	
	public String[] getTributiAssociati() {
		return tributiAssociati;
	}

	public void setTributiAssociati(String[] tributiAssociati) {
		this.tributiAssociati = tributiAssociati;
	}

	public CategoriaTributoFormImpl() {}

	public String getIdTributo() {
		return idTributo;
	}

	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}

	public String getDeTrb() {
		return deTrb;
	}

	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}

	public String getCdAde() {
		return cdAde;
	}

	public void setCdAde(String cdAde) {
		this.cdAde = cdAde;
	}

	public String getTpEntrata() {
		return tpEntrata;
	}

	public void setTpEntrata(String tpEntrata) {
		this.tpEntrata = tpEntrata;
	}

	public String getFlPredeterm() {
		return flPredeterm;
	}

	public void setFlPredeterm(String flPredeterm) {
		this.flPredeterm = flPredeterm;
	}

	public String getFlIniziativa() {
		return flIniziativa;
	}

	public void setFlIniziativa(String flIniziativa) {
		this.flIniziativa = flIniziativa;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getSoggEsclusi() {
		return soggEsclusi;
	}

	public void setSoggEsclusi(String soggEsclusi) {
		this.soggEsclusi = soggEsclusi;
	}


	@Override
	public void setNativePojo(Object nativePojo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTO incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	public String[] getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	@Override
	public void reset(ActionMapping mapping, ServletRequest request) {
		this.selectedItems = new String[] {};
	}
	
	public String getCdPagamentoSpontaneo() {
		return cdPagamentoSpontaneo;
	}

	public void setCdPagamentoSpontaneo(String cdPagamentoSpontaneo) {
		this.cdPagamentoSpontaneo = cdPagamentoSpontaneo;
	}

	public String getDePagamentoSpontaneo() {
		return dePagamentoSpontaneo;
	}

	public void setDePagamentoSpontaneo(String dePagamentoSpontaneo) {
		this.dePagamentoSpontaneo = dePagamentoSpontaneo;
	}
	

	public String getTassonomia() { 
		return tassonomia; 
	} 

	public void setTassonomia(String tassonomia) { 
		this.tassonomia = tassonomia; 
	} 

	public String getDescrTassonomia() { 
		if (this.tassonomia!=null) { 
			try { 
				EnumTassonomiaDebito en = EnumTassonomiaDebito.getByKey(this.tassonomia); 
				if (en!=null){ 
					return en.getDescrizione(); 
				} 
			} catch (Exception e){ 

			} 
		} 
		return ""; 

	} 
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
       
		request.setAttribute(Globals.MESSAGE_KEY, null);
        
		ActionErrors erros = super.validate(mapping, request); 

		ActionMessages messaggi = new ActionMessages();

		for (Iterator iterator = erros.get(); iterator.hasNext();) {
			ActionMessage m = (ActionMessage) iterator.next();
			String s = m.getKey();
			
			messaggi.add("ERRORI",m);
		}
		
		request.setAttribute(Globals.MESSAGE_KEY,messaggi );
		return erros;
	}
	
}
