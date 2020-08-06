/**
 *
 */
package it.nch.idp.backoffice.tavolooperativo;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

public class RicercaRendicontazioniForm extends ValidatorForm{

	private String dataInvioDaGG;
	private String dataInvioDaMM;
	private String dataInvioDaYY;
	private String dataInvioAGG;
	private String dataInvioAMM;
	private String dataInvioAYY;
	private String e2emsgid;
	private String receiverId;
	private String receiverSys;
	private String stato;
	private Timestamp timestamp_invio;
	private String tipoRendicontazioni;

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

	public String getTipoRendicontazioni() {
		return tipoRendicontazioni;
	}
	public void setTipoRendicontazioni(String tipoRendicontazioni) {
		this.tipoRendicontazioni = tipoRendicontazioni;
	}


}
