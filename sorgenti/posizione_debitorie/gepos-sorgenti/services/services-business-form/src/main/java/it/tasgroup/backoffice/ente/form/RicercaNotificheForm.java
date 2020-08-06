/**
 *
 */
package it.tasgroup.backoffice.ente.form;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.validator.ValidatorForm;

public class RicercaNotificheForm extends ValidatorForm{

	private String dataInvioDa;
	private String dataInvioA;

	private String dataReInvioDa;
	private String dataReInvioA;
	private String e2emsgid;
	private String receiverId;
	private String receiverSys;
	private String stato;
	private Timestamp timestamp_invio;
	private String tipoNotifiche;
	private String tipoDebito;
	private String idDebito;
	private String idPagamento;
	private String tipoFormatoNotifiche;
	private String[] selectedMessages;
	private boolean flagEnte;
	
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public void setDataInvioDa(String dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}
	
	public String getDataInvioA() {
		return dataInvioA;
	}
	
	public void setDataInvioA(String dataInvioA) {
		this.dataInvioA = dataInvioA;
	}
	
	public String getDataInvioDa() {
		return dataInvioDa;
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
		if (StringUtils.isEmpty(e2emsgid) && 
				StringUtils.isEmpty(dataInvioA) && 
				StringUtils.isEmpty(dataInvioDa) && 
				StringUtils.isEmpty(idDebito))
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
	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}
	
	public String getDataReInvioDa() {
		return dataReInvioDa;
	}

	public void setDataReInvioDa(String dataReInvioDa) {
		this.dataReInvioDa = dataReInvioDa;
	}

	public String getDataReInvioA() {
		return dataReInvioA;
	}

	public void setDataReInvioA(String dataReInvioA) {
		this.dataReInvioA = dataReInvioA;
	}

	public String getIdDebito() {
		return idDebito;
	}

	public void setIdDebito(String idDebito) {
		this.idDebito = idDebito;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public Boolean checkRulesStoricoFields() {
		return !(StringUtils.isEmpty(tipoDebito) && !StringUtils.isEmpty(idDebito) || 
				!StringUtils.isEmpty(tipoDebito)&& StringUtils.isEmpty(idDebito));
	}
	public void resetForm() {

		this.dataInvioDa=null;
		this.dataInvioA=null;
		this.dataReInvioDa=null;
		this.dataReInvioA=null;
		this.idDebito = null;
		this.idPagamento = null;
		this.e2emsgid=null;
		this.receiverId=null;
		this.receiverSys=null;
		this.stato=null;
		this.timestamp_invio=null;
		this.tipoNotifiche=null;
		this.tipoFormatoNotifiche=null;
		this.selectedMessages=null;
		this.flagEnte = false;
		this.tipoDebito = null;

	}
	
	
}
