package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumModRiversamento implements MessageDescription { 


	FOUR_CORNER_MODE ("D", "4 CORNER", "4CORNER"),
	THREE_CORNER_MODE ("C", "3 CORNER","3CORNER");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumModRiversamento(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumModRiversamento getByKey(String chiave) {
	    EnumModRiversamento desiredItem = null; // Default
        for (EnumModRiversamento item : EnumModRiversamento.values()) {
            if (item.getChiave().equals(chiave)) {
                desiredItem = item;
                break;
            }
        }
        return desiredItem;
    }
}