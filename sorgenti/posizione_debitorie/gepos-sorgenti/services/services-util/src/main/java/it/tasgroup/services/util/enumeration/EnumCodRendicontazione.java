package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumCodRendicontazione implements MessageDescription{
	
	RH("RH","Rendicontazione Ordinaria Conto Tecnico",""),
	BB("BB","Esiti Bollettini Bancari",""),
	SL("SL","Esiti Rendicontazione Canali Telematici",""),
	EP("EP","Esiti Bonifici Riaccredito",""),
	EX("EX","Esiti Bonifici Riaccredito",""),
	AL("AL","Esiti AEA",""),
	IR("IR","Esiti RID",""),
	CBILL("057","Rendicontazioni da PSP/CBILL",""),
	NDP("FR","Rendicontazioni da PSP/NDP",""),
	OPI("OPI","Giornale di Cassa","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumCodRendicontazione(String chiave, String descrizione, String chiaveBundle) {
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
