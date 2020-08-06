package it.tasgroup.iris.dto;

import java.io.Serializable;

public class FunzioniPropDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String funzione;
	private String descrizioneFunzione;
	private String abilitata;

	// private String statoFunzione;

	public String getFunzione() {
		return funzione;
	}

	public void setFunzione(String funzione) {
		this.funzione = funzione;
	}

	public String getAbilitata() {
		return abilitata;
	}

	public void setAbilitata(String abilitata) {
		this.abilitata = abilitata;
	}

	public String getDescrizioneFunzione() {
		return descrizioneFunzione;
	}

	public void setDescrizioneFunzione(String descrizioneFunzione) {
		this.descrizioneFunzione = descrizioneFunzione;
	}

}
