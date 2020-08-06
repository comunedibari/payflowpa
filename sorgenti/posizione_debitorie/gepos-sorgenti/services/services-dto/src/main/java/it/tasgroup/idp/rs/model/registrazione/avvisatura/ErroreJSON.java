package it.tasgroup.idp.rs.model.registrazione.avvisatura;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErroreJSON {

	private int status;
	private String messaggio;
	
	public ErroreJSON() {
		
	}

	public ErroreJSON(int status, String messaggio) {
		this.status = status;
		this.messaggio = messaggio;
	}

	@XmlElement(required = true)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@XmlElement(required = true)
	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
}
