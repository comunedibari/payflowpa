package it.tasgroup.idp.billerservices.rest.result;

import java.io.Serializable;

public class SendResult implements Serializable {
	
	 private final static long serialVersionUID = 1L;
	 protected String idTrasmissione;
	 
	public String getIdTrasmissione() {
		return idTrasmissione;
	}
	public void setIdTrasmissione(String idTrasmissione) {
		this.idTrasmissione = idTrasmissione;
	}

}
