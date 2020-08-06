package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class RendicontazioniDTOLight implements Serializable{
	
	private Long id;
	private Timestamp dataCreazione;
	private String stato;
	private BigDecimal importo;
	private Integer numeroEsiti;
	private Integer numEsitiInsoluto;
	private Integer numEsitiPagato;
	private Timestamp dataRicezione;
	private String utenteCreatore;
	private String codRendicontazione;
	
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

	public Timestamp getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(Timestamp dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public String getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public String getCodRendicontazione() {
		return codRendicontazione;
	}

	public void setCodRendicontazione(String codRendicontazione) {
		this.codRendicontazione = codRendicontazione;
	}

	public Integer getNumEsitiPagato() {
		return numEsitiPagato;
	}

	public void setNumEsitiPagato(Integer numEsitiPagato) {
		this.numEsitiPagato = numEsitiPagato;
	}


}
