package it.tasgroup.ge;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the JLTE037 database table.
 *
 */
@Entity
@Table(name="INTEST_OPER")
public class IntestatariOperatori extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String intestatario;
	private String operatore;
	private String tipoOperatore;
	
	@Id
	public String getIntestatario() {
		return intestatario;
	}


	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	@Column(name="OPERATORE")
	public String getOperatore() {
		return operatore;
	}


	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	@Column(name="TP_OPERATORE")
	public String getTipoOperatore() {
		return tipoOperatore;
	}


	public void setTipoOperatore(String tipoOperatore) {
		this.tipoOperatore = tipoOperatore;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IntestatariOperatore [intestatario=");
		builder.append(intestatario);
		builder.append(", \noperatore=");
		builder.append(operatore);
		builder.append(", \ntipoOperatore=");
		builder.append(tipoOperatore);
		builder.append("]");
		return builder.toString();
	}

	
}