package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
@NamedQuery(
	name="DistintePagamentoByCodTransazione", 
	query=
	"SELECT distintePagamento FROM DistintePagamento distintePagamento " +
	" WHERE distintePagamento.codTransazione = :codTransazione "),
@NamedQuery(
			name="DistintePagamentoByIdGruppo", 
			query=
			"SELECT distintePagamento FROM DistintePagamento distintePagamento " +
			" WHERE distintePagamento.idGruppo = :idGruppo "),
@NamedQuery(
		name="DistintePagamentoByGwAndDataCreazione", 
		query=
		"SELECT distintePagamento FROM DistintePagamento distintePagamento " + 
		" WHERE distintePagamento.idCfgGatewayPagamento = :idCfgGatewayPagamento " +
		" AND distintePagamento.stato='ESEGUITO' "		
		+ "	AND distintePagamento.dataCreazione "
		+ " BETWEEN :dataCreazioneStart AND :dataCreazioneEnd"),
@NamedQuery(
		 name="DistintaByIdGruppo", 
		 query= " SELECT flusso FROM DistintePagamento flusso WHERE flusso.idGruppo = :idGruppo "
		),
@NamedQuery(
			name="getIdGruppoMultiPayeeMailToSend", 
			query= " SELECT distinct idGruppo FROM DistintePagamento flusso WHERE flusso.idGruppo is not null "
					+ " AND flusso.emailReceiptSent != 1 "
					+ " AND flusso.stato='ESEGUITO' "
		),
@NamedQuery(
	name = "DistinteEseguiteByIuvAndIdFiscCreditore", 
	query = "SELECT distintePagamento FROM DistintePagamento distintePagamento " 
	+ " WHERE distintePagamento.iuv = :iuv "
	+ " AND distintePagamento.stato = 'ESEGUITO' "
	+ " AND distintePagamento.identificativoFiscaleCreditore = :idFiscCreditore ")
})

/**
 * The persistent class for the DISTINTE_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="DISTINTE_PAGAMENTO")
public class DistintePagamento extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String codTransazione;
	private Timestamp dataCreazione;
	private Timestamp dataSpedizione;
	private String divisa;
	private Long idCfgGatewayPagamento;
	private BigDecimal importo;
	private BigDecimal importoCommissioni;
	private int numeroDisposizioni;
	private String stato;
	private String utenteCreatore;
	private String codPagamento;
	private Timestamp dataPagamento;
	private String emailVersante="";
	
	private String tipoSoggettoVers; 
	private String codFiscaleVers; 
	private String anagraficaVers;
	private String indirizzoVers; 
	private String numeroCivicoVers; 
	private String capVers; 
	private String localitaVers; 
	private String provinciaVers; 
	private String nazioneVers; 
	private String tipoIdentificativoAttestante;
	private String identificativoAttestante;
	private String descrizioneAttestante;
	
	
	/*** Persistent Associations ***/
	private CasellarioDispo casellarioDispo;
	//private Set<Aea> aeas;
	private Set<Pagamenti> pagamentis;
	private Set<Rid> rids;
	private Set<PagamentiOnline> pagamentiOnlines;
	
	/*** Campi inseriti per nodo dei pagamenti ***/
	private String codTransazionePSP="0";  //Codice Contesto Pagamento
	private String iuv="0";
	private String identificativoFiscaleCreditore="0";
    
	private String idGruppo;
    
	private short  emailReceiptSent;
	
	private String locale;
	
	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="distinte_pagamento_pk_gen")	
	@SequenceGenerator(
	    name="distinte_pagamento_pk_gen",
	    sequenceName="DISTINTE_PAGAMENTO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="COD_TRANSAZIONE")
	public String getCodTransazione() {
		return this.codTransazione;
	}

	public void setCodTransazione(String codTransazione) {
		this.codTransazione = codTransazione;
	}

	
	@Column(name="ID_CFG_GATEWAY_PAGAMENTO")
	public Long getIdCfgGatewayPagamento() {
		return idCfgGatewayPagamento;
	}
	public void setIdCfgGatewayPagamento(Long idCfgGatewayPagamento) {
		this.idCfgGatewayPagamento = idCfgGatewayPagamento;
	}	

	
	@Column(name="DATA_CREAZIONE")
	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Column(name="DATA_SPEDIZIONE")
	public Timestamp getDataSpedizione() {
		return this.dataSpedizione;
	}

	public void setDataSpedizione(Timestamp dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}


	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	@Column(name="IMPORTO_COMMISSIONI")
	public BigDecimal getImportoCommissioni() {
		return this.importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}


	@Column(name="NUMERO_DISPOSIZIONI")
	public int getNumeroDisposizioni() {
		return this.numeroDisposizioni;
	}

	public void setNumeroDisposizioni(int numeroDisposizioni) {
		this.numeroDisposizioni = numeroDisposizioni;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="UTENTE_CREATORE")
	public String getUtenteCreatore() {
		return this.utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}
	
	
	@Column(name="COD_PAGAMENTO")
	public String getCodPagamento() {
		return this.codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}
	
	
	@Column(name="EMAIL_VERSANTE")
	public String getEmailVersante() {
		return this.emailVersante;
	}

	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}	

	
	@Column(name="DATA_PAGAMENTO")
	public Timestamp getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
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

		
	//bi-directional many-to-one association to CasellarioDispo
	@OneToOne(mappedBy="distintePagamento")
	public CasellarioDispo getCasellarioDispo() {
		return this.casellarioDispo;
	}

	public void setCasellarioDispo(CasellarioDispo casellarioDispo) {
		this.casellarioDispo = casellarioDispo;
	}
	

	//bi-directional many-to-one association to Pagamenti
	@OneToMany(mappedBy="distintePagamento",cascade=CascadeType.MERGE)	
	public Set<Pagamenti> getPagamentis() {
		return this.pagamentis;
	}

	public void setPagamentis(Set<Pagamenti> pagamentis) {
		this.pagamentis = pagamentis;
	}
	

	//bi-directional many-to-one association to Rid
	@OneToMany(mappedBy="distintePagamento")
	public Set<Rid> getRids() {
		return this.rids;
	}

	public void setRids(Set<Rid> rids) {
		this.rids = rids;
	}
	

	//bi-directional many-to-one association to PagamentiOnline
	@OneToMany(mappedBy="distintePagamento")
	public Set<PagamentiOnline> getPagamentiOnlines() {
		return this.pagamentiOnlines;
	}

	public void setPagamentiOnlines(Set<PagamentiOnline> pagamentiOnlines) {
		this.pagamentiOnlines = pagamentiOnlines;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DistintePagamento [codTransazione=");
		builder.append(codTransazione);
		builder.append(", dataCreazione=");
		builder.append(dataCreazione);
		builder.append(", dataSpedizione=");
		builder.append(dataSpedizione);
		builder.append(", divisa=");
		builder.append(divisa);
		builder.append(", idCfgGatewayPagamento=");
		builder.append(idCfgGatewayPagamento);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", importoCommissioni=");
		builder.append(importoCommissioni);
		builder.append(", numeroDisposizioni=");
		builder.append(numeroDisposizioni);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", utenteCreatore=");
		builder.append(utenteCreatore);
		builder.append(", codPagamento=");
		builder.append(codPagamento);
		//builder.append(", aeas=");
		//builder.append(aeas);
		//builder.append(", casellarioDispo=");
		//builder.append(casellarioDispo);
		//builder.append(", pagamentis=");
		//builder.append(pagamentis);
		//builder.append(", rids=");
		//builder.append(rids);
		//builder.append(", pagamentiOnlines=");
		//builder.append(pagamentiOnlines);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codTransazione == null) ? 0 : codTransazione.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DistintePagamento other = (DistintePagamento) obj;
		if (codTransazione == null) {
			if (other.codTransazione != null) {
				return false;
			}
		} else if (!codTransazione.equals(other.codTransazione)) {
			return false;
		}
		return true;
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

	@Column(name="COD_TRANSAZIONE_PSP")
	public String getCodTransazionePSP() {
		return codTransazionePSP;
	}

	public void setCodTransazionePSP(String codTransazionePSP) {
		this.codTransazionePSP = codTransazionePSP;
	}

	@Column(name="ID_GRUPPO")
	public String getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}
    
	@Column(name="EMAIL_RECEIPT_SENT")
	public short getEmailReceiptSent() {
		return emailReceiptSent;
	}

	public void setEmailReceiptSent(short emailReceiptSent) {
		this.emailReceiptSent = emailReceiptSent;
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
	
}