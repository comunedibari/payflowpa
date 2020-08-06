package it.tasgroup.idp.esiti;

/**
 * 
 * @author Battaglil
 * 
 * utilizzato per il mapping degli errori di validazione rilevati nel messaggio di allineamento Pendenza
 *
 */
public class ErroreEsitoPendenza {
	
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
	private String codice;
	private String descrizione;

}
