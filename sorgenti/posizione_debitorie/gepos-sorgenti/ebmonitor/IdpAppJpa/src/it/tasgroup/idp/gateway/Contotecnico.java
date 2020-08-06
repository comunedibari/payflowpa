package it.tasgroup.idp.gateway;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;


@NamedQueries({
//@NamedQuery(
//	name="ContoById", 
//	query=
//		"SELECT conto FROM Contotecnico conto " +
//			" WHERE conto.id = :id "
//				)
//})

@NamedQuery(
		name="ContoById", 
		query=
			"SELECT conto FROM Contotecnico conto " +
				" WHERE conto.id = :id ",
	    hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }
	  )
	})

/**
 * The persistent class for the CONTOTECNICO database table.
 * 
 */

@Entity
@Table(name="CONTOTECNICO")
@NamedQuery(name="Contotecnico.findAll", query="SELECT c FROM Contotecnico c")
//Disabilitare durante l'esecuzione dei test JUnit
//@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL) 
public class Contotecnico extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String descrizione;
	private String iban;
	private String intestatario;
	private String intestazione;
	private String opAnnullamento;
	private Timestamp tsAnnullamento;
	private int version;
	/*** Auto Generated Identity Property ***/
	private Long id;	

	public Contotecnico() {
	}

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="contotecnico_pk_gen")	
	@SequenceGenerator(
	    name="contotecnico_pk_gen",
	    sequenceName="CONTOTECNICO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}


	public String getIntestatario() {
		return this.intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}


	public String getIntestazione() {
		return this.intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}


	@Column(name="OP_ANNULLAMENTO")
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}


	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}

	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
	}


}