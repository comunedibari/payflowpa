package it.tasgroup.idp.billerservices.plugin.csv;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CSVRowPagamento {
	public enum Stato {DA_PAGARE, CANCELLATO, NON_PAGABILE, DA_PAGARE_VARIAZIONE, PAGATO, PAGATO_IRREGOLARE} 
	public enum EsitoVerifica {SCONOSCIUTO, POSIZIONE_NON_PRESENTE, POSIZIONE_NON_PAGABILE, PAGAMENTO_NON_ESEGUITO, PAGAMENTO_ESEGUITO}
	
	private long posizioneRaw;
	
	// Identificativo fisico
	private Long idPagamento;
	
	// Identificativo logico
	private Long idTrasmissione;
	private String creditore;
	private String tipoDebito;
	private String idDebito;
	private String idCondizione;
	
	// Dati corredo
	private List<Debitore> debitori;
	private String causaleDebito;
	private Integer annoRiferimento;
	private Date dataScadenza;
	private Date dataInizioValidita; 
	private Date dataFineValidita;
	private Double importoPagamento; 
	private List<Voce> voci;
	private Stato stato;
	private String noteDebito;
	private String causalePagamento;
	private String ibanRiaccredito;
	private String ibanBeneficiario;
	private Double totalePagato;
	private Date dataValutaAccredito;
	private String canalePagamento;
	private Date dataPagamento;
	private String notePagamento;
	private Boolean pagatoIDP;
	private EsitoVerifica esitoVerifica;
	
	public static class Debitore {
		private String codiceFiscale;
		private String anagrafica;

		public String getCodiceFiscale() {
			return codiceFiscale;
		}

		public void setCodiceFiscale(String codiceFiscale) {
			this.codiceFiscale = codiceFiscale;
		}

		public String getAnagrafica() {
			return anagrafica;
		}

		public void setAnagrafica(String anagrafica) {
			this.anagrafica = anagrafica;
		}
		
	}
	
	public static class Voce {
		private String idVoce;
		private Double importo;
		
		public String getIdVoce() {
			return idVoce;
		}
		public void setIdVoce(String idVoce) {
			this.idVoce = idVoce;
		}
		public Double getImporto() {
			return importo;
		}
		public void setImporto(Double importo) {
			this.importo = importo;
		} 

	}

	public Long getIdTrasmissione() {
		return idTrasmissione;
	}

	public void setIdTrasmissione(long idTrasmissione) {
		this.idTrasmissione = idTrasmissione;
	}

	public String getCreditore() {
		return creditore;
	}

	public void setCreditore(String creditore) {
		this.creditore = creditore;
	}

	public List<Debitore> getDebitori() {
		return debitori;
	}

	public void setDebitori(List<Debitore> debitori) {
		this.debitori = debitori;
	}

	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	public String getIdDebito() {
		return idDebito;
	}

	public void setIdDebito(String idDebito) {
		this.idDebito = idDebito;
	}

	public Long getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(Long idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getCausaleDebito() {
		return causaleDebito;
	}

	public void setCausaleDebito(String causaleDebito) {
		this.causaleDebito = causaleDebito;
	}

	public Integer getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(Integer annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public Double getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(Double importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	public List<Voce> getVoci() {
		return voci;
	}

	public void setVoci(List<Voce> voci) {
		this.voci = voci;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public String getNoteDebito() {
		return noteDebito;
	}

	public void setNoteDebito(String noteDebito) {
		this.noteDebito = noteDebito;
	}

	public String getCausalePagamento() {
		return causalePagamento;
	}

	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}

	public String getIbanRiaccredito() {
		return ibanRiaccredito;
	}

	public void setIbanRiaccredito(String ibanRiaccredito) {
		this.ibanRiaccredito = ibanRiaccredito;
	}

	public Double getTotalePagato() {
		return totalePagato;
	}

	public void setTotalePagato(Double totalePagato) {
		this.totalePagato = totalePagato;
	}

	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
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

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getNotePagamento() {
		return notePagamento;
	}

	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}

	public Boolean isPagatoIDP() {
		if (pagatoIDP==null) return false;
		return pagatoIDP;
	}

	public void setPagatoIDP(Boolean pagatoIDP) {
		this.pagatoIDP = pagatoIDP;
	}

	public EsitoVerifica getEsitoVerifica() {
		return esitoVerifica;
	}

	public void setEsitoVerifica(EsitoVerifica esitoVerifica) {
		this.esitoVerifica = esitoVerifica;
	}

	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	public long getPosizioneRaw() {
		return posizioneRaw;
	}

	public void setPosizioneRaw(long posizioneRaw) {
		this.posizioneRaw = posizioneRaw;
	}

}
