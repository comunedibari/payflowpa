package it.tasgroup.idp.rs.model.registrazione.avvisatura;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RichiestaInvio {

	private String codiceFiscaleDebitore;
	private String nomeDebitore;
	private String cognomeDebitore;
	private List<HashMap<String, String>> canali;
	
	public RichiestaInvio() {
		
	}

	@XmlElement(required = true)
	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}

	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}

	@XmlElement(required = true)
	public String getNomeDebitore() {
		return nomeDebitore;
	}

	public void setNomeDebitore(String nomeDebitore) {
		this.nomeDebitore = nomeDebitore;
	}

	@XmlElement(required = true)
	public String getCognomeDebitore() {
		return cognomeDebitore;
	}

	public void setCognomeDebitore(String cognomeDebitore) {
		this.cognomeDebitore = cognomeDebitore;
	}

	@XmlElement(required = true)
	public List<HashMap<String, String>> getCanali() {
		return canali;
	}

	public void setCanali(List<HashMap<String, String>> canali) {
		this.canali = canali;
	}
	
}
