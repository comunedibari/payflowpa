package it.tasgroup.iris.dto;

import java.io.Serializable;

public class TestataMessaggioDTO implements Serializable{
	private String senderSys;
	private String senderSil;
	private SessionIdDTO session;
	private String receiverSys;
	private String receiverSil;
	private String idFiscaleCreditore;
	private String codiceContestoPagamento;
	
	private String idPspModello3;
	private String idIntermediarioModello3;
	private String idCanaleModello3;
	
	public String getSenderSys() {
		return senderSys;
	}
	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
	}
	public String getSenderSil() {
		return senderSil;
	}
	public void setSenderSil(String senderSil) {
		this.senderSil = senderSil;
	}
	public SessionIdDTO getSession() {
		return session;
	}
	public void setSession(SessionIdDTO session) {
		this.session = session;
	}
	public String getReceiverSys() {
		return receiverSys;
	}
	public void setReceiverSys(String receiverSys) {
		this.receiverSys = receiverSys;
	}
	public String getReceiverSil() {
		return receiverSil;
	}
	public void setReceiverSil(String receiverSil) {
		this.receiverSil = receiverSil;
	}
	public String getIdFiscaleCreditore() {
		return idFiscaleCreditore;
	}
	public void setIdFiscaleCreditore(String idFiscaleCreditore) {
		this.idFiscaleCreditore = idFiscaleCreditore;
	}
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}
	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}
	@Override
	public String toString() {
		return "TestataMessaggioDTO [senderSys=" + senderSys + ", senderSil=" + senderSil + ", session=" + session + ", receiverSys=" + receiverSys + ", receiverSil="
				+ receiverSil + " , idFiscaleCreditore= "+idFiscaleCreditore+"]";
	}
	public String getIdPspModello3() {
		return idPspModello3;
	}
	public void setIdPspModello3(String idPspModello3) {
		this.idPspModello3 = idPspModello3;
	}
	public String getIdIntermediarioModello3() {
		return idIntermediarioModello3;
	}
	public void setIdIntermediarioModello3(String idIntermediarioModello3) {
		this.idIntermediarioModello3 = idIntermediarioModello3;
	}
	public String getIdCanaleModello3() {
		return idCanaleModello3;
	}
	public void setIdCanaleModello3(String idCanaleModello3) {
		this.idCanaleModello3 = idCanaleModello3;
	}
	
}
