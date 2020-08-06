package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@NamedQueries({
@NamedQuery(
	name="AeaByStato", 
	query=
	" SELECT aea  " +
	" from Aea aea " +
	" where aea.stato = :stato " +
	" and aea.aea.dataCreazione < :dataMin")})

/**
 * The persistent class for the AEA database table.
 * 
 */
@Entity
@Table(name="AEA")
public class Aea extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/	
	private String abiAddebito;
	private String abiAddebitoVar;
	private String abiBancaAllineamento;
	private String cabAddebito;
	private String cabAddebitoVar;
	private String causale;
	private String checkDigitAddebito;
	private String checkDigitAddebitoVar;
	private String cinAddebito;
	private String cinAddebitoVar;
	private String codIndividuale;
	private String codIndividualeVar;
	private String codPaeseAddebito;
	private String codPaeseAddebitoVar;
	private String codRiferimento;
	private Timestamp dataAttivazione;
	private Timestamp dataCessazione;
	private Timestamp dataCreazione;
	private Timestamp dataCreazioneOrig;
	private String descrizioneStato;
	private String divisa;
	private String flagIniziativa;
	private String flagStorno;
	private String ibanAddebito;
	private Integer idDisposizioneOrig;
	private String idFiscaleCreditore;
	private String idFiscaleSottoscrittore;
	private Integer idRendicontazioni;
	private String indirizzoSottoscrittore;
	private String intestatario;
	private BigDecimal massimoImportoRid;
	private String numeroCcAddebito;
	private String numeroCcAddebitoVar;
	private String numeroRateRid;
	private Integer progressivo;
	private String ragSocCreditore;
	private String ragSocIntAddebito;
	private String ragSocSottoscrittore;
	private String siaCreditore;
	private String siaCreditoreVar;
	private String stato;
	private String tipoCodIndividuale;
	private String tipoCodIndividualeVar;
	private String tipoIncassoRid;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	
	/*** Persistent Associations ***/
	private Aea aea;
	private Set<Aea> aeas;
	private DistintePagamento distintePagamento;
	
	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="aea_pk_gen")	
	@SequenceGenerator(
	    name="aea_pk_gen",
	    sequenceName="AEA_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="ABI_ADDEBITO")
	public String getAbiAddebito() {
		return this.abiAddebito;
	}

	public void setAbiAddebito(String abiAddebito) {
		this.abiAddebito = abiAddebito;
	}


	@Column(name="ABI_ADDEBITO_VAR")
	public String getAbiAddebitoVar() {
		return this.abiAddebitoVar;
	}

	public void setAbiAddebitoVar(String abiAddebitoVar) {
		this.abiAddebitoVar = abiAddebitoVar;
	}


	@Column(name="ABI_BANCA_ALLINEAMENTO")
	public String getAbiBancaAllineamento() {
		return this.abiBancaAllineamento;
	}

	public void setAbiBancaAllineamento(String abiBancaAllineamento) {
		this.abiBancaAllineamento = abiBancaAllineamento;
	}


	@Column(name="CAB_ADDEBITO")
	public String getCabAddebito() {
		return this.cabAddebito;
	}

	public void setCabAddebito(String cabAddebito) {
		this.cabAddebito = cabAddebito;
	}


	@Column(name="CAB_ADDEBITO_VAR")
	public String getCabAddebitoVar() {
		return this.cabAddebitoVar;
	}

	public void setCabAddebitoVar(String cabAddebitoVar) {
		this.cabAddebitoVar = cabAddebitoVar;
	}


	public String getCausale() {
		return this.causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}


	@Column(name="CHECK_DIGIT_ADDEBITO")
	public String getCheckDigitAddebito() {
		return this.checkDigitAddebito;
	}

	public void setCheckDigitAddebito(String checkDigitAddebito) {
		this.checkDigitAddebito = checkDigitAddebito;
	}


	@Column(name="CHECK_DIGIT_ADDEBITO_VAR")
	public String getCheckDigitAddebitoVar() {
		return this.checkDigitAddebitoVar;
	}

	public void setCheckDigitAddebitoVar(String checkDigitAddebitoVar) {
		this.checkDigitAddebitoVar = checkDigitAddebitoVar;
	}


	@Column(name="CIN_ADDEBITO")
	public String getCinAddebito() {
		return this.cinAddebito;
	}

	public void setCinAddebito(String cinAddebito) {
		this.cinAddebito = cinAddebito;
	}


	@Column(name="CIN_ADDEBITO_VAR")
	public String getCinAddebitoVar() {
		return this.cinAddebitoVar;
	}

	public void setCinAddebitoVar(String cinAddebitoVar) {
		this.cinAddebitoVar = cinAddebitoVar;
	}


	@Column(name="COD_INDIVIDUALE")
	public String getCodIndividuale() {
		return this.codIndividuale;
	}

	public void setCodIndividuale(String codIndividuale) {
		this.codIndividuale = codIndividuale;
	}


	@Column(name="COD_INDIVIDUALE_VAR")
	public String getCodIndividualeVar() {
		return this.codIndividualeVar;
	}

	public void setCodIndividualeVar(String codIndividualeVar) {
		this.codIndividualeVar = codIndividualeVar;
	}


	@Column(name="COD_PAESE_ADDEBITO")
	public String getCodPaeseAddebito() {
		return this.codPaeseAddebito;
	}

	public void setCodPaeseAddebito(String codPaeseAddebito) {
		this.codPaeseAddebito = codPaeseAddebito;
	}


	@Column(name="COD_PAESE_ADDEBITO_VAR")
	public String getCodPaeseAddebitoVar() {
		return this.codPaeseAddebitoVar;
	}

	public void setCodPaeseAddebitoVar(String codPaeseAddebitoVar) {
		this.codPaeseAddebitoVar = codPaeseAddebitoVar;
	}


	@Column(name="COD_RIFERIMENTO")
	public String getCodRiferimento() {
		return this.codRiferimento;
	}

	public void setCodRiferimento(String codRiferimento) {
		this.codRiferimento = codRiferimento;
	}


	@Column(name="DATA_ATTIVAZIONE")
	public Timestamp getDataAttivazione() {
		return this.dataAttivazione;
	}

	public void setDataAttivazione(Timestamp dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}


	@Column(name="DATA_CESSAZIONE")
	public Timestamp getDataCessazione() {
		return this.dataCessazione;
	}

	public void setDataCessazione(Timestamp dataCessazione) {
		this.dataCessazione = dataCessazione;
	}


	@Column(name="DATA_CREAZIONE")
	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Column(name="DATA_CREAZIONE_ORIG")
	public Timestamp getDataCreazioneOrig() {
		return this.dataCreazioneOrig;
	}

	public void setDataCreazioneOrig(Timestamp dataCreazioneOrig) {
		this.dataCreazioneOrig = dataCreazioneOrig;
	}


	@Column(name="DESCRIZIONE_STATO")
	public String getDescrizioneStato() {
		return this.descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}


	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}


	@Column(name="FLAG_INIZIATIVA")
	public String getFlagIniziativa() {
		return this.flagIniziativa;
	}

	public void setFlagIniziativa(String flagIniziativa) {
		this.flagIniziativa = flagIniziativa;
	}


	@Column(name="FLAG_STORNO")
	public String getFlagStorno() {
		return this.flagStorno;
	}

	public void setFlagStorno(String flagStorno) {
		this.flagStorno = flagStorno;
	}


	@Column(name="IBAN_ADDEBITO")
	public String getIbanAddebito() {
		return this.ibanAddebito;
	}

	public void setIbanAddebito(String ibanAddebito) {
		this.ibanAddebito = ibanAddebito;
	}


	@Column(name="ID_DISPOSIZIONE_ORIG")
	public Integer getIdDisposizioneOrig() {
		return this.idDisposizioneOrig;
	}

	public void setIdDisposizioneOrig(Integer idDisposizioneOrig) {
		this.idDisposizioneOrig = idDisposizioneOrig;
	}


	@Column(name="ID_FISCALE_CREDITORE")
	public String getIdFiscaleCreditore() {
		return this.idFiscaleCreditore;
	}

	public void setIdFiscaleCreditore(String idFiscaleCreditore) {
		this.idFiscaleCreditore = idFiscaleCreditore;
	}


	@Column(name="ID_FISCALE_SOTTOSCRITTORE")
	public String getIdFiscaleSottoscrittore() {
		return this.idFiscaleSottoscrittore;
	}

	public void setIdFiscaleSottoscrittore(String idFiscaleSottoscrittore) {
		this.idFiscaleSottoscrittore = idFiscaleSottoscrittore;
	}


	@Column(name="ID_RENDICONTAZIONI")
	public Integer getIdRendicontazioni() {
		return this.idRendicontazioni;
	}

	public void setIdRendicontazioni(Integer idRendicontazioni) {
		this.idRendicontazioni = idRendicontazioni;
	}


	@Column(name="INDIRIZZO_SOTTOSCRITTORE")
	public String getIndirizzoSottoscrittore() {
		return this.indirizzoSottoscrittore;
	}

	public void setIndirizzoSottoscrittore(String indirizzoSottoscrittore) {
		this.indirizzoSottoscrittore = indirizzoSottoscrittore;
	}


	public String getIntestatario() {
		return this.intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}


	@Column(name="MASSIMO_IMPORTO_RID")
	public BigDecimal getMassimoImportoRid() {
		return this.massimoImportoRid;
	}

	public void setMassimoImportoRid(BigDecimal massimoImportoRid) {
		this.massimoImportoRid = massimoImportoRid;
	}


	@Column(name="NUMERO_CC_ADDEBITO")
	public String getNumeroCcAddebito() {
		return this.numeroCcAddebito;
	}

	public void setNumeroCcAddebito(String numeroCcAddebito) {
		this.numeroCcAddebito = numeroCcAddebito;
	}


	@Column(name="NUMERO_CC_ADDEBITO_VAR")
	public String getNumeroCcAddebitoVar() {
		return this.numeroCcAddebitoVar;
	}

	public void setNumeroCcAddebitoVar(String numeroCcAddebitoVar) {
		this.numeroCcAddebitoVar = numeroCcAddebitoVar;
	}


	@Column(name="NUMERO_RATE_RID")
	public String getNumeroRateRid() {
		return this.numeroRateRid;
	}

	public void setNumeroRateRid(String numeroRateRid) {
		this.numeroRateRid = numeroRateRid;
	}


	public Integer getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}


	@Column(name="RAG_SOC_CREDITORE")
	public String getRagSocCreditore() {
		return this.ragSocCreditore;
	}

	public void setRagSocCreditore(String ragSocCreditore) {
		this.ragSocCreditore = ragSocCreditore;
	}


	@Column(name="RAG_SOC_INT_ADDEBITO")
	public String getRagSocIntAddebito() {
		return this.ragSocIntAddebito;
	}

	public void setRagSocIntAddebito(String ragSocIntAddebito) {
		this.ragSocIntAddebito = ragSocIntAddebito;
	}


	@Column(name="RAG_SOC_SOTTOSCRITTORE")
	public String getRagSocSottoscrittore() {
		return this.ragSocSottoscrittore;
	}

	public void setRagSocSottoscrittore(String ragSocSottoscrittore) {
		this.ragSocSottoscrittore = ragSocSottoscrittore;
	}


	@Column(name="SIA_CREDITORE")
	public String getSiaCreditore() {
		return this.siaCreditore;
	}

	public void setSiaCreditore(String siaCreditore) {
		this.siaCreditore = siaCreditore;
	}


	@Column(name="SIA_CREDITORE_VAR")
	public String getSiaCreditoreVar() {
		return this.siaCreditoreVar;
	}

	public void setSiaCreditoreVar(String siaCreditoreVar) {
		this.siaCreditoreVar = siaCreditoreVar;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="TIPO_COD_INDIVIDUALE")
	public String getTipoCodIndividuale() {
		return this.tipoCodIndividuale;
	}

	public void setTipoCodIndividuale(String tipoCodIndividuale) {
		this.tipoCodIndividuale = tipoCodIndividuale;
	}


	@Column(name="TIPO_COD_INDIVIDUALE_VAR")
	public String getTipoCodIndividualeVar() {
		return this.tipoCodIndividualeVar;
	}

	public void setTipoCodIndividualeVar(String tipoCodIndividualeVar) {
		this.tipoCodIndividualeVar = tipoCodIndividualeVar;
	}


	@Column(name="TIPO_INCASSO_RID")
	public String getTipoIncassoRid() {
		return this.tipoIncassoRid;
	}

	public void setTipoIncassoRid(String tipoIncassoRid) {
		this.tipoIncassoRid = tipoIncassoRid;
	}

	//bi-directional many-to-one association to Aea
    @ManyToOne
	@JoinColumn(name="ID_REVOCA")
	public Aea getAea() {
		return this.aea;
	}

	public void setAea(Aea aea) {
		this.aea = aea;
	}
	

	//bi-directional many-to-one association to Aea
	@OneToMany(mappedBy="aea")
	public Set<Aea> getAeas() {
		return this.aeas;
	}

	public void setAeas(Set<Aea> aeas) {
		this.aeas = aeas;
	}
	

	//bi-directional many-to-one association to DistintePagamento
    @ManyToOne
	@JoinColumn(name="ID_DISTINTE_PAGAMENTO")
	public DistintePagamento getDistintePagamento() {
		return this.distintePagamento;
	}

	public void setDistintePagamento(DistintePagamento distintePagamento) {
		this.distintePagamento = distintePagamento;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Aea [abiAddebito=");
		builder.append(abiAddebito);
		builder.append(", abiAddebitoVar=");
		builder.append(abiAddebitoVar);
		builder.append(", abiBancaAllineamento=");
		builder.append(abiBancaAllineamento);
		builder.append(", cabAddebito=");
		builder.append(cabAddebito);
		builder.append(", cabAddebitoVar=");
		builder.append(cabAddebitoVar);
		builder.append(", causale=");
		builder.append(causale);
		builder.append(", checkDigitAddebito=");
		builder.append(checkDigitAddebito);
		builder.append(", checkDigitAddebitoVar=");
		builder.append(checkDigitAddebitoVar);
		builder.append(", cinAddebito=");
		builder.append(cinAddebito);
		builder.append(", cinAddebitoVar=");
		builder.append(cinAddebitoVar);
		builder.append(", codIndividuale=");
		builder.append(codIndividuale);
		builder.append(", codIndividualeVar=");
		builder.append(codIndividualeVar);
		builder.append(", codPaeseAddebito=");
		builder.append(codPaeseAddebito);
		builder.append(", codPaeseAddebitoVar=");
		builder.append(codPaeseAddebitoVar);
		builder.append(", codRiferimento=");
		builder.append(codRiferimento);
		builder.append(", dataAttivazione=");
		builder.append(dataAttivazione);
		builder.append(", dataCessazione=");
		builder.append(dataCessazione);
		builder.append(", dataCreazione=");
		builder.append(dataCreazione);
		builder.append(", dataCreazioneOrig=");
		builder.append(dataCreazioneOrig);
		builder.append(", descrizioneStato=");
		builder.append(descrizioneStato);
		builder.append(", divisa=");
		builder.append(divisa);
		builder.append(", flagIniziativa=");
		builder.append(flagIniziativa);
		builder.append(", flagStorno=");
		builder.append(flagStorno);
		builder.append(", ibanAddebito=");
		builder.append(ibanAddebito);
		builder.append(", idDisposizioneOrig=");
		builder.append(idDisposizioneOrig);
		builder.append(", idFiscaleCreditore=");
		builder.append(idFiscaleCreditore);
		builder.append(", idFiscaleSottoscrittore=");
		builder.append(idFiscaleSottoscrittore);
		builder.append(", idRendicontazioni=");
		builder.append(idRendicontazioni);
		builder.append(", indirizzoSottoscrittore=");
		builder.append(indirizzoSottoscrittore);
		builder.append(", intestatario=");
		builder.append(intestatario);
		builder.append(", massimoImportoRid=");
		builder.append(massimoImportoRid);
		builder.append(", numeroCcAddebito=");
		builder.append(numeroCcAddebito);
		builder.append(", numeroCcAddebitoVar=");
		builder.append(numeroCcAddebitoVar);
		builder.append(", numeroRateRid=");
		builder.append(numeroRateRid);
		builder.append(", progressivo=");
		builder.append(progressivo);
		builder.append(", ragSocCreditore=");
		builder.append(ragSocCreditore);
		builder.append(", ragSocIntAddebito=");
		builder.append(ragSocIntAddebito);
		builder.append(", ragSocSottoscrittore=");
		builder.append(ragSocSottoscrittore);
		builder.append(", siaCreditore=");
		builder.append(siaCreditore);
		builder.append(", siaCreditoreVar=");
		builder.append(siaCreditoreVar);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", tipoCodIndividuale=");
		builder.append(tipoCodIndividuale);
		builder.append(", tipoCodIndividualeVar=");
		builder.append(tipoCodIndividualeVar);
		builder.append(", tipoIncassoRid=");
		builder.append(tipoIncassoRid);
		builder.append(", tsAggiornamento=");
		builder.append(tsAggiornamento);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		//builder.append(", aea=");
		//builder.append(aea);
		//builder.append(", aeas=");
		//builder.append(aeas);
		//builder.append(", distintePagamento=");
		//builder.append(distintePagamento);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append("]");
		return builder.toString();
	}
	
	
}