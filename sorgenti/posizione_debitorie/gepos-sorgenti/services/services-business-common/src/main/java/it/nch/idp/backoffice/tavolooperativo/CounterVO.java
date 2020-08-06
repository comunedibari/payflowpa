package it.nch.idp.backoffice.tavolooperativo;

import java.io.Serializable;


public class CounterVO implements Serializable{

	private static final long serialVersionUID = -2963017134229000102L;
	private Long numero;
	
	public Long getNumero() {
		return numero;
	}

	public void setStato(Long numero) {
		this.numero = numero;
	}

}
