package it.tasgroup.iris.domain;

import it.nch.is.fo.pagamenti.Multe;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;

/**
 * The persistent class for the JLTPAGA database table.
 */
@Entity
@Table(name = "PAGAMENTI")
@SqlResultSetMappings({@SqlResultSetMapping(
	name = "pagamentiEffettuatiHomePageMapping",
	entities =  {
        @EntityResult(entityClass = Pagamenti.class)
    }
    , columns =  {
        @ColumnResult(name = "pe_tributoStrutturato")
        , @ColumnResult(name = "co_imTotale")
        , @ColumnResult(name = "co_tiPagamento")
        , @ColumnResult(name = "pagamentoContoTerzi")
        , @ColumnResult(name = "pe_de_causale")
        , @ColumnResult(name = "enti_denom")
        , @ColumnResult(name = "tributi_de_trb")
        , @ColumnResult(name = "d_cod_pagamento")
        , @ColumnResult(name = "lapl_pagante")
        , @ColumnResult(name = "causalePagamento")
        , @ColumnResult(name = "pe_riscossore")
        , @ColumnResult(name = "pe_riferimento")
        , @ColumnResult(name = "cdPlugin")
        , @ColumnResult(name = "flagConsegnaRicevuta")
    }
    )
    , @SqlResultSetMapping(name = "importoTotalePagatoByPendenzaMapping",columns =  {
        @ColumnResult(name = "totalePagato")
    }
    )
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "importoTotalePagatoByPendenza",
	query = "select sum(pa.im_pagato) as totalePagato from pagamenti pa " +
    		"  where  pa.id_pendenza = :id_pendenza and pa.st_pagamento in ('ST','IC','ES','EF')",
    		resultSetMapping = "importoTotalePagatoByPendenzaMapping"
    		)
})

@NamedQueries({
    @NamedQuery(
            name="getPagamentiByIdDistinta",
            query="select pag from Pagamenti pag where pag.flussoDistinta.id = :idDistinta "),
    @NamedQuery(
            name="getPagamentiByCodFiscale",
            query="select p from Pagamenti p where p.stPagamento = 'ES' and p.coPagante = :codFiscale "),
    @NamedQuery(
            name="getPagamentiByIdCondizione",
            query="select pag from Pagamenti pag where pag.condPagamento.idCondizione = :idCondizione and pag.stPagamento = 'ES' "),
        @NamedQuery(
                /** la query getPagamentoByDistintaContesto è volutamente ridondante per evitare che col solo id si possa accedere all'oggetto Pagamento ( la query viene eseguita da servizi REST) **/
                name="getPagamentoByDistintaContesto",
                query="select p from Pagamenti p join p.flussoDistinta d where p.id =:idFisico and d.iuv = :iuv and d.codTransazionePSP =:codiceContestoPagamento")
})

public class Pagamenti extends BaseIdEntity {
    private String cdTrbEnte;
    private String coPagante;
	private Date dataAccreditoContotecnico;

    private Date dataAccreditoEnte;
    private String distinta;
    private Date dtScadenza;
    private Date dataEsecuzione;
    private Date dataRegolamento;
    private GestioneFlussi flussoDistinta;
    private String idEnte;
    private String idPendenza;
    private String idPendenzaente;
    private String idTributo;
    private String identificativoFlusso;
    private String TRN;
    private BigDecimal imPagato;
    private Timestamp notificaEseguito;

    private Timestamp notificaIncasso;

    private Timestamp notificaRegolato;

    private String opAggiornamento;
    private String opInserimento;
    private String stPagamento;
    private String stRiga;
    private String statoNotifica;
    private String tiDebito;
    private String tiPagamento;
    private String tipospontaneo;
    private Timestamp tsAggiornamento;
    private Timestamp tsDecorrenza;
    private Timestamp tsInserimento;
    private Timestamp tsOrdine;
    private Timestamp tsStorno;
    private CondizionePagamento condPagamento;
    private Multe multa;
    private String flagIncasso;
    private Long idDocumentoRepository;
    private String idRiscossionePSP;
    private String notePagamento;
    private String idDocumentoExt;
    private String flagOpposizione730="0";
    private String codRendicontazioneIncasso;
    private BigDecimal totaleRendicontazioneIncasso;
    private String mittRendicontazioneIncasso;
    
    private BigDecimal commissioniPsp;
    private Long     idAllegatoRepository;
    private String  tipoAllegatoRepository;
    
    private String     riferimentoContabile;
    private Date       dataContabile;
    private BigDecimal importoContabile;

    private Long id;

    @Transient
    public static String DOC_NOT_AVAILABLE= "#ND";
   
    @Transient
    private List<RevochePagamento> revochePagamento;
    
    @Transient
    private Integer numeroRevoche;

	@Transient
	private Integer numeroRevocheDaValutare;
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="pagamenti_pk_gen")
	@SequenceGenerator(
		    name="pagamenti_pk_gen",
		    sequenceName="PAGAMENTI_ID_SEQ",
		    allocationSize=1
		)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @Column(name = "CD_TRB_ENTE")
    public String getCdTrbEnte() {
        return this.cdTrbEnte;
    }

    public void setCdTrbEnte(String cdTrbEnte) {
        this.cdTrbEnte = cdTrbEnte;
    }

    @Column(name = "CO_PAGANTE")
    public String getCoPagante() {
        return this.coPagante;
    }

    public void setCoPagante(String coPagante) {
        this.coPagante = coPagante;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_ACCREDITO_CONTOTECNICO")
    public Date getDataAccreditoContotecnico() {
        return this.dataAccreditoContotecnico;
    }

    public void setDataAccreditoContotecnico(Date dataAccreditoContotecnico) {
        this.dataAccreditoContotecnico = dataAccreditoContotecnico;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_ACCREDITO_ENTE")
    public Date getDataAccreditoEnte() {
        return this.dataAccreditoEnte;
    }

    public void setDataAccreditoEnte(Date dataAccreditoEnte) {
        this.dataAccreditoEnte = dataAccreditoEnte;
    }

	@Column(name = "DISTINTA")
	public String getDistinta() {
        return this.distinta;
    }

    public void setDistinta(String distinta) {
        this.distinta = distinta;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_SCADENZA")
    public Date getDtScadenza() {
        return this.dtScadenza;
    }

    public void setDtScadenza(Date dtScadenza) {
        this.dtScadenza = dtScadenza;
    }

    @ManyToOne(targetEntity = GestioneFlussi.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DISTINTE_PAGAMENTO")
    public GestioneFlussi getFlussoDistinta() {
        return this.flussoDistinta;
    }

    public void setFlussoDistinta(GestioneFlussi flussoDistinta) {
        this.flussoDistinta = flussoDistinta;
    }

    @Column(name = "ID_ENTE")
    public String getIdEnte() {
        return this.idEnte;
    }

    public void setIdEnte(String idEnte) {
        this.idEnte = idEnte;
    }

    @Column(name = "ID_PENDENZA")
    public String getIdPendenza() {
        return this.idPendenza;
    }

    public void setIdPendenza(String idPendenza) {
        this.idPendenza = idPendenza;
    }

    @Column(name = "ID_PENDENZAENTE")
    public String getIdPendenzaente() {
        return this.idPendenzaente;
    }

    public void setIdPendenzaente(String idPendenzaente) {
        this.idPendenzaente = idPendenzaente;
    }

    @Column(name = "ID_TRIBUTO")
    public String getIdTributo() {
        return this.idTributo;
    }

    public void setIdTributo(String idTributo) {
        this.idTributo = idTributo;
    }

    @Column(name = "IM_PAGATO")
    public BigDecimal getImPagato() {
        return this.imPagato;
    }

    public void setImPagato(BigDecimal imPagato) {
        this.imPagato = imPagato;
    }

    @Column(name = "NOTIFICA_ESEGUITO")
    public Timestamp getNotificaEseguito() {
        return this.notificaEseguito;
    }

    public void setNotificaEseguito(Timestamp notificaEseguito) {
        this.notificaEseguito = notificaEseguito;
    }

    @Column(name = "NOTIFICA_INCASSO")
    public Timestamp getNotificaIncasso() {
        return this.notificaIncasso;
    }

    public void setNotificaIncasso(Timestamp notificaIncasso) {
        this.notificaIncasso = notificaIncasso;
    }

    @Column(name = "NOTIFICA_REGOLATO")
    public Timestamp getNotificaRegolato() {
        return this.notificaRegolato;
    }

    public void setNotificaRegolato(Timestamp notificaRegolato) {
        this.notificaRegolato = notificaRegolato;
    }

    @Column(name = "OP_AGGIORNAMENTO")
    public String getOpAggiornamento() {
        return this.opAggiornamento;
    }

    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }

    @Column(name = "OP_INSERIMENTO")
    public String getOpInserimento() {
        return this.opInserimento;
    }

    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }

    @Column(name = "ST_PAGAMENTO",length = 2)
    public String getStPagamento() {
        return this.stPagamento;
    }

    public void setStPagamento(String stPagamento) {
        this.stPagamento = stPagamento;
    }

    @Column(name = "ST_RIGA")
    public String getStRiga() {
        return this.stRiga;
    }

    public void setStRiga(String stRiga) {
        this.stRiga = stRiga;
    }

    @Column(name = "STATO_NOTIFICA")
    public String getStatoNotifica() {
        return this.statoNotifica;
    }

    public void setStatoNotifica(String statoNotifica) {
        this.statoNotifica = statoNotifica;
    }

    @Column(name = "TI_DEBITO")
    public String getTiDebito() {
        return this.tiDebito;
    }

    public void setTiDebito(String tiDebito) {
        this.tiDebito = tiDebito;
    }

    @Column(name = "TI_PAGAMENTO")
    public String getTiPagamento() {
        return this.tiPagamento;
    }

    public void setTiPagamento(String tiPagamento) {
        this.tiPagamento = tiPagamento;
    }

    @Column(name = "TIPOSPONTANEO")
    public String getTipospontaneo() {
        return this.tipospontaneo;
    }

    public void setTipospontaneo(String tipospontaneo) {
        this.tipospontaneo = tipospontaneo;
    }

    @Column(name = "TS_AGGIORNAMENTO")
    public Timestamp getTsAggiornamento() {
        return this.tsAggiornamento;
    }

    public void setTsAggiornamento(Timestamp tsAggiornamento) {
        this.tsAggiornamento = tsAggiornamento;
    }

    @Column(name = "TS_DECORRENZA")
    public Timestamp getTsDecorrenza() {
        return this.tsDecorrenza;
    }

    public void setTsDecorrenza(Timestamp tsDecorrenza) {
        this.tsDecorrenza = tsDecorrenza;
    }

    @Column(name = "TS_INSERIMENTO")
    public Timestamp getTsInserimento() {
        return this.tsInserimento;
    }

    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }

    @Column(name = "TS_ORDINE")
    public Timestamp getTsOrdine() {
        return this.tsOrdine;
    }

    public void setTsOrdine(Timestamp tsOrdine) {
        this.tsOrdine = tsOrdine;
    }

    @ManyToOne(targetEntity = CondizionePagamento.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONDIZIONE", nullable = true)
    public CondizionePagamento getCondPagamento() {
        return condPagamento;
    }

    public void setCondPagamento(CondizionePagamento condPagamento) {
        this.condPagamento = condPagamento;
    }

    //@OneToOne(cascade=CascadeType.ALL,mappedBy="pagamento")
    @Transient
    public Multe getMulta() {
        return multa;
    }

    public void setMulta(Multe multa) {
        this.multa = multa;
    }

    @Column(name = "FLAG_INCASSO")
    public String getFlagIncasso() {
        return flagIncasso;
    }

    public void setFlagIncasso(String flagIncasso) {
        this.flagIncasso = flagIncasso;
    }

    @Column(name = "FLAG_OPPOSIZIONE_730")
    public String getFlagOpposizione730() {
        return flagOpposizione730;
    }

    public void setFlagOpposizione730(String flagOpposizione730) {
        this.flagOpposizione730 = flagOpposizione730;
    }

    @Column(name = "ID_DOCUMENTO_REPOSITORY")
    public Long getIdDocumentoRepository() {
        return idDocumentoRepository;
    }

    public void setIdDocumentoRepository(Long idDocumentoRepository) {
        this.idDocumentoRepository = idDocumentoRepository;
    }

    @Column(name = "ID_RISCOSSIONE_PSP")
    public String getIdRiscossionePSP() {
        return idRiscossionePSP;
    }

    public void setIdRiscossionePSP(String idRiscossionePSP) {
        this.idRiscossionePSP = idRiscossionePSP;
    }

    @Column(name = "NOTE_PAGAMENTO")
    public String getNotePagamento() {
        return notePagamento;
    }

    public void setNotePagamento(String notePagamento) {
        this.notePagamento = notePagamento;
    }

    @Transient
    public boolean isValidPayment() {
        if (stPagamento.equals(StatiPagamentiIris.ESEGUITO.getPagaMapping()) ||
                stPagamento.equals(
                    StatiPagamentiIris.ESEGUITO_SBF.getPagaMapping()) ||
                stPagamento.equals(StatiPagamentiIris.IN_CORSO.getPagaMapping())) {
            return true;
        }

        return false;
    }

    @Transient
    public boolean isInProgress() {
        if (stPagamento.equals(StatiPagamentiIris.IN_CORSO.getPagaMapping())) {
            return true;
        }

        return false;
    }

    @Transient
    public boolean isExecutedPayment() {
        if (stPagamento.equals(StatiPagamentiIris.ESEGUITO.getPagaMapping()) ||
                stPagamento.equals(
                    StatiPagamentiIris.ESEGUITO_SBF.getPagaMapping())) {
            return true;
        }

        return false;
    }

    @Column(name = "TS_STORNO")
    public Timestamp getTsStorno() {
        return tsStorno;
    }

    public void setTsStorno(Timestamp tsStorno) {
        this.tsStorno = tsStorno;
    }

    public static class MaxPagamentiComparator implements Comparator<Pagamenti> {
        @Override
        public int compare(Pagamenti o1, Pagamenti o2) {
            Timestamp dateToCompare1 = (o1.getTsAggiornamento() != null)
                ? o1.getTsAggiornamento() : o1.getTsInserimento();
            Timestamp dateToCompare2 = (o2.getTsAggiornamento() != null)
                ? o2.getTsAggiornamento() : o2.getTsInserimento();

            return dateToCompare1.compareTo(dateToCompare2);
        }
    }

    @Column(name = "ID_DOCUMENTO_EXT")
	public String getIdDocumentoExt() {
		return idDocumentoExt;
	}

	public void setIdDocumentoExt(String idDocumentoExt) {
		this.idDocumentoExt = idDocumentoExt;
	}

	@Transient
	public boolean isAssociatedDocumentAvailable() {
		if (idDocumentoExt==null || !idDocumentoExt.equals(DOC_NOT_AVAILABLE)) {
		   return true;
	    }else {
		   return false;
	    }
	}


    @Column(name = "TRN")
	public String getTRN() {
		return TRN;
	}

	public void setTRN(String tRN) {
		TRN = tRN;
	}

	@Column(name = "IDENTIFICATIVO_FLUSSO")
	public String getIdentificativoFlusso() {
		return identificativoFlusso;
	}

	public void setIdentificativoFlusso(String identificativoFlusso) {
		this.identificativoFlusso = identificativoFlusso;
	}

	@Column(name = "DATA_ESECUZIONE")
	public Date getDataEsecuzione() {
		return dataEsecuzione;
	}

	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}

	@Column(name = "DATA_REGOLAMENTO")
	public Date getDataRegolamento() {
		return dataRegolamento;
	}

	public void setDataRegolamento(Date dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}

    @Column(name = "COD_RENDICONTAZIONE_INCASSO")
    public String getCodRendicontazioneIncasso() {
        return codRendicontazioneIncasso;
    }

    public void setCodRendicontazioneIncasso(String codRendicontazioneIncasso) {
        this.codRendicontazioneIncasso = codRendicontazioneIncasso;
    }


    @Column(name ="TOTALE_TRANSAZIONE_INCASSO")
    public BigDecimal getTotaleRendicontazioneIncasso() {
        return totaleRendicontazioneIncasso;
    }

    public void setTotaleRendicontazioneIncasso(BigDecimal totaleRendicontazioneIncasso) {
        this.totaleRendicontazioneIncasso = totaleRendicontazioneIncasso;
    }

    @Column(name="MITT_RENDICONTAZIONE_INCASSO")
    public String getMittRendicontazioneIncasso() {
        return mittRendicontazioneIncasso;
    }

    public void setMittRendicontazioneIncasso(String mittRendicontazioneIncasso) {
        this.mittRendicontazioneIncasso = mittRendicontazioneIncasso;
    }
    
    @Column(name="COMMISSIONI_PSP")
	public BigDecimal getCommissioniPsp() {
		return commissioniPsp;
	}

	public void setCommissioniPsp(BigDecimal commissioniPsp) {
		this.commissioniPsp = commissioniPsp;
	}
    
	@Column(name="ID_ALLEGATO_REPOSITORY")
	public Long getIdAllegatoRepository() {
		return idAllegatoRepository;
	}

	public void setIdAllegatoRepository(Long idAllegatoRepository) {
		this.idAllegatoRepository = idAllegatoRepository;
	}
    
	@Column(name="TIPO_ALLEGATO_REPOSITORY")
	public String getTipoAllegatoRepository() {
		return tipoAllegatoRepository;
	}

	public void setTipoAllegatoRepository(String tipoAllegatoRepository) {
		this.tipoAllegatoRepository = tipoAllegatoRepository;
	}


	@Transient
    public List<RevochePagamento> getRevochePagamento() {
		return revochePagamento;
	}

	@Transient
	public void setRevochePagamento(List<RevochePagamento> revochePagamento) {
		this.revochePagamento = revochePagamento;
	}
	@Transient
    public Integer getNumeroRevoche() {
		return numeroRevoche;
	}

    
	public void setNumeroRevoche(Integer numeroRevoche) {
		this.numeroRevoche = numeroRevoche;
	}
	@Transient
	public Integer getNumeroRevocheDaValutare() {
		return numeroRevocheDaValutare;
	}

	public void setNumeroRevocheDaValutare(Integer numeroRevocheDaValutare) {
		this.numeroRevocheDaValutare = numeroRevocheDaValutare;
	}
	@Column(name="RIFERIMENTO_CONTABILE")
	public String getRiferimentoContabile() {
		return riferimentoContabile;
	}

	public void setRiferimentoContabile(String riferimentoContabile) {
		this.riferimentoContabile = riferimentoContabile;
	}
	@Column(name="DATA_CONTABILE")
	public Date getDataContabile() {
		return dataContabile;
	}

	public void setDataContabile(Date dataContabile) {
		this.dataContabile = dataContabile;
	}

	@Column(name="IMPORTO_CONTABILE")
	public BigDecimal getImportoContabile() {
		return importoContabile;
	}

	public void setImportoContabile(BigDecimal importoContabile) {
		this.importoContabile = importoContabile;
	}

}
