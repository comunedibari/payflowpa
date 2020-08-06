package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumCausale implements MessageDescription {

	C_90211 ("90211", "RICHIESTA DELEGA", ""),
	C_90218 ("90218", "RICHIESTA REVOCA", ""),
	C_90212 ("90212", "ACCETTAZIONE DELEGA", "");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumCausale(String chiave, String descrizione, String chiaveBundle) {
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
