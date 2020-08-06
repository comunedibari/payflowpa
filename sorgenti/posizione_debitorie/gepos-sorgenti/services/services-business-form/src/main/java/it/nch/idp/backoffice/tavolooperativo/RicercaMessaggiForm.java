/**
 *
 */
package it.nch.idp.backoffice.tavolooperativo;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author PazziK
 *
 */
public class RicercaMessaggiForm extends ValidatorForm{

	private String dataCaricamentoDaGG;
	private String dataCaricamentoDaMM;
	private String dataCaricamentoDaYY;
	private String dataCaricamentoAGG;
	private String dataCaricamentoAMM;
	private String dataCaricamentoAYY;
	private String e2emsgid;
	private String senderId;
	private String senderSys;
	private String stato;
	private Timestamp timestamp_ricezione;
	private String tipoMessaggi;
	private String tipoRicerca;
	private String tipoOperazione;
	private String tipoDebito;
	private boolean ente;
	private String[] selectedMessages;

	public void setDataCaricamentoDaGG(String dataCaricamentoDaGG) {
		this.dataCaricamentoDaGG = dataCaricamentoDaGG;
	}
	public String getDataCaricamentoDaGG() {
		return dataCaricamentoDaGG;
	}
	public void setDataCaricamentoDaMM(String dataCaricamentoDaMM) {
		this.dataCaricamentoDaMM = dataCaricamentoDaMM;
	}
	public String getDataCaricamentoDaMM() {
		return dataCaricamentoDaMM;
	}
	public void setDataCaricamentoDaYY(String dataCaricamentoDaYY) {
		this.dataCaricamentoDaYY = dataCaricamentoDaYY;
	}
	public String getDataCaricamentoDaYY() {
		return dataCaricamentoDaYY;
	}
	public void setDataCaricamentoAGG(String dataCaricamentoAGG) {
		this.dataCaricamentoAGG = dataCaricamentoAGG;
	}
	public String getDataCaricamentoAGG() {
		return dataCaricamentoAGG;
	}
	public void setDataCaricamentoAMM(String dataCaricamentoAMM) {
		this.dataCaricamentoAMM = dataCaricamentoAMM;
	}
	public String getDataCaricamentoAMM() {
		return dataCaricamentoAMM;
	}
	public void setDataCaricamentoAYY(String dataCaricamentoAYY) {
		this.dataCaricamentoAYY = dataCaricamentoAYY;
	}
	public String getDataCaricamentoAYY() {
		return dataCaricamentoAYY;
	}
	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}
	public String getE2emsgid() {
		return e2emsgid;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
	}
	public String getSenderSys() {
		return senderSys;
	}

	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Timestamp getTimestamp_ricezione() {
		return timestamp_ricezione;
	}
	public void setTimestamp_ricezione(Timestamp timestamp_ricezione) {
		this.timestamp_ricezione = timestamp_ricezione;
	}

	public String getTipoMessaggi() {
		return tipoMessaggi;
	}
	public void setTipoMessaggi(String tipoMessaggi) {
		this.tipoMessaggi = tipoMessaggi;
	}

	public Boolean checkRequiredFields() {
		
		if (StringUtils.isEmpty(e2emsgid) && StringUtils.isEmpty(dataCaricamentoAGG) && StringUtils.isEmpty(dataCaricamentoAMM) && StringUtils.isEmpty(dataCaricamentoAYY) && StringUtils.isEmpty(dataCaricamentoDaGG) && StringUtils.isEmpty(dataCaricamentoDaMM) && StringUtils.isEmpty(dataCaricamentoDaYY))
		
			return false;
					
		return true;
		
	}
	public String getTipoRicerca() {
		return tipoRicerca;
	}
	public void setTipoRicerca(String tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}
	public String getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	public boolean isEnte() {
		return ente;
	}
	public String[] getSelectedMessages() {
		return selectedMessages;
	}
	public void setSelectedMessages(String[] selectedMessages) {
		this.selectedMessages = selectedMessages;
	}
	public void setEnte(boolean ente) {
		this.ente = ente;
	}
	public String getTipoDebito() {
		return tipoDebito;
	}
	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	public void resetForm() {
		this.dataCaricamentoDaGG=null;
		this.dataCaricamentoDaMM=null;
		this.dataCaricamentoDaYY=null;
		this.dataCaricamentoAGG=null;
		this.dataCaricamentoAMM=null;
		this.dataCaricamentoAYY=null;
		this.e2emsgid=null;
		this.senderId=null;
		this.senderSys=null;
		this.stato=null;
		this.timestamp_ricezione=null;
		this.tipoMessaggi=null;
		this.tipoRicerca=null;
		this.tipoOperazione=null;
		this.tipoDebito=null;
		this.ente=false;
		this.selectedMessages=null;
	}
	
	
}
