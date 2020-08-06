package it.tasgroup.addon.api.domain;

import java.util.HashSet;

public enum EnumDemanioIdricoIstruttoria {
	SIDIT_CAUZIONE_SUOLO("SIDIT_CAUZIONE_SUOLO", "Cauzione Suolo"), 
	SIDIT_CAUZIONE_ACQUA("SIDIT_CAUZIONE_ACQUA", "Cauzione Acqua"), 
	SIDIT_IMPOSTA_ACQUA("SIDIT_IMPOSTA_ACQUA", "Imposta Acqua"), 
	SIDIT_IMPOSTA_SUOLO("SIDIT_IMPOSTA_SUOLO", "Imposta Suolo"),
	SIDIT_PRIMO_CANONE_ACQUA("SIDIT_PRIMO_CANONE_ACQUA", "Primo Canone Acqua"),
	SIDIT_PRIMO_CANONE_SUOLO("SIDIT_PRIMO_CANONE_SUOLO", "Primo Canone Suolo"),
	SIDIT_ISTRUTTORIA_ACQUA("SIDIT_ISTRUTTORIA_ACQUA", "Istruttoria Acqua"),
	SIDIT_ISTRUTTORIA_SUOLO("SIDIT_ISTRUTTORIA_SUOLO", "Istruttoria Suolo"),
	SIDIT_INDENNIZZO_ACQUA("SIDIT_INDENNIZZO_ACQUA", "Indennizzo Acqua"),
	SIDIT_INDENNIZZO_SUOLO("SIDIT_INDENNIZZO_SUOLO", "Indennizzo Suolo");
	
	public static HashSet<String> TIPO_TRIBUTO_KEYS = init(); 
	
	private String codice;
	private String descrizione;
	
	private EnumDemanioIdricoIstruttoria(String codice, String descrizione) {
		this.codice = codice;
		this.descrizione = descrizione;
	}
	
	private final static HashSet<String>  init() {
		HashSet<String> res = new HashSet<String>();
		for (EnumDemanioIdricoIstruttoria c : EnumDemanioIdricoIstruttoria.values()) {
		   res.add(c.codice);
		}
		return res;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	 

}
