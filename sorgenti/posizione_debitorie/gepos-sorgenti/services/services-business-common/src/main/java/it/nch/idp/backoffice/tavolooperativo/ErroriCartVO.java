package it.nch.idp.backoffice.tavolooperativo;

import it.nch.idp.Downloadable;

import java.sql.Blob;
import java.sql.Timestamp;


public class ErroriCartVO extends Downloadable{

	private static final long serialVersionUID = 6848378537229471348L;
	public static final String MESSAGGIO_ERRATO_XML_KEY = "messaggioErratoXML";
	private String id_errore_cart;
	private byte[] messaggioXMLAsBytes;
	private Blob messaggioXML;
	private String tipo_messaggio;
	private String stato_errore;
	private Integer pr_versione;
	private String op_inserimento;
	private Timestamp ts_inserimento;
	
	public ErroriCartVO() {
		
	}

	public ErroriCartVO(
			Timestamp ts_inserimento,
			String tipo_messaggio,
			String stato_errore,
			String id_errore_cart
			) {
		this.ts_inserimento=ts_inserimento;
		this.tipo_messaggio=tipo_messaggio;
		this.stato_errore=stato_errore;
		this.id_errore_cart=id_errore_cart;
	}
	
	public String getId_errore_cart() {
		return id_errore_cart;
	}
	public void setId_errore_cart(String id_errore_cart) {
		this.id_errore_cart = id_errore_cart;
	}
	public String getTipo_messaggio() {
		return tipo_messaggio;
	}
	public void setTipo_messaggio(String tipo_messaggio) {
		this.tipo_messaggio = tipo_messaggio;
	}
	public String getStato_errore() {
		return stato_errore;
	}
	public void setStato_errore(String stato_errore) {
		this.stato_errore = stato_errore;
	}
	public Integer getPr_versione() {
		return pr_versione;
	}
	public void setPr_versione(Integer pr_versione) {
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
}
