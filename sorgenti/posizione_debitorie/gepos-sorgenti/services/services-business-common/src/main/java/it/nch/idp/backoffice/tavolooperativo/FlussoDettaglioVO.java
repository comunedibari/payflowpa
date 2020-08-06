package it.nch.idp.backoffice.tavolooperativo;

import java.util.Collection;

public class FlussoDettaglioVO extends FlussoVO {

	private String idBonifico;
	private String idEsito;
	private String mittente;
	private String ricevente;
	private Integer numDisp;
	private String idRiconciliazione;
	private Collection listaFlussiDettaglio;

	public Collection getListaFlussiDettaglio() {
		return listaFlussiDettaglio;
	}

	public void setListaFlussiDettaglio(Collection listaFlussiDettaglio) {
		this.listaFlussiDettaglio = listaFlussiDettaglio;
	}

	public String getIdBonifico() {
		return idBonifico;
	}
	
	public void setIdBonifico(String idBonifico) {
		this.idBonifico = idBonifico;
	}

	public String getIdEsito() {
		return idEsito;
	}

	public void setIdEsito(String idEsito) {
		this.idEsito = idEsito;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getRicevente() {
		return ricevente;
	}

	public void setRicevente(String ricevente) {
		this.ricevente = ricevente;
	}

	public Integer getNumDisp() {
		return numDisp;
	}

	public void setNumDisp(Integer numDisp) {
		this.numDisp = numDisp;
	}

	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}

	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}

	
	
	

}
