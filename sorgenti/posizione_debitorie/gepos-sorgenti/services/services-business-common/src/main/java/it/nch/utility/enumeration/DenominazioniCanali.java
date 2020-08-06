package it.nch.utility.enumeration;

import java.io.Serializable;

public enum DenominazioniCanali implements Serializable {

	OPEN_TOSCANA("OPEN_TOSCANA"), E_MAIL("E-MAIL"), SMS("SMS");

	private final String description;

	DenominazioniCanali(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public static String[] descriptions() {
		
		String[] ret = new String[DenominazioniCanali.values().length];
		int i = 0;
		for (DenominazioniCanali denominazioneCanale : DenominazioniCanali.values()) {
			ret[i++] = denominazioneCanale.getDescription();
		}
		return ret;

	}
	
	
}
