package it.tasgroup.iris.dto;

import java.io.Serializable;

public class ApplicazioniDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codiceApplicazione;
	private String descrizione;
	
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


}
