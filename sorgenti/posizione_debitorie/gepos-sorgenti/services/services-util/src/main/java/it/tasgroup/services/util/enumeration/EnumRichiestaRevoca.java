package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumRichiestaRevoca implements MessageDescription{
	
	OK("OK", "Accettare sempre la richiesta","tributi.tributoEnte.gestioneRevoca.ok"),
	KO("KO", "Rifiutare sempre la richiesta", "tributi.tributoEnte.gestioneRevoca.ko"),
	ASK("ASK", "Gestire la richiesta da operatore", "tributi.tributoEnte.gestioneRevoca.ask");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumRichiestaRevoca(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumRichiestaRevoca getByKey(String chiave) {
		
		EnumRichiestaRevoca desiredItem = null; // Default
		
		for (EnumRichiestaRevoca item : EnumRichiestaRevoca.values()) {
			
			if (item.getChiave().equals(chiave)) {
				
				desiredItem = item;
				
				break;
				
				}
			}
		
		return desiredItem;
	}
	

}
