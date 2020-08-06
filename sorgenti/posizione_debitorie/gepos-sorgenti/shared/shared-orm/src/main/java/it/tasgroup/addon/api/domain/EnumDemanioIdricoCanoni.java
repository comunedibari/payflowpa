package it.tasgroup.addon.api.domain;

import java.util.HashSet;

public enum EnumDemanioIdricoCanoni {
	//predeterminati
	AVVP_CANONE_ACQUA("AVVP_CANONE_ACQUA", "Canone Acqua"), 
	AVVP_IMPOSTA_ACQUA("AVVP_IMPOSTA_ACQUA", "Imposta Acqua"), 
	AVVP_NON_CONC_ACQUA("AVVP_NON_CONC_ACQUA", "Acqua (non concessionati)"), 
	AVVP_CANONE_OCCUPAZIONE_SUOLO("AVVP_CANONE_OCCUPAZIONE_SUOLO", "Canone Suolo"),
	AVVP_IMPOSTA_OCC_SUOLO("AVVP_IMPOSTA_OCC_SUOLO", "Imposta Suolo"),
	AVVP_NON_CONC_OCC_SUOLO("AVVP_NON_CONC_OCC_SUOLO", "Suolo (non concessionati)"),
	//liberi
	L_AVVP_CANONE_ACQUA("L_AVVP_CANONE_ACQUA", "Canone Acqua"),
	L_AVVP_CANONE_OCCUPAZIONE_SUOLO("L_AVVP_CANONE_OCCUPAZIONE_SUOLO", "Canone Suolo"),
	L_AVVP_IMPOSTA_ACQUA("L_AVVP_IMPOSTA_ACQUA", "Imposta Acqua"),
	L_AVVP_IMPOSTA_OCC_SUOLO("L_AVVP_IMPOSTA_OCC_SUOLO", "Imposta Suolo"),
	L_AVVP_NON_CONC_ACQUA("L_AVVP_NON_CONC_ACQUA", "Acqua (non concessionati)"),
	L_AVVP_NON_CONC_OCC_SUOLO("L_AVVP_NON_CONC_OCC_SUOLO", "Suolo (non concessionati)");
	
	
	public static HashSet<String> TIPO_TRIBUTO_KEYS = init(); 
	
	private String codice;
	private String descrizione;
	
	private EnumDemanioIdricoCanoni(String codice, String descrizione) {
		this.codice = codice;
		this.descrizione = descrizione;
	}
	
	private final static HashSet<String>  init() {
		HashSet<String> res = new HashSet<String>();
		for (EnumDemanioIdricoCanoni c : EnumDemanioIdricoCanoni.values()) {
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
