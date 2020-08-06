package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoPendenza implements MessageDescription {
	
	ANNULLATA		("A", "Annullata", "iris.pend.stato.annullata");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	private EnumStatoPendenza(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}
	
	@Override
	public String getChiave() {
		return chiave;
	}

	@Override
	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}

}
