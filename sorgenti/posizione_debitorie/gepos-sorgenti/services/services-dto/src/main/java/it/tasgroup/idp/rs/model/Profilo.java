package it.tasgroup.idp.rs.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Profilo {
	
	String anagrafica;
	String codiceFiscale;
	String email;
	
	public String getAnagrafica() {
		return anagrafica;
	}
	
	public void setAnagrafica(String anagrafica) {
		this.anagrafica = anagrafica;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}


}
