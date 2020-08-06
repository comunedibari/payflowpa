package it.tasgroup.idp.rs.model.creditore;

import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatistichePagamento implements Serializable {

	public StatistichePagamento() {
	}

	public StatistichePagamento(String creditore, String codFiscaleCreditore, String descrizioneCreditore, String tipoDebito,
								Date dataOraPagamentoDa, Date dataOraPagamentoA, Long numPagamentiEseguiti, BigDecimal totPagamentiEseguiti, Long numPagamentiIncassati, BigDecimal totPagamentiIncassati) {
		this.creditore = creditore;
		this.codFiscaleCreditore = codFiscaleCreditore;
		this.descrizioneCreditore = descrizioneCreditore;
		this.tipoDebito = tipoDebito;
		this.dataOraPagamentoDa = dataOraPagamentoDa;
		this.dataOraPagamentoA = dataOraPagamentoA;
		this.numPagamentiEseguiti = numPagamentiEseguiti;
		this.totPagamentiEseguiti = totPagamentiEseguiti;
		this.numPagamentiIncassati = numPagamentiIncassati;
		this.totPagamentiIncassati = totPagamentiIncassati;
	}

	/**
	 * Statistiche pagamento in un determinato stato (nel periodo specificato nella classe esterna)
	 */
	public static class StatistichePagamentiPerStato {
		private EnumStatoPagamento statoPagamento;
		private Long numPagamenti;

		/**
		 * Stato pagamento
		 */
		@XmlElement(required=true)
		public EnumStatoPagamento getStatoPagamento() {
			return statoPagamento;
		}
		public void setStatoPagamento(EnumStatoPagamento statoPagamento) {
			this.statoPagamento = statoPagamento;
		}

		/**
		 * Numero pagamenti nello stato considerato
		 */
		@XmlElement(required=true)
		public Long getNumPagamenti() {
			return numPagamenti;
		}
		public void setNumPagamenti(Long numPagamenti) {
			this.numPagamenti = numPagamenti;
		}



	}

	/**
	 * Statistiche pagamento per un determinato PSP (nel periodo specificato nella classe esterna)
	 */
	public static class StatistichePagamentiPerCanalePagamento {

		private String identificativoPSP;
		private String descrizionePSP;
		private String identificativoCanale;
		private EnumTipoVersamento tipoVersamento;
		private Long numPagamentiEseguiti;
		private Long numPagamentiIncassati;
		private BigDecimal totPagamentiIncassati;
		private BigDecimal totPagamentiEseguiti;

		public StatistichePagamentiPerCanalePagamento() {
		}



		public StatistichePagamentiPerCanalePagamento(String identificativoPSP, String descrizionePSP, String identificativoCanale,
													  EnumTipoVersamento tipoVersamento, Long numPagamentiEseguiti, BigDecimal totPagamentiEseguiti,
													  Long numPagamentiIncassati, BigDecimal totPagamentiIncassati) {
			this.identificativoPSP = identificativoPSP;
			this.descrizionePSP = descrizionePSP;
			this.identificativoCanale = identificativoCanale;
			this.tipoVersamento = tipoVersamento;
			this.numPagamentiEseguiti = numPagamentiEseguiti;
			this.totPagamentiEseguiti = totPagamentiEseguiti;
			this.numPagamentiIncassati = numPagamentiIncassati;
			this.totPagamentiIncassati = totPagamentiIncassati;
		}

		private List<StatistichePagamentiPerStato> statistichePerStato;

		/**
		 * Identificativo del PSP
		 */
		@XmlElement(required=true)
		public String getIdentificativoPSP() {
			return identificativoPSP;
		}

		public void setIdentificativoPSP(String identificativoPSP) {
			this.identificativoPSP = identificativoPSP;
		}

		/**
		 * Descrizione/ragione sociale del PSP
		 */
		@XmlElement(required=true)
		public String getDescrizionePSP() {
			return descrizionePSP;
		}

		public void setDescrizionePSP(String descrizionePSP) {
			this.descrizionePSP = descrizionePSP;
		}

		/**
		 * Identificativo del canale di pagamento esposto dal PSP
		 */
		@XmlElement(required=true)
		public String getIdentificativoCanale() {
			return identificativoCanale;
		}

		public void setIdentificativoCanale(String identificativoCanale) {
			this.identificativoCanale = identificativoCanale;
		}

		/**
		 * Tipo Versamento
		 */
		@XmlElement(required=true)
		public EnumTipoVersamento getTipoVersamento() {
			return tipoVersamento;
		}

		public void setTipoVersamento(EnumTipoVersamento tipoVersamento) {
			this.tipoVersamento = tipoVersamento;
		}

		/**
		 * Numero pagamenti eseguiti sul PSP/Canale
		 */
		@XmlElement(required=true)
		public Long getNumPagamentiEseguiti() {
			return numPagamentiEseguiti;
		}

		public void setNumPagamentiEseguiti(Long numPagamentiEseguiti) {
			this.numPagamentiEseguiti = numPagamentiEseguiti;
		}

		/**
		 * Importo totale transato sul PSP/Canale
		 */
		@XmlElement(required=true)
		public BigDecimal getTotPagamentiEseguiti() {
			return totPagamentiEseguiti;
		}

		public void setTotPagamentiEseguiti(BigDecimal totPagamentiEseguiti) {
			this.totPagamentiEseguiti = totPagamentiEseguiti;
		}

		@XmlElement(required=true)
		public Long getNumPagamentiIncassati() {
			return numPagamentiIncassati;
		}

		public void setNumPagamentiIncassati(Long numPagamentiIncassati) {
			this.numPagamentiIncassati = numPagamentiIncassati;
		}

		@XmlElement(required=true)
		public BigDecimal getTotPagamentiIncassati() {
			return totPagamentiIncassati;
		}

		public void setTotPagamentiIncassati(BigDecimal totPagamentiIncassati) {
			this.totPagamentiIncassati = totPagamentiIncassati;
		}

		/**
		 *  Statistiche pagamenti per stato (se richieste)
		 */
		public List<StatistichePagamentiPerStato> getStatistichePerStato() {
			return statistichePerStato;
		}

		public void setStatistichePerStato(
				List<StatistichePagamentiPerStato> statistichePerStato) {
			this.statistichePerStato = statistichePerStato;
		}

	}

	//------------------------------------
	// Intestazione report
	//------------------------------------
    private String creditore;
    private String codFiscaleCreditore;
    private String descrizioneCreditore;
    private String tipoDebito;
	private Date dataOraPagamentoDa;
	private Date dataOraPagamentoA;

	// ------------------------------------
	// Risultati
	// ------------------------------------
	private Long numPagamentiEseguiti;
	private BigDecimal totPagamentiEseguiti;
	private Long numPagamentiIncassati;
	private BigDecimal totPagamentiIncassati;
    private List<StatistichePagamentiPerStato> statistichePerStato;

	// ------------------------------------
	// Risultati per canale
	// ------------------------------------
    private List<StatistichePagamentiPerCanalePagamento> statistichePerCanalePagamento;

    /**
     * Identificativo del creditore (assegnato all'atto del censimento sulla piattaforma)
     */
    @XmlElement(required=true)
	public String getCreditore() {
		return creditore;
	}

	public void setCreditore(String creditore) {
		this.creditore = creditore;
	}

	/**
	 * Codice fiscale del creditore
	 */
	@XmlElement(required=true)
	public String getCodFiscaleCreditore() {
		return codFiscaleCreditore;
	}

	public void setCodFiscaleCreditore(String codFiscaleCreditore) {
		this.codFiscaleCreditore = codFiscaleCreditore;
	}

	/**
	 * Descrizione / Ragione sociale del creditore
	 */
	@XmlElement(required=true)
	public String getDescrizioneCreditore() {
		return descrizioneCreditore;
	}

	public void setDescrizioneCreditore(String descrizioneCreditore) {
		this.descrizioneCreditore = descrizioneCreditore;
	}

	/**
	 * Tipo Debito (solo se specificato nel filtro di richiesta)
	 */
	@XmlElement(required=true)
	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	/**
	 * Intervallo di estrazione. Timestamp iniziale
	 */
	@XmlElement(required=true)
	public Date getDataOraPagamentoDa() {
		return dataOraPagamentoDa;
	}

	public void setDataOraPagamentoDa(Date dataOraPagamentoDa) {
		this.dataOraPagamentoDa = dataOraPagamentoDa;
	}


	/**
	 * Intervallo di estrazione. Timestamp finale.
	 */
	@XmlElement(required=true)
	public Date getDataOraPagamentoA() {
		return dataOraPagamentoA;
	}

	public void setDataOraPagamentoA(Date dataOraPagamentoA) {
		this.dataOraPagamentoA = dataOraPagamentoA;
	}

	/**
	 * Numero pagamenti eseguiti nel periodo
	 */
	@XmlElement(required=true)
	public Long getNumPagamentiEseguiti() {
		return numPagamentiEseguiti;
	}

	public void setNumPagamentiEseguiti(Long numPagamentiEseguiti) {
		this.numPagamentiEseguiti = numPagamentiEseguiti;
	}

	/**
	 * Totale transato nel periodo
	 */
	@XmlElement(required=true)
	public BigDecimal getTotPagamentiEseguiti() {
		return totPagamentiEseguiti;
	}

	public void setTotPagamentiEseguiti(BigDecimal totPagamentiEseguiti) {
		this.totPagamentiEseguiti = totPagamentiEseguiti;
	}

	/**
	 * Numero pagamenti incassati nel periodo (aventi flagIncasso=RIACCREDITATO_ENTE)
	 */
	@XmlElement(required=true)
	public Long getNumPagamentiIncassati() {
		return numPagamentiIncassati;
	}

	public void setNumPagamentiIncassati(Long numPagamentiIncassati) {
		this.numPagamentiIncassati = numPagamentiIncassati;
	}

	/**
	 * Totale incassato nel periodo
	 */
	@XmlElement(required=true)
	public BigDecimal getTotPagamentiIncassati() {
		return totPagamentiIncassati;
	}

	public void setTotPagamentiIncassati(BigDecimal totPagamentiIncassati) {
		this.totPagamentiIncassati = totPagamentiIncassati;
	}

	/**
	 * Statistiche per stato,  solo se richiesto
	 */
	public List<StatistichePagamentiPerStato> getStatistichePerStato() {
		return statistichePerStato;
	}

	public void setStatistichePerStato(
			List<StatistichePagamentiPerStato> statistichePerStato) {
		this.statistichePerStato = statistichePerStato;
	}

	/**
	 * Statistiche per PSP/Canale, solo se richiesto
	 */
	public List<StatistichePagamentiPerCanalePagamento> getStatistichePerCanalePagamento() {
		return statistichePerCanalePagamento;
	}

	public void setStatistichePerCanalePagamento(
			List<StatistichePagamentiPerCanalePagamento> statistichePerCanalePagamento) {
		this.statistichePerCanalePagamento = statistichePerCanalePagamento;
	}



}
