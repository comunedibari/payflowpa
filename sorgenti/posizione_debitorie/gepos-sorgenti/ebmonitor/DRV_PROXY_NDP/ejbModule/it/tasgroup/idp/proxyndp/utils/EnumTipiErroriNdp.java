package it.tasgroup.idp.proxyndp.utils;

import java.util.ArrayList;
import java.util.List;


public enum EnumTipiErroriNdp  {

	ERRORE_PARSING ((short)0, "FLUSSO ILLEGGIBILE", "label"),
//	ERRORE_NONVALIDO_CBI 	 ((short)1, "FLUSSO NON VALIDO RISPETTO ALLO SCHEMA CBI", "label"),
//	ERRORE_NONVALIDO_CONTROLLI_CBI 	 ((short)2, "FLUSSO NON VALIDO RISPETTO AI CONTROLLI CBI", "label"),
//	ERRORE_CODTRANSAZIONE_DOPPIO  ((short)3, "FLUSSO NON VALIDO RISPETTO AI CONTROLLI APPLICATIVI:	ID RICONCILIAZIONE DOPPIO", "label"),
	ERRORE_FLUSSODOPPIO  ((short)4, "FLUSSO NON VALIDO RISPETTO AI CONTROLLI APPLICATIVI:	FLUSSO DOPPIO", "label"),
	ERRORE_CODTRANSAZIONE_NONPRESENTE  ((short)5, "FLUSSO NON VALIDO RISPETTO AI CONTROLLI APPLICATIVI: NON PRESENTE", "label"),
	ERRORE_CODTRANSAZIONE_GIAELABORATO  ((short)6, "FLUSSO NON VALIDO RISPETTO AI CONTROLLI APPLICATIVI: GIA ELABORATO", "label"),
	ERRORE_CODUNIVOCO_NONPRESENTE  ((short)8, "NESSUN BONIFICO CONTIENE IL CODICE UNIVOCO RICEVUTO NEL FLUSSO EP", "label"),
	ERRORE_FATAL  ((short)7, "ERRORE IMPREVISTO", "label");
		

	private short chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipiErroriNdp(short chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	private EnumTipiErroriNdp(short chiave) {
		this.chiave = chiave;
	}

	public short getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}


	public static EnumTipiErroriNdp getByKey(short chiave) {
		EnumTipiErroriNdp desiredItem = null; // Default
		for (EnumTipiErroriNdp item : EnumTipiErroriNdp.values()) {
			if (item.getChiave() == chiave) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}


	public static List<String> getListaDescrizioni(){
		ArrayList<String> listaDescrizioni = new ArrayList<String>();

		for (EnumTipiErroriNdp item : EnumTipiErroriNdp.values())
			listaDescrizioni.add(item.descrizione);

		return listaDescrizioni;
	}

	public static List<String> getListaChiaviBundle(){
		ArrayList<String> listaChiaviBundle = new ArrayList<String>();

		for (EnumTipiErroriNdp item : EnumTipiErroriNdp.values())
			listaChiaviBundle.add(item.chiaveBundle);

		return listaChiaviBundle;
	}

	public static EnumTipiErroriNdp getByDescrizione(String descrizione) {
		EnumTipiErroriNdp desiredItem = null; // Default
		for (EnumTipiErroriNdp item : EnumTipiErroriNdp.values()) {
			if (item.getDescrizione().equals(descrizione)) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}

}
