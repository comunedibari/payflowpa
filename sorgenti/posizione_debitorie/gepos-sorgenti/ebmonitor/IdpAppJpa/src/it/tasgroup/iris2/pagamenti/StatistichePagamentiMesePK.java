package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the STAT_POSIZIONI_MESE database table.
 * 
 */
@Embeddable
public class StatistichePagamentiMesePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String  meseEstrazione;
	private String  idEnte;
	private String  idSystem;
	private String  idTributo;
	private String  cdTributoEnte;
	private String  atteso;
	private Integer circuito;
	private Integer modalita;
	private String  incasso;
	
	public StatistichePagamentiMesePK(){
		
	}
	
    public StatistichePagamentiMesePK(String  meseEstrazione,
	    								String  idEnte,
	    								String  idSystem,
	    								String  idTributo,
	    								String  cdTributoEnte,
	    								String  atteso,
										Integer  circuito,
										Integer  modalita,
										String  incasso) {
    	this.meseEstrazione = meseEstrazione;
    	this.idEnte = idEnte;
    	this.idSystem = idSystem;
    	this.idTributo = idTributo;
    	this.cdTributoEnte = cdTributoEnte;
    	this.atteso = atteso;
    	this.circuito = circuito;
    	this.modalita = modalita;
    	this.incasso = incasso;
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
	
	@Column(name="ATTESO")
	public String getAtteso() {
		return atteso;
	}

	public void setAtteso(String atteso) {
		this.atteso = atteso;
	}

	@Column(name="CIRCUITO")
	public Integer getCircuito() {
		return circuito;
	}

	public void setCircuito(Integer circuito) {
		this.circuito = circuito;
	}

	@Column(name="MODALITA")
	public Integer getModalita() {
		return modalita;
	}

	public void setModalita(Integer modalita) {
		this.modalita = modalita;
	}

	@Column(name="INCASSO")
	public String getIncasso() {
		return incasso;
	}

	public void setIncasso(String incasso) {
		this.incasso = incasso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StatistichePagamentiMesePK)) {
			return false;
		}
		StatistichePagamentiMesePK castOther = (StatistichePagamentiMesePK)other;
		return 
			this.idEnte.equals(castOther.idEnte)
			&& this.meseEstrazione.equals(castOther.meseEstrazione)
			&& this.idSystem.equals(castOther.idSystem)
			&& this.idTributo.equals(castOther.idTributo)
			&& this.cdTributoEnte.equals(castOther.cdTributoEnte)
			&& this.atteso.equals(castOther.atteso)
			&& this.circuito.equals(castOther.circuito)
			&& this.modalita.equals(castOther.modalita)
			&& this.incasso.equals(castOther.incasso);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEnte.hashCode();
		hash = hash * prime + this.meseEstrazione.hashCode();
		hash = hash * prime + this.idSystem.hashCode();
		hash = hash * prime + this.idTributo.hashCode();
		hash = hash * prime + this.cdTributoEnte.hashCode();
		hash = hash * prime + this.atteso.hashCode();
		hash = hash * prime + this.circuito.hashCode();
		hash = hash * prime + this.modalita.hashCode();
		hash = hash * prime + this.incasso.hashCode();
		
		return hash;
    }
	
	@Override
	public String toString() {
		return 
			"StatistichePagamentiMesePK "
			+ "["
			+ "meseEstrazione: " 	+ meseEstrazione
			+ " - idEnte: " 		+ idEnte
			+ " - idSystem: " 		+ idSystem
			+ " - idTributo: " 		+ idTributo
			+ " - cdTributoEnte: " 	+ cdTributoEnte
			+ " - atteso: " 		+ atteso
			+ " - circuito: " 		+ circuito
			+ " - modalita: " 		+ modalita
			+ " - incasso: " 		+ incasso
			+ "]";
	}
}
