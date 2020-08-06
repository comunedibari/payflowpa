package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the STAT_POSIZIONI_MESE database table.
 * 
 */
@Embeddable
public class StatistichePosizioniMesePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String  meseEstrazione;
	private String  idEnte;
	private String  idSystem;
	private String  idTributo;
	private String  cdTributoEnte;
	private String  tipoMessaggio;
	
	
	public StatistichePosizioniMesePK(){
		
	}
	
    public StatistichePosizioniMesePK(String  meseEstrazione,
	    								String  idEnte,
	    								String  idSystem,
	    								String  idTributo,
	    								String  cdTributoEnte,
	    								String  tipoMessaggio) {
    	this.meseEstrazione = meseEstrazione;
    	this.idEnte = idEnte;
    	this.idSystem = idSystem;
    	this.idTributo = idTributo;
    	this.cdTributoEnte = cdTributoEnte;
    	this.tipoMessaggio = tipoMessaggio;
	}
	
	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	@Column(name="MESE_ESTRAZIONE")
	public String getMeseEstrazione() {
		return meseEstrazione;
	}

	public void setMeseEstrazione(String meseEstrazione) {
		this.meseEstrazione = meseEstrazione;
	}

	@Column(name="ID_SYSTEM")
	public String getIdSystem() {
		return idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

	@Column(name="ID_TRIBUTO")
	public String getIdTributo() {
		return idTributo;
	}

	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}

	@Column(name="CD_TRB_ENTE")
	public String getCdTributoEnte() {
		return cdTributoEnte;
	}

	public void setCdTributoEnte(String cdTributoEnte) {
		this.cdTributoEnte = cdTributoEnte;
	}

	@Column(name="TIPO_MESSAGGIO")
	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StatistichePosizioniMesePK)) {
			return false;
		}
		StatistichePosizioniMesePK castOther = (StatistichePosizioniMesePK)other;
		return 
			this.idEnte.equals(castOther.idEnte)
			&& this.meseEstrazione.equals(castOther.meseEstrazione)
			&& this.idSystem.equals(castOther.idSystem)
			&& this.idTributo.equals(castOther.idTributo)
			&& this.cdTributoEnte.equals(castOther.cdTributoEnte)
			&& this.tipoMessaggio.equals(castOther.tipoMessaggio);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEnte.hashCode();
		hash = hash * prime + this.meseEstrazione.hashCode();
		hash = hash * prime + this.idSystem.hashCode();
		hash = hash * prime + this.idTributo.hashCode();
		hash = hash * prime + this.cdTributoEnte.hashCode();
		hash = hash * prime + this.tipoMessaggio.hashCode();
		
		return hash;
    }
	
	@Override
	public String toString() {
		return 
			"StatistichePosizioniMesePK "
			+ "["
			+ "meseEstrazione: " 	+ meseEstrazione
			+ " - idEnte: " 		+ idEnte
			+ " - idSystem: " 		+ idSystem
			+ " - cdTributoEnte: " + cdTributoEnte
			+ " - tipoMessaggio: " + tipoMessaggio
			+ "]";
	}
}
