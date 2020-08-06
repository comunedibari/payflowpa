package it.tasgroup.iris.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SEQ_IUV database table.
 * 
 */
@Embeddable
public class IUVPosizEnteMapPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private String idEnte;
	private String iuv;
    
	public IUVPosizEnteMapPK(){
		
	}
	
    public IUVPosizEnteMapPK(String idEnte, String iuv){
		this.idEnte=idEnte;
		this.iuv=iuv;
	}
	
	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	@Column(name="IUV")
	public String getIuv() {
		return this.iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IUVPosizEnteMapPK)) {
			return false;
		}
		IUVPosizEnteMapPK castOther = (IUVPosizEnteMapPK)other;
		return 
			this.idEnte.equals(castOther.idEnte)
			&& this.iuv.equals(castOther.iuv);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEnte.hashCode();
		hash = hash * prime + this.iuv.hashCode();
		
		return hash;
    }
}