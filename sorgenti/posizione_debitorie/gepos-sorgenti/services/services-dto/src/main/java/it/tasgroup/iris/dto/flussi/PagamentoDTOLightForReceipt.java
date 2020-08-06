package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;

public class PagamentoDTOLightForReceipt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7228816094995844688L;
	
    private Long   id;
	private String idPendenzaEnte;
	private String idPagamentoEnte;
	private String idEnte;
	private String codPagante;
	private String codPagamento;
	private String codAziendaSanitariaEnte;
	private boolean externalReceipt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}
	public String getIdPagamentoEnte() {
		return idPagamentoEnte;
	}
	public void setIdPagamentoEnte(String idPagamentoEnte) {
		this.idPagamentoEnte = idPagamentoEnte;
	}
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getCodPagante() {
		return codPagante;
	}
	public void setCodPagante(String codPagante) {
		this.codPagante = codPagante;
	}
	public String getCodPagamento() {
		return codPagamento;
	}
	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}
	public String getCodAziendaSanitariaEnte() {
		return codAziendaSanitariaEnte;
	}
	public void setCodAziendaSanitariaEnte(String codAziendaSanitariaEnte) {
		this.codAziendaSanitariaEnte = codAziendaSanitariaEnte;
	}
	public boolean isExternalReceipt() {
		return externalReceipt;
	}
	public void setExternalReceipt(boolean externalReceipt) {
		this.externalReceipt = externalReceipt;
	}
	
    
    
}