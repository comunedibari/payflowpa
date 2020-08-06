package it.tasgroup.idp.domain.enti;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the JLTENTR database table.
 * 
 */
@Embeddable
public class TributiEntiPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String idEnte;
	private String cdTrbEnte;
    
	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEnte() {
		return this.cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TributiEntiPK)) {
			return false;
		}
		TributiEntiPK castOther = (TributiEntiPK)other;
		return 
			this.idEnte.equals(castOther.idEnte)
			&& this.cdTrbEnte.equals(castOther.cdTrbEnte);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEnte.hashCode();
		hash = hash * prime + this.cdTrbEnte.hashCode();
		
		return hash;
    }
}