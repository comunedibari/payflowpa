package it.tasgroup.services.util.enumeration;

public enum EnumRispostaAnnullamento {
	AUTORIZZAZIONE_ANNULLATA("ATA","AUTORIZZAZIONE ANNULLATA",""),
	AUTORIZZAZIONE_NON_ANNULLATA("ATN","AUTORIZZAZIONE NON ANNULLATA","");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	private EnumRispostaAnnullamento(String chiave, String descrizione, String chiaveBundle) {
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

}
