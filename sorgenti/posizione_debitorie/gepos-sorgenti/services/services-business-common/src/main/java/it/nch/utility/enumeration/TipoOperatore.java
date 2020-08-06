package it.nch.utility.enumeration;

import java.io.Serializable;

public enum TipoOperatore implements Serializable{
	AC("Amministratore"),
	OP("Utente");

	private final String description;

	TipoOperatore(String description){
		this.description = description;
	}
	public String getUpperDescription(){
		return description.toUpperCase();
	}
	public String getDescription(){
		return description;
	}
	public String getShortDescription(){
		return toString();
	}
}
