package it.tasgroup.iris.domain;

import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.TributoStrutturato;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="JLTPEND")
@NamedQueries( 
{
	@NamedQuery(name="countPendenzeTributo",query="select count(p) from Pendenza p where p.tributoEnte.tribEnId.cdTrbEntePk =:cdTrbEnte and p.tributoEnte.tribEnId.idEntePk =:idEnte")	
}
)
public class Pendenza extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private String idPendenza;
	private int annoRiferimento;
	private String coAbi;
	private String coRiscossore;
	private String deCausale; 
	private String deMittente;
	private String dvRiferimento;
	private String flModPagamento;
	private String idMittente;
	private String idPendenzaente;
	private String idSystem;
	private BigDecimal imTotale;
	private String note;
	private String opAggiornamento;
	private String opAnnullamento;
	private String opInserimento;
	private int prVersione;
	private String riferimento;
	private String stPendenza;
	private String stRiga;
	private Timestamp tsAggiornamento;
	private Timestamp tsAnnullamento;
	private Timestamp tsCreazioneente;
	private Timestamp tsDecorrenza;
	private Timestamp tsEmissioneente;
	private Timestamp tsInserimento;
	private Timestamp tsModificaente;
	private Timestamp tsPrescrizione;
	private Set<CondizionePagamento> condizioni;
	private TributoEnte tributoEnte;
	private String idEnte;
	private String tipoDebito;
	private CategoriaTributo categoriaTributo;
	private Set<DestinatariPendenza> destinatari;
	private TributoStrutturato tributoStrutturato;
	private Integer cartellaPagamento;
	private Long tsAnnullamentoMillis=0L;
	
    public Pendenza() {
    }


	@Id
	@Column(name="ID_PENDENZA", unique=true, nullable=false, length=40)
	public String getIdPendenza() {
		return this.idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}


	@Column(name="ANNO_RIFERIMENTO", nullable=false)
	public int getAnnoRiferimento() {
		return this.annoRiferimento;
	}

	public void setAnnoRiferimento(int annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}


	@Column(name="CO_ABI", length=10)
	public String getCoAbi() {
		return this.coAbi;
	}

	public void setCoAbi(String coAbi) {
		this.coAbi = coAbi;
	}


	@Column(name="CO_RISCOSSORE", length=70)
	public String getCoRiscossore() {
		return this.coRiscossore;
	}

	public void setCoRiscossore(String coRiscossore) {
		this.coRiscossore = coRiscossore;
	}


	@Column(name="DE_CAUSALE", nullable=false, length=512)
	public String getDeCausale() {
		return this.deCausale;
	}

	public void setDeCausale(String deCausale) {
		this.deCausale = deCausale;
	}


	@Column(name="DE_MITTENTE", length=140)
	public String getDeMittente() {
		return this.deMittente;
	}

	public void setDeMittente(String deMittente) {
		this.deMittente = deMittente;
	}


	@Column(name="DV_RIFERIMENTO", nullable=false, length=6)
	public String getDvRiferimento() {
		return this.dvRiferimento;
	}

	public void setDvRiferimento(String dvRiferimento) {
		this.dvRiferimento = dvRiferimento;
	}


	@Column(name="FL_MOD_PAGAMENTO", length=2)
	public String getFlModPagamento() {
		return this.flModPagamento;
	}

	public void setFlModPagamento(String flModPagamento) {
		this.flModPagamento = flModPagamento;
	}


	@Column(name="ID_MITTENTE", nullable=false, length=70)
	public String getIdMittente() {
		return this.idMittente;
	}

	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}


	@Column(name="ID_PENDENZAENTE", nullable=false, length=70)
	public String getIdPendenzaente() {
		return this.idPendenzaente;
	}

	public void setIdPendenzaente(String idPendenzaente) {
		this.idPendenzaente = idPendenzaente;
	}


	@Column(name="ID_SYSTEM", nullable=false, length=70)
	public String getIdSystem() {
		return this.idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}


	@Column(name="IM_TOTALE", nullable=false, precision=15, scale=2)
	public BigDecimal getImTotale() {
		return this.imTotale;
	}

	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}


	@Column(length=4000)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	@Column(name="OP_AGGIORNAMENTO", length=80)
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_ANNULLAMENTO", length=80)
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}


	@Column(name="OP_INSERIMENTO", nullable=false, length=80)
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="PR_VERSIONE", nullable=false)
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}


	@Column(length=70)
	public String getRiferimento() {
		return this.riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}


	@Column(name="ST_PENDENZA", nullable=false, length=4)
	public String getStPendenza() {
		return this.stPendenza;
	}

	public void setStPendenza(String stPendenza) {
		this.stPendenza = stPendenza;
	}


	@Column(name="ST_RIGA", nullable=false, length=2)
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}

	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
		//Mantiene allineato tsAnnullamentoMillis
		if (this.tsAnnullamento!=null) {
			this.tsAnnullamentoMillis=this.tsAnnullamento.getTime();
		} else {
			this.tsAnnullamentoMillis=0L;
		}
	}


	@Column(name="TS_CREAZIONEENTE", nullable=false)
	public Timestamp getTsCreazioneente() {
		return this.tsCreazioneente;
	}

	public void setTsCreazioneente(Timestamp tsCreazioneente) {
		this.tsCreazioneente = tsCreazioneente;
	}


	@Column(name="TS_DECORRENZA", nullable=false)
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}


	@Column(name="TS_EMISSIONEENTE", nullable=false)
	public Timestamp getTsEmissioneente() {
		return this.tsEmissioneente;
	}

	public void setTsEmissioneente(Timestamp tsEmissioneente) {
		this.tsEmissioneente = tsEmissioneente;
	}


	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	@Column(name="TS_MODIFICAENTE")
	public Timestamp getTsModificaente() {
		return this.tsModificaente;
	}

	public void setTsModificaente(Timestamp tsModificaente) {
		this.tsModificaente = tsModificaente;
	}


	@Column(name="TS_PRESCRIZIONE", nullable=false)
	public Timestamp getTsPrescrizione() {
		return this.tsPrescrizione;
	}

	public void setTsPrescrizione(Timestamp tsPrescrizione) {
		this.tsPrescrizione = tsPrescrizione;
	}


	//bi-directional many-to-one association to CondizionePagamento
	@OneToMany(mappedBy="pendenza", cascade={CascadeType.ALL})
	@OrderBy("dtScadenza ASC")
	public Set<CondizionePagamento> getCondizioni() {
		return this.condizioni;
	}

	public void setCondizioni(Set<CondizionePagamento> condizioni) {
		this.condizioni = condizioni;
	}
	
	@OneToMany(mappedBy="pendenza", cascade={CascadeType.ALL})
	public Set<DestinatariPendenza> getDestinatari() {
		return destinatari;
	}
	
	public void setDestinatari(Set<DestinatariPendenza> destinatari) {
		this.destinatari = destinatari;
	}
	

	//bi-directional many-to-one association to TributiEnte
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="CD_TRB_ENTE", referencedColumnName="CD_TRB_ENTE", nullable=false),
		@JoinColumn(name="ID_ENTE", referencedColumnName="ID_ENTE", nullable=false)
		})
	public TributoEnte getTributoEnte() {
		return this.tributoEnte;
	}


	public void setTributoEnte(TributoEnte tributo) {
		this.tributoEnte = tributo;
	}	


	@Column(name="ID_ENTE", nullable=false, insertable=false, updatable=false)
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
    	this.idEnte = idEnte;
    }	
	
	
	@Column(name="CD_TRB_ENTE", nullable=false, insertable=false, updatable=false)
	public String getTipoDebito() {
		return this.tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}
	
	
	//uni-directional many-to-one association to Tributo
    @ManyToOne
	@JoinColumn(name="ID_TRIBUTO", nullable=false)
	public CategoriaTributo getCategoriaTributo() {
		return this.categoriaTributo;
	}
	
	public void setCategoriaTributo(CategoriaTributo tributo) {
		this.categoriaTributo = tributo;
	}

	@Column(name="CARTELLA_PAGAMENTO")
	public Integer getCartellaPagamento() {
		return cartellaPagamento;
	}

	public void setCartellaPagamento(Integer cartellaPagamento) {
		this.cartellaPagamento = cartellaPagamento;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idPendenza == null) ? 0 : idPendenza.hashCode());
		return result;
	}
	

	@Column(name="TS_ANNULLAMENTO_MILLIS")
	public Long getTsAnnullamentoMillis() {
		return tsAnnullamentoMillis;
	}

	@Deprecated //usare setTsAnnullamento();
	public void setTsAnnullamentoMillis(Long tsAnnullamentoMillis) {
		this.tsAnnullamentoMillis = tsAnnullamentoMillis;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pendenza other = (Pendenza) obj;
		if (idPendenza == null) {
			if (other.idPendenza != null)
				return false;
		} else if (!idPendenza.equals(other.idPendenza))
			return false;
		return true;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pendenza [idPendenza=");
		builder.append(idPendenza);
		builder.append(", annoRiferimento=");
		builder.append(annoRiferimento);
		builder.append(", cdTrbEnte=");
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
		builder.append(", idMittente=");
		builder.append(idMittente);
		builder.append(", idPendenzaente=");
		builder.append(idPendenzaente);
		builder.append(", idSystem=");
		builder.append(idSystem);
		builder.append(", idTributo=");
		builder.append(", imTotale=");
		builder.append(imTotale);
		builder.append(", note=");
		builder.append(note);
		builder.append(", opAggiornamento=");
		builder.append(opAggiornamento);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", riferimento=");
		builder.append(riferimento);
		builder.append(", stPendenza=");
		builder.append(stPendenza);
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", tsAggiornamento=");
		builder.append(tsAggiornamento);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		builder.append(", tsCreazioneente=");
		builder.append(tsCreazioneente);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		builder.append(", tsEmissioneente=");
		builder.append(tsEmissioneente);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append(", tsModificaente=");
		builder.append(tsModificaente);
		builder.append(", tsPrescrizione=");
		builder.append(tsPrescrizione);
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}
	


	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="ID_TRIBUTO_STRUTTURATO")
	public TributoStrutturato getTributoStrutturato() {
		return tributoStrutturato;
	}

 
	public void setTributoStrutturato(TributoStrutturato tributoStrutturato) {
		this.tributoStrutturato = tributoStrutturato;
	}
	
	@Transient
	public boolean isChiusa() {
		
		return "C".equals(getStPendenza());
		
	}
	
	
}
