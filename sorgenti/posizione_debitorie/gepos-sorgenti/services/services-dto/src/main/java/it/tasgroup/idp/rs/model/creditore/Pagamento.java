package it.tasgroup.idp.rs.model.creditore;
 
import it.tasgroup.idp.rs.enums.EnumStatoIncasso;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Rappresenta un pagamento che e' avvenuto sulla piattaforma con il relativo esito.
 */
@XmlRootElement
public class Pagamento {

	/**
	 *  Contiene i dettagli della transazione di pagamento
	 *  (con la quale il psp ha prelevato i fondi al debitore).
	 */
	public static class InformazioniTransazionePagamento implements Serializable {

		private String identiticativoPsp;
	    private String descrizionePsp;
	    private String identificativoCanalePagamento;
	    private EnumTipoVersamento tipoVersamento;
	    private Date dataOraTransazionePagamento;
	    private String identificativoUnivocoRiscossione;
	    private BigDecimal importoTransazionePagamento;
	    private BigDecimal importoCommissioniTransazionePagamento;
	    private Long numeroPagamentiTransazionePagamento;
	    private String modalitaPagamento;

		/**
		 * Identificativo del PSP.
		 */
	    @XmlElement(required = true)
		public String getIdentiticativoPsp() {
			return identiticativoPsp;
		}


		public void setIdentiticativoPsp(String identiticativoPsp) {
			this.identiticativoPsp = identiticativoPsp;
		}

		/**
		 * Descrizione del PSP.
		 */
		@XmlElement(required = true)
		public String getDescrizionePsp() {
			return descrizionePsp;
		}


		public void setDescrizionePsp(String descrizionePsp) {
			this.descrizionePsp = descrizionePsp;
		}

		/**
		 * Identificativo del canale di pagamento del PSP.
		 */
		@XmlElement(required = true)
		public String getIdentificativoCanalePagamento() {
			return identificativoCanalePagamento;
		}


		public void setIdentificativoCanalePagamento(
				String identificativoCanalePagamento) {
			this.identificativoCanalePagamento = identificativoCanalePagamento;
		}

		/**
		 * Tipo versamento
		 */
		@XmlElement(required = true)
		public EnumTipoVersamento getTipoVersamento() {
			return tipoVersamento;
		}


		public void setTipoVersamento(EnumTipoVersamento tipoVersamento) {
			this.tipoVersamento = tipoVersamento;
		}

		/**
		 * Data pagamento, assegnata dal PSP.
		 */
		@XmlElement(required = true)
		public Date getDataOraTransazionePagamento() {
			return dataOraTransazionePagamento;
		}

		public void setDataOraTransazionePagamento(Date dataOraTransazionePagamento) {
			this.dataOraTransazionePagamento = dataOraTransazionePagamento;
		}

		/**
		 * Identificativo univoco riscossione, assegnato dal PSP.
		 */
		@XmlElement(required = true)
		public String getIdentificativoUnivocoRiscossione() {
			return identificativoUnivocoRiscossione;
		}


		public void setIdentificativoUnivocoRiscossione(
				String identificativoUnivocoRiscossione) {
			this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
		}


		/**
		 * Importo transazione pagamento. In caso di pagamento con carrello, rappresenta l'importo totale della transazione.
		 */
		@XmlElement(required = true)
		public BigDecimal getImportoTransazionePagamento() {
			return importoTransazionePagamento;
		}


		public void setImportoTransazionePagamento(
				BigDecimal importoTransazionePagamento) {
			this.importoTransazionePagamento = importoTransazionePagamento;
		}


		/**
		 * Importo commissioni associate alla transazione di pagamento (informazione presente solo se resa disponbile dal PSP)
		 */
		public BigDecimal getImportoCommissioniTransazionePagamento() {
			return importoCommissioniTransazionePagamento;
		}

		public void setImportoCommissioniTransazionePagamento(
				BigDecimal importoCommissioniTransazionePagamento) {
			this.importoCommissioniTransazionePagamento = importoCommissioniTransazionePagamento;
		}


		/**
		 * Numero pagamenti nella transazione di pagamento. In caso di pagamento con carrello, rappresenta il numero di pagamenti contenuti nel carrello.
		 */
		@XmlElement(required = true)
		public Long getNumeroPagamentiTransazionePagamento() {
			return numeroPagamentiTransazionePagamento;
		}


		public void setNumeroPagamentiTransazionePagamento(
				Long numeroPagamentiTransazionePagamento) {
			this.numeroPagamentiTransazionePagamento = numeroPagamentiTransazionePagamento;
		}


		/**
		 * Modalita' pagamento.
		 */
		@XmlElement
		public String getModalitaPagamento() {
			return modalitaPagamento;
		}


		public void setModalitaPagamento(String modalitaPagamento) {
			this.modalitaPagamento = modalitaPagamento;
		}


	}

	/**
	 *  Contiene i dettagli della transazione di incasso
	 *  (con la quale il psp ha riversato i fondi al creditore).
	 */
	public static class InformazioniTransazioneIncasso implements Serializable {

		private String identiticativoPspIncasso;
	    private Date dataEsecuzioneIncasso;
	    private String trnIncasso;
	    private BigDecimal importoTotaleTransazioneIncasso;
	    private String tipoFlussoIncasso;
	    private String identificativoFlussoIncasso;
		private EnumStatoIncasso flagIncasso;
		private String descrizioneAttestante;
		private String tipoIdentificativoAttestatante;
		private String identificativoAttestante;
		private Pagamento pagamento; 
		
		public InformazioniTransazioneIncasso() {
		}
		
		public InformazioniTransazioneIncasso(String identiticativoPspIncasso, Date dataEsecuzioneIncasso, String trnIncasso, BigDecimal importoTotaleTransazioneIncasso, String tipoFlussoIncasso, String identificativoFlussoIncasso) {
			this.identiticativoPspIncasso = identiticativoPspIncasso;
			this.dataEsecuzioneIncasso = dataEsecuzioneIncasso;
			this.trnIncasso = trnIncasso;
			this.importoTotaleTransazioneIncasso = importoTotaleTransazioneIncasso;
			this.tipoFlussoIncasso = tipoFlussoIncasso;
			this.identificativoFlussoIncasso = identificativoFlussoIncasso;
		}

		public InformazioniTransazioneIncasso(String identiticativoPspIncasso, Date dataEsecuzioneIncasso, String trnIncasso, BigDecimal importoTotaleTransazioneIncasso, String tipoFlussoIncasso, String identificativoFlussoIncasso, EnumStatoIncasso statoIncasso) {
			this.identiticativoPspIncasso = identiticativoPspIncasso;
			this.dataEsecuzioneIncasso = dataEsecuzioneIncasso;
			this.trnIncasso = trnIncasso;
			this.importoTotaleTransazioneIncasso = importoTotaleTransazioneIncasso;
			this.tipoFlussoIncasso = tipoFlussoIncasso;
			this.identificativoFlussoIncasso = identificativoFlussoIncasso;
			this.flagIncasso = statoIncasso;
		}

		/**
		 * Stato incasso del pagamento
		 */
		@XmlElement(required = true)
		public EnumStatoIncasso getFlagIncasso() {
			return flagIncasso;
		}

		public void setFlagIncasso(EnumStatoIncasso flagIncasso) {
			this.flagIncasso = flagIncasso;
		}
		/**
		 * Identificativo PSP che ha prodotto l'incasso (Presente solo se posizione pagata e flag incasso = ACCREDITATO)
		 */
	    @XmlElement(required = true)
		public String getIdentiticativoPspIncasso() {
			return identiticativoPspIncasso;
		}


		public void setIdentiticativoPspIncasso(String identiticativoPspIncasso) {
			this.identiticativoPspIncasso = identiticativoPspIncasso;
		}

		/**
		 * Data esecuzione dell'incasso
		 */
		@XmlElement(required = true)
		public Date getDataEsecuzioneIncasso() {
			return dataEsecuzioneIncasso;
		}


		public void setDataEsecuzioneIncasso(Date dataEsecuzioneIncasso) {
			this.dataEsecuzioneIncasso = dataEsecuzioneIncasso;
		}

		/**
		 * Codice TRN della transazione di incasso
		 */
		@XmlElement(required = true)
		public String getTrnIncasso() {
			return trnIncasso;
		}


		public void setTrnIncasso(String trnIncasso) {
			this.trnIncasso = trnIncasso;
		}

		/**
		 * Importo totale della transazione di incasso.
		 * Se l'incasso della posizione e' avvenuto con un riversamento cumulativo, contiene il totale dell'accredito.
		 */
		@XmlElement(required = true)
		public BigDecimal getImportoTotaleTransazioneIncasso() {
			return importoTotaleTransazioneIncasso;
		}


		public void setImportoTotaleTransazioneIncasso(
				BigDecimal importoTotaleTransazioneIncasso) {
			this.importoTotaleTransazioneIncasso = importoTotaleTransazioneIncasso;
		}

		/**
		 * Tipo rendicontazione associata alla transazione di incasso..
		 * Presente solo se l'incasso della posizione e' avvenuto con un riversamento cumulativo da nodo pagamenti, contiene il valore 'FR' (Flusso Riversamento).
		 */
		public String getTipoFlussoIncasso() {
			return tipoFlussoIncasso;
		}


		public void setTipoFlussoIncasso(String tipoFlussoIncasso) {
			this.tipoFlussoIncasso = tipoFlussoIncasso;
		}

		/**
		 * Identificativo del flusso di rendicontazione  scalare associato alla transazione di incasso.
		 * Presente solo se l'incasso della posizione e' avvenuto con un riversamento cumulativo da nodo pagamenti, contiene il valore dell'id del flusso 'FR' (Flusso Riversamento).
		 */
		public String getIdentificativoFlussoIncasso() {
			return identificativoFlussoIncasso;
		}


		public void setIdentificativoFlussoIncasso(String identificativoFlussoIncasso) {
			this.identificativoFlussoIncasso = identificativoFlussoIncasso;
		}
		
		public void setDescrizioneAttestante(String descrizioneAttestante) {
			this.descrizioneAttestante = descrizioneAttestante;
		}
		
		public String getDescrizioneAttestante() {
			return descrizioneAttestante;
		}
		
		public void setTipoIdentificativoAttestatante(String tipoIdentificativoAttestatante) {
			this.tipoIdentificativoAttestatante = tipoIdentificativoAttestatante;
		}
		
		public String getTipoIdentificativoAttestatante() {
			return tipoIdentificativoAttestatante;
		}
		
		public void setIdentificativoAttestante(String identificativoAttestante) {
			this.identificativoAttestante = identificativoAttestante;
		}
		
		public String getIdentificativoAttestante() {
			return identificativoAttestante;
		}
		
		public Pagamento getPagamento() {
			return pagamento;
		}
		
		public void setPagamento(Pagamento pagamento) {
			this.pagamento = pagamento;
		}
		
	}



	// id fisico del pagamento
	private Long id;

	// -------------------------
	// Dati debito
	// -------------------------
	private CondizionePagamento condizionePagamento;

	// ------------------------
	// Stato del pagamento
	// ------------------------
    private EnumStatoPagamento statoPagamento;
    private EnumStatoIncasso flagIncasso;

	// ------------------------
	// Identificativi
	// ------------------------
    private String codPagamento;
    private String identificativoUnivocoVersamento;
    private String codiceContestoPagamento;

	// ------------------------
	// Dati base del pagamento
	// ------------------------
    private Date dataOraPagamento;
    private String notePagamento;
    private BigDecimal importoPagato;
    private Versante versante;

    // -------------------------------
    // Dati transazione pagamento
    // -------------------------------
    private InformazioniTransazionePagamento informazioniTransazionePagamento;

    // -------------------------------
    // DatiTransazione incasso
    // -------------------------------
    private InformazioniTransazioneIncasso informazioniTransazioneIncasso;

    // ---------------------------------------
    //  Ricevuta (solo se pagamento eseguito)
    // ---------------------------------------
    private String urlDownloadRicevuta;

	/**
	 * Id fisico del pagamento
	 * @return
     */
	@XmlElement(required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Dati della posizione da pagare
	 */
	@XmlElement(required = true)
	public CondizionePagamento getCondizionePagamento() {
		return condizionePagamento;
	}

	public void setCondizionePagamento(CondizionePagamento condizionePagamento) {
		this.condizionePagamento = condizionePagamento;
	}


	/**
	 * Stato del pagamento.
	 */
	@XmlElement(required = true)
	public EnumStatoPagamento getStatoPagamento() {
		return statoPagamento;
	}


	public void setStatoPagamento(EnumStatoPagamento statoPagamento) {
		this.statoPagamento = statoPagamento;
	}


	/**
	 * Flag che rappresenta lo stato del processo di incasso. Ha senso solo per pagamenti eseguiti.
	 */
	@XmlElement(required = true)
	public EnumStatoIncasso getFlagIncasso() {
		return flagIncasso;
	}


	public void setFlagIncasso(EnumStatoIncasso flagIncasso) {
		this.flagIncasso = flagIncasso;
	}


	/**
	 * Codice univoco assegnato alla transazione di pagamento nella piattaforma.
	 */
	@XmlElement(required = true)
	public String getCodPagamento() {
		return codPagamento;
	}


	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}


	/**
	 * IUV AgID che identifica univocamente questo pagamento. Univoco a livello di creditore.
	 */
	@XmlElement(required = true)
	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}


	public void setIdentificativoUnivocoVersamento(
			String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}


	/**
	 * Codice Contesto pagamento, identificativo AgID.
	 */
	@XmlElement(required = true)
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}

	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}


	/**
	 * Data ed ora nella quale e' iniziata la transazione di pagamento.
	 */
	@XmlElement(required = true)
	public Date getDataOraPagamento() {
		return dataOraPagamento;
	}


	public void setDataOraPagamento(Date dataOraPagamento) {
		this.dataOraPagamento = dataOraPagamento;
	}

	/**
	 * Eventuali Note sul  pagamento.
	 */
	public String getNotePagamento() {
		return notePagamento;
	}


	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}

	/**
	 * Importo effettivamente pagato.
	 */
	@XmlElement(required = true)
	public BigDecimal getImportoPagato() {
		return importoPagato;
	}


	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}


	/**
	 * Dati del soggetto che ha effettuato il pagamento
	 */
	@XmlElement(required = true)
	public Versante getVersante() {
		return versante;
	}

	public void setVersante(Versante versante) {
		this.versante = versante;
	}

	/**
	 * Dettaglio sulla transazione di pagamento
	 */
	@XmlElement(required = true)
	public InformazioniTransazionePagamento getInformazioniTransazionePagamento() {
		return informazioniTransazionePagamento;
	}

	public void setInformazioniTransazionePagamento(
			InformazioniTransazionePagamento informazioniTransazionePagamento) {
		this.informazioniTransazionePagamento = informazioniTransazionePagamento;
	}

	/**
	 * Dettaglio sulla transazione di incasso, presente solo se flagIncasso = RIACCREDITATO_ENTE
	 */
	public InformazioniTransazioneIncasso getInformazioniTransazioneIncasso() {
		return informazioniTransazioneIncasso;
	}

	public void setInformazioniTransazioneIncasso(
			InformazioniTransazioneIncasso informazioniTransazioneIncasso) {
		this.informazioniTransazioneIncasso = informazioniTransazioneIncasso;
	}

	/**
	 * URL per il download della ricevuta. (Presente solo se il pagamento e' stato eseguito).
	 */
	public String getUrlDownloadRicevuta() {
		return urlDownloadRicevuta;
	}


	public void setUrlDownloadRicevuta(String urlDownloadRicevuta) {
		this.urlDownloadRicevuta = urlDownloadRicevuta;
	}


}
