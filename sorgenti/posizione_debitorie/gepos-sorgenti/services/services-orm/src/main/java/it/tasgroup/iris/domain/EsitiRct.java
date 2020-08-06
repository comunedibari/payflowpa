package it.tasgroup.iris.domain;

import java.io.Serializable;
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


/**
 * The persistent class for the ESITI_RCT database table.
 * 
 */
@Entity
@Table(name="ESITI_RCT")
@NamedQueries(
{
	@NamedQuery(
			name="getEsitiRctById",
			query="select esiti from EsitiRct esiti "+
				  "where esiti.id =:id"),
	@NamedQuery(
		name="listEsitiRctByIdRendicontazione",
		query="select esiti from EsitiRct esiti " +
				"where esiti.rendicontazioni.id=:idRendicontazione "),
	@NamedQuery(
			name="listEsitiRctByIdRendicontazioneAndTioesitoRct",
			query="select esiti from EsitiRct esiti " +
					"where esiti.rendicontazioni.id=:idRendicontazione " +
					"and esiti.tipoRecord=:tipoesito")
		}
	
)
public class EsitiRct extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String canalePagamento;
	private String causaleCbi;
	private Timestamp dataContabile;
	private Timestamp dataValuta;
	private Integer flagRiconciliazione;
	private Integer idBozzeBonificiRiaccredito;
	private String idRiconciliazione;
	private BigDecimal importo;
	private String opAggiornamento;
	private String opInserimento;
	private Integer progressivo;
	private String riferimentoBanca;
	private String segno;
	private String tipoRecord;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;

	private Rendicontazioni rendicontazioni;

    public EsitiRct() {
    }

	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="esiti_rct_pk_gen")	
	@SequenceGenerator(
		    name="esiti_rct_pk_gen",
		    sequenceName="ESITI_RCT_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		


	@Column(name="CANALE_PAGAMENTO")
	public String getCanalePagamento() {
		return this.canalePagamento;
	}

	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}


	@Column(name="CAUSALE_CBI")
	public String getCausaleCbi() {
		return this.causaleCbi;
	}

	public void setCausaleCbi(String causaleCbi) {
		this.causaleCbi = causaleCbi;
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


	public Integer getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
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


	@Column(name="TIPO_RECORD")
	public String getTipoRecord() {
		return this.tipoRecord;
	}

	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
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
	@JoinColumn(name="ID_RENDICONTAZIONI")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}
	
}