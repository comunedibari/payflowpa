package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the RIVERSAMENTI database table.
 * 
 */
@Entity
@Table(name="RIVERSAMENTI")
public class Riversamento extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private short flagChiusura;
	private String note;
	private String opAggiornamento;
	private String opInserimento;
	private String stato;
	private String iban;
	private String cdTrbEnte;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	
	private IncassiBonificiRh incassiBonificiRh;

	public Riversamento() {
	}
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="riversamento_pk_gen")	
	@SequenceGenerator(
		    name="riversamento_pk_gen",
		    sequenceName="RIVERSAMENTI_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 			

	@Column(name="FLAG_CHIUSURA")
	public short getFlagChiusura() {
		return this.flagChiusura;
	}

	public void setFlagChiusura(short flagChiusura) {
		this.flagChiusura = flagChiusura;
	}


	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_INCASSI_BONIFICI_RH")
	public IncassiBonificiRh getIncassiBonificiRh() {
		return this.incassiBonificiRh;
	}

	public void setIncassiBonificiRh(IncassiBonificiRh incassiBonificiRh) {
		this.incassiBonificiRh = incassiBonificiRh;
	}

	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	@Column(name="IBAN")
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

}