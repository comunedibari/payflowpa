package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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

@NamedQueries({
@NamedQuery(
	name="EsitiRctByIdRiconciliazione", 
	query=
	"SELECT RCT FROM EsitiRct RCT " +
	" WHERE RCT.idRiconciliazione = :idRiconciliazione "),
@NamedQuery(name="EsitiRct.findAllNonRiconciliatiByIdCasellarioInfo", 
			query="SELECT e FROM EsitiRct e "
					+ " where flagRiconciliazione<>1 "
					+ "	AND e.rendicontazioni.casellarioInfo.id = :idCasellarioInfo ")
})

/**
 * The persistent class for the ESITI_RCT database table.
 *
 */
@Entity
@Table(name="ESITI_RCT")
public class EsitiRct extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String canalePagamento;
	private String causaleCbi;
	private Timestamp dataContabile;
	private Timestamp dataValuta;
	private short flagRiconciliazione;
	private int idBozzeBonificiRiaccredito;
	private String idRiconciliazione;
	private BigDecimal importo;
	private int progressivo;
	private String riferimentoBanca;
	private String segno;
	private String tipoRecord;
	
	/*** Persistent Associations ***/
	private Rendicontazioni rendicontazioni;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="esiti_rct_pk_gen")	
	@SequenceGenerator(
	    name="esiti_rct_pk_gen",
	    sequenceName="ESITI_RCT_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
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


	public int getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(int progressivo) {
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


	//bi-directional many-to-one association to Rendicontazioni
    @ManyToOne
	@JoinColumn(name="ID_RENDICONTAZIONI")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EsitiRct [canalePagamento=");
		builder.append(canalePagamento);
		builder.append(", causaleCbi=");
		builder.append(causaleCbi);
		builder.append(", dataContabile=");
		builder.append(dataContabile);
		builder.append(", dataValuta=");
		builder.append(dataValuta);
		builder.append(", flagRiconciliazione=");
		builder.append(flagRiconciliazione);
		builder.append(", idBozzeBonificiRiaccredito=");
		builder.append(idBozzeBonificiRiaccredito);
		builder.append(", idRiconciliazione=");
		builder.append(idRiconciliazione);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", progressivo=");
		builder.append(progressivo);
		builder.append(", riferimentoBanca=");
		builder.append(riferimentoBanca);
		builder.append(", segno=");
		builder.append(segno);
		builder.append(", tipoRecord=");
		builder.append(tipoRecord);
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
		EsitiRct other = (EsitiRct) obj;
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