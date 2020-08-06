package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the INCASSI_BONIFICI_RH database table.
 * 
 */
@Entity
@Table(name="INCASSI_BONIFICI_RH")
@NamedQueries(
{
	@NamedQuery(
			name="getIncassiBonificiRhById",
			query="select esiti from IncassiBonificiRh esiti "+
				  "where esiti.id =:id"),
	@NamedQuery(
		name="listIncassiBonificiRhByIdRendicontazione",
		query="select esiti from IncassiBonificiRh esiti " +
				"where esiti.rendicontazioni.id=:idRendicontazione ")
		}
)
public class IncassiBonificiRh extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String anomalia;
	private String causale;
	private Timestamp dataContabile;
	private Timestamp dataValuta;
	private Integer flagRiconciliazione;
	private Integer idBozzeBonificiRiaccredito;
	private String idRiconciliazione;
	private String idRiconciliazioneOrig;
	private BigDecimal importo;
	private String opAggiornamento;
	private String opInserimento;
	private Integer progressivo61;
	private Integer progressivo62;
	private String riferimentoBanca;
	private String segno;
	private String trn;
	private String ibanAccredito;

	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private Riversamento riversamento;
	private GestioneFlussi distinta;

	public IncassiBonificiRh() {
	}

	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="incassi_bonifici_rh_pk_gen")	
	@SequenceGenerator(
		    name="incassi_bonifici_rh_pk_gen",
		    sequenceName="INCASSI_BONIFICI_RH_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 			
	
	private Rendicontazioni rendicontazioni;

	public String getAnomalia() {
		return this.anomalia;
	}

	public void setAnomalia(String anomalia) {
		this.anomalia = anomalia;
	}


	public String getCausale() {
		return this.causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}


	@Column(name="DATA_CONTABILE")
	public Timestamp getDataContabile() {
		return this.dataContabile;
	}

	public void setDataContabile(Timestamp dataContabile) {
		this.dataContabile = dataContabile;
	}


	@Column(name="DATA_VALUTA")
	public Timestamp getDataValuta() {
		return this.dataValuta;
	}

	public void setDataValuta(Timestamp dataValuta) {
		this.dataValuta = dataValuta;
	}


	@Column(name="FLAG_RICONCILIAZIONE")
	public Integer getFlagRiconciliazione() {
		return this.flagRiconciliazione;
	}

	public void setFlagRiconciliazione(Integer flagRiconciliazione) {
		this.flagRiconciliazione = flagRiconciliazione;
	}


	@Column(name="ID_BOZZE_BONIFICI_RIACCREDITO")
	public Integer getIdBozzeBonificiRiaccredito() {
		return this.idBozzeBonificiRiaccredito;
	}

	public void setIdBozzeBonificiRiaccredito(Integer idBozzeBonificiRiaccredito) {
		this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
	}

	@Column(name="ID_RICONCILIAZIONE")
	public String getIdRiconciliazione() {
		return this.idRiconciliazione;
	}

	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
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


	public Integer getProgressivo61() {
		return this.progressivo61;
	}

	public void setProgressivo61(Integer progressivo61) {
		this.progressivo61 = progressivo61;
	}


	public Integer getProgressivo62() {
		return this.progressivo62;
	}

	public void setProgressivo62(Integer progressivo62) {
		this.progressivo62 = progressivo62;
	}


	@Column(name="RIFERIMENTO_BANCA")
	public String getRiferimentoBanca() {
		return this.riferimentoBanca;
	}

	public void setRiferimentoBanca(String riferimentoBanca) {
		this.riferimentoBanca = riferimentoBanca;
	}


	public String getSegno() {
		return this.segno;
	}

	public void setSegno(String segno) {
		this.segno = segno;
	}


	public String getTrn() {
		return this.trn;
	}

	public void setTrn(String trn) {
		this.trn = trn;
	}

	@Column(name="IBAN_ACCREDITO")
	public String getIbanAccredito() {
		return ibanAccredito;
	}
	
	public void setIbanAccredito(String ibanAccredito) {
		this.ibanAccredito = ibanAccredito;
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

	//bi-directional many-to-one association to Rendicontazioni
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RENDICONTAZIONE")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}


	//bi-directional many-to-one association to Riversamenti
	@OneToOne(mappedBy="incassiBonificiRh", cascade={CascadeType.MERGE,CascadeType.PERSIST})
	public Riversamento getRiversamento() {
		return this.riversamento;
	}

	public void setRiversamento(Riversamento riversamento) {
		this.riversamento = riversamento;
	}


	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RICONCILIAZIONE", referencedColumnName="COD_TRANSAZIONE", insertable=false, updatable=false)
	public GestioneFlussi getDistintePagamento() {
		return this.distinta;
	}

	public void setDistintePagamento(GestioneFlussi distintePagamento) {
		this.distinta = distintePagamento;
	}

	@Column(name="ID_RICONCILIAZIONE_ORIG")
	public String getIdRiconciliazioneOrig() {
		return idRiconciliazioneOrig;
	}

	public void setIdRiconciliazioneOrig(String idRiconciliazioneOrig) {
		this.idRiconciliazioneOrig = idRiconciliazioneOrig;
	}

}