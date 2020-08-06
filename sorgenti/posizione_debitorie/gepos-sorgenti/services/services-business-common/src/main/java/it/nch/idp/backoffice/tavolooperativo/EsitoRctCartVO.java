package it.nch.idp.backoffice.tavolooperativo;

import it.nch.idp.Downloadable;

import java.sql.Blob;
import java.sql.Timestamp;

public class EsitoRctCartVO  extends Downloadable{

	private static final long serialVersionUID = -3977574956729266070L;
	public static final String MESSAGGIO_XML_KEY = "confermaXML";
	private String e2emsgid;
	private String senderId;
	private String senderSys;
	private String stato;
	private Timestamp timestamp_ricezione;
	private byte[] confermaXMLAsBytes;
	private Blob confermaXML;
	private int presenza_xml;
	
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

	public Timestamp getTimestamp_ricezione() {
		return timestamp_ricezione;
	}

	public void setTimestamp_ricezione(Timestamp timestamp_ricezione) {
		this.timestamp_ricezione = timestamp_ricezione;
	}
	
	public Blob getConfermaXML() {
		return confermaXML;
	}

	public void setConfermaXML(Blob confermaXML) {
		this.confermaXML = confermaXML;
		confermaXMLAsBytes = setDownloadableStream(confermaXML);
	}

	public byte[] getConfermaXMLAsBytes() {
		return confermaXMLAsBytes;
	}

	public void setConfermaXMLAsBytes(byte[] confermaXMLAsBytes) {
		this.confermaXMLAsBytes =confermaXMLAsBytes;
	}

	public int getPresenza_xml() {
		return presenza_xml;
	}

	public void setPresenza_xml(int presenza_xml) {
		this.presenza_xml = presenza_xml;
	}

}
