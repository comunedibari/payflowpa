package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;
import java.math.BigDecimal;

public class AnonymousPaymentDTOLight implements Serializable {

	private static final long serialVersionUID = 1L;

	private String distinta;

	private String stPagamento;
	
	private String coPagante;
	private BigDecimal imPagato;
	private String cdTrbEnte;
	private String idEnte;

	// private String idPendenza;
	// private String idPendenzaente;
	// private String idTributo;
	// private String notePagamento;

	public String getDistinta() {
		return distinta;
	}

	public void setDistinta(String distinta) {
		this.distinta = distinta;
	}

	public String getStPagamento() {
		return stPagamento;
	}

	public void setStPagamento(String stPagamento) {
		this.stPagamento = stPagamento;
	}

	public String getCoPagante() {
		return coPagante;
	}

	public void setCoPagante(String coPagante) {
		this.coPagante = coPagante;
	}

	public BigDecimal getImPagato() {
		return imPagato;
	}

	public void setImPagato(BigDecimal imPagato) {
		this.imPagato = imPagato;
	}

	public String getCdTrbEnte() {
		return cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

}
