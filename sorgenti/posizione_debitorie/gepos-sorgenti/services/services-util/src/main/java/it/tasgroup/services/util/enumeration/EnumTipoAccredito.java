package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Tipo di accredito
 */
public enum EnumTipoAccredito implements MessageDescription{

	/**
	 * Accredito Non Riconciliabile in automatico
	 */
	NON_DEFINITO("N","Accredito Non Definito","","Riversamento A Scelta"),

	/**
	 * Accredito Singolo
	 */
	SINGOLO("S","Accredito Singolo","","Riversamento Singolo"),

	/**
	 *Accredito  Cumulativo
	 */
	CUMULATIVO("C","Accredito  Cumulativo","","Riversamento Cumulativo");


	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private String descrRiconciliazione;

	private EnumTipoAccredito(String chiave, String descrizione, String chiaveBundle, String descrRiconciliazione) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.descrRiconciliazione = descrRiconciliazione;
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

	public static EnumTipoAccredito getByKey(String chiave) {

		EnumTipoAccredito desiredItem = null; // Default

		for (EnumTipoAccredito item : EnumTipoAccredito.values()) {

			if (item.getChiave().equals(chiave)) {

				desiredItem = item;

				break;

				}
			}

		return desiredItem;
	}

	public String getDescrRiconciliazione() {
		return descrRiconciliazione;
	}

	public void setDescrRiconciliazione(String descrRiconciliazione) {
		this.descrRiconciliazione = descrRiconciliazione;
	}


}
