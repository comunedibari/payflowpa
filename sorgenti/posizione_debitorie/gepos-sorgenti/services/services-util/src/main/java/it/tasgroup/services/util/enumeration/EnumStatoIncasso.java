package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoIncasso implements MessageDescription { 


	ATTESO ("0", "Atteso", "iris.flagIncasso.atteso"),
	ACCREDITATO_CONTO_TECNICO ("1", "Accreditato su C/Tecnico","iris.flagIncasso.accreditatoContoTecnico"),
	RIACCREDITATO_ENTE  ("2", "Riaccreditato Ente", "iris.flagIncasso.riaccreditatoEnte"),
	NON_GESTITO  ("N", "Non Gestito", "iris.flagIncasso.nonGestito");

	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumStatoIncasso(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumStatoIncasso getByKey(String chiave) {
	    EnumStatoIncasso desiredItem = null; // Default
        for (EnumStatoIncasso item : EnumStatoIncasso.values()) {
            if (item.getChiave().equals(chiave)) {
                desiredItem = item;
                break;
            }
        }
        return desiredItem;
    }
}