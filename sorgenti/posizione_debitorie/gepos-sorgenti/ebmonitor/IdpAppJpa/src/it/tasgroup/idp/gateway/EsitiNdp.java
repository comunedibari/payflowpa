package it.tasgroup.idp.gateway;

import it.tasgroup.iris2.pagamenti.Rendicontazioni;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ESITI_NDP database table.
 * 
 */
@Entity
@Table(name="ESITI_NDP")
@NamedQueries({
@NamedQuery(
	name="EsitiNdp.findAll", 
	query="SELECT e FROM EsitiNdp e"),
@NamedQuery(
	name="findEsitiNdpByRendicontazioniAndFlagRiconciliazione", 
	query="SELECT e FROM EsitiNdp e " +
	      "WHERE e.rendicontazioni = :rendicontazioni " +
		  "  AND e.flagRiconciliazione = :flagRiconciliazione "
	),
@NamedQuery(
		name="findEsitiNdpByRendicontazioni", 
		query="SELECT e FROM EsitiNdp e " +
		      "WHERE e.rendicontazioni = :rendicontazione "
		),
@NamedQuery(
			name="findEsitiNdpByIUV", 
			query="SELECT e FROM EsitiNdp e " +
			      "WHERE e.idRiconciliazione = :iuv order by ts_inserimento desc"
			)	
})
public class EsitiNdp extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date dataPagamento;
	private String esitoPagamento;
	private short flagRiconciliazione;
	private int idBozzeBonificiRiaccredito;
	private String idRiconciliazione;
	private String idRiscossione;
	private BigDecimal importo;
	private String segno;
	private String codAnomalia;
	private Integer indicePagamento;
	
	//bi-directional many-to-one association to Rendicontazioni
	/*** Persistent Associations ***/
	private Rendicontazioni rendicontazioni;	

	public EsitiNdp() {
	}

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="esitiNdp_pk_gen")	
	@SequenceGenerator(
	    name="esitiNdp_pk_gen",
	    sequenceName="ESITI_NDP_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_PAGAMENTO")
	public Date getDataPagamento() {
		return this.dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}


	@Column(name="ESITO_PAGAMENTO")
	public String getEsitoPagamento() {
		return this.esitoPagamento;
	}

	public void setEsitoPagamento(String esitoPagamento) {
		this.esitoPagamento = esitoPagamento;
	}


	@Column(name="FLAG_RICONCILIAZIONE")
	public short getFlagRiconciliazione() {
		return this.flagRiconciliazione;
	}

	public void setFlagRiconciliazione(short flagRiconciliazione) {
		this.flagRiconciliazione = flagRiconciliazione;
	}


	@Column(name="ID_BOZZE_BONIFICI_RIACCREDITO")
	public int getIdBozzeBonificiRiaccredito() {
		return this.idBozzeBonificiRiaccredito;
	}

	public void setIdBozzeBonificiRiaccredito(int idBozzeBonificiRiaccredito) {
		this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
	}


	@ManyToOne
	@JoinColumn(name="ID_RENDICONTAZIONI")	
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}


	@Column(name="ID_RICONCILIAZIONE")
	public String getIdRiconciliazione() {
		return this.idRiconciliazione;
	}

	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}


	@Column(name="ID_RISCOSSIONE")
	public String getIdRiscossione() {
		return this.idRiscossione;
	}

	public void setIdRiscossione(String idRiscossione) {
		this.idRiscossione = idRiscossione;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	public String getSegno() {
		return this.segno;
	}

	public void setSegno(String segno) {
		this.segno = segno;
	}
	
	
	@Column(name="COD_ANOMALIA")
	public String getCodAnomalia() {
		return this.codAnomalia;
	}
	
	public void setCodAnomalia(String codAnomalia) {
		this.codAnomalia = codAnomalia;
	}

	@Column(name="INDICE_PAGAMENTO")
	public Integer getIndicePagamento() {
		return indicePagamento;
	}

	public void setIndicePagamento(Integer indicePagamento) {
		this.indicePagamento = indicePagamento;
	}
}