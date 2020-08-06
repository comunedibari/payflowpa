package it.tasgroup.iris.dto.flussi;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class DistinteRiaccreditoDTO implements Serializable{

	private Long id;
	private String stato;
	private BigDecimal importo;
	private BigDecimal importoCommissione;
	private Timestamp dataCreazione;
	private Timestamp dataSpedizione;
	private Integer numeroDisposizioni;
	private String divisa;
	private String codTransazione;
	private CasellarioDispoDTO casellarioDispo;
	
	public CasellarioDispoDTO getCasellarioDispo() {
		return casellarioDispo;
	}
	public void setCasellarioDispo(CasellarioDispoDTO casellarioDispo) {
		this.casellarioDispo = casellarioDispo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public BigDecimal getImportoCommissione() {
		return importoCommissione;
	}
	public void setImportoCommissione(BigDecimal importoCommissione) {
		this.importoCommissione = importoCommissione;
	}
	public Timestamp getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Integer getNumeroDisposizioni() {
		return numeroDisposizioni;
	}
	public void setNumeroDisposizioni(Integer numeroDisposizioni) {
		this.numeroDisposizioni = numeroDisposizioni;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getCodTransazione() {
		return codTransazione;
	}
	public void setCodTransazione(String codTransazione) {
		this.codTransazione = codTransazione;
	}
	public void setDataSpedizione(Timestamp dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}
	public Timestamp getDataSpedizione() {
		return dataSpedizione;
	}
	
	
	
	
}