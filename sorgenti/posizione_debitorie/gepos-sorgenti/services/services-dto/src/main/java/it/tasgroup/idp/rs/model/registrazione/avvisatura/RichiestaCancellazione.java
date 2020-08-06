package it.tasgroup.idp.rs.model.registrazione.avvisatura;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RichiestaCancellazione {

	private String codiceFiscaleDebitore;
	
	public RichiestaCancellazione() {

	}

	@XmlElement(required = true)
	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}

	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}
	
}
