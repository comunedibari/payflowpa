package it.tasgroup.iris.domain;

import it.nch.is.fo.tributi.TributoEnte;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CONFIG_NOTIFICHE database table.
 * 
 */
@Entity
@Table(name="CFG_NOTIFICA_PAGAMENTO")
public class CfgNotificaPagamento extends BaseIdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String consegnaNotifica;
	private String formatoNotifica;
	private String freqNotifica;
	private String opAggiornamento;
	private String opInserimento;
	private String tipoNotifica;
	private Date tsAggiornamento;
	private Date tsInserimento;
	private Long id;
	
	private TributoEnte tributoEnte;

	public CfgNotificaPagamento() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="cfg_notifica_pagamento_pk_gen")	
	@SequenceGenerator(
		    name="cfg_notifica_pagamento_pk_gen",
		    sequenceName="CFG_NOTIFICA_PAGAMENTO_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	        
	public void setId(Long id) {		
		this.id = id;	 
	}

	@Column(name="CONSEGNA_NOTIFICA")
	public String getConsegnaNotifica() {
		return this.consegnaNotifica;
	}

	public void setConsegnaNotifica(String consegnaNotifica) {
		this.consegnaNotifica = consegnaNotifica;
	}


	@Column(name="FORMATO_NOTIFICA")
	public String getFormatoNotifica() {
		return this.formatoNotifica;
	}

	public void setFormatoNotifica(String formatoNotifica) {
		this.formatoNotifica = formatoNotifica;
	}


	@Column(name="FREQ_NOTIFICA")
	public String getFreqNotifica() {
		return this.freqNotifica;
	}

	public void setFreqNotifica(String freqNotifica) {
		this.freqNotifica = freqNotifica;
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

	@Column(name="TIPO_NOTIFICA")
	public String getTipoNotifica() {
		return this.tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Date getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Date tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Date getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Date tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	
	//bi-directional many-to-one association to TributoEnte
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CD_TRB_ENTE", referencedColumnName="CD_TRB_ENTE"),
		@JoinColumn(name="ID_ENTE", referencedColumnName="ID_ENTE")
		})
	public TributoEnte getTributoEnte() {
		return this.tributoEnte;
	}

	public void setTributoEnte(TributoEnte tributoEnte) {
		this.tributoEnte = tributoEnte;
	}

}