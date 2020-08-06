package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoFlussoCasellarioDispo implements MessageDescription{
	
	AL("AL","Allineamento Elettronico Archivi",""),
	PC("PC","Bonifici Riaccredito CBI",""),
	IR("IR","Pagamenti RID",""),
	CB("CB","Bonifico Riaccredito SEPA","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoFlussoCasellarioDispo(String chiave, String descrizione, String chiaveBundle) {
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
