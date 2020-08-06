package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoConfigurazionePagamento implements MessageDescription {
	ATTIVO ("ATTIVO", "DELEGA RID", ""),
	DA_ATTIVARE ("DA_ATTIVARE", "REVOCA RID", "");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumStatoConfigurazionePagamento(String chiave, String descrizione, String chiaveBundle) {
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

}
