package it.tasgroup.idp.rs.model;


//import it.tasgroup.idp.rs.enums.EnumStatoPagamentoCondizione;

import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Condizione di Pagamento.
 * Rappresenta un pagamento "atteso". Può rappresentare l'unico pagamento previsto per
 * chiudere una posizione debitoria (Pendenza) oppure il pagamento di una parte di essa, quale ad
 * esempio una rata.  E' l'unità minima pagabile sulla piattaforma.
 */
@XmlRootElement
public class CondizionePagamento implements Serializable {

    public CondizionePagamento() {
    }

//	public CondizionePagamento(String idPagamentoEnte, Date scadenza, Date inizioValidita , Date fineValidita, BigDecimal importo,
//			String codEnte, String codFiscaleEnte, String descEnte, String codCategoriaTributo, String descrTributo,
//			String codTributo, String note, String idPendenzaEnte, String causalePendenza, String causalePagamento,
//			List<InfoDebitori> debitori, String codPagamento, EnumStatoPagamentoCondizione statoPagamentoCalcolato,
//			String idEnte, Date dataEmissione, Date dataPrescrizione, String annoRiferimento, String causalePendenzaGrezza) {
//		this.idPagamentoEnte = idPagamentoEnte;
//		this.scadenza = scadenza;
//		this.inizioValidita = inizioValidita;
//		this.fineValidita = fineValidita;
//		this.importo = importo;
//		this.codEnte = codEnte;
//		this.codFiscaleEnte = codFiscaleEnte;
//		this.descEnte = descEnte;
//		this.codCategoriaTributo = codCategoriaTributo;
//		this.descrTributo = descrTributo;
//		this.codTributo = codTributo;
//		this.note = note;
//		this.idPendenzaEnte = idPendenzaEnte;
//		this.causalePendenza = causalePendenza;
//		this.causalePagamento = causalePagamento;
//		this.debitori = debitori;
//		this.codPagamento = codPagamento;
//		this.statoPagamentoCalcolato = statoPagamentoCalcolato;
//		this.idEnte = idEnte;
//		this.dataEmissione = dataEmissione;
//		this.dataPrescrizione = dataPrescrizione;
//		this.annoRiferimento = annoRiferimento;
//		this.causalePendenzaGrezza = causalePendenzaGrezza;
//	}

    private String id;

    private String idPagamentoEnte;

    private Date scadenza;

    private Date inizioValidita;

    private Date fineValidita;

    private BigDecimal importo;

    private String codEnte;

    private String codFiscaleEnte;

    private String descEnte;

    private String codCategoriaTributo;

    private String descrTributo;

    private String codTributo;

    private String note;

    private String idPendenzaEnte;

    private String causalePendenza;

    private String causalePagamento;

    private List<InfoDebitori> debitori;

    private String codPagamento;

    private EnumStatoPagamentoCondizione statoPagamentoCalcolato;

    private String idEnte;
    private Date dataEmissione;
    private Date dataPrescrizione;
    private String annoRiferimento;
    private String causalePendenzaGrezza;
    
    private List<VoceImporto> vociImporto;

    private String esitoAttualizzazione;
    
    @XmlRootElement
    public static class VoceImporto implements Serializable {
		private static final long serialVersionUID = 1L;
		private String descrizione;
        private BigDecimal importo;
		public VoceImporto() {
			
		}
		public VoceImporto(String descrizione, BigDecimal importo) {
			super();
			this.descrizione = descrizione;
			this.importo = importo;
		}
		public String getDescrizione() {
			return descrizione;
		}
		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}
		public BigDecimal getImporto() {
			return importo;
		}
		public void setImporto(BigDecimal importo) {
			this.importo = importo;
		}
    	
    }
    
    
    @XmlRootElement
    public static class InfoDebitori implements Serializable {
        public InfoDebitori() {
        }

        public InfoDebitori(String codFiscaleDebitore, String descDebitore, String emailDebitore) {
            this.codFiscaleDebitore = codFiscaleDebitore;
            this.descDebitore = descDebitore;
            this.emailDebitore = emailDebitore;
        }

        private String codFiscaleDebitore;

        private String descDebitore;

        private String emailDebitore;

        /**
         * Codice Fiscale del debitore, intestatario del debito (Può essere persona fisica o giuridica)
         */
        @XmlElement(required = true)
        public String getCodFiscaleDebitore() {
            return codFiscaleDebitore;
        }

        public void setCodFiscaleDebitore(String codFiscaleDebitore) {
            this.codFiscaleDebitore = codFiscaleDebitore;
        }

        /**
         * Descrizione debitore (Nome e Cognome o Ragione Sociale)
         */
        public String getDescDebitore() {
            return descDebitore;
        }

        public void setDescDebitore(String descDebitore) {
            this.descDebitore = descDebitore;
        }

        /**
         * Email del debitore, eventualmente registrata sulla piattaforma
         */
        public String getEmailDebitore() {
            return emailDebitore;
        }

        public void setEmailDebitore(String emailDebitore) {
            this.emailDebitore = emailDebitore;
        }


    }


    /**
     *  Id fisico della condizione
     * @return
     */
    @XmlElement(required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Identificativo del pagamento assegnato dall'ente creditore (e.g il codice IUV assegnato  alla posizione dall'ente, il numero prenotazione di un ticket sanitario, etc.)
     */
    @XmlElement(required = true)
    public String getIdPagamentoEnte() {
        return idPagamentoEnte;
    }

    public void setIdPagamentoEnte(String idPagamentoEnte) {
        this.idPagamentoEnte = idPagamentoEnte;
    }

    /**
     * Data Scadenza del pagamento. Ha una valenza meramente informativa, per ricordare la scadenza all'utente.
     * Il pagamento viene comunque accettato fino al giorno indicato da "fineValidità"
     */
    @XmlElement(required = true)
    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

	/**
     * Data inizio validita'.
     */
    public Date getInizioValidita() {
		return inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

    /**
     * Data fine validità. Dopo questa data il pagamento on line non è più possibile. 
     */
    @XmlElement(required = true)
    public Date getFineValidita() {
        return fineValidita;
    }

    public void setFineValidita(Date fineValidita) {
        this.fineValidita = fineValidita;
    }

    /**
     * Importo da pagare
     */
    @XmlElement(required = true)
    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    /**
     * Codice identificativo dell'ente nella piattaforma (e.g. ASL8Arezzo)
     */
    @XmlElement(required = true)
    public String getCodEnte() {
        return codEnte;
    }

    public void setCodEnte(String codEnte) {
        this.codEnte = codEnte;
    }

    /**
     * Codice Fiscale dell'ente creditore
     */
    public String getCodFiscaleEnte() {
        return codFiscaleEnte;
    }

    public void setCodFiscaleEnte(String codFiscaleEnte) {
        this.codFiscaleEnte = codFiscaleEnte;
    }

    /**
     * Descrizione Ente (e.g. ASL 8 Arezzo)
     */
    @XmlElement(required = true)
    public String getDescEnte() {
        return descEnte;
    }

    public void setDescEnte(String descEnte) {
        this.descEnte = descEnte;
    }

    /**
     * Codice categoria tributo assegnato dalla piattaforma (e.g. Categoria013, "PrestazioniSanitarie")
     */
    @XmlElement(required = true)
    public String getCodCategoriaTributo() {
        return codCategoriaTributo;
    }

    public void setCodCategoriaTributo(String codCategoriaTributo) {
        this.codCategoriaTributo = codCategoriaTributo;
    }

    /**
     * Descrizione del tributo (e.g. Ticket Sanitario)
     */
    @XmlElement(required = true)
    public String getDescrTributo() {
        return descrTributo;
    }

    public void setDescrTributo(String descrTributo) {
        this.descrTributo = descrTributo;
    }

    /**
     * Codice dello specifico tributo all'interno della sua categoria (e.g. TICKET_SANITARIO )
     */
    @XmlElement(required = true)
    public String getCodTributo() {
        return codTributo;
    }

    public void setCodTributo(String codTributo) {
        this.codTributo = codTributo;
    }

    /**
     * Note (eventuali) da mostrare all'utente a riguardo del debito.
     */
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Id pendenza  come codificato dall'ente. Può coincidere con l'idPagamentoEnte, oppure differire.
     * Campo informativo.
     */
    @XmlElement(required = true)
    public String getIdPendenzaEnte() {
        return idPendenzaEnte;
    }

    public void setIdPendenzaEnte(String idPendenzaEnte) {
        this.idPendenzaEnte = idPendenzaEnte;
    }

    /**
     * Causale del pagamento. Presente solo in casi particolari, e.g. Una Pendenza ha più rate e la condizione
     * si riferisce ad una rata specifica (e.g. Prima Rata 2015).
     */
    public String getCausalePagamento() {
        return causalePagamento;
    }

    public void setCausalePagamento(String causalePagamento) {
        this.causalePagamento = causalePagamento;
    }

    /**
     * Causale del pagamento della pendenza (e.g. Prenotazione CUP n. 2345678 del 23/07/2015 ).
     */
    @XmlElement(required = true)
    public String getCausalePendenza() {
        return causalePendenza;
    }

    public void setCausalePendenza(String causalePendenza) {
        this.causalePendenza = causalePendenza;
    }

    /**
     * Lista dei debitori (intestatari del debito).
     */
    @XmlElement(required = true)
    public List<InfoDebitori> getDebitori() {
        return debitori;
    }

    public void setDebitori(List<InfoDebitori> debitori) {
        this.debitori = debitori;
    }

    /**
     * Codice del pagamento, presente opzionalmente solo se la
     * condizione è stata pagata sulla piattaforma.
     */
    @XmlElement(required = true)
    public String getCodPagamento() {
        return codPagamento;
    }

    public void setCodPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
    }

    /**
     * Stato della condizione di pagamento sulla piattaforma.
     * Consente di capire se la posizione è pagabile o è già stata pagata.
     */
    public EnumStatoPagamentoCondizione getStatoPagamentoCalcolato() {
        return statoPagamentoCalcolato;
    }

    public void setStatoPagamentoCalcolato(EnumStatoPagamentoCondizione statoPagamentoCalcolato) {
        this.statoPagamentoCalcolato = statoPagamentoCalcolato;
    }

    
    public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public Date getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public Date getDataPrescrizione() {
		return dataPrescrizione;
	}

	public void setDataPrescrizione(Date dataPrescrizione) {
		this.dataPrescrizione = dataPrescrizione;
	}

	public String getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public String getCausalePendenzaGrezza() {
		return causalePendenzaGrezza;
	}

	public void setCausalePendenzaGrezza(String causalePendenzaGrezza) {
		this.causalePendenzaGrezza = causalePendenzaGrezza;
	}

	/**
     * Lista delle voci (descrizione e importo) che contribuiscono 
     * all'importo della condizione di pagamento
     */
	public List<VoceImporto> getVociImporto() {
		return vociImporto;
	}

	public void setVociImporto(List<VoceImporto> vociImporto) {
		this.vociImporto = vociImporto;
	}

	/**
	 * Esito dell' eventuale attualizzazione.
	 *  
	 * @return <code>null</code> se non e' prevesta attualizzazione o se e' stato superato il numero massimo di condizioni attualizzabili per singola richiesta
	 * <br><code>OK</code> attualizzato con successo
	 * <br><code>KO</code> non attualizzato (problemi in fase di attualizzazione) 
	 */
	public String getEsitoAttualizzazione() {
		return esitoAttualizzazione;
	}

	public void setEsitoAttualizzazione(String esitoAttualizzazione) {
		this.esitoAttualizzazione = esitoAttualizzazione;
	}
    
}
