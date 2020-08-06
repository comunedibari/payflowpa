package it.tasgroup.idp.domain.enti;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the JLTSIL database table.
 * 
 */
@Embeddable
public class SilPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private String idEnte;
	private String idSystem;

	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	@Column(name="ID_SYSTEM")
	public String getIdSystem() {
		return this.idSystem;
	}
	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SilPK [idEnte=");
		builder.append(idEnte);
		builder.append(", idSystem=");
		builder.append(idSystem);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SilPK)) {
			return false;
		}
		SilPK castOther = (SilPK)other;
		return 
			this.idEnte.equals(castOther.idEnte)
			&& this.idSystem.equals(castOther.idSystem);
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEnte.hashCode();
		hash = hash * prime + this.idSystem.hashCode();		
		return hash;
    }
}