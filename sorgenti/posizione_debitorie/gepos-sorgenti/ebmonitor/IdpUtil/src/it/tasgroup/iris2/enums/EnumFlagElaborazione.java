package it.tasgroup.iris2.enums;

import java.util.ArrayList;
import java.util.List;


public enum EnumFlagElaborazione  {

	DA_ELABORARE ((short)0, "DA ELABORARE", "label"),
	ELABORATO 	 ((short)1, "ELABORATO", "label");

	private short chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumFlagElaborazione(short chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	private EnumFlagElaborazione(short chiave) {
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


	public static EnumFlagElaborazione getByKey(short chiave) {
		EnumFlagElaborazione desiredItem = null; // Default
		for (EnumFlagElaborazione item : EnumFlagElaborazione.values()) {
			if (item.getChiave() == chiave) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}


	public static List<String> getListaDescrizioni(){
		ArrayList<String> listaDescrizioni = new ArrayList<String>();

		for (EnumFlagElaborazione item : EnumFlagElaborazione.values())
			listaDescrizioni.add(item.descrizione);

		return listaDescrizioni;
	}

	public static List<String> getListaChiaviBundle(){
		ArrayList<String> listaChiaviBundle = new ArrayList<String>();

		for (EnumFlagElaborazione item : EnumFlagElaborazione.values())
			listaChiaviBundle.add(item.chiaveBundle);

		return listaChiaviBundle;
	}

	public static EnumFlagElaborazione getByDescrizione(String descrizione) {
		EnumFlagElaborazione desiredItem = null; // Default
		for (EnumFlagElaborazione item : EnumFlagElaborazione.values()) {
			if (item.getDescrizione().equals(descrizione)) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}

}
