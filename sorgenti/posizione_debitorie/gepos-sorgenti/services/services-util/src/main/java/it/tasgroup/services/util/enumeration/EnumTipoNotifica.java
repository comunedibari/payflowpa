package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoNotifica implements MessageDescription{

	ESEGUITO 			("ESEGUITO", "Eseguito", ""),
	ESEGUITO_SBF		("ESEGUITO SBF", "Eseguito SBF", "");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoNotifica(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}

}
