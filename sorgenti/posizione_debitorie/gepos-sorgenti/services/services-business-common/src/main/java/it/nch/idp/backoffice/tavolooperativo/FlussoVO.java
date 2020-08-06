package it.nch.idp.backoffice.tavolooperativo;

import java.math.BigDecimal;
import java.util.Date;

public class FlussoVO {

	private Date dataCreazione;
	private Date dataSpedRic;
	private String tipoFlusso;
	private String statoFlusso;
	private String nomeSupporto;
	private String nomeFile;
	private BigDecimal importo;
	private Integer numRecord;
	private Integer numProgressivo;
	private String idRendicontazione;
	private Date dataValuta;
	private String iban;

	public Date getDataValuta() {
		return dataValuta;
	}
	public void setDataValuta(Date dataValuta) {
		this.dataValuta = dataValuta;
	}
	public String getIdRendicontazione() {
		return idRendicontazione;
	}
	public void setIdRendicontazione(String idRendicontazione) {
		this.idRendicontazione = idRendicontazione;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Date getDataSpedRic() {
		return dataSpedRic;
	}
	public void setDataSpedRic(Date dataSpedRic) {
		this.dataSpedRic = dataSpedRic;
	}
	public String getTipoFlusso() {
		return tipoFlusso;
	}
	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
	}
	public String getStatoFlusso() {
		return statoFlusso;
	}
	public void setStatoFlusso(String statoFlusso) {
		this.statoFlusso = statoFlusso;
	}
	public String getNomeSupporto() {
		return nomeSupporto;
	}
	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public Integer getNumRecord() {
		return numRecord;
	}
	public void setNumRecord(Integer numRecord) {
		this.numRecord = numRecord;
	}
	public Integer getNumProgressivo() {
		return numProgressivo;
	}
	public void setNumProgressivo(Integer numProgressivo) {
		this.numProgressivo = numProgressivo;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}

}
