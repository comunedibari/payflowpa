/**
 * 
 */
package it.tasgroup.iris.dto;

import java.math.BigDecimal;

/**
 * @author pazzik
 *
 */
public class RichiestaAutorizzazioneCBILLDTO {
	
	private String codiceRicercaPagamento;
	
	private String siaEnte;
	
	private BigDecimal importoPagamento;
	
	
	private boolean erroreSeNonPagabile;
	
	// TODO PAZZIK: GESTIRE PER TOTEM ASL
	private String tipoCodicePagamento;
	
	private String codiceTributoEnte;
	

	public String getCodiceRicercaPagamento() {
		return codiceRicercaPagamento;
	}

	public void setCodiceRicercaPagamento(String codiceRicercaPagamento) {
		this.codiceRicercaPagamento = codiceRicercaPagamento;
	}

	public String getSiaEnte() {
		return siaEnte;
	}

	public void setSiaEnte(String siaEnte) {
		this.siaEnte = siaEnte;
	}

	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	
	public boolean isErroreSeNonPagabile() {
		return erroreSeNonPagabile;
	}

	public void setErroreSeNonPagabile(boolean erroreSeNonPagabile) {
		this.erroreSeNonPagabile = erroreSeNonPagabile;
	}

	public String getTipoCodicePagamento() {
		return tipoCodicePagamento;
	}

	public void setTipoCodicePagamento(String tipoCodicePagamento) {
		this.tipoCodicePagamento = tipoCodicePagamento;
	}

	public String getCodiceTributoEnte() {
		return codiceTributoEnte;
	}

	public void setCodiceTributoEnte(String codiceTributoEnte) {
		this.codiceTributoEnte = codiceTributoEnte;
	}

	@Override
	public String toString() {
		return "RichiestaAutorizzazioneCBILLDTO [codiceRicercaPagamento="
				+ codiceRicercaPagamento + ", siaEnte=" + siaEnte
				+ ", importoPagamento=" + importoPagamento				
				+ ", erroreSeNonPagabile=" + erroreSeNonPagabile
				+ ", tipoCodicePagamento=" + tipoCodicePagamento
				+ ", codiceTributoEnte=" + codiceTributoEnte + "]";
	}

}
