package it.nch.is.fo.common;

import java.io.Serializable;

public class CodiceDescrizioneVO implements Serializable {	   
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5392564829714826566L;
	
	public CodiceDescrizioneVO () {
		
	}
	
	public CodiceDescrizioneVO (String codice, String descrizione) {
		this.codice = codice;
		this.descrizione = descrizione;
	}
	

	private String codice;
    
	private String descrizione;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
		
}
