package it.nch.is.fo.sistemaEnte;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.tasgroup.services.util.enumeration.EnumFlagSmartSIL;

import java.util.Date;



public class SistemaEnteFormImpl extends BaseForm implements  ISistemaEnteForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3256053368448987115L;
	private String idEnte;
	private String codEnte;
	private String idSystem;
	private String deSystem;
	private String stato;
	private int prVersione;
	private String opInserimento;
	private Date tsInserimento;
	private String opAggiornamento;
	private Date tsAggiornamento;
	private String [] selectedItems;
	private String descEnte;
	private String codIntermediario;
	private String silIntermediario;
	private boolean usaIntermediario;
	private String isNew = "true";
	private String isSSilEnabled;
	private String authId;
	private String userId;
	
	public String getIsSSilEnabled() {
		return isSSilEnabled;
	}
	public void setIsSSilEnabled(String isSSilEnabled) {
		this.isSSilEnabled = isSSilEnabled;
	}
	
	public String getIsSSilEnabledDesc() {
		return (isSSilEnabled != null) ? EnumFlagSmartSIL.getDescrizioneByKey(isSSilEnabled) : "";
	}
	public void setIsSSilEnabledDesc(String descrizione) {
		isSSilEnabled = EnumFlagSmartSIL.getKeyByDescrizione(descrizione);
	}
	
		public String getDescEnte() {
		return descEnte;
	}
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getIdSystem() {
		return idSystem;
	}
	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}
	public String getDeSystem() {
		return deSystem;
	}
	public void setDeSystem(String deSystem) {
		this.deSystem = deSystem;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public int getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}
	public String getOpInserimento() {
		return opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	public Date getTsInserimento() {
		return tsInserimento;
	}
	public void setTsInserimento(Date tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	public String getOpAggiornamento() {
		return opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}
	public Date getTsAggiornamento() {
		return tsAggiornamento;
	}
	public void setTsAggiornamento(Date tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	public String[] getSelectedItems() {
		return selectedItems;
	}
	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@SuppressWarnings("rawtypes")
	@Override
	public DTO incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setNativePojo(Object nativePojo) {
		// TODO Auto-generated method stub
		
	}
	
	public String getCodIntermediario() {
		return codIntermediario;
	}
	
	public void setCodIntermediario(String codIntermediario) {
		this.codIntermediario = codIntermediario;
	}
	
	public String getSilIntermediario() {
		return silIntermediario;
	}
	
	public void setSilIntermediario(String silIntermediario) {
		this.silIntermediario = silIntermediario;
	}
	
	public boolean isUsaIntermediario() {
		return usaIntermediario;
	}
	
	public void setUsaIntermediario(boolean usaIntermediario) {
		this.usaIntermediario = usaIntermediario;
	}
	
	public String getCodEnte() {
		return codEnte;
	}
	
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
