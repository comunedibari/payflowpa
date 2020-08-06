package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;

@NamedQueries({
@NamedQuery(
	name="EsitiBbAllOByIdRiconciliazione", 
	query=
	"SELECT esitiBb FROM EsitiBb esitiBb " +
	" order by esitiBb.tsInserimento desc "),
@NamedQuery(
	name="EsitiBbByIdRiconciliazione", 
	query=
	"SELECT esitiBb FROM EsitiBb esitiBb " +
	" WHERE esitiBb.idRiconciliazione = :idRiconciliazione ")})

/**
 * The persistent class for the ESITI_BB database table.
 * 
 */
@Entity
@Table(name="ESITI_BB")
public class EsitiBb extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String abiEsattrice;
	private String cabEsattrice;
	private String codeline;
	private Timestamp dataPagamento;
	private Timestamp dataValuta;
	private short flagRiconciliazione;
	private String ibanAccredito;
	private int idBozzeBonificiRiaccredito;
	private String idRiconciliazione;
	private BigDecimal importo;
	private String modalitaPagamento;
	private String numeroIncasso;
	private int progressivo;
	
	/*** Persistent Associations ***/
	private Rendicontazioni rendicontazioni;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="esiti_bb_pk_gen")	
	@SequenceGenerator(
	    name="esiti_bb_pk_gen",
	    sequenceName="ESITI_BB_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}
	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
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
	public short getFlagRiconciliazione() {
		return this.flagRiconciliazione;
	}

	public void setFlagRiconciliazione(short flagRiconciliazione) {
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
	public int getIdBozzeBonificiRiaccredito() {
		return this.idBozzeBonificiRiaccredito;
	}

	public void setIdBozzeBonificiRiaccredito(int idBozzeBonificiRiaccredito) {
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


	public int getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}


	//bi-directional many-to-one association to Rendicontazioni
    @ManyToOne
	@JoinColumn(name="ID_RENDICONTAZIONE")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EsitiBb [abiEsattrice=");
		builder.append(abiEsattrice);
		builder.append(", cabEsattrice=");
		builder.append(cabEsattrice);
		builder.append(", codeline=");
		builder.append(codeline);
		builder.append(", dataPagamento=");
		builder.append(dataPagamento);
		builder.append(", dataValuta=");
		builder.append(dataValuta);
		builder.append(", flagRiconciliazione=");
		builder.append(flagRiconciliazione);
		builder.append(", ibanAccredito=");
		builder.append(ibanAccredito);
		builder.append(", idBozzeBonificiRiaccredito=");
		builder.append(idBozzeBonificiRiaccredito);
		builder.append(", idRiconciliazione=");
		builder.append(idRiconciliazione);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", modalitaPagamento=");
		builder.append(modalitaPagamento);
		builder.append(", numeroIncasso=");
		builder.append(numeroIncasso);
		builder.append(", progressivo=");
		builder.append(progressivo);
		//builder.append(", rendicontazioni=");
		//builder.append(rendicontazioni);
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
		result = prime
				* result
				+ ((idRiconciliazione == null) ? 0 : idRiconciliazione
						.hashCode());
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
		EsitiBb other = (EsitiBb) obj;
		if (idRiconciliazione == null) {
			if (other.idRiconciliazione != null) {
				return false;
			}
		} else if (!idRiconciliazione.equals(other.idRiconciliazione)) {
			return false;
		}
		return true;
	}
	

	
}