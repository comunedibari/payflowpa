package it.nch.idp.eventi;

import java.sql.Timestamp;

public class EventiVO {
   
	private Long id;
	private String codiceEvento;
	private String datiEvento;
	private Timestamp dataUltimaEsecuz;
	private int numeroTentativi;
	private String stato;
	private String descrStato;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodiceEvento() {
		return codiceEvento;
	}
	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}
	public String getDatiEvento() {
		return datiEvento;
	}
	public void setDatiEvento(String datiEvento) {
		this.datiEvento = datiEvento;
	}
	public Timestamp getDataUltimaEsecuz() {
		return dataUltimaEsecuz;
	}
	public void setDataUltimaEsecuz(Timestamp dataUltimaEsecuz) {
		this.dataUltimaEsecuz = dataUltimaEsecuz;
	}
	public int getNumeroTentativi() {
		return numeroTentativi;
	}
	public void setNumeroTentativi(int numeroTentativi) {
		this.numeroTentativi = numeroTentativi;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDescrStato() {
		return descrStato;
	}
	public void setDescrStato(String descrStato) {
		this.descrStato = descrStato;
	}
}
