package it.tasgroup.idp.anagrafica;

/**
 * @author FabriziE
 */

import it.tasgroup.idp.domain.BaseEntity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="GEC_CANALI")
@NamedQueries(
{
@NamedQuery(
		name="findCanaliByDenominazione",
		query="from Canali c " +
			  "where c.denominazione = :denominazione")
,
@NamedQuery(
	name="findAllCanali",
	query="from Canali " +
		  "order by denominazione desc ")
,
@NamedQuery(
		name="findCanaliByIdUtente",
		query="select c from Canali c, UtentiCanali uc " +
			  "where c.id = uc.id.idCanale " +
			  "and uc.id.idUtente = :idUtente " +
			  "and uc.stato = :stato " +
			  "and c.stato = :statoCanale " +
		      "order by c.denominazione desc ")
}
)
public class Canali extends BaseEntity {
	private static final long serialVersionUID = 1231963839181791205L;
	
	private String denominazione;
	private String stato;	
	private BigInteger numTentativi;
	
	private BigInteger tempoRetry;
	private String configurazione;
	private Integer   version;
	
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="canali_pk_gen")	
	@SequenceGenerator(
		    name="canali_pk_gen",
		    sequenceName="GEC_CANALI_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
	 return this.id;
	}
	public void setId(Long id) {
	 this.id = id;
	}

	
	@Column(name="NUM_TENTATIVI")
	public BigInteger getNumTentativi() {
		return this.numTentativi;
	}

	public void setNumTentativi(BigInteger NumTentativi) {
		this.numTentativi = NumTentativi;
	}

	@Column(name="STATO")
	public String getStato() {
		return this.stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	@Column(name="TEMPO_RETRY")
	public BigInteger getTempoRetry() {
		return tempoRetry;
	}

	public void setTempoRetry(BigInteger tempoRetry) {
		this.tempoRetry = tempoRetry;
	}

	@Column(name="CONFIGURAZIONE")
	public String getConfigurazione() {
		return configurazione;
	}

	public void setConfigurazione(String configurazione) {
		this.configurazione = configurazione;
	}

	
	@Column(name="DENOMINAZIONE")
	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	@Column(name="VERSION")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Canale [id=");
		builder.append(getId());
		builder.append(", denominazione=");
		builder.append(getDenominazione());
		builder.append(", stato=");
		builder.append(getStato());
		builder.append(", numTentativi=");
		builder.append(getNumTentativi());
		builder.append(", tempoRetry=");
		builder.append(getTempoRetry());
		builder.append(", configurazione=");
		builder.append(getConfigurazione());
		builder.append(", tsAggiornamento=");
		builder.append(getTsAggiornamento());
		builder.append(", opAggiornamento=");
		builder.append(getOpAggiornamento());
		builder.append(", tsInserimento=");
		builder.append(getTsInserimento());
		builder.append(", opInserimento=");
		builder.append(getOpInserimento());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

	
}