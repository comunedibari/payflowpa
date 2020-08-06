package it.tasgroup.idp.esiti;

import java.util.ArrayList;
import java.util.List;

public class EsitiModel {
	public String e2emsgid;
	public String senderId;
	public String senderSys;
	public String stato;
	public String statoFlusso;
	public String trtSenderId;
	public String trtSenderSys;
	public boolean isOTF = true;

	public List<EsitoPendenza> pendenze = new ArrayList<EsitoPendenza>();

	
	public String getE2emsgid() {
		return this.e2emsgid;
	}
	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}
	public String getSenderId() {
		return this.senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderSys() {
		return this.senderSys;
	}
	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
	}

	public List<EsitoPendenza> getPendenze() {
		return pendenze;
	}

	public void setPendenze(List<EsitoPendenza> pendenze) {
		this.pendenze = pendenze;
	}	
	
	public void add(EsitoPendenza pendenza) {
		this.pendenze.add(pendenza);
	}
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}	
	public String getStatoFlusso() {
		return statoFlusso;
	}
	public void setStatoFlusso(String statoFlusso) {
		this.statoFlusso = statoFlusso;
	}
	public String getTrtSenderId() {
		return trtSenderId;
	}
	public void setTrtSenderId(String trtSenderId) {
		this.trtSenderId = trtSenderId;
	}
	public String getTrtSenderSys() {
		return trtSenderSys;
	}
	public void setTrtSenderSys(String trtSenderSys) {
		this.trtSenderSys = trtSenderSys;
	}
	public boolean isOTF() {
		return isOTF;
	}
	public void setOTF(boolean isOTF) {
		this.isOTF = isOTF;
	}	
	
	
}