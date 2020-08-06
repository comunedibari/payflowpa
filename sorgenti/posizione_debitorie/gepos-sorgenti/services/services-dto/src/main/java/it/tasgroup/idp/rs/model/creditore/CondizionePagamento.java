package it.tasgroup.idp.rs.model.creditore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.tasgroup.idp.rs.enums.EnumStatoCondizione;
import it.tasgroup.idp.rs.enums.EnumStatoPagamentoCondizione;
import it.tasgroup.idp.rs.enums.EnumTipoRateazione;

/**
 * Rappresenta una condizione pagamento, ovvero una posizione da pagare/pagata nella piattaforma.
 * Una condizione di pagamento puo' essere l'unico pagamento previsto per una pendenza (debito) oppure essere uno
 * dei pagamenti necessari per saldare completamente un debito (e.g. rate).
 */
@XmlRootElement
public class CondizionePagamento {


    // -------------------------------
    // Dati Debito
    // -------------------------------

    private String creditore;

    private String codFiscaleCreditore;

    private String descrizioneCreditore;

    private String tipoDebito;

    private String descrizioneTipoDebito;

    private String codCategoriaDebito;

    private String descrizioneCategoriaDebito;

    private String idDebito;

    private String causaleDebito;

    private String noteDebito;

    private Date dataEmissioneDebito;

    private Date dataCreazioneDebito;

    private String idMittente;

    private String descrizioneMittente;

    private String divisa;

    private BigDecimal importoTotaleDebito;

    private String annoRiferimento;

    private List<Debitore> debitori;

	private String idLista;

	private String idPendenza;

	private String riferimentoIdDebito;

    // -------------------------------
    // Dati Condizione Pagamento
    // -------------------------------

    private String idCondizione; 	// Id Fisico condizione pagamento

    private String idPagamento;

    private Date dataInizioValidita;

    private Date dataScadenza;

    private Date dataFineValidita;

    private Date dataAnnullamento;

    private BigDecimal importo;

    private String causalePagamento;

	private String causaleBollettino;

	private String datiRiscossione;

	private String riferimentoAllegatoAvviso;

    private EnumTipoRateazione tipoRateazionePagamento;
    
    private String statoPendenza;
	private List<Voce> voci;
	private List<String> idAllegati;
 
    // -------------------------------
    // Informazioni pagamento condizione
    // -------------------------------
    private InformazioniPagamentoCondizione informazioniPagamentoCondizione;

	// -------------------------------
	// Informazioni incasso condizione
	// -------------------------------
	private Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso;
	private String cdPlugin;
	private String descrizioneCausale;
	private String cdTributoEnte;
	private String codPagamento;
	private String utenteCreatore;
	private String riscossore;
	private String riferimento;
	
	/**
     * Codice assegnato al creditore all'atto del censimento sulla piattaforma
     */
    @XmlElement(required = true)
		public String getCreditore() {
		return creditore;
	}


	public void setCreditore(String creditore) {
		this.creditore = creditore;
	}


	/**
	 * Codice fiscale del creditore
	 */
	@XmlElement(required = true)
	public String getCodFiscaleCreditore() {
		return codFiscaleCreditore;
	}


	public void setCodFiscaleCreditore(String codFiscaleCreditore) {
		this.codFiscaleCreditore = codFiscaleCreditore;
	}

	/**
	 * Descrizione, ragione sociale del creditore
	 */
	@XmlElement(required = true)
	public String getDescrizioneCreditore() {
		return descrizioneCreditore;
	}


	public void setDescrizioneCreditore(String descrizioneCreditore) {
		this.descrizioneCreditore = descrizioneCreditore;
	}



    /**
     * Tipo debito (e.g. MULTA)
     */
	 @XmlElement(required = true)
	public String getTipoDebito() {
		return tipoDebito;
	}


	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}


	/**
	 * Descrizione testuale del tipo debito (e.g. Sanzione codice della strada).
	 */
	@XmlElement(required = true)
	public String getDescrizioneTipoDebito() {
		return descrizioneTipoDebito;
	}


	public void setDescrizioneTipoDebito(String descrizioneTipoDebito) {
		this.descrizioneTipoDebito = descrizioneTipoDebito;
	}


	/**
	 * Codice della categoria debito. E.g. 'Categoria001'
	 */
	@XmlElement(required = true)
	public String getCodCategoriaDebito() {
		return codCategoriaDebito;
	}


	public void setCodCategoriaDebito(String codCategoriaDebito) {
		this.codCategoriaDebito = codCategoriaDebito;
	}

	/**
	 * Descrizione della categoria debito. E.g. 'Tributi e tassazione locale'
	 */
	@XmlElement(required = true)
	public String getDescrizioneCategoriaDebito() {
		return descrizioneCategoriaDebito;
	}


	public void setDescrizioneCategoriaDebito(String descrizioneCategoriaDebito) {
		this.descrizioneCategoriaDebito = descrizioneCategoriaDebito;
	}

    /**
     * Identificativo univoco del debito (univoco a parit� di creditore e tipoDebito)
     */
	 @XmlElement(required = true)
	public String getIdDebito() {
		return idDebito;
	}


	public void setIdDebito(String idDebito) {
		this.idDebito = idDebito;
	}

    /**
     * Causale specifica del debito (e.g. Verbale n. 12345 del 12/07/2015)
     */
	@XmlElement(required = true)
	public String getCausaleDebito() {
		return causaleDebito;
	}


	public void setCausaleDebito(String causaleDebito) {
		this.causaleDebito = causaleDebito;
	}

    /**
     * Eventuali note del debito (e.g. Il veicolo sostava in zona ZTL, Cod. Accertatore 12345)
     */
	public String getNoteDebito() {
		return noteDebito;
	}


	public void setNoteDebito(String noteDebito) {
		this.noteDebito = noteDebito;
	}

	/**
	 * Data di emissione del debito da parte della pubblica amministrazione
	 */
	@XmlElement(required = true)
	public Date getDataEmissioneDebito() {
		return dataEmissioneDebito;
	}


	public void setDataEmissioneDebito(Date dataEmissioneDebito) {
		this.dataEmissioneDebito = dataEmissioneDebito;
	}

	/**
	 * Data di acquisizione del debito nella piattaforma
	 */
	@XmlElement(required = true)
	public Date getDataCreazioneDebito() {
		return dataCreazioneDebito;
	}


	public void setDataCreazioneDebito(Date dataCreazioneDebito) {
		this.dataCreazioneDebito = dataCreazioneDebito;
	}

	/**
	 * Identificativo Mittente
	 */
	public String getIdMittente() {
		return idMittente;
	}


	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}

	/**
	 * Descrizione Mittente
	 */
	public String getDescrizioneMittente() {
		return descrizioneMittente;
	}


	public void setDescrizioneMittente(String descrizioneMittente) {
		this.descrizioneMittente = descrizioneMittente;
	}

	/**
	 * Divisa del pagamento. Fissa a EUR
	 */
	public String getDivisa() {
		return divisa;
	}


	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * Importo totale del debito. In genere coincide con l'importo della condizione di pagamento a meno
	 * di pendenze rateizzate. In tal caso l'importo totale debito corrisponde  alla somma delle rate.
	 */
	@XmlElement(required = true)
	public BigDecimal getImportoTotaleDebito() {
		return importoTotaleDebito;
	}


	public void setImportoTotaleDebito(BigDecimal importoTotaleDebito) {
		this.importoTotaleDebito = importoTotaleDebito;
	}

    /**
     * Anno di pertinenza del debito
     */
	@XmlElement(required = true)
	public String getAnnoRiferimento() {
		return annoRiferimento;
	}


	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	/**
	 * Lista dei debitori a cui e' stato iscritto il debito
	 */
	@XmlElement(required = true)
	public List<Debitore> getDebitori() {
		return debitori;
	}


	public void setDebitori(List<Debitore> debitori) {
		this.debitori = debitori;
	}



	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	public String getRiferimentoIdDebito() {
		return riferimentoIdDebito;
	}

	public void setRiferimentoIdDebito(String riferimentoIdDebito) {
		this.riferimentoIdDebito = riferimentoIdDebito;
	}

	/**
	 * Id Fisico della condizione di pagamento
	 */
	@XmlElement(required = true)
	public String getIdCondizione() {
		return idCondizione;
	}


	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	/**
	 * Identificativo del pagamento (noto al creditore).
	 */
	@XmlElement(required = true)
	public String getIdPagamento() {
		return idPagamento;
	}


	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	/**
	 * Data inizio validita'  Prima di questa data la condizione non e' pagabile nella piattaforma
	 */
	@XmlElement(required = true)
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}


	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}


	/**
	 * Data scadenza della condizione di pagamento
	 */
	@XmlElement(required = true)
	public Date getDataScadenza() {
		return dataScadenza;
	}


	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	/**
	 * Data fine validita'  Dopo questa data la condizione non e' pagabile nella piattaforma
	 */
	@XmlElement(required = true)
	public Date getDataFineValidita() {
		return dataFineValidita;
	}


	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}


	/**
	 * Importo dovuto
	 */
	@XmlElement(required = true)
	public BigDecimal getImporto() {
		return importo;
	}


	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	/**
	 * Eventuale causale specifica del pagamento.(e.g. Prima rata)
	 */
	public String getCausalePagamento() {
		return causalePagamento;
	}


	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}

	/**
	 * Tipo pagamento: Indica se la condizione e' un pagamento in "Soluzione Unica" o se e' una "Rata"
	 * di un debito rateizzato.
	 */
	@XmlElement(required = true)
	public EnumTipoRateazione getTipoRateazionePagamento() {
		return tipoRateazionePagamento;
	}

	public void setTipoRateazionePagamento(EnumTipoRateazione tipoRateazionePagamento) {
		this.tipoRateazionePagamento = tipoRateazionePagamento;
	}

	/**
	 * Eventuale decomposizione dell'importo nelle sue componenti
	 */
	public List<Voce> getVoci() {
		return voci;
	}


	public void setVoci(List<Voce> voci) {
		this.voci = voci;
	}
	
	public List<String> getIdAllegati() {
		return idAllegati;
	}


	public void setIdAllegati(List<String> idAllegati2) {
		this.idAllegati = idAllegati2;
	}
	
	public String getVociPagamentoStr() {
		String vociStr ="";
		for(Voce vp : voci) {
			vociStr += vp.getDescrizione() + "=" + vp.getImporto() + "<br/>";
		}
		return vociStr;
	}

	/**
	 *
	 * Contiene le informazioni riguardanti lo stato della condizione.
	 * Nota: Questo elemento non e' mai valorizzato in risposta a chiamate provenienti
	 * dall'endpoint /pagamenti
	 *
	 */
	public InformazioniPagamentoCondizione getInformazioniPagamentoCondizione() {
		return informazioniPagamentoCondizione;
	}


	public void setInformazioniPagamentoCondizione(
			InformazioniPagamentoCondizione informazioniPagamentoCondizione) {
		this.informazioniPagamentoCondizione = informazioniPagamentoCondizione;
	}
	
		/**
	 *
	 * @return
	 */
	public Pagamento.InformazioniTransazioneIncasso getInformazioniTransazioneIncasso() {
		return informazioniTransazioneIncasso;
	}

	public void setInformazioniTransazioneIncasso(Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso) {
		this.informazioniTransazioneIncasso = informazioniTransazioneIncasso;
	}
	
	
	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}
	
	public String getCdPlugin() {
		return cdPlugin;
	}
	
	public void setDescrizioneCausale(String descrizioneCausale) {
		this.descrizioneCausale = descrizioneCausale;
	}
	
	public String getDescrizioneCausale() {
		return descrizioneCausale;
	}
	
	public void setCdTributoEnte(String cdTributoEnte) {
		this.cdTributoEnte = cdTributoEnte;
	}
	
	public String getCdTributoEnte() {
		return cdTributoEnte;
	}
	
	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}
	
	public String getCodPagamento() {
		return codPagamento;
	}
	
	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}
	
	public String getUtenteCreatore() {
		return utenteCreatore;
	}
	
	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}
	
	public String getRiscossore() {
		return riscossore;
	}
	
	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	
	public String getRiferimento() {
		return riferimento;
	}
	

    public String getStatoPendenza() {
		return statoPendenza;
	}


	public void setStatoPendenza(String statoPendenza) {
		this.statoPendenza = statoPendenza;
	}


	
	
	public boolean getPagata() {
		if ( getInformazioniPagamentoCondizione() != null) {
		    return getInformazioniPagamentoCondizione().getStatoPagamentoCondizione() == EnumStatoPagamentoCondizione.PAGATA;
		} else {
		     return false;
		}	
	}
	
	
	public boolean getPagataFuoriIris() {
		boolean returnValue=false;
		if ( getInformazioniPagamentoCondizione() != null) {
			boolean pagataIdp = getInformazioniPagamentoCondizione().getPagatoIdp();
			boolean isPagata = getInformazioniPagamentoCondizione().getStatoCondizione() == EnumStatoCondizione.PAGATA;
			if (isPagata && !pagataIdp) {
				returnValue = true;
			} 
		}
		return returnValue;
	}
	
	public boolean getRimborsataEnte() {
		boolean returnValue=false;
		if ( getInformazioniPagamentoCondizione() != null) {
			if ("R".equals(getInformazioniPagamentoCondizione().getStatoCondizioneCP())) {
				returnValue = true;
			} 
		}
		return returnValue;
	}
	
}
