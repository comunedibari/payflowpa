package it.nch.idp.backoffice.tavolooperativo;

import it.nch.idp.Downloadable;

import java.sql.Blob;
import java.sql.Timestamp;

public class NotificheCartVO extends Downloadable{

	private static final long serialVersionUID = -4295620198690244812L;
	public static final String NOTIFICA_XML_KEY = "notificaXML";
	private String e2emsgid;
	private String receiverId;
	private String senderId;
	private String receiverSys;
	private String stato;
	private String tipoNotifica;
	private String tipoFormatoNotifica;
	private Timestamp timestamp_invio;
	private byte[] notificaXMLAsBytes;
	private Blob notificaXML;
	private int presenza_xml;
	private String cdTrbEnte;
	private String deTrb;
	private int tentativi;
	private ConfermaCartVO confermaCartVO;
	

	public NotificheCartVO() {
		
	}
		
		public NotificheCartVO(
		String e2emsgid,
		String receiverId,
		String receiverSys,
		String stato,
		Timestamp timestamp_invio,
		String cdTrbEnte,
		int tentativi,
		ConfermaCartVO confermaCartVO
		) {
				
				this.e2emsgid=e2emsgid;
				this.receiverId=receiverId;
				this.receiverSys=receiverSys;
				this.stato=stato;
				this.timestamp_invio=timestamp_invio;
				this.cdTrbEnte = cdTrbEnte;
				this.tentativi=tentativi;
				this.confermaCartVO = confermaCartVO;
		}
		

	public NotificheCartVO(
			String e2emsgid,
			String receiverId,
			String receiverSys,
			String stato,
			Timestamp timestamp_invio,
			String cdTrbEnte,
			int tentativi
			) {
		
		this (e2emsgid,receiverId,receiverSys, stato,timestamp_invio,cdTrbEnte,tentativi, null);
	}
		
		public ConfermaCartVO getConfermaCartVO() {
				return confermaCartVO;
		}
		
		public void setConfermaCartVO(ConfermaCartVO confermaCartVO) {
				this.confermaCartVO = confermaCartVO;
		}
		
		public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverSys() {
		return receiverSys;
	}

	public void setReceiverSys(String receiverSys) {
		this.receiverSys = receiverSys;
	}

	public String getE2emsgid() {
		return e2emsgid;
	}

	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
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

	public Blob getNotificaXML() {
		return notificaXML;
	}

	public void setNotificaXML(Blob notificaXML) {
		this.notificaXML = notificaXML;
		notificaXMLAsBytes = setDownloadableStream(notificaXML);
	}

	public byte[] getNotificaXMLAsBytes() {
		return notificaXMLAsBytes;
	}

	public void setNotificaXMLAsBytes(byte[] notificaXMLAsBytes) {
		this.notificaXMLAsBytes = notificaXMLAsBytes;
	}
	public int getPresenza_xml() {
		return presenza_xml;
	}

	public void setPresenza_xml(int presenza_xml) {
		this.presenza_xml = presenza_xml;
	}
	
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}


	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}


	public int getTentativi() {
		return tentativi;
	}


	public void setTentativi(int tentativi) {
		this.tentativi = tentativi;
	}

	public String getDeTrb() {
		return deTrb;
	}

	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}

	public String getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	public String getTipoFormatoNotifica() {
		return tipoFormatoNotifica;
	}

	public void setTipoFormatoNotifica(String tipoFormatoNotifica) {
		this.tipoFormatoNotifica = tipoFormatoNotifica;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

}
