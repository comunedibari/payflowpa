package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
@NamedQuery(
		name="DistinteRiaccreditoByStato", 
		query=
		" SELECT distinteRiaccredito  " +
		" from DistinteRiaccredito as distinteRiaccredito " +
		" where distinteRiaccredito.stato = :stato "),
@NamedQuery(
		name="DistinteRiaccreditoByCodTransazione", 
		query=
		" SELECT distinteRiaccredito  " +
		" from DistinteRiaccredito as distinteRiaccredito " +
		" where distinteRiaccredito.codTransazione = :codTransazione ")})		

/**
 * The persistent class for the DISTINTE_RIACCREDITO database table.
 *
 */
@Entity
@Table(name="DISTINTE_RIACCREDITO")
public class DistinteRiaccredito extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String codTransazione;
	private Timestamp dataCreazione;
	private Timestamp dataSpedizione;
	private String divisa;
	private BigDecimal importo;
	private BigDecimal importoCommissioni;
	private int numeroDisposizioni;
	private String stato;
	private String utenteCreatore;
	
	/*** Persistent Associations ***/
	private Set<BonificiRiaccredito> bonificiRiaccreditos;
	private CasellarioDispo casellarioDispo;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="distinte_riaccredito_pk_gen")	
	@SequenceGenerator(
	    name="distinte_riaccredito_pk_gen",
	    sequenceName="DISTINTE_RIACCREDITO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}
	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="COD_TRANSAZIONE")
	public String getCodTransazione() {
		return this.codTransazione;
	}

	public void setCodTransazione(String codTransazione) {
		this.codTransazione = codTransazione;
	}


	@Column(name="DATA_CREAZIONE")
	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Column(name="DATA_SPEDIZIONE")
	public Timestamp getDataSpedizione() {
		return this.dataSpedizione;
	}

	public void setDataSpedizione(Timestamp dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}


	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	@Column(name="IMPORTO_COMMISSIONI")
	public BigDecimal getImportoCommissioni() {
		return this.importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}


	@Column(name="NUMERO_DISPOSIZIONI")
	public int getNumeroDisposizioni() {
		return this.numeroDisposizioni;
	}

	public void setNumeroDisposizioni(int numeroDisposizioni) {
		this.numeroDisposizioni = numeroDisposizioni;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="UTENTE_CREATORE")
	public String getUtenteCreatore() {
		return this.utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	
	//bi-directional many-to-one association to BonificiRiaccredito
	@OneToMany(mappedBy="distinteRiaccredito",cascade=CascadeType.PERSIST)
	@OrderBy("id desc")
	public Set<BonificiRiaccredito> getBonificiRiaccreditos() {
		return this.bonificiRiaccreditos;
	}

	public void setBonificiRiaccreditos(Set<BonificiRiaccredito> bonificiRiaccreditos) {
		this.bonificiRiaccreditos = bonificiRiaccreditos;
	}

	
	//bi-directional one-to-one association to CasellarioDispo
	@OneToOne(mappedBy="distinteRiaccredito")
	public CasellarioDispo getCasellarioDispo() {
		return this.casellarioDispo;
	}

	public void setCasellarioDispo(CasellarioDispo casellarioDispo) {
		this.casellarioDispo = casellarioDispo;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DistinteRiaccredito [codTransazione=");
		builder.append(codTransazione);
		builder.append(", dataCreazione=");
		builder.append(dataCreazione);
		builder.append(", dataSpedizione=");
		builder.append(dataSpedizione);
		builder.append(", divisa=");
		builder.append(divisa);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", importoCommissioni=");
		builder.append(importoCommissioni);
		builder.append(", numeroDisposizioni=");
		builder.append(numeroDisposizioni);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", utenteCreatore=");
		builder.append(utenteCreatore);
		//builder.append(", bonificiRiaccreditos=");
		//builder.append(bonificiRiaccreditos);
		//builder.append(", casellarioDispo=");
		//builder.append(casellarioDispo);
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
		result = prime * result
				+ ((codTransazione == null) ? 0 : codTransazione.hashCode());
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
		DistinteRiaccredito other = (DistinteRiaccredito) obj;
		if (codTransazione == null) {
			if (other.codTransazione != null) {
				return false;
			}
		} else if (!codTransazione.equals(other.codTransazione)) {
			return false;
		}
		return true;
	}

	
}