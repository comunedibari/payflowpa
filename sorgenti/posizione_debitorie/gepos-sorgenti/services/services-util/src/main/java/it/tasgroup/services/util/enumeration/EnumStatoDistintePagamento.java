package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoDistintePagamento implements MessageDescription{
	
	INCORSO("IN CORSO","Flusso In Corso",""),
	INERRORE("IN ERRORE","Flusso In Errore",""),
	ESEGUITOSBF("ESEGUITO SBF","Flusso Eseguito S.B.F.",""),
	ESEGUITO("ESEGUITO","Flusso Eseguito",""),
	NONESEGUITO("NON ESEGUITO","Flusso Non Eseguito",""),
	DA_ELABORARE("DA ELABORARE","Flusso Da Elaborare",""),
	PARZESEGUITO("PARZ ESEGUITO","Flusso Parz. Eseguito","");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatoDistintePagamento(String chiave, String descrizione, String chiaveBundle) {
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
