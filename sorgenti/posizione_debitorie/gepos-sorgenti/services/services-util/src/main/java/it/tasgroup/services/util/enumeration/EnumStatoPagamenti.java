package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoPagamenti implements MessageDescription{
	
	ESE("ES","Eseguito","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatoPagamenti(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumStatoPagamenti getByChiave(String chiave) {
		
		EnumStatoPagamenti ret = null; // Default             
		
		for (EnumStatoPagamenti item : EnumStatoPagamenti.values()) {                 
			if (item.getChiave().equalsIgnoreCase(chiave)) {                     
				ret = item;                     
				break;                 
				}             
			}   
		
		return ret; 
	}
}
