package it.tasgroup.iris2.enums;

public enum EnumTipoOperazioneAvvisiDigitali {
  
	CREATED	 ("C", "Avviso Creato"),
	UPDATED	 ("U", "Avviso Aggiornato"),
	DELETED	 ("D", "Avviso Cancellato"),
	SKIPPED  ("N", "Da non elaborare");
	
	private String chiave;
	private String descrizione;
	
	private EnumTipoOperazioneAvvisiDigitali(String chiave, String descrizione) {
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		
	}
	
	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}
}
