package it.nch.idp.monitoraggiomessaggi;



import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


public class MonitoraggioMessaggiContainerForm extends ValidatorForm{
	
	// selezione da checkbox
	private Integer[] ids;
	private Integer[] selectedIds;
	private Integer isAllUnsubsribed;
	
	private String filtroStato; 
	private String filtroCanale; 
	private String filtroTipoMessaggio; 
	private String filtroIdUtente; 
	
	private String dataDaGG;
	private String dataDaMM;
	private String dataDaYY;
	private String dataAGG;
	private String dataAMM;
	private String dataAYY;

	
	protected Collection listaStati;
	protected Collection listaCanali;
	protected Collection listaTipoMessaggio;
	
	public String getFiltroStato() {
		return filtroStato;
	}

	public void setFiltroStato(String filtroStato) {
		this.filtroStato = filtroStato;
	}

	public String getFiltroCanale() {
		return filtroCanale;
	}

	public void setFiltroCanale(String filtroCanale) {
		this.filtroCanale = filtroCanale;
	}

	public String getFiltroTipoMessaggio() {
		return filtroTipoMessaggio;
	}

	public void setFiltroTipoMessaggio(String filtroTipoMessaggio) {
		this.filtroTipoMessaggio = filtroTipoMessaggio;
	}

	public String getFiltroIdUtente() {
		return filtroIdUtente;
	}

	public void setFiltroIdUtente(String filtroIdUtente) {
		this.filtroIdUtente = filtroIdUtente;
	}

	public Collection getListaStati() {
		return listaStati;
	}

	public void setListaStati(Collection listaStati) {
		this.listaStati = listaStati;
	}

	public Collection getListaCanali() {
		return listaCanali;
	}

	public void setListaCanali(Collection listaCanali) {
		this.listaCanali = listaCanali;
	}

	public Collection getListaTipoMessaggio() {
		return listaTipoMessaggio;
	}

	public void setListaTipoMessaggio(Collection listaTipoMessaggio) {
		this.listaTipoMessaggio = listaTipoMessaggio;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public Integer[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(Integer[] selectedIds) {
		this.selectedIds = selectedIds;
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

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);
	}

	public String getDataDaGG() {
		return dataDaGG;
	}

	public void setDataDaGG(String dataDaGG) {
		this.dataDaGG = dataDaGG;
	}

	public String getDataDaMM() {
		return dataDaMM;
	}

	public void setDataDaMM(String dataDaMM) {
		this.dataDaMM = dataDaMM;
	}

	public String getDataDaYY() {
		return dataDaYY;
	}

	public void setDataDaYY(String dataDaYY) {
		this.dataDaYY = dataDaYY;
	}

	public String getDataAGG() {
		return dataAGG;
	}

	public void setDataAGG(String dataAGG) {
		this.dataAGG = dataAGG;
	}

	public String getDataAMM() {
		return dataAMM;
	}

	public void setDataAMM(String dataAMM) {
		this.dataAMM = dataAMM;
	}

	public String getDataAYY() {
		return dataAYY;
	}

	public void setDataAYY(String dataAYY) {
		this.dataAYY = dataAYY;
	}

	public Boolean checkRequiredFields() {
		
		if (StringUtils.isEmpty(dataAGG) && StringUtils.isEmpty(dataAMM) && StringUtils.isEmpty(dataAYY) && StringUtils.isEmpty(dataDaGG) && StringUtils.isEmpty(dataDaMM) && StringUtils.isEmpty(dataDaYY))
		
			return false;
					
		return true;
		
	}
	
}
