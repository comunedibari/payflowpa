package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoDRP implements MessageDescription {

	ESEGUITO ("ESEGUITO", "Eseguito", "iris.drp.stato.eseguito"),
	INCASSATO ("INCASSATO", "Incassato", "iris.drp.stato.incassato"),
	ANNULLATO ("ANNULLATO", "Annullato", "iris.drp.stato.eseguito.annullato"),
	ANNULLATO_OP ("ANNULLATO OP", "Annullato Operatore", "iris.drp.stato.annullatooperatore"),
	IN_CORSO ("IN CORSO", "RICHIESTA IN LAVORAZIONE", "iris.drp.stato.incorso"),
	ESEGUITO_SBF ("ESEGUITO SBF", "DISPOSIZIONE DI INCASSO RID IN CARICO ALLA BANCA", "iris.drp.stato.eseguito.sbf");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumStatoDRP(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumStatoDRP getByKey(String chiave) {
		
		EnumStatoDRP desiredItem = null; // Default     
		
		for (EnumStatoDRP item : EnumStatoDRP.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				desiredItem = item;                     
				break;                 
				}             
			}             
		
		return desiredItem; 
	}

}
