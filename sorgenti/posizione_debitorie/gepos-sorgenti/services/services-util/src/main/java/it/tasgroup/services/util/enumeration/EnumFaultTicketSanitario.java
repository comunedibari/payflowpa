package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumFaultTicketSanitario implements MessageDescription{
	
	KO_0001("0001","Codice Fiscale Non Valido","ws.pts.ecd.fault.cf.nonvalido"),
	KO_0002("0002","Codice Fiscale Non Censito","ws.pts.ecd.fault.cf.noncensito"),
	KO_0003("0003","Errore Recupero Prenotazioni","ws.pts.ecd.fault.generico"),
	KO_0004("0004","Errore Specifico","ws.pts.ecd.fault.specifico"),
	KO_0101("0101","Codici Prenotazioni Errati","ws.pts.aut.fault.codici.errati"),
	KO_0102("0102","Errore Notifica pagamento","ws.pts.aut.fault.generico"),
	KO_0103("0103","Errore Specifico","ws.pts.aut.fault.specifico"),
	KO_0104("0104","Errore Produzione Documento Fiscale","ws.pts.aut.fault.receipt.invalid"),
	KO_0105("0105","Errore Timeout Documento Fiscale","ws.pts.aut.fault.receipt.timeout");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumFaultTicketSanitario(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public void setChiaveBundle(String chiaveBundle) {
		this.chiaveBundle = chiaveBundle;
	}
	
}
