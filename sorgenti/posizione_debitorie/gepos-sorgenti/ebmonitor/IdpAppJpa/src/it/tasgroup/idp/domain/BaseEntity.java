package it.tasgroup.idp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Entity implementation class for Entity: BaseEntity
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {	
	
	/*** Common Auditing Properties ***/
	private String opInserimento;
	private String opAggiornamento;
	private Timestamp tsInserimento;
	private Timestamp tsAggiornamento;

	private boolean opAggiornamentoAssigned = false;
	private boolean tsAggiornamentoAssigned = false;

	private static final long serialVersionUID = 1L;

	
	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
		this.opAggiornamentoAssigned = true;
	}	

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	
	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
		this.tsAggiornamentoAssigned = true;
	}
	
	@PrePersist()
	public void completeForInsert() {
		if (this.tsInserimento==null)
			this.tsInserimento = new Timestamp(System.currentTimeMillis());
		if (this.opInserimento==null)
			this.opInserimento = "IDP";
	}

	@PreUpdate
	public void completeForUpdate() {
		if (this.tsAggiornamentoAssigned == false && this.tsAggiornamento == null)
			this.tsAggiornamento = new Timestamp(System.currentTimeMillis());
		if (this.opAggiornamentoAssigned == false && this.opAggiornamento==null)
			this.opAggiornamento = "IDP";
	}
	
}
