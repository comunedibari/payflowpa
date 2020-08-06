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
 * The persistent class for the ESITI_BB database table.
 * 
 */
@Entity
@Table(name="ESITI_BB")
@NamedQueries(
{
	@NamedQuery(
		name="getEsitiBbById",
		query="select esiti from EsitiBb esiti "+
			  "where esiti.id =:id"),
	@NamedQuery(
		name="listEsitiBbByIdRendicontazione",
		query="select esiti from EsitiBb esiti " +
				"where esiti.rendicontazioni.id=:idRendicontazione ")
		}
)
public class EsitiBb extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String abiEsattrice;
	private String cabEsattrice;
	private String codeline;
	private Timestamp dataPagamento;
	private Timestamp dataValuta;
	private Integer flagRiconciliazione;
	private String ibanAccredito;
	private Integer idBozzeBonificiRiaccredito;
	private String idRiconciliazione;
	private BigDecimal importo;
	private String modalitaPagamento;
	private String numeroIncasso;
	private String opAggiornamento;
	private String opInserimento;
	private Integer progressivo;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	
	private Rendicontazioni rendicontazioni;

    public EsitiBb() {
    }

	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="esiti_bb_pk_gen")	
	@SequenceGenerator(
		    name="esiti_bb_pk_gen",
		    sequenceName="ESITI_BB_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		

	@Column(name="ABI_ESATTRICE")
	public String getAbiEsattrice() {
		return this.abiEsattrice;
	}

	public void setAbiEsattrice(String abiEsattrice) {
		this.abiEsattrice = abiEsattrice;
	}


	@Column(name="CAB_ESATTRICE")
	public String getCabEsattrice() {
		return this.cabEsattrice;
	}

	public void setCabEsattrice(String cabEsattrice) {
		this.cabEsattrice = cabEsattrice;
	}


	public String getCodeline() {
		return this.codeline;
	}

	public void setCodeline(String codeline) {
		this.codeline = codeline;
	}


	@Column(name="DATA_PAGAMENTO")
	public Timestamp getDataPagamento() {
		return this.dataPagamento;
	}

	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
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


	@Column(name="IBAN_ACCREDITO")
	public String getIbanAccredito() {
		return this.ibanAccredito;
	}

	public void setIbanAccredito(String ibanAccredito) {
		this.ibanAccredito = ibanAccredito;
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


	@Column(name="MODALITA_PAGAMENTO")
	public String getModalitaPagamento() {
		return this.modalitaPagamento;
	}

	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}


	@Column(name="NUMERO_INCASSO")
	public String getNumeroIncasso() {
		return this.numeroIncasso;
	}

	public void setNumeroIncasso(String numeroIncasso) {
		this.numeroIncasso = numeroIncasso;
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
	
}