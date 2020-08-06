package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the RID database table.
 * 
 */
@Entity
@Table(name="RID")
public class Rid extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String abiBancaAssuntrice;
	private String abiBancaDomiciliataria;
	private String cabBancaAssuntrice;
	private String causale;
	private String causaleStorno;
	private String codDebitore;
	private String codRiferimento;
	private Timestamp dataCreazione;
	private Timestamp dataDecorrenza;
	private Timestamp dataScadenza;
	private Timestamp dataScadenzaOrig;
	private String descrizioneStato;
	private String divisa;
	private String flagIniziativa;
	private String flagStorno;
	private String ibanOrdinante;
	private Integer idBozzeBonificiRiaccredito;
	private Integer idDisposizioneOrig;
	private Integer idRendicontazioni;
	private BigDecimal importo;
	private String indirizzoDebitore;
	private String intestatario;
	private Integer progressivo;
	private String ragSocCreditore;
	private String ragSocDebitore;
	private String riferimentiDebito;
	private String siaCreditore;
	private String stato;
	private String tipoCodIndividuale;
	private String tipoIncassoRid;
	
	/*** Persistent Associations ***/
	private DistintePagamento distintePagamento;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="rid_pk_gen")	
	@SequenceGenerator(
	    name="rid_pk_gen",
	    sequenceName="RID_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	
 
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="ABI_BANCA_ASSUNTRICE")
	public String getAbiBancaAssuntrice() {
		return this.abiBancaAssuntrice;
	}

	public void setAbiBancaAssuntrice(String abiBancaAssuntrice) {
		this.abiBancaAssuntrice = abiBancaAssuntrice;
	}


	@Column(name="ABI_BANCA_DOMICILIATARIA")
	public String getAbiBancaDomiciliataria() {
		return this.abiBancaDomiciliataria;
	}

	public void setAbiBancaDomiciliataria(String abiBancaDomiciliataria) {
		this.abiBancaDomiciliataria = abiBancaDomiciliataria;
	}


	@Column(name="CAB_BANCA_ASSUNTRICE")
	public String getCabBancaAssuntrice() {
		return this.cabBancaAssuntrice;
	}

	public void setCabBancaAssuntrice(String cabBancaAssuntrice) {
		this.cabBancaAssuntrice = cabBancaAssuntrice;
	}

	@Column(name="CAUSALE")
	public String getCausale() {
		return this.causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}


	@Column(name="CAUSALE_STORNO")
	public String getCausaleStorno() {
		return this.causaleStorno;
	}

	public void setCausaleStorno(String causaleStorno) {
		this.causaleStorno = causaleStorno;
	}


	@Column(name="COD_DEBITORE")
	public String getCodDebitore() {
		return this.codDebitore;
	}

	public void setCodDebitore(String codDebitore) {
		this.codDebitore = codDebitore;
	}


	@Column(name="COD_RIFERIMENTO")
	public String getCodRiferimento() {
		return this.codRiferimento;
	}

	public void setCodRiferimento(String codRiferimento) {
		this.codRiferimento = codRiferimento;
	}


	@Column(name="DATA_CREAZIONE")
	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Column(name="DATA_DECORRENZA")
	public Timestamp getDataDecorrenza() {
		return this.dataDecorrenza;
	}

	public void setDataDecorrenza(Timestamp dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}


	@Column(name="DATA_SCADENZA")
	public Timestamp getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}


	@Column(name="DATA_SCADENZA_ORIG")
	public Timestamp getDataScadenzaOrig() {
		return this.dataScadenzaOrig;
	}

	public void setDataScadenzaOrig(Timestamp dataScadenzaOrig) {
		this.dataScadenzaOrig = dataScadenzaOrig;
	}


	@Column(name="DESCRIZIONE_STATO")
	public String getDescrizioneStato() {
		return this.descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	
	@Column(name="DIVISA")
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


	@Column(name="IBAN_ORDINANTE")
	public String getIbanOrdinante() {
		return this.ibanOrdinante;
	}

	public void setIbanOrdinante(String ibanOrdinante) {
		this.ibanOrdinante = ibanOrdinante;
	}


	@Column(name="ID_BOZZE_BONIFICI_RIACCREDITO")
	public Integer getIdBozzeBonificiRiaccredito() {
		return this.idBozzeBonificiRiaccredito;
	}

	public void setIdBozzeBonificiRiaccredito(Integer idBozzeBonificiRiaccredito) {
		this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
	}


	@Column(name="ID_DISPOSIZIONE_ORIG")
	public Integer getIdDisposizioneOrig() {
		return this.idDisposizioneOrig;
	}

	public void setIdDisposizioneOrig(Integer idDisposizioneOrig) {
		this.idDisposizioneOrig = idDisposizioneOrig;
	}


	@Column(name="ID_RENDICONTAZIONI")
	public Integer getIdRendicontazioni() {
		return this.idRendicontazioni;
	}

	public void setIdRendicontazioni(Integer idRendicontazioni) {
		this.idRendicontazioni = idRendicontazioni;
	}

	@Column(name="IMPORTO")
	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	@Column(name="INDIRIZZO_DEBITORE")
	public String getIndirizzoDebitore() {
		return this.indirizzoDebitore;
	}

	public void setIndirizzoDebitore(String indirizzoDebitore) {
		this.indirizzoDebitore = indirizzoDebitore;
	}

	@Column(name="INTESTATARIO")
	public String getIntestatario() {
		return this.intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}


	@Column(name="PROGRESSIVO")
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


	@Column(name="RAG_SOC_DEBITORE")
	public String getRagSocDebitore() {
		return this.ragSocDebitore;
	}

	public void setRagSocDebitore(String ragSocDebitore) {
		this.ragSocDebitore = ragSocDebitore;
	}


	@Column(name="RIFERIMENTI_DEBITO")
	public String getRiferimentiDebito() {
		return this.riferimentiDebito;
	}

	public void setRiferimentiDebito(String riferimentiDebito) {
		this.riferimentiDebito = riferimentiDebito;
	}


	@Column(name="SIA_CREDITORE")
	public String getSiaCreditore() {
		return this.siaCreditore;
	}

	public void setSiaCreditore(String siaCreditore) {
		this.siaCreditore = siaCreditore;
	}

	@Column(name="STATO")
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

	@Column(name="TIPO_INCASSO_RID")
	public String getTipoIncassoRid() {
		return this.tipoIncassoRid;
	}

	public void setTipoIncassoRid(String tipoIncassoRid) {
		this.tipoIncassoRid = tipoIncassoRid;
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
		builder.append("Rid [abiBancaAssuntrice=");
		builder.append(abiBancaAssuntrice);
		builder.append(", abiBancaDomiciliataria=");
		builder.append(abiBancaDomiciliataria);
		builder.append(", cabBancaAssuntrice=");
		builder.append(cabBancaAssuntrice);
		builder.append(", causale=");
		builder.append(causale);
		builder.append(", causaleStorno=");
		builder.append(causaleStorno);
		builder.append(", codDebitore=");
		builder.append(codDebitore);
		builder.append(", codRiferimento=");
		builder.append(codRiferimento);
		builder.append(", dataCreazione=");
		builder.append(dataCreazione);
		builder.append(", dataDecorrenza=");
		builder.append(dataDecorrenza);
		builder.append(", dataScadenza=");
		builder.append(dataScadenza);
		builder.append(", dataScadenzaOrig=");
		builder.append(dataScadenzaOrig);
		builder.append(", descrizioneStato=");
		builder.append(descrizioneStato);
		builder.append(", divisa=");
		builder.append(divisa);
		builder.append(", flagIniziativa=");
		builder.append(flagIniziativa);
		builder.append(", flagStorno=");
		builder.append(flagStorno);
		builder.append(", ibanOrdinante=");
		builder.append(ibanOrdinante);
		builder.append(", idBozzeBonificiRiaccredito=");
		builder.append(idBozzeBonificiRiaccredito);
		builder.append(", idDisposizioneOrig=");
		builder.append(idDisposizioneOrig);
		builder.append(", idRendicontazioni=");
		builder.append(idRendicontazioni);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", indirizzoDebitore=");
		builder.append(indirizzoDebitore);
		builder.append(", intestatario=");
		builder.append(intestatario);
		builder.append(", progressivo=");
		builder.append(progressivo);
		builder.append(", ragSocCreditore=");
		builder.append(ragSocCreditore);
		builder.append(", ragSocDebitore=");
		builder.append(ragSocDebitore);
		builder.append(", riferimentiDebito=");
		builder.append(riferimentiDebito);
		builder.append(", siaCreditore=");
		builder.append(siaCreditore);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", tipoCodIndividuale=");
		builder.append(tipoCodIndividuale);
		builder.append(", tipoIncassoRid=");
		builder.append(tipoIncassoRid);
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