package it.tasgroup.iris.domain;

import it.nch.is.fo.tributi.TributoEnte;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CFG_TRIBUTIENTE_GATEWAY database table.
 * 
 */
@Entity
@Table(name="CFG_TRIBUTIENTE_GATEWAY")
@NamedQueries(
{
@NamedQuery(
		name="getCfgGatewayTributiEnte",
		query="select cf.cfgGatewayPagamento from CfgTributiEntiGateway cf where cf.tributoEnte.idEnte =:idEnte and cf.tributoEnte.cdTrbEnte =:cdTrbEnte  and cf.cfgGatewayPagamento is not null )"
),
@NamedQuery(
		name="getIdCfgGatewayTributiEnte",
		query="select cf.cfgGatewayPagamento.id from CfgTributiEntiGateway cf where cf.tributoEnte.idEnte =:idEnte and cf.tributoEnte.cdTrbEnte =:cdTrbEnte and "
				+ "(cf.modelloVersamento is null  or cf.modelloVersamento=:modelloVersamento) and (cf.tipoVersamento is null or cf.tipoVersamento=:tipoVersamento)  and cf.cfgGatewayPagamento is not null )"
),
@NamedQuery(
		name="getModelloETipoVersamentoCfgGatewayTributiEnte",
		query="select cf from CfgTributiEntiGateway cf where cf.tributoEnte.idEnte =:idEnte and cf.tributoEnte.cdTrbEnte =:cdTrbEnte  and cf.cfgGatewayPagamento.id is null )"
)
})
public class CfgTributiEntiGateway extends BaseIdEntity {
	
	private static final long serialVersionUID = 1L;
	
	
	private Long id;	
	private TributoEnte tributoEnte;
	private CfgFornitoreGateway cfgFornitoreGateway;
	private CfgGatewayPagamento cfgGatewayPagamento;	
	private String opAggiornamento;
	private String opInserimento;
	private String tipoNotifica;
	private Date tsAggiornamento;
	private Date tsInserimento;
	
	private String modelloVersamento;
	private String tipoVersamento;

	public CfgTributiEntiGateway() {
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

	/*
	@Column(name="TIPO_NOTIFICA")
	public String getTipoNotifica() {
		return this.tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}*/


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
	
	@Column(name="MODELLO_VERSAMENTO")
	public String getModelloVersamento() {
		return modelloVersamento;
	}

	public void setModelloVersamento(String modelloVersamento) {
		this.modelloVersamento = modelloVersamento;
	}

	@Column(name="TIPO_VERSAMENTO")
	public String getTipoVersamento() {
		return tipoVersamento;
	}

	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
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
    
	//bi-directional many-to-one association to TributoEnte
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name="ID_CFG_FORNITORE_GATEWAY", referencedColumnName="ID")
	})
	public CfgFornitoreGateway getCfgFornitoreGateway() {
		return cfgFornitoreGateway;
	}

	public void setCfgFornitoreGateway(CfgFornitoreGateway cfgFornitoreGateway) {
		this.cfgFornitoreGateway = cfgFornitoreGateway;
	}

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name="ID_CFG_GATEWAY_PAGAMENTO", referencedColumnName="ID")
	})
	public CfgGatewayPagamento getCfgGatewayPagamento() {
		return cfgGatewayPagamento;
	}

	public void setCfgGatewayPagamento(CfgGatewayPagamento cfgGatewayPagamento) {
		this.cfgGatewayPagamento = cfgGatewayPagamento;
	}

	
}