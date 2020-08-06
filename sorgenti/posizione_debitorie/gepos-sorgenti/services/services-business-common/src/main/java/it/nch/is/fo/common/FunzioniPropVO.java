package it.nch.is.fo.common;

import java.util.Collection;

public class FunzioniPropVO {
	
	private String funzione;
	private String descrizioneFunzione;
	private String abilitata;
	private String statoFunzione;
	
	public String getFunzione() {
		return funzione;
	}
	public String getStatoFunzione() {
		return statoFunzione;
	}
	public void setStatoFunzione(String statoFunzione) {
		this.statoFunzione = statoFunzione;
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
