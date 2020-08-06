package it.tasgroup.idp.rs.model.pagamenti;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RichiestaNotificaPagamento {

	private String identificativoDominio;
	private String identificativoUnivocoVersamento;
	private EnumTipoNotifica tipoNotifica;
	private Integer indiceVersamento;
	private BigDecimal importoPagamento;
	private Date dataPagamento;
	private String codiceFiscaleVersante;
	private String identificativoUnivocoRiscossione;
	private String identificativoFlussoRiversamento;
	private Date dataRegolamento;
	private String TRN;
	private BigDecimal  importoRegolamento;
	private String riferimentoContabile;
	private Date dataContabile;
	private BigDecimal importoMovimentoContabile;
	
	
	public RichiestaNotificaPagamento() {

	}
	
	@XmlElement(required = true)
	public String getIdentificativoDominio() {
		return identificativoDominio;
	}

	public void setIdentificativoDominio(String identificativoDominio) {
		this.identificativoDominio = identificativoDominio;
	}

	@XmlElement(required = true)
	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}

	public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}
    
	@XmlElement(required = true)
	public EnumTipoNotifica getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(EnumTipoNotifica tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}
    
	@XmlElement
	public Integer getIndiceVersamento() {
		return indiceVersamento;
	}

	public void setIndiceVersamento(Integer indiceVersamento) {
		this.indiceVersamento = indiceVersamento;
	}

	@XmlElement
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
    
	@XmlElement
	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
    
	@XmlElement
	public String getCodiceFiscaleVersante() {
		return codiceFiscaleVersante;
	}

	public void setCodiceFiscaleVersante(String codiceFiscaleVersante) {
		this.codiceFiscaleVersante = codiceFiscaleVersante;
	}

	@XmlElement
	public String getIdentificativoUnivocoRiscossione() {
		return identificativoUnivocoRiscossione;
	}

	public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
		this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
	}
	
	@XmlElement
	public String getIdentificativoFlussoRiversamento() {
		return identificativoFlussoRiversamento;
	}

	public void setIdentificativoFlussoRiversamento(String identificativoFlussoRiversamento) {
		this.identificativoFlussoRiversamento = identificativoFlussoRiversamento;
	}
    
	@XmlElement
	public Date getDataRegolamento() {
		return dataRegolamento;
	}

	public void setDataRegolamento(Date dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}
	@XmlElement
	public String getTRN() {
		return TRN;
	}

	public void setTRN(String tRN) {
		TRN = tRN;
	}
	@XmlElement
	public BigDecimal getImportoRegolamento() {
		return importoRegolamento;
	}

	public void setImportoRegolamento(BigDecimal importoRegolamento) {
		this.importoRegolamento = importoRegolamento;
	}
	@XmlElement
	public String getRiferimentoContabile() {
		return riferimentoContabile;
	}

	public void setRiferimentoContabile(String riferimentoContabile) {
		this.riferimentoContabile = riferimentoContabile;
	}
	@XmlElement
	public Date getDataContabile() {
		return dataContabile;
	}

	public void setDataContabile(Date dataContabile) {
		this.dataContabile = dataContabile;
	}

	@XmlElement
	public BigDecimal getImportoMovimentoContabile() {
		return importoMovimentoContabile;
	}

	public void setImportoMovimentoContabile(BigDecimal importoMovimentoContabile) {
		this.importoMovimentoContabile = importoMovimentoContabile;
	}

}
