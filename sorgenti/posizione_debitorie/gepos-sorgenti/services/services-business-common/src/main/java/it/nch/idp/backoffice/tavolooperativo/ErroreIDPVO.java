package it.nch.idp.backoffice.tavolooperativo;

import it.nch.idp.Downloadable;

import java.sql.Blob;
import java.sql.Timestamp;

public class ErroreIDPVO extends Downloadable{

	private static final long serialVersionUID = 3944866937529985814L;
	public static final String MESSAGGIO_XML_KEY = "erroreIDPXML";
	
	private String e2emsgid;
	private String senderId;
	private String senderSys;
	private String receiverId;
	private String receiverSys;
	private String stato;
	private String descrizioneStato;
	private int pr_versione;
	private String op_inserimento;
	private Timestamp ts_inserimento;	
	private byte[] esitoXMLAsBytes;
	private Blob esitoXML;
	private int presenza_xml;
	
	
	public ErroreIDPVO() {
		
	}
	public ErroreIDPVO(
			String e2emsgid,
			String senderId,
			String senderSys,
			String receiverId,
			String receiverSys,
			String stato,
			String descrizioneStato,
			int pr_versione,
			String op_inserimento,
			Timestamp ts_inserimento

	) {
		this.e2emsgid=e2emsgid;
		this.senderId=senderId;
		this.senderSys=senderSys;
		this.receiverId=receiverId;
		this.receiverSys=receiverSys;
		this.stato=stato;
		this.descrizioneStato=descrizioneStato;
		this.pr_versione=pr_versione;
		this.op_inserimento=op_inserimento;
		this.ts_inserimento=ts_inserimento;
	}
	
			
	public String getE2emsgid() {
		return e2emsgid;
	}

	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
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

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}	

	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

	public int getPr_versione() {
		return pr_versione;
	}

	public void setPr_versione(int pr_versione) {
		this.pr_versione = pr_versione;
	}

	public String getOp_inserimento() {
		return op_inserimento;
	}

	public void setOp_inserimento(String op_inserimento) {
		this.op_inserimento = op_inserimento;
	}

	public Timestamp getTs_inserimento() {
		return ts_inserimento;
	}

	public void setTs_inserimento(Timestamp ts_inserimento) {
		this.ts_inserimento = ts_inserimento;
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
