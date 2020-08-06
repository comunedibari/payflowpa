package it.tasgroup.idp.domain.iuv;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SEQ_IUV database table.
 * 
 */
@Embeddable
public class IUVSequencePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private String idEnte;
	private String cdTrbEnte;
    
	public IUVSequencePK(){
		
	}
	
    public IUVSequencePK(String idEnte, String cdTrbEnte){
		this.idEnte=idEnte;
		this.cdTrbEnte=cdTrbEnte;
	}
	
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
		if (!(other instanceof IUVSequencePK)) {
			return false;
		}
		IUVSequencePK castOther = (IUVSequencePK)other;
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