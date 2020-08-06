package it.tasgroup.idp.domain.enti;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.tasgroup.idp.domain.BaseEntity;


/**
 * The persistent class for the JLTSIL database table.
 * 
 */
@Entity
@Table(name="JLTSIL")
@NamedQueries({
@NamedQuery (name = "checkSIL", query = "select SIL from Sil as SIL, Enti as ENTI"
                                + " where"
                                + "     SIL.id.idEnte = ENTI.idEnte" 
                                + " and SIL.id.idSystem = :idSystem"  
                                + " and ENTI.cdEnte = :idMittente"
                         ),
@NamedQuery (name = "SILByPk", query = "select SIL from Sil as SIL"
        + " where"
        + "     SIL.id.idEnte = :idEnte"  
        + " and SIL.id.idSystem = :idSystem"
 )
})
public class Sil extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values***/
	private String deSystem;
	private int prVersione;
	private String stato;
	private String trtId;
	private String trtSystem;
	private String sSilEnabledAsString;
	private String userId;
	private String authId;

	/*** Composite Primary Keys ***/ 
	private SilPK id;	

	@EmbeddedId
	public SilPK getId() {
		return this.id;
	}

	public void setId(SilPK id) {
		this.id = id;
	}

	@Column(name="DE_SYSTEM")
	public String getDeSystem() {
		return this.deSystem;
	}

	public void setDeSystem(String deSystem) {
		this.deSystem = deSystem;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public void setTrtId(String trtId) {
		this.trtId = trtId;
	}

	public void setTrtSystem(String trtSystem) {
		this.trtSystem = trtSystem;
	}		
	

	@Column(name="TRT_ID")
	public String getTrtId() {
		return this.trtId;
	}	

	@Column(name="TRT_SYSTEM")
	public String getTrtSystem() {
		return this.trtSystem;
	}
	
	@Column(name="FL_SSIL")
	public String getsSilEnabledAsString() {
		return sSilEnabledAsString;
	}
	
	public void setsSilEnabledAsString(String sSilEnabledAsString) {
		this.sSilEnabledAsString = sSilEnabledAsString;
	}

	@Column(name="USER_ID")
	public String getUserId() {
		return this.userId;
	}	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="AUTH_ID")
	public String getAuthId() {
		return this.authId;
	}	
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	
	
	@Transient
	public boolean isSSilEnabled() {
		return "Y".equals(sSilEnabledAsString);
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sil [id=");
		builder.append(id);
		builder.append(", deSystem=");
		builder.append(deSystem);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append(", sSilEnabledAsString=");
		builder.append(sSilEnabledAsString);
		builder.append("]");
		return builder.toString();
	}

	

}