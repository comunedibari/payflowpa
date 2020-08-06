package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumCanaleTicketSanitario implements MessageDescription{
	
	SDP("SDP","SDP",""),
	WEB("WEB","WEB",""),
    CHIOSCO("CHIOSCO","CHIOSCO",""),
    ALTRE_RETI("ALTRE_RETI","ALTRE_RETI","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumCanaleTicketSanitario(String chiave, String descrizione, String chiaveBundle) {
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
