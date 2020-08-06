package it.tasgroup.iris.dto;

import it.tasgroup.services.util.enumeration.EnumDocExtension;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NotificaPagamentoDTO implements Serializable{
	
	private TestataMessaggioDTO testata; 
	
	private String codiceTransazione;
	
	private String dataOraOperazione;
	
	private String statoNotifica;
	
	private String flagIncasso;
	
	private String canale;
	
	// TODO PAZZIK IMPLEMENTARE UTILIZZO
	private String idPagamentoEnte;
	
	private boolean aggiornaDistinteAnnullate = false;
	
	private boolean ignoraDistintaPagataStessoCanale = false;
	
	private BigDecimal importo;
	
	private BigDecimal importoCommissioni;
	
	private Date dataPagamento;
	
	private String codiceFiscalePagante;
	
	private String codiceContestoPagamento;
	
	private EnumDocExtension formatoDocumentoFiscale;
	
	private boolean nascondiDocumentoPagamentoIris = false;
	
	private List<DettaglioNotificaDTO> dettagli = new ArrayList<DettaglioNotificaDTO>();
	
	public TestataMessaggioDTO getTestata() {
		return testata;
	}
	public void setTestata(TestataMessaggioDTO testata) {
		this.testata = testata;
	}
	public String getCodiceTransazione() {
		return codiceTransazione;
	}
	public void setCodiceTransazione(String codiceTransazione) {
		this.codiceTransazione = codiceTransazione;
	}
	public String getDataOraOperazione() {
		return dataOraOperazione;
	}
	public void setDataOraOperazione(String dataOraOperazione) {
		this.dataOraOperazione = dataOraOperazione;
	}
	public String getStatoNotifica() {
		return statoNotifica;
	}
	public void setStatoNotifica(String statoNotifica) {
		this.statoNotifica = statoNotifica;
	}
	public String getCanale() {
		return canale;
	}
	public void setCanale(String canale) {
		this.canale = canale;
	}
	public String getIdPagamentoEnte() {
		return idPagamentoEnte;
	}
	public void setIdPagamentoEnte(String idPagamentoEnte) {
		this.idPagamentoEnte = idPagamentoEnte;
	}
	public boolean isAggiornaDistinteAnnullate() {
		return aggiornaDistinteAnnullate;
	}
	public void setAggiornaDistinteAnnullate(boolean aggiornaDistinteAnnullate) {
		this.aggiornaDistinteAnnullate = aggiornaDistinteAnnullate;
	}
	
	public boolean isIgnoraDistintaPagataStessoCanale() {
		return ignoraDistintaPagataStessoCanale;
	}
	
	public void setIgnoraDistintaPagataStessoCanale(boolean ignoraDistintaPagataStessoCanale) {
		this.ignoraDistintaPagataStessoCanale = ignoraDistintaPagataStessoCanale;
	}
	
	public BigDecimal getImportoCommissioni() {
		return importoCommissioni;
	}
	
	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
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
	
	public String getCodiceFiscalePagante() {
		return codiceFiscalePagante;
	}
	
	public void setCodiceFiscalePagante(String codiceFiscalePagante) {
		this.codiceFiscalePagante = codiceFiscalePagante;
	}
	
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}
	
	public void setCodiceContestoPagamento(String contestoPagamento) {
		this.codiceContestoPagamento = contestoPagamento;
	}
	
	public List<DettaglioNotificaDTO> getDettagli() {
		return dettagli;
	}
	
	public void setDettagli(List<DettaglioNotificaDTO> dettagli) {
		this.dettagli = dettagli;
	}
	
	public String getFlagIncasso() {
		return flagIncasso;
	}
	
	public void setFlagIncasso(String flagIncasso) {
		this.flagIncasso = flagIncasso;
	}
	
	public EnumDocExtension getFormatoDocumentoFiscale() {
		return formatoDocumentoFiscale;
	}
	
	public void setFormatoDocumentoFiscale(EnumDocExtension formatoDocumentoFiscale) {
		this.formatoDocumentoFiscale = formatoDocumentoFiscale;
	}
	public boolean isNascondiDocumentoPagamentoIris() {
		return nascondiDocumentoPagamentoIris;
	}
	public void setNascondiDocumentoPagamentoIris(
			boolean nascondiDocumentoPagamentoIris) {
		this.nascondiDocumentoPagamentoIris = nascondiDocumentoPagamentoIris;
	}
	
}