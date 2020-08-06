package it.tasgroup.iris.domain;



import it.tasgroup.iris.domain.helper.EntityHelper;
import it.tasgroup.services.util.enumeration.EnumStatoDRP;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * The persistent class for the JLTE030 database table.
 *
 */
@Entity
@Table(name="DISTINTE_PAGAMENTO")
@NamedQueries(
	{
		@NamedQuery(
				name="getDistintaByMsgId",
				query="select flusso from GestioneFlussi flusso where flusso.codTransazione=:idTransazione"),
		@NamedQuery(
				name="getDistintaSecurityCheck",
				query="select flusso from GestioneFlussi flusso where flusso.codPagamento=:codPagamento and flusso.utentecreatore=:codPagante and flusso.id=:idFlusso"),
		@NamedQuery(
				name="getDistintaByIUVIdFiscCredCodContestoPag",
				query="select flusso from GestioneFlussi flusso where flusso.codTransazionePSP=:codTransazionePSP and flusso.iuv=:iuv and flusso.identificativoFiscaleCreditore=:identificativoFiscaleCreditore"),
		@NamedQuery(
				name="getDistintaByCodAndData",
				query="select flusso from GestioneFlussi flusso where flusso.codPagamento=:codPagamento and flusso.codTransazione=:codTransazione and flusso.tmbcreazione>=:dataFrom and flusso.tmbcreazione<:dataTo"),
		@NamedQuery(
				name="getDistintaByCodPagamento",
				query="select flusso from GestioneFlussi flusso where flusso.codPagamento=:codPagamento"),
		@NamedQuery(
				name="countDistinteByStatus",
				query="select count(dps) from GestioneFlussi dps where dps.stato=:statoDocumento "),
		@NamedQuery(
				name="getGestioneFlussiAll",
				query="select gf from GestioneFlussi gf "+
					 	"order by gf.tmbcreazione desc  "),
		@NamedQuery(
				name="getByCodPagamentoCodiceFiscale",
				query="select distinct flusso from GestioneFlussi flusso join flusso.pagamenti fpa where flusso.codPagamento=:codPagamento and (flusso.utentecreatore =:codFiscale or fpa.coPagante=:codFiscale)"
		),
		@NamedQuery(
		        name="getDistintaByCodPagamentoCodFiscale",
		        query="select flusso from GestioneFlussi flusso where flusso.codPagamento=:codPagamento"),		
		@NamedQuery(
		        name="getDistintaByIdGruppo", 
				query= "SELECT flusso FROM GestioneFlussi flusso WHERE flusso.idGruppo = :idGruppo"
		),
		@NamedQuery(
				name="getDistintaByIUVIdFiscCred",
				query="select flusso from GestioneFlussi flusso where flusso.iuv=:iuv and flusso.identificativoFiscaleCreditore=:identificativoFiscaleCreditore order by tsInserimento desc")
	,
		@NamedQuery(
			 name="getDistintaByCondizione",
			 query=" select d as distinta , p as pagamento from CondizionePagamento  c  join c.pagamenti p join p.flussoDistinta d where  c.idCondizione=:idCondizione order by p.tsInserimento desc")
	}
)

public class GestioneFlussi extends BaseIdEntity {


	private BigDecimal importoCommissioni;
	private String codTransazione;
	private int numeroDisposizioni;
	private String stato;
	private Timestamp tmbcreazione;
	private BigDecimal totimportipositivi;
    private String divisa;
	private String utentecreatore;
	private String emailVersante = "-";
    private Timestamp dataSpedizione;
    private CfgGatewayPagamento cfgGatewayPagamento;

    private Timestamp tsInserimento;
	private String opInserimento;
	private Timestamp tsUpdate;
	private String opAggiornamento;

	private String tipoSoggettoVers; 
	private String codFiscaleVers; 
	private String anagraficaVers;
	private String indirizzoVers; 
	private String numeroCivicoVers; 
	private String capVers; 
	private String localitaVers; 
	private String provinciaVers; 
	private String nazioneVers; 
	
	/*** Persistent Collections ***/
	private Set<Pagamenti> pagamenti;
	private Set<PagamentiOnline> pagamentiOnline;
	private Set<Rid> pagamentiRid;
	private Set<AllineamentiElettroniciArchivi> deleghe;

	private Set<CasellarioDispo> casellariDispo;

	private Set<DocumentoDiPagamento> documentiPagamento;

	private Long idDocumentoRepository;

	private String codPagamento;

	private String codTransazionePSP="0";  //Codice Contesto Pagamento
	private String iuv="0";
	private String identificativoFiscaleCreditore="0";

	private Long id;
	private String idGruppo;
	
	private String locale;
	
	private String tipoIdentificativoAttestante;
	private String identificativoAttestante;
	private String descrizioneAttestante;
	//TIPO_IDENTIFICATIVO_ATTESTANTE
	//IDENTIFICATIVO_ATTESTANTE
	//DESCRIZIONE_ATTESTANTE
	
	// campi introdotti per eliminare controllo esistenza su PSP 
	private String idPspModello3;
	private String idIntermediarioModello3;
	private String idCanaleModello3;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="distinte_pagamento_pk_gen")
	@SequenceGenerator(
		    name="distinte_pagamento_pk_gen",
		    sequenceName="DISTINTE_PAGAMENTO_ID_SEQ",
		    allocationSize=1
		)
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	@Column (name="DIVISA")
	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	@Column (name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="IMPORTO_COMMISSIONI")
	public BigDecimal getImportoCommissioni() {
		return this.importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}

    @Column(name="COD_TRANSAZIONE")
	public String getCodTransazione() {
		return codTransazione;
	}

	public void setCodTransazione(String codTransazione) {
		this.codTransazione = codTransazione;
	}

	@Column(name="COD_PAGAMENTO")
	public String getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}

	@Column(name="COD_TRANSAZIONE_PSP")
	public String getCodTransazionePSP() {
		return codTransazionePSP;
	}

	public void setCodTransazionePSP(String codTransazionePSP) {
		this.codTransazionePSP = codTransazionePSP;
	}

	@Column(name="STATO")
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Transient
	public EnumStatoDRP getEnumStatoDRP() {
		return EnumStatoDRP.getByKey(stato);
	}

	@Column(name="DATA_CREAZIONE")
	public Timestamp getTmbcreazione() {
		return this.tmbcreazione;
	}

	public void setTmbcreazione(Timestamp tmbcreazione) {
		this.tmbcreazione = tmbcreazione;
	}

	@Column(name="IMPORTO")
	public BigDecimal getTotimportipositivi() {
		return this.totimportipositivi;
	}

	public void setTotimportipositivi(BigDecimal totimportipositivi) {
		this.totimportipositivi = totimportipositivi;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsUpdate() {
		return this.tsUpdate;
	}

	public void setTsUpdate(Timestamp tsUpdate) {
		this.tsUpdate = tsUpdate;
	}

	@Column(name="UTENTE_CREATORE")
	public String getUtentecreatore() {
		return this.utentecreatore;
	}

	public void setUtentecreatore(String utentecreatore) {
		this.utentecreatore = utentecreatore;
	}


	@Column(name="NUMERO_DISPOSIZIONI")
	public int getNumeroDisposizioni() {
		return numeroDisposizioni;
	}

	public void setNumeroDisposizioni(int numeroDisposizioni) {
		this.numeroDisposizioni = numeroDisposizioni;
	}

	@OneToMany(mappedBy="flussoDistinta",targetEntity=Pagamenti.class,fetch=FetchType.LAZY,
			cascade={CascadeType.MERGE,CascadeType.PERSIST})
	public Set<Pagamenti> getPagamenti() {
		return this.pagamenti;
	}

	public void setPagamenti(Set<Pagamenti> pagamenti) {
		this.pagamenti = pagamenti;
	}

	@OneToMany(mappedBy="flussoDistintaOnline",targetEntity=PagamentiOnline.class,fetch=FetchType.LAZY,
			cascade={CascadeType.MERGE,CascadeType.PERSIST})
	public Set<PagamentiOnline> getPagamentiOnline() {
		return pagamentiOnline;
	}

	public void setPagamentiOnline(Set<PagamentiOnline> pagamentiOnline) {
		this.pagamentiOnline = pagamentiOnline;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@ManyToOne(targetEntity=CfgGatewayPagamento.class,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_GATEWAY_PAGAMENTO")
	public CfgGatewayPagamento getCfgGatewayPagamento() {
		return cfgGatewayPagamento;
	}

	public void setCfgGatewayPagamento(CfgGatewayPagamento cfgGatewayPagamento) {
		this.cfgGatewayPagamento = cfgGatewayPagamento;
	}

	@OneToMany(mappedBy="distintePagamento", targetEntity=AllineamentiElettroniciArchivi.class)
	public Set<AllineamentiElettroniciArchivi> getDeleghe() {
		return this.deleghe;
	}
	public void setDeleghe(Set<AllineamentiElettroniciArchivi> deleghe) {
		this.deleghe = deleghe;
	}

	@OneToMany(mappedBy="distintePagamento", targetEntity=Rid.class)
	public Set<Rid> getPagamentiRid() {
		return this.pagamentiRid;
	}
	public void setPagamentiRid(Set<Rid> pagamentiRid) {
		this.pagamentiRid = pagamentiRid;
	}


	@Override
	public String toString() {
//			String out= "importoCommissioni ="+"'"+this.importoCommissioni+"'"+
//					"codTransazione ="+"'"+this.codTransazione+"'"+
//					"numeroDisposizioni ="+"'"+this.numeroDisposizioni+"'"+
//					"stato ="+"'"+this.stato+"'"+
//					"tmbcreazione ="+"'"+this.tmbcreazione+"'"+
//		    "divisa ="+"'"+this.divisa+"'"+
//			"tsUpdate ="+"'"+this.tsUpdate+"'"+
//			"utentecreatore ="+"'"+this.utentecreatore+"'"+
//			"opAggiornamento ="+"'"+this.opAggiornamento+"'"+
//			"opInserimento ="+"'"+this.opInserimento+"'"+
//			"idCfgGatewayPagamento="+this.idCfgGatewayPagamento;
//			for (Iterator iterator = this.pagamentiOnline.iterator(); iterator.hasNext();) {
//				PagamentiOnline type = (PagamentiOnline) iterator.next();
//				out=out+type.toString();
//			}
//		return out;
		String out = ToStringBuilder.reflectionToString(this).toString();
		return out;
	}

	@Column(name="DATA_SPEDIZIONE")
	public Timestamp getDataSpedizione() {
		return dataSpedizione;
	}

	public void setDataSpedizione(Timestamp dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}

	//bi-directional one-to-one association to CasellarioDispo
	@OneToMany(cascade=CascadeType.ALL,mappedBy="distintePagamento",fetch=FetchType.LAZY)
	public Set<CasellarioDispo> getCasellariDispo() {
		return casellariDispo;
	}

	public void setCasellariDispo(Set<CasellarioDispo> casellariDispo) {
		this.casellariDispo = casellariDispo;
	}


	@Column(name="IUV")
	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	@Column(name="ID_FISCALE_CREDITORE")
	public String getIdentificativoFiscaleCreditore() {
		return identificativoFiscaleCreditore;
	}

	public void setIdentificativoFiscaleCreditore(String identificativoFiscaleCreditore) {
		this.identificativoFiscaleCreditore = identificativoFiscaleCreditore;
	}

	@Transient
	public CasellarioDispo getCasellarioDispo() {

		EntityHelper<CasellarioDispo> helper = new EntityHelper<CasellarioDispo>();

 		return helper.getOneToOneObj(casellariDispo);

	}
	@Transient
	public void setCasellarioDispo(CasellarioDispo casellarioDispo) {

		EntityHelper<CasellarioDispo> helper = new EntityHelper<CasellarioDispo>();

		casellariDispo = helper.setOneToOneObj(casellarioDispo, casellariDispo);

	}


	@OneToMany(mappedBy="distinta",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	public Set<DocumentoDiPagamento> getDocumentiPagamento() {
		return this.documentiPagamento;
	}

	public void setDocumentiPagamento(Set<DocumentoDiPagamento> documenti) {
		this.documentiPagamento = documenti;
	}

	@Transient
	public DocumentoDiPagamento getDocPagamento() {

		EntityHelper<DocumentoDiPagamento> helper = new EntityHelper<DocumentoDiPagamento>();

 		return helper.getOneToOneObj(documentiPagamento);

	}

	@Transient
	public void setDocPagamento(DocumentoDiPagamento docPagamento) {

		EntityHelper<DocumentoDiPagamento> helper = new EntityHelper<DocumentoDiPagamento>();

		documentiPagamento=helper.setOneToOneObj(docPagamento, documentiPagamento);

	}

	@Column(name="ID_DOCUMENTO_REPOSITORY")
	public Long getIdDocumentoRepository() {
		return idDocumentoRepository;
	}
	public void setIdDocumentoRepository(Long idDocumentoRepository) {
		this.idDocumentoRepository = idDocumentoRepository;
	}

	@Column(name="EMAIL_VERSANTE", nullable=false)
	public String getEmailVersante() {
		return emailVersante;
	}

	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}

	@Column(name="ID_GRUPPO", nullable=true)
	public String getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}
	
	
	@Column(name="TIPO_SOGGETTO_VERS")
	public String getTipoSoggettoVers() {
		return tipoSoggettoVers;
	}


	public void setTipoSoggettoVers(String tipoSoggettoVers) {
		this.tipoSoggettoVers = tipoSoggettoVers;
	}

	@Column(name="COD_FISCALE_VERS")
	public String getCodFiscaleVers() {
		return codFiscaleVers;
	}


	public void setCodFiscaleVers(String codFiscaleVers) {
		this.codFiscaleVers = codFiscaleVers;
	}

	@Column(name="ANAGRAFICA_VERS")
	public String getAnagraficaVers() {
		return anagraficaVers;
	}


	public void setAnagraficaVers(String anagraficaVers) {
		this.anagraficaVers = anagraficaVers;
	}

	@Column(name="INDIRIZZO_VERS")
	public String getIndirizzoVers() {
		return indirizzoVers;
	}


	public void setIndirizzoVers(String indirizzoVers) {
		this.indirizzoVers = indirizzoVers;
	}

	@Column(name="NUMERO_CIVICO_VERS")
	public String getNumeroCivicoVers() {
		return numeroCivicoVers;
	}


	public void setNumeroCivicoVers(String numeroCivicoVers) {
		this.numeroCivicoVers = numeroCivicoVers;
	}

	@Column(name="CAP_VERS")
	public String getCapVers() {
		return capVers;
	}


	public void setCapVers(String capVers) {
		this.capVers = capVers;
	}

	@Column(name="LOCALITA_VERS")
	public String getLocalitaVers() {
		return localitaVers;
	}


	public void setLocalitaVers(String localitaVers) {
		this.localitaVers = localitaVers;
	}

	@Column(name="PROVINCIA_VERS")
	public String getProvinciaVers() {
		return provinciaVers;
	}


	public void setProvinciaVers(String provinciaVers) {
		this.provinciaVers = provinciaVers;
	}

	@Column(name="NAZIONE_VERS")
	public String getNazioneVers() {
		return nazioneVers;
	}


	public void setNazioneVers(String nazioneVers) {
		this.nazioneVers = nazioneVers;
	}
	
	@Column(name="LOCALE")
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	@Column(name="TIPO_IDENTIFICATIVO_ATTESTANTE")
	public String getTipoIdentificativoAttestante() {
		return tipoIdentificativoAttestante;
	}
	public void setTipoIdentificativoAttestante(String tipoIdentificativoAttestante) {
		this.tipoIdentificativoAttestante = tipoIdentificativoAttestante;
	}
	
	@Column(name="IDENTIFICATIVO_ATTESTANTE")
	public String getIdentificativoAttestante() {
		return identificativoAttestante;
	}
	public void setIdentificativoAttestante(String identificativoAttestante) {
		this.identificativoAttestante = identificativoAttestante;
	}
	
	@Column(name="DESCRIZIONE_ATTESTANTE")
	public String getDescrizioneAttestante() {
		return descrizioneAttestante;
	}
	public void setDescrizioneAttestante(String descrizioneAttestante) {
		this.descrizioneAttestante = descrizioneAttestante;
	}
	
	@Column(name="ID_PSP_MOD3")
	public String getIdPspModello3() {
		return idPspModello3;
	}
	public void setIdPspModello3(String idPspModello3) {
		this.idPspModello3 = idPspModello3;
	}
	@Column(name="ID_INTERMEDIARIO_MOD3")
	public String getIdIntermediarioModello3() {
		return idIntermediarioModello3;
	}
	public void setIdIntermediarioModello3(String idIntermediarioModello3) {
		this.idIntermediarioModello3 = idIntermediarioModello3;
	}
	@Column(name="ID_CANALE_MOD3")
	public String getIdCanaleModello3() {
		return idCanaleModello3;
	}
	public void setIdCanaleModello3(String idCanaleModello3) {
		this.idCanaleModello3 = idCanaleModello3;
	}

	
	
}
