package it.nch.idp.backoffice.tavolooperativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class FilterVO implements Serializable{


	private static final long serialVersionUID = -3904087815157248162L;
	private String e2emsgid;
	private String senderId;
	private String senderSys;
	private String tipoMessaggio;
	private String tipoFormatoNotifiche;
	private String tipoNotifica;
	private String tipoDebito;
	private String idPagamento;
	private String idDebito;
	private String operazione;
	private String stato;
	private Date dataCaricamentoDa;
	private Date dataCaricamentoA;
	private Date dataReinvioDa;
	private Date dataReinvioA;
	private String idErroreCart;
	private boolean isFilterEmpty;
	private String receiverId;
	private String receiverSys;
	private String ordinamento;
	private String Tipoordinamento;
	private ArrayList<String> tipiDebitoAmmessi;

	public String getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}

	public String getTipoOrdinamento() {
		return Tipoordinamento;
	}

	public void setTipoOrdinamento(String ordinamentoTipo) {
		this.Tipoordinamento = ordinamentoTipo;
	}

	public boolean getIsFilterEmpty() {
		System.out.println("getIsFilterEmpty=" + isFilterEmpty);
		return isFilterEmpty;
	}
	
	public void setIsFilterEmpty() {
		isFilterEmpty = ((e2emsgid == null) || e2emsgid.equals("")) && 
		((senderId == null) || senderId.equals("")) &&
		((senderSys == null) || senderSys.equals("")) &&
		(dataCaricamentoDa == null) &&
		(dataCaricamentoA == null) &&
		((getReceiverId() == null) || getReceiverId().equals("")) &&
		((getReceiverSys() == null) || getReceiverSys().equals(""));
		System.out.println("setIsFilterEmpty with " + isFilterEmpty);
	}
	
	public String getE2emsgid() {
		return e2emsgid;
	}
	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
		setIsFilterEmpty();
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
		setIsFilterEmpty();
	}
	public String getSenderSys() {
		return senderSys;
	}
	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
		setIsFilterEmpty();
	}
	public Date getDataCaricamentoDa() {
		return dataCaricamentoDa;
	}
	public void setDataCaricamentoDa(Date dataCaricamentoDa) {
		this.dataCaricamentoDa = dataCaricamentoDa;
		setIsFilterEmpty();
	}
	public Date getDataCaricamentoA() {
		return dataCaricamentoA;
	}
	public void setDataCaricamentoA(Date dataCaricamentoA) {
		this.dataCaricamentoA = dataCaricamentoA;
		setIsFilterEmpty();
	}
	public Date getDataReinvioDa() {
		return dataReinvioDa;
	}

	public void setDataReinvioDa(Date dataReinvioDa) {
		this.dataReinvioDa = dataReinvioDa;
	}

	public Date getDataReinvioA() {
		return dataReinvioA;
	}

	public void setDataReinvioA(Date dataReinvioA) {
		this.dataReinvioA = dataReinvioA;
	}

	public String getIdErroreCart() {
		return idErroreCart;
	}
	public void setIdErroreCart(String idErroreCart) {
		this.idErroreCart = idErroreCart;
		setIsFilterEmpty();
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setReceiverSys(String receiverSys) {
		this.receiverSys = receiverSys;
	}

	public String getReceiverSys() {
		return receiverSys;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public ArrayList<String> getTipiDebitoAmmessi() {
		return tipiDebitoAmmessi;
	}

	public void setTipiDebitoAmmessi(ArrayList<String> tipiDebitoAmmessi) {
		this.tipiDebitoAmmessi = tipiDebitoAmmessi;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}
	
	public String getTipoFormatoNotifiche() {
		return tipoFormatoNotifiche;
	}

	public void setTipoFormatoNotifiche(String tipoFormatoNotifiche) {
		this.tipoFormatoNotifiche = tipoFormatoNotifiche;
	}

	public String getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
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

}
