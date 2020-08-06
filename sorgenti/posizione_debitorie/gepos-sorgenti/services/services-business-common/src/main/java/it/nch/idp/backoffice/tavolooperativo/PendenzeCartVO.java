package it.nch.idp.backoffice.tavolooperativo;

import it.nch.idp.Downloadable;
import it.tasgroup.services.util.enumeration.EnumAllineamentoPendenze;
import it.tasgroup.services.util.enumeration.EnumTipoOperazioneMessaggi;

import java.sql.Blob;
import java.sql.Timestamp;

public class PendenzeCartVO extends Downloadable{

	private static final long serialVersionUID = -4295620198690244812L;
	
	public static final String MESSAGGIO_XML_KEY = "messaggioXML";
	
	public boolean cartEnabled;
		
	private String e2emsgid;
	private String senderId;
	private String senderSys;
	private String stato;
	private Timestamp timestamp_ricezione;
	private Long num_pendenze;
	private byte[] messaggioXMLAsBytes;
	private Blob messaggioXML;
	private int presenza_xml;
    private String tipo_messaggio;
    private EnumAllineamentoPendenze tipoMessaggioEnum;
	private int num_pend_deleted;   
	private String tipo_tributo;
	private String tipo_operazione;
	private String trt_senderId;
	private String trt_senderSys;
	private EnumTipoOperazioneMessaggi tipoOperazioneEnum;
	
	private Long numeroPendenzeCaricate;
	private Long numeroPendenze;

	public PendenzeCartVO() {
		
	}
	
	// Costruttore per Native Query direct mapping (serve per escludere il fetching del blob.
	// nelle liste 
	public PendenzeCartVO (
			String e2emsgid,
			String senderId,
			String senderSys,
			String stato,
			Timestamp timestamp_ricezione,
			Long num_pendenze,
		    String tipo_messaggio,
		    int num_pend_deleted, 
			String tipo_tributo,
			String tipo_operazione,
			String trt_senderId,
			String trt_senderSys,
			int presenza_xml
    ) {
		this.e2emsgid = e2emsgid;
		this.senderId = senderId;
		this.senderSys = senderSys;
		this.stato = stato;
		this.timestamp_ricezione = timestamp_ricezione;
		this.num_pendenze = num_pendenze;
		this.tipo_messaggio = tipo_messaggio;
		this.num_pend_deleted = num_pend_deleted;
		this.tipo_tributo = tipo_tributo;
		this.tipo_operazione = tipo_operazione;
		this.tipoMessaggioEnum = EnumAllineamentoPendenze.getByKey(this.tipo_messaggio);
		this.tipoOperazioneEnum = EnumTipoOperazioneMessaggi.getByKey(this.tipo_operazione);
		this.trt_senderId = trt_senderId;
		this.trt_senderSys = trt_senderSys;
		this.presenza_xml = presenza_xml;
	}
		
		
		public boolean isCartEnabled() {
				return cartEnabled;
		}
		
		public void setCartEnabled(boolean cartEnabled) {
				this.cartEnabled = cartEnabled;
		}
		
		
		public String getTipo_tributo() {
		return tipo_tributo;
	}

	public void setTipo_tributo(String tipo_tributo) {
		this.tipo_tributo = tipo_tributo;
	}

	public int getNum_pend_deleted() {
		return num_pend_deleted;
	}

	public void setNum_pend_deleted(int num_pend_deleted) {
		this.num_pend_deleted = num_pend_deleted;
	}


	
    public String getTipo_messaggio() {
		return tipo_messaggio;
	}

	public void setTipo_messaggio(String tipo_messaggio) {
		this.tipo_messaggio = tipo_messaggio;
		this.tipoMessaggioEnum = EnumAllineamentoPendenze.getByKey(tipo_messaggio);
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

	public Timestamp getTimestamp_ricezione() {
		return timestamp_ricezione;
	}

	public void setTimestamp_ricezione(Timestamp timestamp_ricezione) {
		this.timestamp_ricezione = timestamp_ricezione;
	}

	public Long getNum_pendenze() {
		return num_pendenze;
	}

	public void setNum_pendenze(Long num_pendenze) {
		this.num_pendenze = num_pendenze;
	}

	public Blob getMessaggioXML() {
		return messaggioXML;
	}

	public void setMessaggioXML(Blob messaggioXML) {
		this.messaggioXML = messaggioXML;
		messaggioXMLAsBytes = setDownloadableStream(messaggioXML);
	}

	public byte[] getMessaggioXMLAsBytes() {
		return messaggioXMLAsBytes;
	}

	public void setMessaggioXMLAsBytes(byte[] messaggioXMLAsBytes) {
		this.messaggioXMLAsBytes = messaggioXMLAsBytes;
	}
	public int getPresenza_xml() {
		return presenza_xml;
	}

	public void setPresenza_xml(int presenza_xml) {
		this.presenza_xml = presenza_xml;
	}

	public String getTipo_operazione() {
		return tipo_operazione;
	}

	public void setTipo_operazione(String tipo_operazione) {
		
		this.tipo_operazione = tipo_operazione;
		
		this.tipoOperazioneEnum = EnumTipoOperazioneMessaggi.getByKey(tipo_operazione);
	}

	public EnumAllineamentoPendenze getTipoMessaggioEnum() {
		return tipoMessaggioEnum;
	}

	public void setTipoMessaggioEnum(EnumAllineamentoPendenze tipoMessaggioEnum) {
		this.tipoMessaggioEnum = tipoMessaggioEnum;
	}

	public EnumTipoOperazioneMessaggi getTipoOperazioneEnum() {
		return tipoOperazioneEnum;
	}

	public void setTipoOperazioneEnum(EnumTipoOperazioneMessaggi enumTipoOperazione) {
		this.tipoOperazioneEnum = enumTipoOperazione;
	}

	public Long getNumeroPendenzeCaricate() {
		return numeroPendenzeCaricate;
	}

	public void setNumeroPendenzeCaricate(Long numeroPendenzeCaricate) {
		this.numeroPendenzeCaricate = numeroPendenzeCaricate;
	}
	
	public Long getNumeroPendenze() {
		return numeroPendenze;
	}

	public void setNumeroPendenze(Long numeroPendenze) {
		this.numeroPendenze = numeroPendenze;
	}

	public String getTrt_senderId() {
		return trt_senderId;
	}

	public void setTrt_senderId(String trt_senderId) {
		this.trt_senderId = trt_senderId;
	}

	public String getTrt_senderSys() {
		return trt_senderSys;
	}

	public void setTrt_senderSys(String trt_senderSys) {
		this.trt_senderSys = trt_senderSys;
	}
	
}
