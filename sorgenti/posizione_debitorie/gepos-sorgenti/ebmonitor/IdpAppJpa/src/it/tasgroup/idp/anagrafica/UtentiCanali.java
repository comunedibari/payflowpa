package it.tasgroup.idp.anagrafica;

/**
 * @author FabriziE
 */

import it.tasgroup.idp.domain.BaseEntity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="GEC_UTENTI_CANALI")
@NamedQueries(
{
@NamedQuery(
	name="findUtentiCanali",
	query="from UtentiCanali c " +
		  "where c.id.idUtente = :idUtente " +
		  "and c.canale.denominazione in (:denominazioni) " +
		  "order by c.canale.denominazione desc ")
,
@NamedQuery(
		name="findUtentiCanaliByUtente",
		query="from UtentiCanali c " +
			  "where c.id.idUtente = :idUtente " +
			  "order by c.canale.denominazione desc ")
}
)
public class UtentiCanali extends BaseEntity {
	private static final long serialVersionUID = 3300364202257877161L;
	
	private UtentiCanaliPK id;
	private Canali canale;
	private Boolean isAnonymous;
	private String stato; //StatoCanaleType
	private String configurazione;
	private Integer   version;

    
	public UtentiCanali() {
		version=new Integer(0);
	}

	@EmbeddedId
	public UtentiCanaliPK getId() {
		return id;
	}

	public void setId(UtentiCanaliPK id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="ID_CANALE", insertable=false, updatable=false)
	public Canali getCanale() {
		return canale;
	}

	public void setCanale(Canali canale) {
		this.canale = canale;
	}	
	
	@Column(name="IS_ANONYMOUS")
	public Boolean getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	@Column(name="STATO")
	public String getStato() {
		return this.stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="CONFIGURAZIONE")
	public String getConfigurazione() {
		return configurazione;
	}

	public void setConfigurazione(String configurazione) {
		this.configurazione = configurazione;
	}

	@Column(name="VERSION")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((canale == null) ? 0 : canale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UtentiCanali other = (UtentiCanali) obj;
		if (canale == null) {
			if (other.canale != null)
				return false;
		} else if (!canale.equals(other.canale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UtentiCanali [id=");
		builder.append(getId());
		builder.append(", id=");
		builder.append(getId());
		builder.append(", isAnonymous=");
		builder.append(getIsAnonymous());
		builder.append(", stato=");
		builder.append(getStato());
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