package it.nch.idp.backoffice.tavolooperativo;

import it.nch.idp.Downloadable;

import java.sql.Blob;
import java.sql.Timestamp;

public class EsitoCartVO  extends Downloadable{

	private static final long serialVersionUID = 922079176843024470L;
	public static final String MESSAGGIO_XML_KEY = "esitoXML";
	private String e2emsgid;
	private String senderId;
	private String senderSys;
	private String stato;
	private Timestamp timestamp_invio;
	private byte[] esitoXMLAsBytes;
	private Blob esitoXML;
	private int presenza_xml;
	
	
	public EsitoCartVO() {
		
	}
	
	public EsitoCartVO (
		String e2emsgid,
		String senderId,
		String senderSys,
		String stato,
		Timestamp timestamp_invio,
		int presenza_xml
    ) {
		this.e2emsgid = e2emsgid;
		this.senderId = senderId;
		this.senderSys = senderSys;
		this.stato = stato;
		this.timestamp_invio = timestamp_invio;	
		this.presenza_xml = presenza_xml;
	}
	
	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderSys() {
		return senderSys;
	}

	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
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
	
	public Blob getEsitoXML() {
		return esitoXML;
	}

	public void setEsitoXML(Blob esitoXML) {
		this.esitoXML = esitoXML;
		esitoXMLAsBytes = setDownloadableStream(esitoXML);
	}

	public byte[] getEsitoXMLAsBytes() {
		return esitoXMLAsBytes;
	}

	public void setEsitoXMLAsBytes(byte[] esitoXMLAsBytes) {
		this.esitoXMLAsBytes =esitoXMLAsBytes;
	}

	public int getPresenza_xml() {
		return presenza_xml;
	}

	public void setPresenza_xml(int presenza_xml) {
		this.presenza_xml = presenza_xml;
	}

}
