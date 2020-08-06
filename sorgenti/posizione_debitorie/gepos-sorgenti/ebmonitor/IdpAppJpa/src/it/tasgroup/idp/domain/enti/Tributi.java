package it.tasgroup.idp.domain.enti;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseEntity;

@NamedQueries({
	@NamedQuery(name = "TributiByIdTributo", 
			query = "SELECT t FROM Tributi t WHERE idTributo = :idTributo") 

})
/**
 * The persistent class for the JLTTRPA database table.
 * 
 */
@Entity
@Table(name="JLTTRPA")
public class Tributi extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private String idTributo;
	private String cdAde;
	private String deTrb;
	private String flIniziativa;
	private String flPredeterm;	
	private int prVersione;	
	private String soggEsclusi;
	private String stato;	
	private String tpEntrata;
	private String tassonomia;
	
	/*** Persistent Associations***/
	private Set<TributiEnti> jltentrs;


	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Id
	@Column(name="ID_TRIBUTO")
	public String getIdTributo() {
		return this.idTributo;
	}

	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}

	@Column(name="CD_ADE")
	public String getCdAde() {
		return this.cdAde;
	}

	public void setCdAde(String cdAde) {
		this.cdAde = cdAde;
	}

	@Column(name="DE_TRB")	
	public String getDeTrb() {
		return this.deTrb;
	}

	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}

	@Column(name="FL_INIZIATIVA")
	public String getFlIniziativa() {
		return this.flIniziativa;
	}

	public void setFlIniziativa(String flIniziativa) {
		this.flIniziativa = flIniziativa;
	}

	@Column(name="FL_PREDETERM")
	public String getFlPredeterm() {
		return this.flPredeterm;
	}

	public void setFlPredeterm(String flPredeterm) {
		this.flPredeterm = flPredeterm;
	}

	
	@Column(name="PR_VERSIONE")	
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="SOGG_ESCLUSI")	
	public String getSoggEsclusi() {
		return this.soggEsclusi;
	}

	public void setSoggEsclusi(String soggEsclusi) {
		this.soggEsclusi = soggEsclusi;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="TP_ENTRATA")
	public String getTpEntrata() {
		return this.tpEntrata;
	}

	public void setTpEntrata(String tpEntrata) {
		this.tpEntrata = tpEntrata;
	}

	@Column(name="TASSONOMIA")
    public String getTassonomia() {
            return tassonomia;
    }

    public void setTassonomia(String tassonomia) {
            this.tassonomia = tassonomia;
    }

	//bi-directional many-to-one association to TributiEnti
	@OneToMany(mappedBy="jlttrpa")
	public Set<TributiEnti> getJltentrs() {
		return this.jltentrs;
	}

	public void setJltentrs(Set<TributiEnti> jltentrs) {
		this.jltentrs = jltentrs;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tributi [idTributo=");
		builder.append(idTributo);
		builder.append(", cdAde=");
		builder.append(cdAde);
		builder.append(", deTrb=");
		builder.append(deTrb);
		builder.append(", flIniziativa=");
		builder.append(flIniziativa);
		builder.append(", flPredeterm=");
		builder.append(flPredeterm);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", soggEsclusi=");
		builder.append(soggEsclusi);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", tpEntrata=");
		builder.append(tpEntrata);
		//builder.append(", jltentrs=");
		//builder.append(jltentrs);
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
				+ ((idTributo == null) ? 0 : idTributo.hashCode());
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
		Tributi other = (Tributi) obj;
		if (idTributo == null) {
			if (other.idTributo != null) {
				return false;
			}
		} else if (!idTributo.equals(other.idTributo)) {
			return false;
		}
		return true;
	}

	
}