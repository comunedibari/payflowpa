/**
 *
 */
package it.tasgroup.backoffice.ente.form;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.validator.ValidatorForm;

import java.sql.Timestamp;

import static org.apache.commons.lang.StringUtils.*;

/**
 * @author PazziK
 *
 */
public class RicercaMessaggiForm extends ValidatorForm{

	private String dataCaricamentoDa;
	private String dataCaricamentoA;
	private String e2emsgid;
	private String senderId;
	private String senderSys;
	private String stato;
	private String idDebito;
	private String idPagamento;
	private Timestamp timestamp_ricezione;
	private String tipoMessaggi;
	private String tipoRicerca;
	private String tipoOperazione;
	private String tipoDebito;
	private boolean ente;
	private String[] selectedMessages;

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
		return !isEmpty(e2emsgid) ||
		!isEmpty(dataCaricamentoA) ||
		!isEmpty(dataCaricamentoDa) || !isEmpty(idDebito);
	}
	
	public Boolean checkRulesStoricoFields() {
		return !(StringUtils.isEmpty(tipoDebito) && !StringUtils.isEmpty(idDebito) || 
				!StringUtils.isEmpty(tipoDebito)&& StringUtils.isEmpty(idDebito));
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

	public String getDataCaricamentoDa() {
		return dataCaricamentoDa;
	}
	public void setDataCaricamentoDa(String dataCaricamentoDa) {
		this.dataCaricamentoDa = dataCaricamentoDa;
	}
	public String getDataCaricamentoA() {
		return dataCaricamentoA;
	}
	public void setDataCaricamentoA(String dataCaricamentoA) {
		this.dataCaricamentoA = dataCaricamentoA;
	}

	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getIdDebito() {
		return idDebito;
	}
	public void setIdDebito(String idDebito) {
		this.idDebito = idDebito;
	}

	public void resetForm() {
		this.dataCaricamentoDa=null;
		this.dataCaricamentoA=null;
		this.idPagamento = null;
		this.idDebito = null;
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
