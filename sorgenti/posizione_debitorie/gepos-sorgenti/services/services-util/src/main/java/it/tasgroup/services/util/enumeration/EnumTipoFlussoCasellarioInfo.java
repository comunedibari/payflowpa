package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoFlussoCasellarioInfo implements MessageDescription{
	
	AL("AL","Esiti AEA",""),
	IR("IR","Esiti RID",""),
	SL("SL","Esiti Rendicontazione Canali Telematici",""),
	EP("EP","Esiti Bonifici di riaccredito CBI",""),
	EX("EX","Esiti Bonifici di riaccredito SEPA",""),
	BB("BB","Esiti Bollettini Bancari",""),
	RH("RH","Rendicontazione Ordinaria Conto Tecnico",""),
	CBILL("057","Rendicontazioni da PSP/CBILL",""),
	NDP("FR","Rendicontazioni da PSP/NDP",""),
	OPI("OPI","Giornale di Cassa","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoFlussoCasellarioInfo(String chiave, String descrizione, String chiaveBundle) {
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
