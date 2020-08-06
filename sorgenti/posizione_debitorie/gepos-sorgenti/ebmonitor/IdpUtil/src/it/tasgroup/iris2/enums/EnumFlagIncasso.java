package it.tasgroup.iris2.enums;

import java.util.ArrayList;
import java.util.List;


public enum EnumFlagIncasso {

	ATTESO   ("0", "ATTESO",""),
	ACCREDITATO_SU_CONTO_TECNICO ("1", "ACCREDITATO SU CONTO TECNICO", "label"),
	RIACCREDITATO_A_ENTE ("2", "NON ESEGUITO", ""),
	STORNATO ("9", "STORNATO", ""),;

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumFlagIncasso(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	private EnumFlagIncasso(String chiave) {
		this.chiave = chiave;
	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}


	public static EnumFlagIncasso getByKey(short chiave) {
		EnumFlagIncasso desiredItem = null; // Default
		for (EnumFlagIncasso item : EnumFlagIncasso.values()) {
			if (item.getChiave().equals(chiave)) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}


	public static List<String> getListaDescrizioni(){
		ArrayList<String> listaDescrizioni = new ArrayList<String>();

		for (EnumFlagIncasso item : EnumFlagIncasso.values())
			listaDescrizioni.add(item.descrizione);

		return listaDescrizioni;
	}

	public static List<String> getListaChiaviBundle(){
		ArrayList<String> listaChiaviBundle = new ArrayList<String>();

		for (EnumFlagIncasso item : EnumFlagIncasso.values())
			listaChiaviBundle.add(item.chiaveBundle);

		return listaChiaviBundle;
	}

	public static EnumFlagIncasso getByDescrizione(String descrizione) {
		EnumFlagIncasso desiredItem = null; // Default
		for (EnumFlagIncasso item : EnumFlagIncasso.values()) {
			if (item.getDescrizione().equals(descrizione)) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}

}
