package it.nch.is.fo.sistemienti;

import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.fwk.fo.dto.business.PojoImpl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name="JLTSIL")
public class SistemaEnte extends PojoImpl implements java.io.Serializable, ISistemaEnte, Pojo{

	/*** PK Reference ***/
	private SistemaEnteId sisEntId;
	
	/*** Persistent Properties ***/
	private String deSystem;
	private String stato;
	private int prVersione;
	private String opInserimento;
	private Date tsInserimento;
	private String opAggiornamento;
	private Date tsAggiornamento;
	private String result;
	private String descEnte;
	private String trtId;
	private String trtSystem;
	private String sSilEnabledAsString = "N";  // Default messo per conpatibilita' con versione priva di Smart SIL essendo NOT NULL il corrispondente campo di DB 
	private String cdEnte;
	private String authId;
	private String userId;
 
	public SistemaEnte() {
		this.sisEntId = new SistemaEnteId();
	}
	
	public SistemaEnte(SistemaEnteId id) {
		this.sisEntId = id;
	}

	public SistemaEnte(String idEnte,String idSystem, int prVersione, String opInserimento, Date tsInserimento, String result) {
		this.sisEntId.setIdEnte(idEnte);
		this.sisEntId.setIdSystem(idSystem);
		this.prVersione = prVersione;
		this.opInserimento = opInserimento;
		this.tsInserimento = tsInserimento;
		this.result=result;
       
	}

	public SistemaEnte(String idEnte,String idSystem, String deSystem, String stato, int prVersione, String opInserimento,
			Date tsInserimento, String opAggiornamento, Date tsAggiornamento, String result) {
		this.sisEntId.setIdEnte(idEnte);
		this.sisEntId.setIdSystem(idSystem);
		this.deSystem = deSystem;
		this.stato = stato;
		this.prVersione = prVersione;
		this.opInserimento = opInserimento;
		this.tsInserimento = tsInserimento;
		this.opAggiornamento = opAggiornamento;
		this.tsAggiornamento = tsAggiornamento;
		this.result=result;
	}

	@Id
	public SistemaEnteId getSisEntId() {
		return sisEntId;
	}
	public void setSisEntId(SistemaEnteId sisEntId) {
		this.sisEntId = sisEntId;
	}
	
	@Column(name="DE_SYSTEM")
	public String getDeSystem() {
		return this.deSystem;
	}
	public void setDeSystem(String deSystem) {
		this.deSystem = deSystem;
	}

	@Column(name="STATO")
	public String getStato() {
		return this.stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO")
	public Date getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Date tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Date getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Date tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	@Override
	@Transient
	public String getIdEnte() {
		return sisEntId.getIdEnte();
	}
	@Override
	public void setIdEnte(String idEnte) {
		this.sisEntId.setIdEnte(idEnte);
	}

	@Override
	@Transient
	public String getIdSystem() {
		return sisEntId.getIdSystem();
	}
	@Override
	public void setIdSystem(String idSystem) {
		this.sisEntId.setIdSystem(idSystem);
	}

	@Override
	@Transient
	public String getDescEnte() {
		return descEnte;
	}
	@Override
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	@Override
	@Transient
	public String getResult() {
		return result;
	}
	@Override
	public void setResult(String result) {
		this.result = result;
	}
	
	
	@Column(name="FL_SSIL")
	public String getsSilEnabledAsString() {
		return sSilEnabledAsString;
	}
	
	public void setsSilEnabledAsString(String sSilEnabledAsString) {
		this.sSilEnabledAsString = sSilEnabledAsString;
	}
	
	@Transient
	public boolean isSSilEnabled() {
		return "Y".equals(sSilEnabledAsString);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sisEntId == null) ? 0 : sisEntId.hashCode());
		return result;
	}
	
	@Transient
	public String getCdEnte() {
		return cdEnte;
	}

	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SistemaEnte other = (SistemaEnte) obj;
		if (sisEntId == null) {
			if (other.sisEntId != null)
				return false;
		} else if (!sisEntId.equals(other.sisEntId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SistemaEnte [sisEntId=" + sisEntId + ", deSystem=" + deSystem
				+ ", stato=" + stato + ", prVersione=" + prVersione
				+ ", opInserimento=" + opInserimento + ", tsInserimento="
				+ tsInserimento + ", opAggiornamento=" + opAggiornamento
				+ ", tsAggiornamento=" + tsAggiornamento + ", result=" + result
				+ ", descEnte=" + descEnte + "]";
	}

	@Column(name="TRT_ID")
	public String getTrtId() {
		return trtId;
	}

	public void setTrtId(String trtId) {
		this.trtId = trtId;
	}

	@Column(name="TRT_SYSTEM")
	public String getTrtSystem() {
		return trtSystem;
	}

	public void setTrtSystem(String trtSystem) {
		this.trtSystem = trtSystem;
	}

	@Column(name="AUTH_ID")
	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	@Column(name="USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
