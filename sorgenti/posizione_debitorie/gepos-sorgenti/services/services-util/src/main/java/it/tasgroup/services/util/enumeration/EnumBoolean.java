package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumBoolean implements MessageDescription { 


	NO ("0", "SI", "iris.flag.NO"),
	SI ("1", "NO","iris.flag.SI");

	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	private EnumBoolean(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}
	
	@Override
	public String getChiave() {
		return this.chiave;
	}
	
	@Override
	public String getDescrizione() {
		return this.descrizione;
	}
	
	@Override
	public String getChiaveBundle() {
		return this.chiaveBundle;
	}
	
	public static EnumBoolean getByKey(String chiave) {
	    EnumBoolean desiredItem = null; // Default
        for (EnumBoolean item : EnumBoolean.values()) {
            if (item.getChiave().equals(chiave)) {
                desiredItem = item;
                break;
            }
        }
        return desiredItem;
    }
}