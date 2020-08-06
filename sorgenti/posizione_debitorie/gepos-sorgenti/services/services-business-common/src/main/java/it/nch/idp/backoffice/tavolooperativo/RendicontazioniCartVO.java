package it.nch.idp.backoffice.tavolooperativo;

import it.nch.idp.Downloadable;

import java.sql.Blob;
import java.sql.Timestamp;

public class RendicontazioniCartVO extends Downloadable{

	private static final long serialVersionUID = -4295620198690244812L;
	public static final String RENDICONTAZIONE_XML_KEY = "rendicontazioneXML";
	private String e2emsgid;
	private String receiverId;
	private String receiverSys;
	private String stato;
	private Timestamp timestamp_invio;
	private byte[] rendicontazioneXMLAsBytes;
	private Blob rendicontazioneXML;
	private int presenza_xml;
	
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

	public Blob getRendicontazioneXML() {
		return rendicontazioneXML;
	}

	public void setRendicontazioneXML(Blob rendicontazioneXML) {
		this.rendicontazioneXML = rendicontazioneXML;
		rendicontazioneXMLAsBytes = setDownloadableStream(rendicontazioneXML);
	}

	public byte[] getRendicontazioneXMLAsBytes() {
		return rendicontazioneXMLAsBytes;
	}

	public void setRendicontazioneXMLAsBytes(byte[] rendicontazioneXMLAsBytes) {
		this.rendicontazioneXMLAsBytes = rendicontazioneXMLAsBytes;
	}
	public int getPresenza_xml() {
		return presenza_xml;
	}

	public void setPresenza_xml(int presenza_xml) {
		this.presenza_xml = presenza_xml;
	}

}
