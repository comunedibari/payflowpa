package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoRichiestaRevoca implements MessageDescription{
	
	ANNULLO_TECNICO("1", "Annullo Tecnico","tributi.tributoEnte.annullo.tecnico"),
	CHARGE_BACK("2", "Charge Back", "tributi.tributoEnte.revoca.chargeback"),
	GENERICO("0", "Revoca Generica", "tributi.tributoEnte.revoca.generica");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoRichiestaRevoca(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumTipoRichiestaRevoca getByKey(String chiave) {
		
		EnumTipoRichiestaRevoca desiredItem = null; // Default
		
		for (EnumTipoRichiestaRevoca item : EnumTipoRichiestaRevoca.values()) {
			
			if (item.getChiave().equals(chiave)) {
				
				desiredItem = item;
				
				break;
				
				}
			}
		
		return desiredItem;
	}
	

}
