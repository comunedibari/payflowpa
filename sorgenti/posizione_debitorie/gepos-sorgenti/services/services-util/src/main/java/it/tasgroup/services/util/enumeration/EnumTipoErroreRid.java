package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoErroreRid implements MessageDescription{
	
	F0("0","FLUSSO ELABORATO CORRETTAMENTE",""),
	F1("1","FLUSSO NON VALIDO RISPETTO ALLO SCHEMA CBI",""),
	F2("2","FLUSSO NON VALIDO RISPETTO AI CONTROLLI CBI",""),
	F3("3","FLUSSO NON VALIDO RISPETTO AI CONTROLLI APPLICATIVI:   ID RICONCILIAZIONE DOPPIO",""),
	F4("4","FLUSSO NON VALIDO RISPETTO AI CONTROLLI APPLICATIVI:   FLUSSO DOPPIO",""),
	F5("5","FLUSSO NON VALIDO RISPETTO AI CONTROLLI APPLICATIVI: COD. TRANSAZIONE NON PRESENTE",""),
	F6("6","FLUSSO NON VALIDO RISPETTO AI CONTROLLI APPLICATIVI: GIA ELABORATO",""),
	F7("7","ERRORE IMPREVISTO",""),
	F8("8","NESSUN BONIFICO CONTIENE IL CODICE UNIVOCO RICEVUTO NEL FLUSSO EP","");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoErroreRid(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static String getValoreByChiave(String chiave) {
		if (F0.getChiave().equals(chiave)) {
			return F0.getDescrizione();
		} else if (F1.getChiave().equals(chiave)) {
			return F1.getDescrizione();
		} else if (F2.getChiave().equals(chiave)) {
			return F2.getDescrizione();
		} else if (F3.getChiave().equals(chiave)) {
			return F3.getDescrizione();
		} else if (F4.getChiave().equals(chiave)) {
			return F4.getDescrizione();
		} else if (F5.getChiave().equals(chiave)) {
			return F5.getDescrizione();
		} else if (F6.getChiave().equals(chiave)) {
			return F6.getDescrizione();
		} else if (F7.getChiave().equals(chiave)) {
			return F7.getDescrizione();
		} else if (F8.getChiave().equals(chiave)) {
			return F8.getDescrizione();
		} else {
			return "";
		}
	}	
	

}
