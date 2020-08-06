package it.tasgroup.idp.billerservices.rest.result;

import java.io.Serializable;


public class GetStatoResult implements Serializable { 
	
	private final static long serialVersionUID = 1L;
	protected Long idTrasmissione;
	protected String stato;
	 
	public Long getIdTrasmissione() {
		return idTrasmissione;
	}
	public void setIdTrasmissione(Long idTrasmissione) {
		this.idTrasmissione = idTrasmissione;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

}


