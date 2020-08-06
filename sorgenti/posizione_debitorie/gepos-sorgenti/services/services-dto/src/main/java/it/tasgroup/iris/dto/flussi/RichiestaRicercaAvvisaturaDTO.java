package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;

public class RichiestaRicercaAvvisaturaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	String codiceFiscaleDebitore;
	
	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}
	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}
	
}
