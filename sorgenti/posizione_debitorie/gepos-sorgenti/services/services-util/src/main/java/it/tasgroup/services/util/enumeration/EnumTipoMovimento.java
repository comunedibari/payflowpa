package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;


/**
 * Tipo di movimento
 */
public enum EnumTipoMovimento implements MessageDescription{

	RH("1","CBI1 RH",""),

	OPI("2","Giornale di Cassa OPI","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoMovimento(String chiave, String descrizione, String chiaveBundle) {
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

	public static EnumTipoMovimento getByKey(String chiave) {

		EnumTipoMovimento desiredItem = null; // Default

		for (EnumTipoMovimento item : EnumTipoMovimento.values()) {

			if (item.getChiave().equals(chiave)) {

				desiredItem = item;

				break;

				}
			}

		return desiredItem;
	}

}
