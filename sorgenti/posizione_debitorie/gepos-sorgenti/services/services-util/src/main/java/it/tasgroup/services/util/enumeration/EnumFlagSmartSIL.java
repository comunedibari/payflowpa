package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumFlagSmartSIL implements MessageDescription { 


	SI ("Y", "SI", ""),
	NO ("N", "NO", "");

	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	private EnumFlagSmartSIL(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumFlagSmartSIL getByKey(String chiave) {
	    EnumFlagSmartSIL desiredItem = null; // Default
        for (EnumFlagSmartSIL item : EnumFlagSmartSIL.values()) {
            if (item.getChiave().equals(chiave)) {
                desiredItem = item;
                break;
            }
        }
        return desiredItem;
    }
	
	public static String getDescrizioneByKey(String chiave) {
	    String result = null; // Default
        for (EnumFlagSmartSIL item : EnumFlagSmartSIL.values()) {
            if (item.getChiave().equals(chiave)) {
                result = item.getDescrizione();
                break;
            }
        }
        return result;
    }
	
	public static String getKeyByDescrizione(String descrizione) {
	    String result = null; // Default
        for (EnumFlagSmartSIL item : EnumFlagSmartSIL.values()) {
            if (item.getDescrizione().equals(descrizione)) {
                result = item.getDescrizione();
                break;
            }
        }
        return result;
    }
	
}