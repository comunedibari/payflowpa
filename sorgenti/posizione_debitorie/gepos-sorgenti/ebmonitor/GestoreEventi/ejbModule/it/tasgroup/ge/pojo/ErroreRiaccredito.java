package it.tasgroup.ge.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ErroreRiaccredito extends CommunicationEventDetail {
	public String cro;
	public Date dataScadenza;
	public String ente;
	public String tributo;
	public String descPendenza;
	public BigDecimal importo;
	public Date dataPagamento;
	public Integer progressivoCBI;
	public String causale;
	public String beneficiario;
	public String ibanBeneficiario;
	public String nomeSupporto;
	
	public String getCro() {
		return cro;
	}
	public void setCro(String cro) {
		this.cro = cro;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getTributo() {
		return tributo;
	}
	public void setTributo(String tributo) {
		this.tributo = tributo;
	}
	public String getDescPendenza() {
		return descPendenza;
	}
	public void setDescPendenza(String descPendenza) {
		this.descPendenza = descPendenza;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public Integer getProgressivoCBI() {
		return progressivoCBI;
	}
	public void setProgressivoCBI(Integer progressivoCBI) {
		this.progressivoCBI = progressivoCBI;
	}
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}
	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}
	public String getNomeSupporto() {
		return nomeSupporto;
	}
	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}
	
	
}
