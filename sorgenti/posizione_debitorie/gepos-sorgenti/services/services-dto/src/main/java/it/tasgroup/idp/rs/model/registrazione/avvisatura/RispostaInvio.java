package it.tasgroup.idp.rs.model.registrazione.avvisatura;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RispostaInvio {

	private String codiceFiscaleDebitore;
	
	public RispostaInvio() {

	}

	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}

	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}

}
