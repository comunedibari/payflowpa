package it.nch.idp.backoffice.tavolooperativo;

import java.math.BigDecimal;
import java.util.Date;

public class FlussoBonificoVO{

	private String idBonifico;
	private String idRiconciliazione;
	private Date dataValuta;
	private String beneficiario;
	private String iban;
	private BigDecimal importo;
	private String idPagamentoCondizione;

	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}
	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}
	public Date getDataValuta() {
		return dataValuta;
	}
	public void setDataValuta(Date dataValuta) {
		this.dataValuta = dataValuta;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getIdBonifico() {
		return idBonifico;
	}
	public void setIdBonifico(String idBonifico) {
		this.idBonifico = idBonifico;
	}
	public String getIdPagamentoCondizione() {
		return idPagamentoCondizione;
	}
	public void setIdPagamentoCondizione(String idPagamentoCondizione) {
		this.idPagamentoCondizione = idPagamentoCondizione;
	}

}
