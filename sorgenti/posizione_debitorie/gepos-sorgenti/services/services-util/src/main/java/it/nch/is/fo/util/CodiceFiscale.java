package it.nch.is.fo.util;

public class CodiceFiscale {

	public static final String ITALIA = "IT";
    public static final String TAXPAYER_IDENTIFICATION_NUMBER = "TIN";
    public static final char DASH = '-';
	
	String codiceFiscale, nazione;
	
	public CodiceFiscale(String codiceFiscale) {
		codiceFiscale = codiceFiscale.toUpperCase();
		if (codiceFiscale.charAt(5) == DASH && codiceFiscale.startsWith(TAXPAYER_IDENTIFICATION_NUMBER)) {
    		this.nazione = codiceFiscale.substring(3, 5);
    		this.codiceFiscale = codiceFiscale.substring(6);
    	} else {
    		this.nazione = ITALIA;
    		this.codiceFiscale = codiceFiscale;
    	}
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNazione() {
		return nazione;
	}

}
