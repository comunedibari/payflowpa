package it.nch.is.fo.tributi;

import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class TributioperId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tipoOperatore;
	private String idEntePk;
	private String cdTrbEntePk;
	private String intestatarioPk;
	private String operatorePk;

	public TributioperId() {
	}

	public TributioperId(String idEnte, String cdTrbEnte, String intestatario, String operatore) {
		this.idEntePk = idEnte;
		this.cdTrbEntePk = cdTrbEnte;
		this.intestatarioPk = intestatario;
		this.operatorePk = operatore;
	}

	@Column(name="ID_ENTE")
	public String getIdEntePk() {
		return this.idEntePk;
	}

	public void setIdEntePk(String idEnte) {
		this.idEntePk = idEnte;
	}

	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEntePk() {
		return this.cdTrbEntePk;
	}

	public void setCdTrbEntePk(String cdTrbEnte) {
		this.cdTrbEntePk = cdTrbEnte;
	}
	
	@Column(name="INTESTATARIO")
	public String getIntestatario() {
		return this.intestatarioPk;
	}
	public void setIntestatario(String intestatario) {
		this.intestatarioPk = intestatario;
	}
	
	@Column(name="OPERATORE")
	public String getOperatore() {
		return this.operatorePk;
	}
	public void setOperatore(String operatore) {
		this.operatorePk = operatore;
	}
	
	@Column(name="TP_OPERATORE")
	public String getTipoOperatore() {
		return this.tipoOperatore;
	}
	public void setTipoOperatore(String tipoOperatore) {
		this.tipoOperatore = tipoOperatore;
	}

}
