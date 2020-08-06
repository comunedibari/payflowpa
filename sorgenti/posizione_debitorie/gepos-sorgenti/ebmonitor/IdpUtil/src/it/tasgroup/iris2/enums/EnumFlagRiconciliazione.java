package it.tasgroup.iris2.enums;

import java.util.ArrayList;
import java.util.List;


public enum EnumFlagRiconciliazione  {

	DA_RICONCILIARE ((short)0, "DA RICONCILIARE", "label"),
	NON_RICONCILIATO ((short)2, "NON RICONCILIATO", "label"),
	RICONCILIATO 	 ((short)1, "RICONCILIATO", "label"),
	NON_ESEGUITO ((short)3, "NON ESEGUITO", "label"),
	RICONCILIATO_CON_IMPORTO_ERRATO ((short)4, "RICONCILIATO CON IMPORTO ERRATO", "label"),
	RICONCILIATO_IRREGOLARE ((short)3, "RICONCILIATO IRREGOLARE", "label"),
	EX_SOSPETTO ((short)-1, "EX_SOSPETTO", "label");
	
	

	private short chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumFlagRiconciliazione(short chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	private EnumFlagRiconciliazione(short chiave) {
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


	public static EnumFlagRiconciliazione getByKey(short chiave) {
		EnumFlagRiconciliazione desiredItem = null; // Default
		for (EnumFlagRiconciliazione item : EnumFlagRiconciliazione.values()) {
			if (item.getChiave() == chiave) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}


	public static List<String> getListaDescrizioni(){
		ArrayList<String> listaDescrizioni = new ArrayList<String>();

		for (EnumFlagRiconciliazione item : EnumFlagRiconciliazione.values())
			listaDescrizioni.add(item.descrizione);

		return listaDescrizioni;
	}

	public static List<String> getListaChiaviBundle(){
		ArrayList<String> listaChiaviBundle = new ArrayList<String>();

		for (EnumFlagRiconciliazione item : EnumFlagRiconciliazione.values())
			listaChiaviBundle.add(item.chiaveBundle);

		return listaChiaviBundle;
	}

	public static EnumFlagRiconciliazione getByDescrizione(String descrizione) {
		EnumFlagRiconciliazione desiredItem = null; // Default
		for (EnumFlagRiconciliazione item : EnumFlagRiconciliazione.values()) {
			if (item.getDescrizione().equals(descrizione)) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}

}
