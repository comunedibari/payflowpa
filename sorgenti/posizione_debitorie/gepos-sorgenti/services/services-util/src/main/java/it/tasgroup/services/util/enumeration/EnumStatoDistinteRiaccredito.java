package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoDistinteRiaccredito implements MessageDescription{
	
	INCORSO("IN CORSO","Flusso In Corso","EnumStatoDistinteRiaccredito.IN_CORSO"),
	INERRORE("IN ERRORE","Flusso In Errore","EnumStatoDistinteRiaccredito.IN_ERRORE"),
	ESEGUITOSBF("ESEGUITO SBF","Flusso Eseguito S.B.F.","EnumStatoDistinteRiaccredito.ESEGUITO_SBF"),
	ESEGUITO("ESEGUITO","Flusso Eseguito","EnumStatoDistinteRiaccredito.ESEGUITO"),
	NONESEGUITO("NON ESEGUITO","Flusso Non Eseguito","EnumStatoDistinteRiaccredito.NON_ESEGUITO"),
	PARZESEGUITO("PARZ ESEGUITO","Flusso Parz. Eseguito","EnumStatoDistinteRiaccredito.PARZ_ESEGUITO");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatoDistinteRiaccredito(String chiave, String descrizione, String chiaveBundle) {
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
