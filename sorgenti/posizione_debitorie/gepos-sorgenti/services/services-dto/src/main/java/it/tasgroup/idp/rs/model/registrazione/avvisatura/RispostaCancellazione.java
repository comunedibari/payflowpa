package it.tasgroup.idp.rs.model.registrazione.avvisatura;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RispostaCancellazione {

	private String codiceFiscaleDebitore;
	
	public RispostaCancellazione() {

	}

	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}

	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}

}