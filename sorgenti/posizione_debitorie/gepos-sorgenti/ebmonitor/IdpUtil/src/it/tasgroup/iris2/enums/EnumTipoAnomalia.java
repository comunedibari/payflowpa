package it.tasgroup.iris2.enums;

import java.util.ArrayList;
import java.util.List;


public enum EnumTipoAnomalia  {
	
	PAG_DUPLICATO ((short)0, "PAG_DUPLICATO", "label"),
	DOC_DUPLICATO ((short)2, "DOC_DUPLICATO", "label"),
	IMP_MINORE 	 ((short)1, "IMP_MINORE", "label"),
	IMP_MAGGIORE 	 ((short)4, "IMP_MAGGIORE", "label"),
	DOC_NON_PRESENTE ((short)3, "DOC_NON_PRESENTE", "label"),
	SOSPETTO_GR ((short)5, "SOSPETTO_GR", "label");

	private short chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoAnomalia(short chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	private EnumTipoAnomalia(short chiave) {
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


	public static EnumTipoAnomalia getByKey(short chiave) {
		EnumTipoAnomalia desiredItem = null; // Default
		for (EnumTipoAnomalia item : EnumTipoAnomalia.values()) {
			if (item.getChiave() == chiave) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}


	public static List<String> getListaDescrizioni(){
		ArrayList<String> listaDescrizioni = new ArrayList<String>();

		for (EnumTipoAnomalia item : EnumTipoAnomalia.values())
			listaDescrizioni.add(item.descrizione);

		return listaDescrizioni;
	}

	public static List<String> getListaChiaviBundle(){
		ArrayList<String> listaChiaviBundle = new ArrayList<String>();

		for (EnumTipoAnomalia item : EnumTipoAnomalia.values())
			listaChiaviBundle.add(item.chiaveBundle);

		return listaChiaviBundle;
	}

	public static EnumTipoAnomalia getByDescrizione(String descrizione) {
		EnumTipoAnomalia desiredItem = null; // Default
		for (EnumTipoAnomalia item : EnumTipoAnomalia.values()) {
			if (item.getDescrizione().equals(descrizione)) {
				desiredItem = item;
				break;
				}
			}
		return desiredItem;
	}

}
