package it.tasgroup.idp.cart.core.model;

import java.io.Serializable;
import java.util.Date;

public class GestioneModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private Long idMessaggio;
	private TipoGestione tipo;
	private Date inizioGestione;
	private Long tempoGestione;
	private String idEgov;
	private ErroreComponenteModel erroreComponente = null;
	private Integer httpResponseCode;
	private String httpHeaders;
	private MessaggioModel messaggio=null;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getIdMessaggio() {
		return idMessaggio;
	}
	public void setIdMessaggio(Long idMessaggio) {
		this.idMessaggio = idMessaggio;
	}
	public TipoGestione getTipo() {
		return tipo;
	}
	public void setTipo(TipoGestione tipo) {
		this.tipo = tipo;
	}
	public Date getInizioGestione() {
		return inizioGestione;
	}
	public void setInizioGestione(Date inizioGestione) {
		this.inizioGestione = inizioGestione;
	}
	public String getIdEgov() {
		return idEgov;
	}
	public void setIdEgov(String idEgov) {
		this.idEgov = idEgov;
	}
	public ErroreComponenteModel getErroreComponente() {
		return erroreComponente;
	}
	public void setErroreComponente(ErroreComponenteModel erroreComponente) {
		this.erroreComponente = erroreComponente;
	}
	public Long getTempoGestione() {
		return tempoGestione;
	}
	public void setTempoGestione(Long tempoGestione) {
		this.tempoGestione = tempoGestione;
	}
	public Integer getHttpResponseCode() {
		return httpResponseCode;
	}
	public void setHttpResponseCode(Integer httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}
	public String getHttpHeaders() {
		return httpHeaders;
	}
	public void setHttpHeaders(String httpHeaders) {
		this.httpHeaders = httpHeaders;
	}
	public MessaggioModel getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(MessaggioModel messaggio) {
		this.messaggio = messaggio;
	}
	
}
