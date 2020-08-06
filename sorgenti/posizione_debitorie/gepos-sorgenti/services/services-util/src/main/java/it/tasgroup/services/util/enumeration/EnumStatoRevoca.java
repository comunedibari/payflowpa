package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoRevoca implements MessageDescription{
	
	RICHIESTA("R", "Richiesta Revoca Da Elaborare","iris.flagRevoca.revocaRichiesta"),
	ACCETTATA("A", "Richiesta Revoca Accettata", "iris.flagRevoca.revocaAccettata"),
	RIFIUTATA("N", "Richiesta Revoca Rifiutata", "iris.flagRevoca.revocaRifiutata"),
	DA_VALUTARE("W", "Richiesta Revoca Da Valutare", "iris.flagRevoca.revoceDaValutare");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatoRevoca(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumStatoRevoca getByKey(String chiave) {
		
		EnumStatoRevoca desiredItem = null; // Default
		
		for (EnumStatoRevoca item : EnumStatoRevoca.values()) {
			
			if (item.getChiave().equals(chiave)) {
				
				desiredItem = item;
				
				break;
				
				}
			}
		
		return desiredItem;
	}
	

}
