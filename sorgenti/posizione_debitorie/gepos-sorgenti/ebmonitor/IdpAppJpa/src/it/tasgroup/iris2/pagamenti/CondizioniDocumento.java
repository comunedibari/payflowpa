package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the CONDIZIONI_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="CONDIZIONI_DOCUMENTO")
public class CondizioniDocumento extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String idCondizione;
	private String opAnnullamento;
	private Timestamp tsAnnullamento;

	/*** Persistent Associations ***/
	private DocumentiPagamento documentiPagamento;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="condizioni_documento_pk_gen")	
	@SequenceGenerator(
	    name="condizioni_documento_pk_gen",
	    sequenceName="CONDIZIONI_DOCUMENTO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	
	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="ID_CONDIZIONE")
	public String getIdCondizione() {
		return this.idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
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


	//bi-directional many-to-one association to DocumentiPagamento
    @ManyToOne
	@JoinColumn(name="ID_DOCUMENTO")
	public DocumentiPagamento getDocumentiPagamento() {
		return this.documentiPagamento;
	}

	public void setDocumentiPagamento(DocumentiPagamento documentiPagamento) {
		this.documentiPagamento = documentiPagamento;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CondizioniDocumento [idCondizione=");
		builder.append(idCondizione);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		//builder.append(", documentiPagamento=");
		//builder.append(documentiPagamento);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append("]");
		return builder.toString();
	}

	
}