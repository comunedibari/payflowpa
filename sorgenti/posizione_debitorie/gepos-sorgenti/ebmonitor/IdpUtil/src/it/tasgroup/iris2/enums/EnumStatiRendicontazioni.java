package it.tasgroup.iris2.enums;

import java.util.ArrayList;
import java.util.List;


public enum EnumStatiRendicontazioni  {

	ACCETTATO ("ACCETTATO", "ACCETTATO", "label"),
	RICEVUTO ("RICEVUTO", "RICEVUTO", "label"),
	ESEGUITO ("ESEGUITO", "ESEGUITO", "label"),
	NON_ESEGUITO ("NON ESEGUITO", "NON ESEGUITO", "label"),
	PARZIALMENTE_RICONCILIATO ("PARZ RICONCILIATO", "PARZ RICONCILIATO", "label"),
	RICONCILIATO ("RICONCILIATO", "RICONCILIATO", "label"),
	NON_RICONCILIATO ("NON RICONCILIATO", "NON RICONCILIATO", "label"),
	IN_CORSO 	 ("IN CORSO", "IN CORSO", "label");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatiRendicontazioni(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	private EnumStatiRendicontazioni(String chiave) {
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


	public static EnumStatiRendicontazioni getByKey(short chiave) {
		EnumStatiRendicontazioni desiredItem = null; // Default
		for (EnumStatiRendicontazioni item : EnumStatiRendicontazioni.values()) {
			if (item.getChiave().equals(chiave)) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}


	public static List<String> getListaDescrizioni(){
		ArrayList<String> listaDescrizioni = new ArrayList<String>();

		for (EnumStatiRendicontazioni item : EnumStatiRendicontazioni.values())
			listaDescrizioni.add(item.descrizione);

		return listaDescrizioni;
	}

	public static List<String> getListaChiaviBundle(){
		ArrayList<String> listaChiaviBundle = new ArrayList<String>();

		for (EnumStatiRendicontazioni item : EnumStatiRendicontazioni.values())
			listaChiaviBundle.add(item.chiaveBundle);

		return listaChiaviBundle;
	}

	public static EnumStatiRendicontazioni getByDescrizione(String descrizione) {
		EnumStatiRendicontazioni desiredItem = null; // Default
		for (EnumStatiRendicontazioni item : EnumStatiRendicontazioni.values()) {
			if (item.getDescrizione().equals(descrizione)) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}

}
