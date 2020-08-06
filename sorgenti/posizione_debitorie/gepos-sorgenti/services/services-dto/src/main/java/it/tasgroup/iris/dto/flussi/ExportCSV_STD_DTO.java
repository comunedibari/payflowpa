/**
 * 
 */
package it.tasgroup.iris.dto.flussi;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author pazzik
 *
 */
public class ExportCSV_STD_DTO {
	
	private String id;
	
	private String creditore;
	
	private String tipoDebito;
	
	private String debitori;
	
	private String idDebito;
	
	private String idPagamento;
	
	private Timestamp dataScadenza;
	
	private Timestamp dataInizioValidita;
	
	private Timestamp dataFineValidita;
	
	private String causalePagamento;
	
	private String statoPagamento;
	
	private BigDecimal importoPagamento;
	
	private String vociPagamento;
	
	private Integer annoRiferimento;
	
	private String noteDebito;
	
	private String causaleDebito;
	
	private String ibanRiaccredito;
	
	private BigDecimal totalePagato;
	
	private Date dataValutaAccredito;
	
	private String canalePagamento;
	
	private Timestamp dataPagamento;
	
	private String notePagamento;
	
	private String pagatoIris;

	private String istitutoAttestante;
	
	private String idAttestante;
	
	private String tipoIdAttestante;

	public String getCreditore() {
		return creditore;
	}
	
	public String getIstitutoAttestante() {
		return istitutoAttestante;
	}

	public void setIstitutoAttestante(String istitutoAttestante) {
		this.istitutoAttestante = istitutoAttestante;
	}

	public String getIdAttestante() {
		return idAttestante;
	}

	public void setIdAttestante(String idAttestante) {
		this.idAttestante = idAttestante;
	}

	public String getTipoIdAttestante() {
		return tipoIdAttestante;
	}

	public void setTipoIdAttestante(String tipoIdAttestante) {
		this.tipoIdAttestante = tipoIdAttestante;
	}

	public void setCreditore(String creditore) {
		this.creditore = creditore;
	}

	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	public String getDebitori() {
		return debitori;
	}

	public void setDebitori(String debitori) {
		this.debitori = debitori;
	}

	public String getIdDebito() {
		return idDebito;
	}

	public void setIdDebito(String idDebito) {
		this.idDebito = idDebito;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Timestamp getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getCausalePagamento() {
		return causalePagamento;
	}

	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}

	public String getStatoPagamento() {
		return statoPagamento;
	}

	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}

	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	public String getVociPagamento() {
		return vociPagamento;
	}

	public void setVociPagamento(String vociPagamento) {
		this.vociPagamento = vociPagamento;
	}

	public Integer getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(Integer annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public String getNoteDebito() {
		return noteDebito;
	}

	public void setNoteDebito(String noteDebito) {
		this.noteDebito = noteDebito;
	}

	public String getCausaleDebito() {
		return causaleDebito;
	}

	public void setCausaleDebito(String causaleDebito) {
		this.causaleDebito = causaleDebito;
	}

	public String getIbanRiaccredito() {
		return ibanRiaccredito;
	}

	public void setIbanRiaccredito(String ibanRiaccredito) {
		this.ibanRiaccredito = ibanRiaccredito;
	}

	public BigDecimal getTotalePagato() {
		return totalePagato;
	}

	public void setTotalePagato(BigDecimal totalePagato) {
		this.totalePagato = totalePagato;
	}

	public Date getDataValutaAccredito() {
		return dataValutaAccredito;
	}

	public void setDataValutaAccredito(Date dataValutaAccredito) {
		this.dataValutaAccredito = dataValutaAccredito;
	}

	public String getCanalePagamento() {
		return canalePagamento;
	}

	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}

	public Timestamp getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getPagatoIris() {
		return pagatoIris;
	}

	public void setPagatoIris(String pagatoIris) {
		this.pagatoIris = pagatoIris;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotePagamento() {
		return notePagamento;
	}

	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}
	
}
