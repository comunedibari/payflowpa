package it.tasgroup.idp.pojo;

public class VerificaStatoPagamentoInput {
	
	private String idPagamento;
	private String tipoPendenza;
	private String cdEnte;
	private String sil;
	
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public String getTipoPendenza() {
		return tipoPendenza;
	}
	public void setTipoPendenza(String tipoPendenza) {
		this.tipoPendenza = tipoPendenza;
	}
	public String getCdEnte() {
		return cdEnte;
	}
	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}
	public String getSil() {
		return sil;
	}
	public void setSil(String sil) {
		this.sil = sil;
	}
	
}
