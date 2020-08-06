/**
 *
 */
package it.nch.idp.backoffice.tavolooperativo;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class RicercaNotificheForm extends ValidatorForm{

	private String dataInvioDaGG;
	private String dataInvioDaMM;
	private String dataInvioDaYY;
	private String dataInvioAGG;
	private String dataInvioAMM;
	private String dataInvioAYY;
	private String dataReInvioDaGG;
	private String dataReInvioDaMM;
	private String dataReInvioDaYY;
	private String dataReInvioAGG;
	private String dataReInvioAMM;
	private String dataReInvioAYY;
	private String e2emsgid;
	private String receiverId;
	private String receiverSys;
	private String stato;
	private Timestamp timestamp_invio;
	private String tipoNotifiche;
	private String tipoFormatoNotifiche;
	private String[] selectedMessages;
	private boolean flagEnte;

	public void setDataInvioDaGG(String dataInvioDaGG) {
		this.dataInvioDaGG = dataInvioDaGG;
	}
	public String getDataInvioDaGG() {
		return dataInvioDaGG;
	}
	public void setDataInvioDaMM(String dataInvioDaMM) {
		this.dataInvioDaMM = dataInvioDaMM;
	}
	public String getDataInvioDaMM() {
		return dataInvioDaMM;
	}
	public void setDataInvioDaYY(String dataInvioDaYY) {
		this.dataInvioDaYY = dataInvioDaYY;
	}
	public String getDataInvioDaYY() {
		return dataInvioDaYY;
	}
	public void setDataInvioAGG(String dataInvioAGG) {
		this.dataInvioAGG = dataInvioAGG;
	}
	public String getDataInvioAGG() {
		return dataInvioAGG;
	}
	public void setDataInvioAMM(String dataInvioAMM) {
		this.dataInvioAMM = dataInvioAMM;
	}
	public String getDataInvioAMM() {
		return dataInvioAMM;
	}
	public void setDataInvioAYY(String dataInvioAYY) {
		this.dataInvioAYY = dataInvioAYY;
	}
	public String getDataInvioAYY() {
		return dataInvioAYY;
	}
	public String getDataReInvioDaGG() {
		return dataReInvioDaGG;
	}
	public void setDataReInvioDaGG(String dataReInvioGG) {
		this.dataReInvioDaGG = dataReInvioGG;
	}
	public String getDataReInvioDaMM() {
		return dataReInvioDaMM;
	}
	public void setDataReInvioDaMM(String dataReInvioMM) {
		this.dataReInvioDaMM = dataReInvioMM;
	}
	public String getDataReInvioDaYY() {
		return dataReInvioDaYY;
	}
	public void setDataReInvioDaYY(String dataReInvioYY) {
		this.dataReInvioDaYY = dataReInvioYY;
	}
	public String getDataReInvioAGG() {
		return dataReInvioAGG;
	}
	public void setDataReInvioAGG(String dataReInvioAGG) {
		this.dataReInvioAGG = dataReInvioAGG;
	}
	public String getDataReInvioAMM() {
		return dataReInvioAMM;
	}
	public void setDataReInvioAMM(String dataReInvioAMM) {
		this.dataReInvioAMM = dataReInvioAMM;
	}
	public String getDataReInvioAYY() {
		return dataReInvioAYY;
	}
	public void setDataReInvioAYY(String dataReInvioAYY) {
		this.dataReInvioAYY = dataReInvioAYY;
	}
	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}
	public String getE2emsgid() {
		return e2emsgid;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverSys(String receiverSys) {
		this.receiverSys = receiverSys;
	}
	public String getReceiverSys() {
		return receiverSys;
	}

	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Timestamp getTimestamp_invio() {
		return timestamp_invio;
	}
	public void setTimestamp_invio(Timestamp timestamp_invio) {
		this.timestamp_invio = timestamp_invio;
	}

	public String getTipoNotifiche() {
		return tipoNotifiche;
	}
	public void setTipoNotifiche(String tipoNotifiche) {
		this.tipoNotifiche = tipoNotifiche;
	}

	public String getTipoFormatoNotifiche() {
		return tipoFormatoNotifiche;
	}
	public void setTipoFormatoNotifiche(String tipoFormatoNotifiche) {
		this.tipoFormatoNotifiche = tipoFormatoNotifiche;
	}

	public Boolean checkRequiredFields() {
		if (StringUtils.isEmpty(e2emsgid) && StringUtils.isEmpty(dataInvioAGG) && StringUtils.isEmpty(dataInvioAMM) && StringUtils.isEmpty(dataInvioAYY) && StringUtils.isEmpty(dataInvioDaGG) && StringUtils.isEmpty(dataInvioDaMM) && StringUtils.isEmpty(dataInvioDaYY))
			return false;
					
		return true;
		
	}
	public String[] getSelectedMessages() {
		return selectedMessages;
	}
	public void setSelectedMessages(String[] selectedMessages) {
		this.selectedMessages = selectedMessages;
	}
	
	public boolean isFlagEnte() {
		return flagEnte;
	}
	public void setFlagEnte(boolean flagEnte) {
		this.flagEnte = flagEnte;
	}
	public void resetForm() {

		this.dataInvioDaGG=null;
		this.dataInvioDaMM=null;
		this.dataInvioDaYY=null;
		this.dataInvioAGG=null;
		this.dataInvioAMM=null;
		this.dataInvioAYY=null;
		this.dataReInvioDaGG=null;
		this.dataReInvioDaMM=null;
		this.dataReInvioDaYY=null;
		this.dataReInvioAGG=null;
		this.dataReInvioAMM=null;
		this.dataReInvioAYY=null;
		this.e2emsgid=null;
		this.receiverId=null;
		this.receiverSys=null;
		this.stato=null;
		this.timestamp_invio=null;
		this.tipoNotifiche=null;
		this.tipoFormatoNotifiche=null;
		this.selectedMessages=null;
		this.flagEnte = false;

	}
	
	
}
