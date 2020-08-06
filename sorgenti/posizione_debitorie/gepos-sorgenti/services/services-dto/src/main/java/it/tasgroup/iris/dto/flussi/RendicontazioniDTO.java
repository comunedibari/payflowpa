package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class RendicontazioniDTO implements Serializable{
	
	private Long id;
	private String idFlusso;
	private String idPsp;
	private Timestamp dataCreazione;
	private Timestamp dataRegolamento;
	private String stato;
	private BigDecimal importo;
	private Integer numeroEsiti;
	private Integer numEsitiInsoluto;
	private String transRefNumber;
	
	private CasellarioInfoDTO casellarioInfo;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
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

	public Integer getNumeroEsiti() {
		return numeroEsiti;
	}

	public void setNumeroEsiti(Integer numeroEsiti) {
		this.numeroEsiti = numeroEsiti;
	}

	public Integer getNumEsitiInsoluto() {
		return numEsitiInsoluto;
	}

	public void setNumEsitiInsoluto(Integer numEsitiInsoluto) {
		this.numEsitiInsoluto = numEsitiInsoluto;
	}

	public CasellarioInfoDTO getCasellarioInfo() {
		return casellarioInfo;
	}

	public void setCasellarioInfo(CasellarioInfoDTO casellarioInfo) {
		this.casellarioInfo = casellarioInfo;
	}

	public String getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
	}

	public String getIdPsp() {
		return idPsp;
	}

	public void setIdPsp(String idPsp) {
		this.idPsp = idPsp;
	}

	public String getTransRefNumber() {
		return transRefNumber;
	}

	public void setTransRefNumber(String transRefNumber) {
		this.transRefNumber = transRefNumber;
	}

	public Timestamp getDataRegolamento() {
		return dataRegolamento;
	}

	public void setDataRegolamento(Timestamp dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}
	
	
}