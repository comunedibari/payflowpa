package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumFlagStornoAEA implements MessageDescription {

	OTTO ("8", "D+8 Settimane", ""),
	UNO ("1", "D+5 Settimane", "");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumFlagStornoAEA(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumFlagStornoAEA getByKey(String chiave) {
		EnumFlagStornoAEA desiredItem = null; // Default             
		for (EnumFlagStornoAEA item : EnumFlagStornoAEA.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				desiredItem = item;                     
				break;                 
				}             
			}             
		return desiredItem; 
	}

}
