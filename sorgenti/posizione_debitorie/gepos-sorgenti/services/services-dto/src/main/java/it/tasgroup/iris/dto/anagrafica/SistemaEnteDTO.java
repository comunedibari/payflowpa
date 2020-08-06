package it.tasgroup.iris.dto.anagrafica;

import it.tasgroup.services.util.enumeration.EnumFlagSmartSIL;

import java.io.Serializable;
import java.util.Date;

public class SistemaEnteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5026584648895669549L;
	private String idEnte;
	private String idSystem;
	private String deSystem;
	private String stato;
	private int prVersione;
	private String opInserimento;
	private Date tsInserimento;
	private String opAggiornamento;
	private Date tsAggiornamento;
	private String result;
    private String descEnte;
    private String trtId;
    private String trtSystem;
    private boolean usaIntermediario;
    private String cdEnte;
    private String isSSilEnabled;
    private String authId;
    private String userId;
    
	private boolean isNew;

	public String getIsSSilEnabled() {
		return isSSilEnabled;
	}
	public void setIsSSilEnabled(String isSSilEnabled) {
		this.isSSilEnabled = isSSilEnabled;
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

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getTrtId() {
		return trtId;
	}
	public void setTrtId(String trtId) {
		this.trtId = trtId;
	}
	public String getTrtSystem() {
		return trtSystem;
	}
	public void setTrtSystem(String trtSystem) {
		this.trtSystem = trtSystem;
	}
	public boolean isUsaIntermediario() {
		return usaIntermediario;
	}
	public void setUsaIntermediario(boolean usaIntermediario) {
		this.usaIntermediario = usaIntermediario;
	}
	
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public String getCdEnte() {
		return cdEnte;
	}
	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}
	
	public String getIsSSilEnabledDesc()  {
		return (isSSilEnabled != null) ? EnumFlagSmartSIL.getDescrizioneByKey(isSSilEnabled) : "";
	}
	
	public void setIsSSilEnabledDesc(String isSSilEnabledDesc)  {
		isSSilEnabled = EnumFlagSmartSIL.getKeyByDescrizione(isSSilEnabledDesc);
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
