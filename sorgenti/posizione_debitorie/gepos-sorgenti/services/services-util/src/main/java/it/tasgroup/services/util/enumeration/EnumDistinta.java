package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumDistinta implements MessageDescription {

	DELEGA_RID ("01", "DELEGA RID", ""),
	REVOCA_RID ("02", "REVOCA RID", ""),
	RID_ON_LINE ("03", "RID ON LINE", "");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumDistinta(String chiave, String descrizione, String chiaveBundle) {
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
