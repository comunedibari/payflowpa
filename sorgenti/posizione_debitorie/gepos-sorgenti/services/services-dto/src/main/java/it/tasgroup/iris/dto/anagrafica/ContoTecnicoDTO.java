/**
 * 
 */
package it.tasgroup.iris.dto.anagrafica;

/**
 * @author pazzik
 *
 */
public class ContoTecnicoDTO {
	
	private String descrizione;
	private String iban;
	private String intestazione;
	
	private IntestatarioDTO intestatario;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getIntestazione() {
		return intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}

	public IntestatarioDTO getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(IntestatarioDTO intestatario) {
		this.intestatario = intestatario;
	}
	
	public String getLabel(){
		
		return "Conto Tecnico (" + getIban() + ")";
	}

}
