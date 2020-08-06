package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the RENDICONTAZIONI database table.
 * 
 */
@Entity
@Table(name="RENDICONTAZIONI")
public class Rendicontazioni extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codRendicontazione;
	private Timestamp dataCreazione;
	private Timestamp dataRegolamento;
	private Timestamp dataRicezione;
	private String divisa;
	private Integer flagElaborazione;
	private BigDecimal importo;
	private Integer numEsitiInsoluto;
	private Integer numEsitiPagato;
	private Integer numeroEsiti;
	private String opAggiornamento;
	private String opInserimento;
	private String stato;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private String utenteCreatore;
	private CasellarioInfo casellarioInfo;
	private String idFlusso;
	private String idRegolamento;
	
	private Set<EsitiRct> esitiRct;
	private Set<EsitiBb> esitiBb;
	private Set<IncassiBonificiRh> incassiBonificiRhs;
	private Set<EsitiBonificiRiaccredito> esitiBonificiRiaccredito;
	private Set<Rid> rid;
	private Set<AllineamentiElettroniciArchivi> aea;
	
    public Rendicontazioni() {
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="rendicontazioni_pk_gen")	
	@SequenceGenerator(
		    name="rendicontazioni_pk_gen",
		    sequenceName="RENDICONTAZIONI_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		    

	@Column(name="COD_RENDICONTAZIONE")
	public String getCodRendicontazione() {
		return this.codRendicontazione;
	}

	public void setCodRendicontazione(String codRendicontazione) {
		this.codRendicontazione = codRendicontazione;
	}


	@Column(name="DATA_CREAZIONE")
	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Column(name="DATA_RICEZIONE")
	public Timestamp getDataRicezione() {
		return this.dataRicezione;
	}

	public void setDataRicezione(Timestamp dataRicezione) {
		this.dataRicezione = dataRicezione;
	}


	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}


	@Column(name="FLAG_ELABORAZIONE")
	public Integer getFlagElaborazione() {
		return this.flagElaborazione;
	}

	public void setFlagElaborazione(Integer flagElaborazione) {
		this.flagElaborazione = flagElaborazione;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	@Column(name="NUM_ESITI_INSOLUTO")
	public Integer getNumEsitiInsoluto() {
		return this.numEsitiInsoluto;
	}

	public void setNumEsitiInsoluto(Integer numEsitiInsoluto) {
		this.numEsitiInsoluto = numEsitiInsoluto;
	}


	@Column(name="NUM_ESITI_PAGATO")
	public Integer getNumEsitiPagato() {
		return this.numEsitiPagato;
	}

	public void setNumEsitiPagato(Integer numEsitiPagato) {
		this.numEsitiPagato = numEsitiPagato;
	}


	@Column(name="NUMERO_ESITI")
	public Integer getNumeroEsiti() {
		return this.numeroEsiti;
	}

	public void setNumeroEsiti(Integer numeroEsiti) {
		this.numeroEsiti = numeroEsiti;
	}


	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	@Column(name="UTENTE_CREATORE")
	public String getUtenteCreatore() {
		return this.utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}


	//bi-directional one-to-one association to DistinteRiaccredito
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CASELLARIO_INFO", nullable=true)
	public CasellarioInfo getCasellarioInfo() {
		return this.casellarioInfo;
	}

	public void setCasellarioInfo(CasellarioInfo casellarioInfo) {
		this.casellarioInfo = casellarioInfo;
	}

	
	//bi-directional many-to-one association to IncassiBonificiRh
	@OneToMany(mappedBy="rendicontazioni")
	public Set<EsitiRct> getEsitiRct() {
		return esitiRct;
	}

	public void setEsitiRct(Set<EsitiRct> esitiRct) {
		this.esitiRct = esitiRct;
	}

	//bi-directional many-to-one association to IncassiBonificiRh
	@OneToMany(mappedBy="rendicontazioni")
	public Set<EsitiBb> getEsitiBb() {
		return esitiBb;
	}

	public void setEsitiBb(Set<EsitiBb> esitiBb) {
		this.esitiBb = esitiBb;
	}

	//bi-directional many-to-one association to IncassiBonificiRh
	@OneToMany(mappedBy="rendicontazioni")
	public Set<IncassiBonificiRh> getIncassiBonificiRhs() {
		return incassiBonificiRhs;
	}

	public void setIncassiBonificiRhs(Set<IncassiBonificiRh> incassiBonificiRhs) {
		this.incassiBonificiRhs = incassiBonificiRhs;
	}

	//bi-directional many-to-one association to IncassiBonificiRh
	@OneToMany(mappedBy="rendicontazioni")
	public Set<Rid> getRid() {
		return rid;
	}

	public void setRid(Set<Rid> rid) {
		this.rid = rid;
	}


	//bi-directional many-to-one association to IncassiBonificiRh
	@OneToMany(mappedBy="rendicontazioni")
	public Set<EsitiBonificiRiaccredito> getEsitiBonificiRiaccredito() {
		return esitiBonificiRiaccredito;
	}

	public void setEsitiBonificiRiaccredito(Set<EsitiBonificiRiaccredito> esitiBonificiRiaccredito) {
		this.esitiBonificiRiaccredito = esitiBonificiRiaccredito;
	}


	//bi-directional many-to-one association to IncassiBonificiRh
	@OneToMany(mappedBy="rendicontazioni")
	public Set<AllineamentiElettroniciArchivi> getAea() {
		return aea;
	}

	public void setAea(Set<AllineamentiElettroniciArchivi> aea) {
		this.aea = aea;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name="ID_FLUSSO")
	public String getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
	}

	@Column(name="DATA_REGOLAMENTO")
	public Timestamp getDataRegolamento() {
		return dataRegolamento;
	}

	public void setDataRegolamento(Timestamp dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}

	@Column(name="ID_REGOLAMENTO")
	public String getIdRegolamento() {
		return idRegolamento;
	}

	public void setIdRegolamento(String idRegolamento) {
		this.idRegolamento = idRegolamento;
	}

	@Override
	public String toString() {
		return "Rendicontazioni [codRendicontazione=" + codRendicontazione
				+ ", dataCreazione=" + dataCreazione + ", dataRegolamento="
				+ dataRegolamento + ", dataRicezione=" + dataRicezione
				+ ", divisa=" + divisa + ", flagElaborazione="
				+ flagElaborazione + ", importo=" + importo
				+ ", numEsitiInsoluto=" + numEsitiInsoluto
				+ ", numEsitiPagato=" + numEsitiPagato + ", numeroEsiti="
				+ numeroEsiti + ", opAggiornamento=" + opAggiornamento
				+ ", opInserimento=" + opInserimento + ", stato=" + stato
				+ ", tsAggiornamento=" + tsAggiornamento + ", tsInserimento="
				+ tsInserimento + ", utenteCreatore=" + utenteCreatore
				+ ", casellarioInfo=" + casellarioInfo + ", idFlusso="
				+ idFlusso + ", idRegolamento=" + idRegolamento + ", esitiRct="
				+ esitiRct + ", esitiBb=" + esitiBb + ", incassiBonificiRhs="
				+ incassiBonificiRhs + ", esitiBonificiRiaccredito="
				+ esitiBonificiRiaccredito + ", rid=" + rid + ", aea=" + aea
				+ "]";
	}
	
}