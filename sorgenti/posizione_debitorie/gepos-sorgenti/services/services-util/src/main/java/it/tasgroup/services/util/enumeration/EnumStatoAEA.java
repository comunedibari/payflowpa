package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoAEA implements MessageDescription {

	IN_CORSO_REVOCA ("IN CORSO DI REVOCA", "RICHIESTA REVOCA ALL'ADDEBITO IN CONTO INOLTRATA ALLA BANCA", ""),
	IN_CORSO_APPROVAZIONE ("IN CORSO DI APPROVAZIONE", "RICHIESTA AUTORIZZAZIONE ALL'ADDEBITO IN CONTO INOLTRATA ALLA BANCA", ""),
	IN_CORSO ("IN CORSO", "RICHIESTA IN LAVORAZIONE", ""),
	REVOCATA ("REVOCATA", "RICHIESTA REVOCATA", ""),
	VARIATA ("VARIATA", "RICHIESTA VARIATA", ""),
	RIFIUTATA ("RIFIUTATA", "RICHIESTA RIFIUTATA", ""),
	ACCETTATA ("ACCETTATA", "RICHIESTA ACCETTATA", "");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumStatoAEA(String chiave, String descrizione, String chiaveBundle) {
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
