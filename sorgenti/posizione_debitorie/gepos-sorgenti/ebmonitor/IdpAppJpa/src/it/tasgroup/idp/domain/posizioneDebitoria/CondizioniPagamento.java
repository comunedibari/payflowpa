package it.tasgroup.idp.domain.posizioneDebitoria;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@NamedQueries({
@NamedQuery(
	name="CondizioniPagamentoByIdCondizione", 
	query=
	"SELECT condizioniPagamento FROM CondizioniPagamento condizioniPagamento " +
	" WHERE condizioniPagamento.idCondizione = :idCondizione "),
@NamedQuery(
			name="CondizioniPagamentoByCdEnteAndIdPagamentoEnte", 
			query=
			"SELECT condizioniPagamento FROM CondizioniPagamento condizioniPagamento " +
			" WHERE condizioniPagamento.idPagamento = :idPagamento  and condizioniPagamento.idEnte = :idEnte and condizioniPagamento.stRiga in ('V','H') "),
@NamedQuery(
		name="CondizioniPagamentoByIdEnteAndIdPagamentoEnte", 
		query=
		"SELECT condizioniPagamento FROM CondizioniPagamento condizioniPagamento " +
		" WHERE condizioniPagamento.idPagamento = :idPagamento and condizioniPagamento.idEnte = :idEnte and condizioniPagamento.stRiga in (:stRiga) order by condizioniPagamento.tsInserimento asc"),
@NamedQuery(
		name="CondizioniPagamentoByIdPendenza", 
		query=
		"SELECT condizioniPagamento FROM CondizioniPagamento condizioniPagamento " +
		" WHERE condizioniPagamento.pendenze.idPendenza = :idPendenza ")	})
/**
 * The persistent class for the JLTCOPD database table.
 * 
 */
@Entity
@Table(name="JLTCOPD")
public class CondizioniPagamento extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String idCondizione;
	private String cdTrbEnte;
	private String coCip;
	private String deCanalepag;
	private Date dtFinevalidita;
	private Date dtIniziovalidita;
	private Timestamp dtPagamento;
	private Date dtScadenza;
	private String idEnte;
	private String idPagamento;
//	private String idPendenza;
	private BigDecimal imTotale;
	private String opAnnullamento;
	private int prVersione;
	private String stPagamento;
	private String stRiga;
	private String tiCip;
	private String tiPagamento;
	private Timestamp tsAnnullamento;
	private Long tsAnnullamentoMillis = 0L;
	private Timestamp tsDecorrenza;
	
	private String causalePagamento;
	private String deMezzopagamento;
	private String deNotepagamento;	
	private String ragioneSocialeBeneficiario;	
	private BigDecimal imPagamento;	
	private String ibanBeneficiario;
	
	/*** Persistent Associations ***/
	private Set<AllegatiPendenza> allegatiPendenza;
	private Set<VociPagamento> vociPagamento;
	
	/*** Persistent Associations ***/
	private Pendenze pendenze;	

	
    @Id
//    @GenericGenerator(name = "cond", parameters = @Parameter(name = "sequence", value = "JLTCOPD_ID_CONDIZIONE_SEQ"), strategy="it.tasgroup.idp.domain.StringSequenceGenerator")
//    @GeneratedValue(generator = "cond")        
    @Column( name = "ID_CONDIZIONE" )		
	public String getIdCondizione() {
		return this.idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEnte() {
		return this.cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	@Column(name="CO_CIP")
	public String getCoCip() {
		return this.coCip;
	}

	public void setCoCip(String coCip) {
		this.coCip = coCip;
	}

	@Column(name="DE_CANALEPAG")
	public String getDeCanalepag() {
		return this.deCanalepag;
	}

	public void setDeCanalepag(String deCanalepag) {
		this.deCanalepag = deCanalepag;
	}

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINEVALIDITA")
	public Date getDtFinevalidita() {
		return this.dtFinevalidita;
	}

	public void setDtFinevalidita(Date dtFinevalidita) {
		this.dtFinevalidita = dtFinevalidita;
	}

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIOVALIDITA")
	public Date getDtIniziovalidita() {
		return this.dtIniziovalidita;
	}

	public void setDtIniziovalidita(Date dtIniziovalidita) {
		this.dtIniziovalidita = dtIniziovalidita;
	}

	@Column(name="DT_PAGAMENTO")
	public Timestamp getDtPagamento() {
		return this.dtPagamento;
	}

	public void setDtPagamento(Timestamp dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

    @Temporal( TemporalType.DATE)
	@Column(name="DT_SCADENZA")
	public Date getDtScadenza() {
		return this.dtScadenza;
	}

	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}

	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	@Column(name="ID_PAGAMENTO")
	public String getIdPagamento() {
		return this.idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

//	@Column(name="ID_PENDENZA")
//	public String getIdPendenza() {
//		return this.idPendenza;
//	}
//
//	public void setIdPendenza(String idPendenza) {
//		this.idPendenza = idPendenza;
//	}

	@Column(name="IM_TOTALE")
	public BigDecimal getImTotale() {
		return this.imTotale;
	}

	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}


	@Column(name="OP_ANNULLAMENTO")
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}


	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="ST_PAGAMENTO")
	public String getStPagamento() {
		return this.stPagamento;
	}

	public void setStPagamento(String stPagamento) {
		this.stPagamento = stPagamento;
	}

	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}

	@Column(name="TI_CIP")
	public String getTiCip() {
		return this.tiCip;
	}

	public void setTiCip(String tiCip) {
		this.tiCip = tiCip;
	}

	@Column(name="TI_PAGAMENTO")
	public String getTiPagamento() {
		return this.tiPagamento;
	}

	public void setTiPagamento(String tiPagamento) {
		this.tiPagamento = tiPagamento;
	}


	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}

	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
		// Mantiene allineato ts_annullamentoMillis con ts_annullamento
		if (this.tsAnnullamento!=null) {
			this.tsAnnullamentoMillis=this.tsAnnullamento.getTime();
		} else {
			this.tsAnnullamentoMillis=0L;
		}

	}

	@Column(name="TS_DECORRENZA")
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}	


	//bi-directional many-to-one association to AllegatiPendenza
	@OneToMany(mappedBy="condizioniPagamento", cascade=CascadeType.ALL)
	public Set<AllegatiPendenza> getAllegatiPendenza() {
		return this.allegatiPendenza;
	}

	public void setAllegatiPendenza(Set<AllegatiPendenza> allegatiPendenza) {
		this.allegatiPendenza = allegatiPendenza;
	}
	
	//bi-directional many-to-one association to VociPagamento
	@OneToMany(mappedBy="condizioniPagamento", cascade=CascadeType.ALL)
	public Set<VociPagamento> getVociPagamento() {
		return this.vociPagamento;
	}

	public void setVociPagamento(Set<VociPagamento> vociPagamento) {
		this.vociPagamento = vociPagamento;
	}
	
	@Column(name="IM_PAGAMENTO")
	public BigDecimal getImPagamento() {
		return this.imPagamento;
	}

	public void setImPagamento(BigDecimal imPagamento) {
		this.imPagamento = imPagamento;
	}	


	@Column(name="RAGIONE_SOCIALE_BENEFICIARIO")
	public String getRagioneSocialeBeneficiario() {
		return this.ragioneSocialeBeneficiario;
	}

	public void setRagioneSocialeBeneficiario(String ragioneSocialeBeneficiario) {
		this.ragioneSocialeBeneficiario = ragioneSocialeBeneficiario;
	}	

	@Column(name="DE_MEZZOPAGAMENTO")
	public String getDeMezzopagamento() {
		return this.deMezzopagamento;
	}

	public void setDeMezzopagamento(String deMezzopagamento) {
		this.deMezzopagamento = deMezzopagamento;
	}


	@Column(name="DE_NOTEPAGAMENTO")
	public String getDeNotepagamento() {
		return this.deNotepagamento;
	}

	public void setDeNotepagamento(String deNotepagamento) {
		this.deNotepagamento = deNotepagamento;
	}	
	
	@Column(name="IBAN_BENEFICIARIO")
	public String getIbanBeneficiario() {
		return this.ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}
	
	@Column(name="CAUSALE_PAGAMENTO")
	public String getCausalePagamento() {
		return this.causalePagamento;
	}

	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}	
	
	//bi-directional many-to-one association to pendenze
    @ManyToOne
	@JoinColumn(name="ID_PENDENZA")
	public Pendenze getPendenze() {
		return this.pendenze;
	}

	public void setPendenze(Pendenze pendenze) {
		this.pendenze = pendenze;
	}	


	@Column(name="TS_ANNULLAMENTO_MILLIS")
	public Long getTsAnnullamentoMillis() {
		return tsAnnullamentoMillis;
	}

	@Deprecated // usare set tsAnnullamento
	public void setTsAnnullamentoMillis(Long tsAnnullamentoMillis) {
		this.tsAnnullamentoMillis = tsAnnullamentoMillis;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CondizioniPagamento [idCondizione=");
		builder.append(idCondizione);
		builder.append(", cdTrbEnte=");
		builder.append(cdTrbEnte);
		builder.append(", coCip=");
		builder.append(coCip);
		builder.append(", deCanalepag=");
		builder.append(deCanalepag);
		builder.append(", dtFinevalidita=");
		builder.append(dtFinevalidita);
		builder.append(", dtIniziovalidita=");
		builder.append(dtIniziovalidita);
		builder.append(", dtPagamento=");
		builder.append(dtPagamento);
		builder.append(", dtScadenza=");
		builder.append(dtScadenza);
		builder.append(", idEnte=");
		builder.append(idEnte);
		builder.append(", idPagamento=");
		builder.append(idPagamento);
//		builder.append(", idPendenza=");
//		builder.append(idPendenza);
		builder.append(", imTotale=");
		builder.append(imTotale);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", stPagamento=");
		builder.append(stPagamento);
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", tiCip=");
		builder.append(tiCip);
		builder.append(", tiPagamento=");
		builder.append(tiPagamento);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		//builder.append(", allegatiPendenza=");
		//builder.append(allegatiPendenza);
		//builder.append(", vociPagamento=");
		//builder.append(vociPagamento);
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
				+ ((idCondizione == null) ? 0 : idCondizione.hashCode());
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
		CondizioniPagamento other = (CondizioniPagamento) obj;
		if (idCondizione == null) {
			if (other.idCondizione != null) {
				return false;
			}
		} else if (!idCondizione.equals(other.idCondizione)) {
			return false;
		}
		return true;
	}
	
	
}