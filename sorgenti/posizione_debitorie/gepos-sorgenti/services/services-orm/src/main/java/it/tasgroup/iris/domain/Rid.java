package it.tasgroup.iris.domain;

/**
 * @author FabriziE
 */

import it.nch.is.fo.profilo.Intestatari;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="RID")
@NamedQueries(
{
@NamedQuery(
	name="countRidByStatus",
	query="select count(rids) from Rid rids " +
			"where rids.stato=:statoDocumento "),	
@NamedQuery(
		name="getRidByIntestatario",
		query="select rids from Rid rids "+
				"where rids.intestatario.corporate=:intestatario"),
@NamedQuery(
		name="listRidByRendicontazioneAndCausale",
		query="select rids from Rid rids "+
				"where rids.rendicontazioni.id=:idRendicontazione "+
				"and rids.causale<>:causale"),
@NamedQuery(
		name="getRidById",
		query="select rid from Rid rid "+
				"where rid.id=:id ")
				
}
)

public class Rid extends BaseIdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1231963839181791205L;
	
	/*** Persistent Properties ***/
	private Integer progressivo;
	private String flagIniziativa;
	private Timestamp dataCreazione;
	private Timestamp dataScadenza;
	private Timestamp dataDecorrenza;
	private String causale;
	private String stato;	
	private String descrizioneStato;	
	private BigDecimal importo;
	private String abiBancaAssuntrice;
	private String cabBancaAssuntrice;
	private String abiBancaDomiciliaria;
	private String siaCreditore;
	private String tipoCodIndividuale;
	private String codDebitore;
	private String tipoIncassoRid;
	private String divisa;
	private String ibanOrdinante;
	private String ragSocialeCreditore;
	private String ragSocialeDebitore;
	private String indirizzoDebitore;
	private String riferimentoDebito;
	private String codRiferimento;
	private String causaleStorno;
	private String flagStorno;
	private Integer idDisposizioneOrig;
//	private Timestamp dataScadenzaOrig; 
	private Integer dataScadenzaOrig;
	private Integer idBozzeBonificiRiaccredito;
	private String opInserimento;
    private String opAggiornamento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	
	/*** Persistent Collections ***/
	private Intestatari intestatario;
	private GestioneFlussi distintePagamento;
	
	private Rendicontazioni rendicontazioni;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="rid_pk_gen")	
	@SequenceGenerator(
		    name="rid_pk_gen",
		    sequenceName="RID_ID_SEQ",
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

	@Column(name="STATO")
	public String getStato() {
		return this.stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	@Column(name="DESCRIZIONE_STATO")
	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
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

	@Column(name="TIPO_COD_INDIVIDUALE")
	public String getTipoCodIndividuale() {
		return tipoCodIndividuale;
	}

	public void setTipoCodIndividuale(String tipoCodIndividuale) {
		this.tipoCodIndividuale = tipoCodIndividuale;
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

	@Column(name="TIPO_INCASSO_RID")
	public String getTipoIncassoRid() {
		return tipoIncassoRid;
	}

	public void setTipoIncassoRid(String tipoIncassoRid) {
		this.tipoIncassoRid = tipoIncassoRid;
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

//	public Timestamp getDataScadenzaOrig() {
	@Column(name="DATA_SCADENZA_ORIG")
	public Integer getDataScadenzaOrig() {
		return dataScadenzaOrig;
	}

//	public void setDataScadenzaOrig(Timestamp dataScadenzaOrig) {
	public void setDataScadenzaOrig(Integer dataScadenzaOrig) {
		this.dataScadenzaOrig = dataScadenzaOrig;
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

	@Column(name="DATA_SCADENZA")
	public Timestamp getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	@Column(name="IMPORTO")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	@Column(name="ABI_BANCA_ASSUNTRICE")
	public String getAbiBancaAssuntrice() {
		return abiBancaAssuntrice;
	}

	public void setAbiBancaAssuntrice(String abiBancaAssuntrice) {
		this.abiBancaAssuntrice = abiBancaAssuntrice;
	}

	@Column(name="CAB_BANCA_ASSUNTRICE")
	public String getCabBancaAssuntrice() {
		return cabBancaAssuntrice;
	}

	public void setCabBancaAssuntrice(String cabBancaAssuntrice) {
		this.cabBancaAssuntrice = cabBancaAssuntrice;
	}

	@Column(name="ABI_BANCA_DOMICILIATARIA")
	public String getAbiBancaDomiciliaria() {
		return abiBancaDomiciliaria;
	}

	public void setAbiBancaDomiciliaria(String abiBancaDomiciliaria) {
		this.abiBancaDomiciliaria = abiBancaDomiciliaria;
	}

	@Column(name="COD_DEBITORE")
	public String getCodDebitore() {
		return codDebitore;
	}

	public void setCodDebitore(String codDebitore) {
		this.codDebitore = codDebitore;
	}

	@Column(name="IBAN_ORDINANTE")
	public String getIbanOrdinante() {
		return ibanOrdinante;
	}

	public void setIbanOrdinante(String ibanOrdinante) {
		this.ibanOrdinante = ibanOrdinante;
	}

	@Column(name="RAG_SOC_DEBITORE")
	public String getRagSocialeDebitore() {
		return ragSocialeDebitore;
	}

	public void setRagSocialeDebitore(String ragSocialeDebitore) {
		this.ragSocialeDebitore = ragSocialeDebitore;
	}

	@Column(name="RIFERIMENTI_DEBITO")
	public String getRiferimentoDebito() {
		return riferimentoDebito;
	}

	public void setRiferimentoDebito(String riferimentoDebito) {
		this.riferimentoDebito = riferimentoDebito;
	}

	@Column(name="CAUSALE_STORNO")
	public String getCausaleStorno() {
		return causaleStorno;
	}

	public void setCausaleStorno(String causaleStorno) {
		this.causaleStorno = causaleStorno;
	}

	@Column(name="ID_DISPOSIZIONE_ORIG")
	public Integer getIdDisposizioneOrig() {
		return idDisposizioneOrig;
	}

	public void setIdDisposizioneOrig(Integer idDisposizioneOrig) {
		this.idDisposizioneOrig = idDisposizioneOrig;
	}

	@Column(name="ID_BOZZE_BONIFICI_RIACCREDITO")
	public Integer getIdBozzeBonificiRiaccredito() {
		return idBozzeBonificiRiaccredito;
	}

	public void setIdBozzeBonificiRiaccredito(Integer idBozzeBonificiRiaccredito) {
		this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
	}

	@ManyToOne
	@JoinColumn(name="INTESTATARIO")
	public Intestatari getIntestatario() {
		return this.intestatario;
	}
	public void setIntestatario(Intestatari intestatari) {
		this.intestatario = intestatari;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_DISTINTE_PAGAMENTO")
	public GestioneFlussi getDistintePagamento() {
		return this.distintePagamento;
	}
	public void setDistintePagamento(GestioneFlussi distintePagamento) {
		this.distintePagamento = distintePagamento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((getIntestatario().getId() == null || getDistintePagamento().getId() == null) ? 0 : getIntestatario().getId().hashCode()*getDistintePagamento().getId().hashCode());
		
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
		Rid other = (Rid) obj;
		
		if (getDistintePagamento().getId() == null) {
			if (other.getDistintePagamento().getId() != null)
				return false;
		} else if (!getDistintePagamento().getId().equals(other.getDistintePagamento().getId()))
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
		builder.append("RID [id=");
		builder.append(getId());
		builder.append(", progressivo=");
		builder.append(getProgressivo());
		builder.append(", flagIniziativa=");
		builder.append(getFlagIniziativa());
		builder.append(", dataCreazione=");
		builder.append(getDataCreazione());
		builder.append(", dataScadenza=");
		builder.append(getDataScadenza());
		builder.append(", dataDecorrenza=");
		builder.append(getDataDecorrenza());
		builder.append(", causale=");
		builder.append(getCausale());
		builder.append(", stato=");
		builder.append(getStato());
		builder.append(", descrizionestato=");
		builder.append(getDescrizioneStato());
		builder.append(", importo=");
		builder.append(getImporto());
		builder.append(", abiBancaAssuntrice=");
		builder.append(getAbiBancaAssuntrice());
		builder.append(", cabBancaAssuntrice=");
		builder.append(getCabBancaAssuntrice());
		builder.append(", abiBancaDomiciliaria=");
		builder.append(getAbiBancaDomiciliaria());
		builder.append(", siaCreditore=");
		builder.append(getSiaCreditore());
		builder.append(", tipoCodIndividuale=");
		builder.append(getTipoCodIndividuale());
		builder.append(", codDebitore=");
		builder.append(getCodDebitore());
		builder.append(", tipoIncassoRid=");
		builder.append(getTipoIncassoRid());
		builder.append(", divisa=");
		builder.append(getDivisa());
		builder.append(", ibanOrdinante=");
		builder.append(getIbanOrdinante());
		builder.append(", ragSocialeCreditore=");
		builder.append(getRagSocialeCreditore());
		builder.append(", ragSocialeDebitore=");
		builder.append(getRagSocialeDebitore());
		builder.append(", indirizzoDebitore=");
		builder.append(getIndirizzoDebitore());
		builder.append(", riferimentoDebito=");
		builder.append(getRiferimentoDebito());
		builder.append(", codRiferimento=");
		builder.append(getCodRiferimento());
		builder.append(", causaleStorno=");
		builder.append(getCausaleStorno());
		builder.append(", flagStorno=");
		builder.append(getFlagStorno());
		builder.append(", idDisposizioneOrig=");
		builder.append(getIdDisposizioneOrig());
		builder.append(", dataScadenzaOrig=");
		builder.append(getDataScadenzaOrig());
		builder.append(", intestatario=");
		builder.append(getIntestatario());
		builder.append(", distintePagamento=");
		builder.append(getDistintePagamento());
		builder.append(", idBozzeBonificiRiaccredito=");
		builder.append(getIdBozzeBonificiRiaccredito());
		builder.append(", tsAggiornamento=");
		builder.append(getTsAggiornamento());
		builder.append(", tsInserimento=");
		builder.append(getTsInserimento());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

	@Column(name="FLAG_INIZIATIVA")
	public String getFlagIniziativa() {
		return flagIniziativa;
	}

	public void setFlagIniziativa(String flagIniziativa) {
		this.flagIniziativa = flagIniziativa;
	}

	@Column(name="DATA_DECORRENZA")
	public Timestamp getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(Timestamp dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	@Column(name="INDIRIZZO_DEBITORE")
	public String getIndirizzoDebitore() {
		return indirizzoDebitore;
	}

	public void setIndirizzoDebitore(String indirizzoDebitore) {
		this.indirizzoDebitore = indirizzoDebitore;
	}

	//bi-directional many-to-one association to Rendicontazioni
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RENDICONTAZIONI", nullable=true)
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}
	
}