package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoPagamenti implements MessageDescription{
	
	SU("S","Unica",""),
	RA("R","Rate","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoPagamenti(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumTipoPagamenti getByKey(String chiave) {
		EnumTipoPagamenti desiredItem = null; // Default
        for (EnumTipoPagamenti item : EnumTipoPagamenti.values()) {
            if (item.getChiave().equals(chiave)) {
                desiredItem = item;
                break;
            }
        }
        return desiredItem;
    }

}
