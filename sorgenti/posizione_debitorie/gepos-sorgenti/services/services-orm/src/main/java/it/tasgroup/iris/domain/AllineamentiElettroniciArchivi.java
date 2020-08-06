package it.tasgroup.iris.domain;

/**
 * @author FabriziE
 */

import it.nch.is.fo.profilo.Intestatari;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

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

@Entity
@Table(name="AEA")
@NamedQueries(
{
@NamedQuery(
	name="getDelegheAccettateByIntestatario",
	query="select aeas from AllineamentiElettroniciArchivi aeas " +
			"where aeas.stato=:stato " +
		  	"and aeas.intestatario.corporate=:intestatario and aeas.causale = '90211' " +
			"order by dataAttivazione desc "),	
@NamedQuery(
	name="getDelegheAccettateBySottoscrittore",
	query="select aeas from AllineamentiElettroniciArchivi aeas " +
			"where aeas.stato=:stato " +
		  	"and aeas.idFiscaleSottoscrittore=:sottoscrittore  " +
			"and aeas.causale = '90211' order by dataAttivazione desc "),	
 @NamedQuery(
	name="getDelegheAccettateByIntestAndSottoscr",
	query="select aeas from AllineamentiElettroniciArchivi aeas " +
			"where aeas.stato=:stato " +
			"and aeas.intestatario.corporate=:intestatario "+
	 	  	"and aeas.idFiscaleSottoscrittore=:sottoscrittore  " +
			"and aeas.causale = '90211' order by dataAttivazione desc  "),	
@NamedQuery(
	name="countDelegheByStatus",
	query="select count(aeas) from AllineamentiElettroniciArchivi aeas " +
	      	"where aeas.stato=:statoDocumento "),	
@NamedQuery(
	name="getDelegaByIban",
	query="select aeas from AllineamentiElettroniciArchivi aeas "+
   	  	  "where aeas.ibanAddebito=:iban "+
	 	  "and aeas.idFiscaleSottoscrittore=:sottoscrittore " +
	 	  "and (stato ='ACCETTATA' or stato = 'IN_CORSO_APPROVAZIONE' or stato = 'IN_CORSO_REVOCA' or stato = 'VARIATA')"),	
@NamedQuery(
	name="getAllineamentiElettroniciArchiviById",
	query="select aeas from AllineamentiElettroniciArchivi aeas "+
		  	"where aeas.id=:id")
}
)
//getDocumentoDiPagamentoByIdDocumento inserita per problemi con i pagamenti 
public class AllineamentiElettroniciArchivi extends BaseIdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1231963839181791205L;
	
	/*** Persistent Properties ***/
	private Integer progressivo;
	private String flagIniziativa;
	private String causale;
	private String codIndividuale;
	
	private String stato;	
	private String descrizioneStato;

	private String idFiscaleSottoscrittore;
	private String ragSocialeSottoscrittore;
	private String indirizzoSottoscrittore;
	
	private String tipoCodIndividuale;
//	private String tipo;

	private String ibanAddebito;
	
	private String codPaeseAddebito;
	private String checkDigitAddebito;
	private String cinAddebito;
	private String abiAddebito;
	private String cabAddebito;
	private String numeroCcAddebito;
	private String ragSocialeIntAddebito;
	private String idFiscaleCreditore;
	private String ragSocialeCreditore;
	private String siaCreditore;
	private String abiBancaAllineamento;
	private String tipoIncassoRid;
	private BigInteger massimoImportoRid;
	private String numRateRid;
	private String flagStorno;
	private String codRiferimento;
	private String divisa;
	private String siaCreditoreVar;
	private String codIndividualeVar;
	private String tipoCodIndividualeVar;
	private String codPaeseAddebitoVar;
	private String checkDigitAddebitoVar;
	private String cinAddebitoVar;
	private String abiAddebitoVar;
	private String cabAddebitoVar;
	private String numeroCcAddebitoVar;
	private String idDisposizioneOrig;
	private Timestamp dataCreazioneOrig;
    private String opInserimento;
    private String opAggiornamento;
	private Timestamp dataCreazione;
	private Timestamp dataAttivazione;
	private Timestamp dataCessazione;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	
	/*** Persistent Collections ***/
	private GestioneFlussi distintePagamento;
	private AllineamentiElettroniciArchivi revoca;
	private Intestatari intestatario;
	
	private Set<AllineamentiElettroniciArchivi> deleghe;
	
	private Rendicontazioni rendicontazioni;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="allineamenti_pk_gen")	
	@SequenceGenerator(
		    name="allineamenti_pk_gen",
		    sequenceName="AEA_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;
	 
	}	 
	
	public void setId(Long id) {		
		this.id = id;	 
	} 		


	@Column(name="PROGRESSIVO")
	public Integer getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}


	@Column(name="FLAG_INIZIATIVA")
	public String getFlagIniziativa() {
		return this.flagIniziativa;
	}

	public void setFlagIniziativa(String flagIniziativa) {
		this.flagIniziativa = flagIniziativa;
	}

	@Column(name="STATO")
	public String getStato() {
		return this.stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
//	@Column(nullable=false, length=2)
//	public String getTipo() {
//		return this.tipo;
//	}
//	
//	@Transient
//	public EnumTipoDDP getTipoEnum() {
//		return EnumTipoDDP.getByKey(this.tipo);
//	}
//	
//	public void setTipo(String tipo) {
//		this.tipo = tipo;
//	}
//	
//	@Transient
//	public void setTipoEnum(EnumTipoDDP tipoEnum) {
//		this.tipo = tipoEnum.getChiave();
//	}
//
	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	
	

	@Column(name="CAUSALE")
	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	@Column(name="COD_INDIVIDUALE")
	public String getCodIndividuale() {
		return codIndividuale;
	}

	public void setCodIndividuale(String codIndividuale) {
		this.codIndividuale = codIndividuale;
	}

	@Column(name="DESCRIZIONE_STATO")
	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

	@Column(name="ID_FISCALE_SOTTOSCRITTORE")
	public String getIdFiscaleSottoscrittore() {
		return idFiscaleSottoscrittore;
	}

	public void setIdFiscaleSottoscrittore(String idFiscaleSottoscrittore) {
		this.idFiscaleSottoscrittore = idFiscaleSottoscrittore;
	}

	@Column(name="RAG_SOC_SOTTOSCRITTORE")
	public String getRagSocialeSottoscrittore() {
		return ragSocialeSottoscrittore;
	}

	public void setRagSocialeSottoscrittore(String ragSocialeSottoscrittore) {
		this.ragSocialeSottoscrittore = ragSocialeSottoscrittore;
	}

	@Column(name="INDIRIZZO_SOTTOSCRITTORE")
	public String getIndirizzoSottoscrittore() {
		return indirizzoSottoscrittore;
	}

	public void setIndirizzoSottoscrittore(String indirizzoSottoscrittore) {
		this.indirizzoSottoscrittore = indirizzoSottoscrittore;
	}

	@Column(name="TIPO_COD_INDIVIDUALE")
	public String getTipoCodIndividuale() {
		return tipoCodIndividuale;
	}

	public void setTipoCodIndividuale(String tipoCodIndividuale) {
		this.tipoCodIndividuale = tipoCodIndividuale;
	}

	@Column(name="IBAN_ADDEBITO")
	public String getIbanAddebito() {
		return ibanAddebito;
	}

	public void setIbanAddebito(String ibanAddebito) {
		this.ibanAddebito = ibanAddebito;
	}

	@Column(name="COD_PAESE_ADDEBITO")
	public String getCodPaeseAddebito() {
		return codPaeseAddebito;
	}

	public void setCodPaeseAddebito(String codPaeseAddebito) {
		this.codPaeseAddebito = codPaeseAddebito;
	}

	@Column(name="CHECK_DIGIT_ADDEBITO")
	public String getCheckDigitAddebito() {
		return checkDigitAddebito;
	}

	public void setCheckDigitAddebito(String checkDigitAddebito) {
		this.checkDigitAddebito = checkDigitAddebito;
	}

	@Column(name="CIN_ADDEBITO")
	public String getCinAddebito() {
		return cinAddebito;
	}

	public void setCinAddebito(String cinAddebito) {
		this.cinAddebito = cinAddebito;
	}

	@Column(name="ABI_ADDEBITO")
	public String getAbiAddebito() {
		return abiAddebito;
	}

	public void setAbiAddebito(String abiAddebito) {
		this.abiAddebito = abiAddebito;
	}

	@Column(name="CAB_ADDEBITO")
	public String getCabAddebito() {
		return cabAddebito;
	}

	public void setCabAddebito(String cabAddebito) {
		this.cabAddebito = cabAddebito;
	}

	@Column(name="NUMERO_CC_ADDEBITO")
	public String getNumeroCcAddebito() {
		return numeroCcAddebito;
	}

	public void setNumeroCcAddebito(String numeroCcAddebito) {
		this.numeroCcAddebito = numeroCcAddebito;
	}

	@Column(name="RAG_SOC_INT_ADDEBITO")
	public String getRagSocialeIntAddebito() {
		return ragSocialeIntAddebito;
	}

	public void setRagSocialeIntAddebito(String ragSocialeIntAddebito) {
		this.ragSocialeIntAddebito = ragSocialeIntAddebito;
	}

	@Column(name="ID_FISCALE_CREDITORE")
	public String getIdFiscaleCreditore() {
		return idFiscaleCreditore;
	}

	public void setIdFiscaleCreditore(String idFiscaleCreditore) {
		this.idFiscaleCreditore = idFiscaleCreditore;
	}

	@Column(name="RAG_SOC_CREDITORE")
	public String getRagSocialeCreditore() {
		return ragSocialeCreditore;
	}

	public void setRagSocialeCreditore(String ragSocialeCreditore) {
		this.ragSocialeCreditore = ragSocialeCreditore;
	}

	@Column(name="SIA_CREDITORE")
	public String getSiaCreditore() {
		return siaCreditore;
	}

	public void setSiaCreditore(String siaCreditore) {
		this.siaCreditore = siaCreditore;
	}

	@Column(name="ABI_BANCA_ALLINEAMENTO")
	public String getAbiBancaAllineamento() {
		return abiBancaAllineamento;
	}

	public void setAbiBancaAllineamento(String abiBancaAllineamento) {
		this.abiBancaAllineamento = abiBancaAllineamento;
	}

	@Column(name="TIPO_INCASSO_RID")
	public String getTipoIncassoRid() {
		return tipoIncassoRid;
	}

	public void setTipoIncassoRid(String tipoIncassoRid) {
		this.tipoIncassoRid = tipoIncassoRid;
	}

	@Column(name="MASSIMO_IMPORTO_RID")
	public BigInteger getMassimoImportoRid() {
		return massimoImportoRid;
	}

	public void setMassimoImportoRid(BigInteger massimoImportoRid) {
		this.massimoImportoRid = massimoImportoRid;
	}

	@Column(name="NUMERO_RATE_RID")
	public String getNumRateRid() {
		return numRateRid;
	}

	public void setNumRateRid(String numRateRid) {
		this.numRateRid = numRateRid;
	}

	@Column(name="FLAG_STORNO")
	public String getFlagStorno() {
		return flagStorno;
	}

	public void setFlagStorno(String flagStorno) {
		this.flagStorno = flagStorno;
	}

	@Column(name="COD_RIFERIMENTO")
	public String getCodRiferimento() {
		return codRiferimento;
	}

	public void setCodRiferimento(String codRiferimento) {
		this.codRiferimento = codRiferimento;
	}

	@Column(name="DIVISA")
	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	@Column(name="SIA_CREDITORE_VAR")
	public String getSiaCreditoreVar() {
		return siaCreditoreVar;
	}

	public void setSiaCreditoreVar(String siaCreditoreVar) {
		this.siaCreditoreVar = siaCreditoreVar;
	}

	@Column(name="COD_INDIVIDUALE_VAR")
	public String getCodIndividualeVar() {
		return codIndividualeVar;
	}

	public void setCodIndividualeVar(String codIndividualeVar) {
		this.codIndividualeVar = codIndividualeVar;
	}

	@Column(name="TIPO_COD_INDIVIDUALE_VAR")
	public String getTipoCodIndividualeVar() {
		return tipoCodIndividualeVar;
	}

	public void setTipoCodIndividualeVar(String tipoCodIndividualeVar) {
		this.tipoCodIndividualeVar = tipoCodIndividualeVar;
	}

	@Column(name="COD_PAESE_ADDEBITO_VAR")
	public String getCodPaeseAddebitoVar() {
		return codPaeseAddebitoVar;
	}

	public void setCodPaeseAddebitoVar(String codPaeseAddebitoVar) {
		this.codPaeseAddebitoVar = codPaeseAddebitoVar;
	}

	@Column(name="CHECK_DIGIT_ADDEBITO_VAR")
	public String getCheckDigitAddebitoVar() {
		return checkDigitAddebitoVar;
	}

	public void setCheckDigitAddebitoVar(String checkDigitAddebitoVar) {
		this.checkDigitAddebitoVar = checkDigitAddebitoVar;
	}

	@Column(name="CIN_ADDEBITO_VAR")
	public String getCinAddebitoVar() {
		return cinAddebitoVar;
	}

	public void setCinAddebitoVar(String cinAddebitoVar) {
		this.cinAddebitoVar = cinAddebitoVar;
	}

	@Column(name="ABI_ADDEBITO_VAR")
	public String getAbiAddebitoVar() {
		return abiAddebitoVar;
	}

	public void setAbiAddebitoVar(String abiAddebitoVar) {
		this.abiAddebitoVar = abiAddebitoVar;
	}

	@Column(name="CAB_ADDEBITO_VAR")
	public String getCabAddebitoVar() {
		return cabAddebitoVar;
	}

	public void setCabAddebitoVar(String cabAddebitoVar) {
		this.cabAddebitoVar = cabAddebitoVar;
	}

	@Column(name="NUMERO_CC_ADDEBITO_VAR")
	public String getNumeroCcAddebitoVar() {
		return numeroCcAddebitoVar;
	}

	public void setNumeroCcAddebitoVar(String numeroCcAddebitoVar) {
		this.numeroCcAddebitoVar = numeroCcAddebitoVar;
	}

	@Column(name="ID_DISPOSIZIONE_ORIG")
	public String getIdDisposizioneOrig() {
		return idDisposizioneOrig;
	}

	public void setIdDisposizioneOrig(String idDisposizioneOrig) {
		this.idDisposizioneOrig = idDisposizioneOrig;
	}

	@Column(name="DATA_CREAZIONE_ORIG")
	public Timestamp getDataCreazioneOrig() {
		return dataCreazioneOrig;
	}

	public void setDataCreazioneOrig(Timestamp dataCreazioneOrig) {
		this.dataCreazioneOrig = dataCreazioneOrig;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="DATA_CREAZIONE")
	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	@Column(name="DATA_ATTIVAZIONE")
	public Timestamp getDataAttivazione() {
		return dataAttivazione;
	}

	public void setDataAttivazione(Timestamp dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}

	@Column(name="DATA_CESSAZIONE")
	public Timestamp getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Timestamp dataCessazione) {
		this.dataCessazione = dataCessazione;
	}
	
	@OneToMany(mappedBy="revoca", targetEntity=AllineamentiElettroniciArchivi.class)
	public Set<AllineamentiElettroniciArchivi> getDeleghe() {
		return this.deleghe;
	}
	public void setDeleghe(Set<AllineamentiElettroniciArchivi> deleghe) {
		this.deleghe = deleghe;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="INTESTATARIO")
	public Intestatari getIntestatario() {
		return this.intestatario;
	}
	public void setIntestatario(Intestatari intestatario) {
		this.intestatario = intestatario;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_DISTINTE_PAGAMENTO")
	public GestioneFlussi getDistintePagamento() {
		return this.distintePagamento;
	}
	public void setDistintePagamento(GestioneFlussi distintePagamento) {
		this.distintePagamento = distintePagamento;
	}

	@ManyToOne
	@JoinColumn(name="ID_REVOCA")
	public AllineamentiElettroniciArchivi getRevoca() {
		return this.revoca;
	}
	public void setRevoca(AllineamentiElettroniciArchivi revoca) {
		this.revoca = revoca;
	}

	// bi-directional many-to-one association to Rendicontazioni
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RENDICONTAZIONI", nullable = true)
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((getDistintePagamento().getId() == null || 
				                    getRevoca().getId() == null || 
				                    getIntestatario().getId() == null) ? 0 : getDistintePagamento().getId().hashCode()*getRevoca().getId().hashCode()*getIntestatario().getId().hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AllineamentiElettroniciArchivi other = (AllineamentiElettroniciArchivi) obj;
		
		if (getDistintePagamento().getId() == null) {
			if (other.getDistintePagamento().getId() != null)
				return false;
		} else if (!getDistintePagamento().getId().equals(other.getDistintePagamento().getId()))
			return false;
		
		if (getRevoca().getId() == null) {
			if (other.getRevoca().getId() != null)
				return false;
		} else if (!getRevoca().getId().equals(other.getRevoca().getId()))
			return false;
		
		if (getIntestatario().getId() == null) {
			if (other.getIntestatario().getId() != null)
				return false;
		} else if (!getIntestatario().getId().equals(other.getIntestatario().getId()))
			return false;
		
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AllineamentoElettriniciArchivi [id=");
		builder.append(getId());
		builder.append(", progressivo=");
		builder.append(getProgressivo());
		builder.append(", flagIniziativa=");
		builder.append(getFlagIniziativa());
		builder.append(", dataCreazione=");
		builder.append(getDataCreazione());
		builder.append(", dataAttivazione=");
		builder.append(getDataAttivazione());
		builder.append(", dataCessazione=");
		builder.append(getDataCessazione());
		builder.append(", causale=");
		builder.append(getCausale());
		builder.append(", codIndividuale=");
		builder.append(getCodIndividuale());
		builder.append(", stato=");
		builder.append(getStato());
		builder.append(", descrizioneStato=");
		builder.append(getDescrizioneStato());
		builder.append(", idFiscaleSottoscrittore=");
		builder.append(getIdFiscaleSottoscrittore());
		builder.append(", ragSocialeSottoscrittore=");
		builder.append(getRagSocialeSottoscrittore());
		builder.append(", indirizzoSottoscrittore=");
		builder.append(getIndirizzoSottoscrittore());
		builder.append(", tipoCodIndividuale=");
		builder.append(getTipoCodIndividuale());
		builder.append(", ibanAddebito=");
		builder.append(getIbanAddebito());
		builder.append(", codPaeseAddebito=");
		builder.append(getCodPaeseAddebito());
		builder.append(", checkDigitAddebito()=");
		builder.append(getCheckDigitAddebito());
		builder.append(", cinAddebito=");
		builder.append(getCinAddebito());
		builder.append(", abiAddebito=");
		builder.append(getAbiAddebito());
		builder.append(", cabAddebito=");
		builder.append(getCabAddebito());
		builder.append(", numeroCcAddebito=");
		builder.append(getNumeroCcAddebito());
		builder.append(", ragSocialeIntAddebito=");
		builder.append(getRagSocialeIntAddebito());
		builder.append(", idFiscaleCreditore=");
		builder.append(getIdFiscaleCreditore());
		builder.append(", ragSocialeCreditore=");
		builder.append(getRagSocialeCreditore());
		builder.append(", siaCreditore=");
		builder.append(getSiaCreditore());
		builder.append(", abiBancaAllineamento=");
		builder.append(getAbiBancaAllineamento());
		builder.append(", tipoIncassoRid=");
		builder.append(getTipoIncassoRid());
		builder.append(", massimoImportoRid=");
		builder.append(getMassimoImportoRid());
		builder.append(", numRateRid=");
		builder.append(getNumRateRid());
		builder.append(", flagStorno=");
		builder.append(getFlagStorno());
		builder.append(", codRiferimento=");
		builder.append(getCodRiferimento());
		builder.append(", divisa=");
		builder.append(getDivisa());
		builder.append(", siaCreditoreVar=");
		builder.append(getSiaCreditoreVar());
		builder.append(", tipoCodIndividualeVar=");
		builder.append(getTipoCodIndividualeVar());
		builder.append(", codIndividualeVar=");
		builder.append(getCodIndividualeVar());
		builder.append(", codPaeseAddebitoVar=");
		builder.append(getCodPaeseAddebitoVar());
		builder.append(", checkDigitAddebitoVar=");
		builder.append(getCheckDigitAddebitoVar());
		builder.append(", cinAddebitoVar=");
		builder.append(getCinAddebitoVar());
		builder.append(", abiAddebitoVar=");
		builder.append(getAbiAddebitoVar());
		builder.append(", cabAddebitoVar=");
		builder.append(getCabAddebitoVar());
		builder.append(", numeroCcAddebitoVar=");
		builder.append(getNumeroCcAddebitoVar());
		builder.append(", revoca=");
		builder.append(getRevoca());
		builder.append(", idDisposizioneOrig=");
		builder.append(getIdDisposizioneOrig());
		builder.append(", dataCreazioneOrig=");
		builder.append(getDataCreazioneOrig());
		builder.append(", intestatario=");
		builder.append(getIntestatario().getCorporate());
		builder.append(", distintePagamento=");
		builder.append(getDistintePagamento() != null ? getDistintePagamento().toString() : "");
		builder.append(", rendicontazioneId=");
		builder.append(", tsAggiornamento=");
		builder.append(getTsAggiornamento());
		builder.append(", tsInserimento=");
		builder.append(getTsInserimento());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

	
}