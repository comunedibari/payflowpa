package it.tasgroup.idp.domain;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the IBAN database table.
 * 
 */
@Entity
@Table(name="IBAN")
@NamedQuery(name="listaIban", query="SELECT i FROM Iban i " +
		" WHERE i.iban IN (:listaIban) and i.idSystem = :silMittente "
		+ " and i.cdTrbEnte = :tipoTributo ")
public class Iban implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="CD_TRB_ENTE")
	private String cdTrbEnte;

	private String iban;

	@Column(name="ID_ENTE")
	private String idEnte;

	@Column(name="ID_SYSTEM")
	private String idSystem;

	public Iban() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCdTrbEnte() {
		return this.cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getIdSystem() {
		return this.idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

}