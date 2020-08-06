package it.tasgroup.idp.domain.posizioneDebitoria;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
@NamedQuery(
	name="PendenzeById", 
	query=
	"SELECT pend FROM Pendenze pend " +
	" WHERE pend.idPendenza = :idPendenza "),
@NamedQuery(
			name="DeletePendenzeAnnullate", 
			query=
				"delete FROM Pendenze pend " +
					" WHERE pend.tsAnnullamento is not null and " 
					+ " pend.idTributoStrutturato is null and "
					+ " pend.stRiga = :stRiga  "),
@NamedQuery(
		name="DeletePendenzeAnnullateById", 
		query=
			"delete FROM Pendenze pend " +
				" WHERE pend.tsAnnullamento is not null and " 				
				+ " pend.stRiga = :stRiga and"
				+ " pend.idPendenza >= :idPendMin and pend.idPendenza <= :idPendMax "),	 					
@NamedQuery(
		name="PendenzaNonAnnullata", 
		query=
		"SELECT pend FROM Pendenze pend " +
		" WHERE pend.idPendenzaente = :idPendenzaente "
		+ " AND pend.stRiga = :stRiga "
		+ " AND pend.idEnte = :idEnte "
		+ " AND pend.cdTrbEnte = :cdTrbEnte"
		+ " AND pend.tsAnnullamento is null ")}	)



/**
 * The persistent class for the JLTPEND database table.
 * 
 */
@Entity
@Table(name="JLTPEND")
public class Pendenze extends BaseEntity implements Serializable {
		
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String idPendenza;
	private int annoRiferimento;
	private String cdTrbEnte;
	private String coAbi;
	private String coRiscossore;
	private String deCausale;
	private String deMittente;
	private String dvRiferimento;
	private String flModPagamento;
	private String idEnte;
	private String idMittente;
	private String idPendenzaente;
	private String idSystem;
	private String idTributo;
	private BigDecimal imTotale;
	private String note;
	private String opAnnullamento;
	private int prVersione;
	private String riferimento;
	private String stPendenza;
	private String stRiga;
	private Timestamp tsAnnullamento;
	private Timestamp tsCreazioneente;
	private Timestamp tsDecorrenza;
	private Timestamp tsEmissioneente;
	private Timestamp tsModificaente;
	private Timestamp tsPrescrizione;
	private Long tsAnnullamentoMillis = 0L;
	
	/*** Persistent Associations ***/
	private Set<CondizioniPagamento> condizioniPagamento;
	private Set<DestinatariPendenze> destinatari;	
	
	//reference non jpa verso tributoStrutturato
	private String idTributoStrutturato;

	
    @Id
    @Column( name = "ID_PENDENZA" )	
//    @GenericGenerator(name = "pend", strategy="it.tasgroup.idp.domain.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "JLTPEND_ID_PENDENZA_SEQ") })
//    @GeneratedValue(generator = "pend")        
	public String getIdPendenza() {
		return this.idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	@Column(name="ANNO_RIFERIMENTO")
	public int getAnnoRiferimento() {
		return this.annoRiferimento;
	}

	public void setAnnoRiferimento(int annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEnte() {
		return this.cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	@Column(name="CO_ABI")
	public String getCoAbi() {
		return this.coAbi;
	}

	public void setCoAbi(String coAbi) {
		this.coAbi = coAbi;
	}

	@Column(name="CO_RISCOSSORE")
	public String getCoRiscossore() {
		return this.coRiscossore;
	}

	public void setCoRiscossore(String coRiscossore) {
		this.coRiscossore = coRiscossore;
	}

	@Column(name="DE_CAUSALE")
	public String getDeCausale() {
		return this.deCausale;
	}

	public void setDeCausale(String deCausale) {
		this.deCausale = deCausale;
	}

	@Column(name="DE_MITTENTE")
	public String getDeMittente() {
		return this.deMittente;
	}

	public void setDeMittente(String deMittente) {
		this.deMittente = deMittente;
	}

	@Column(name="DV_RIFERIMENTO")
	public String getDvRiferimento() {
		return this.dvRiferimento;
	}

	public void setDvRiferimento(String dvRiferimento) {
		this.dvRiferimento = dvRiferimento;
	}

	@Column(name="FL_MOD_PAGAMENTO")
	public String getFlModPagamento() {
		return this.flModPagamento;
	}

	public void setFlModPagamento(String flModPagamento) {
		this.flModPagamento = flModPagamento;
	}

	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	@Column(name="ID_MITTENTE")
	public String getIdMittente() {
		return this.idMittente;
	}

	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}

	@Column(name="ID_PENDENZAENTE")
	public String getIdPendenzaente() {
		return this.idPendenzaente;
	}

	public void setIdPendenzaente(String idPendenzaente) {
		this.idPendenzaente = idPendenzaente;
	}

	@Column(name="ID_SYSTEM")
	public String getIdSystem() {
		return this.idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

	@Column(name="ID_TRIBUTO")
	public String getIdTributo() {
		return this.idTributo;
	}

	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}

	@Column(name="IM_TOTALE")
	public BigDecimal getImTotale() {
		return this.imTotale;
	}

	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getRiferimento() {
		return this.riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	@Column(name="ST_PENDENZA")
	public String getStPendenza() {
		return this.stPendenza;
	}

	public void setStPendenza(String stPendenza) {
		this.stPendenza = stPendenza;
	}

	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
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

	@Column(name="TS_CREAZIONEENTE")
	public Timestamp getTsCreazioneente() {
		return this.tsCreazioneente;
	}

	public void setTsCreazioneente(Timestamp tsCreazioneente) {
		this.tsCreazioneente = tsCreazioneente;
	}

	@Column(name="TS_DECORRENZA")
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}

	@Column(name="TS_EMISSIONEENTE")
	public Timestamp getTsEmissioneente() {
		return this.tsEmissioneente;
	}

	public void setTsEmissioneente(Timestamp tsEmissioneente) {
		this.tsEmissioneente = tsEmissioneente;
	}

	@Column(name="TS_MODIFICAENTE")
	public Timestamp getTsModificaente() {
		return this.tsModificaente;
	}

	public void setTsModificaente(Timestamp tsModificaente) {
		this.tsModificaente = tsModificaente;
	}

	@Column(name="TS_PRESCRIZIONE")
	public Timestamp getTsPrescrizione() {
		return this.tsPrescrizione;
	}

	public void setTsPrescrizione(Timestamp tsPrescrizione) {
		this.tsPrescrizione = tsPrescrizione;
	}
	
	@Column(name="ID_TRIBUTO_STRUTTURATO")
	public String getIdTributoStrutturato() {
		return this.idTributoStrutturato;
	}

	public void setIdTributoStrutturato(String idTributoStrutturato) {
		this.idTributoStrutturato = idTributoStrutturato;
	}		
	 
	//bi-directional many-to-one association to condizioniPagamento
	@OneToMany(mappedBy="pendenze",cascade=CascadeType.ALL)
	public Set<CondizioniPagamento> getCondizioniPagamento() {
		return this.condizioniPagamento;
	}

	public void setCondizioniPagamento(Set<CondizioniPagamento> condizioniPagamento) {
		this.condizioniPagamento = condizioniPagamento;
	}	
	
	@OneToMany(mappedBy="pendenze",cascade=CascadeType.ALL)
	public Set<DestinatariPendenze> getDestinatariPendenze() {
		return this.destinatari;
	}

	public void setDestinatariPendenze(Set<DestinatariPendenze> destinatari) {
		this.destinatari = destinatari;
	}		
	
	@Column(name="TS_ANNULLAMENTO_MILLIS")
	public Long getTsAnnullamentoMillis() {
		return tsAnnullamentoMillis;
	}

	@Deprecated   // Usare setTsAnnullamento
	public void setTsAnnullamentoMillis(Long tsAnnullamentoMillis) {
		this.tsAnnullamentoMillis = tsAnnullamentoMillis;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pendenze [idPendenza=");
		builder.append(idPendenza);
		builder.append(", annoRiferimento=");
		builder.append(annoRiferimento);
		builder.append(", cdTrbEnte=");
		builder.append(cdTrbEnte);
		builder.append(", coAbi=");
		builder.append(coAbi);
		builder.append(", coRiscossore=");
		builder.append(coRiscossore);
		builder.append(", deCausale=");
		builder.append(deCausale);
		builder.append(", deMittente=");
		builder.append(deMittente);
		builder.append(", dvRiferimento=");
		builder.append(dvRiferimento);
		builder.append(", flModPagamento=");
		builder.append(flModPagamento);
		builder.append(", idEnte=");
		builder.append(idEnte);
		builder.append(", idMittente=");
		builder.append(idMittente);
		builder.append(", idPendenzaente=");
		builder.append(idPendenzaente);
		builder.append(", idSystem=");
		builder.append(idSystem);
		builder.append(", idTributo=");
		builder.append(idTributo);
		builder.append(", imTotale=");
		builder.append(imTotale);
		builder.append(", note=");
		builder.append(note);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", riferimento=");
		builder.append(riferimento);
		builder.append(", stPendenza=");
		builder.append(stPendenza);
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		builder.append(", tsCreazioneente=");
		builder.append(tsCreazioneente);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		builder.append(", tsEmissioneente=");
		builder.append(tsEmissioneente);
		builder.append(", tsModificaente=");
		builder.append(tsModificaente);
		builder.append(", tsPrescrizione=");
		builder.append(tsPrescrizione);
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
				+ ((idPendenza == null) ? 0 : idPendenza.hashCode());
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
		Pendenze other = (Pendenze) obj;
		if (idPendenza == null) {
			if (other.idPendenza != null) {
				return false;
			}
		} else if (!idPendenza.equals(other.idPendenza)) {
			return false;
		}
		return true;
	}

	
}