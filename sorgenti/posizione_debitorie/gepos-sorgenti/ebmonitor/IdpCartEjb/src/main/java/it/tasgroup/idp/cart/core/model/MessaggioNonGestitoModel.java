package it.tasgroup.idp.cart.core.model;

import java.util.Date;

public class MessaggioNonGestitoModel {
	
	private Long id;
	private TipoMessaggio tipo;
	private String tipoMittente;
	private String mittente;
	private Date dataRicezione;
	private String idEgov;
	private String codErrore;
	private String descrErrore;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoMessaggio getTipo() {
		return tipo;
	}
	public void setTipo(TipoMessaggio tipo) {
		this.tipo = tipo;
	}
	public String getTipoMittente() {
		return tipoMittente;
	}
	public void setTipoMittente(String tipoMittente) {
		this.tipoMittente = tipoMittente;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public String getIdEgov() {
		return idEgov;
	}
	public void setIdEgov(String idEgov) {
		this.idEgov = idEgov;
	}
	public String getCodErrore() {
		return codErrore;
	}
	public void setCodErrore(String codErrore) {
		this.codErrore = codErrore;
	}
	public String getDescrErrore() {
		return descrErrore;
	}
	public void setDescrErrore(String descrErrore) {
		this.descrErrore = descrErrore;
	}
	

}
