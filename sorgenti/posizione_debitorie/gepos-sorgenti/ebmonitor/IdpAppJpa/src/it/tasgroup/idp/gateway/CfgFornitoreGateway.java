package it.tasgroup.idp.gateway;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CFG_FORNITORE_GATEWAY database table.
 * 
 */
@Entity
@Table(name="CFG_FORNITORE_GATEWAY")
@NamedQuery(name="CfgFornitoreGateway.findAll", query="SELECT c FROM CfgFornitoreGateway c")
public class CfgFornitoreGateway extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String bundleKey;
	private String descrizione;
	private String stRiga;
	private List<CfgGatewayPagamento> cfgGatewayPagamentos;

	public CfgFornitoreGateway() {
	}

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="cfg_fornitore_pagamento_pk_gen")	
	@SequenceGenerator(
	    name="cfg_fornitore_pagamento_pk_gen",
	    sequenceName="CFG_FORNITORE_PAGAMENTO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		

	@Column(name="BUNDLE_KEY")
	public String getBundleKey() {
		return this.bundleKey;
	}

	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}


	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	//bi-directional many-to-one association to CfgGatewayPagamento
	@OneToMany(mappedBy="cfgFornitoreGateway")
	public List<CfgGatewayPagamento> getCfgGatewayPagamentos() {
		return this.cfgGatewayPagamentos;
	}

	public void setCfgGatewayPagamentos(List<CfgGatewayPagamento> cfgGatewayPagamentos) {
		this.cfgGatewayPagamentos = cfgGatewayPagamentos;
	}

	public CfgGatewayPagamento addCfgGatewayPagamento(CfgGatewayPagamento cfgGatewayPagamento) {
		getCfgGatewayPagamentos().add(cfgGatewayPagamento);
		cfgGatewayPagamento.setCfgFornitoreGateway(this);

		return cfgGatewayPagamento;
	}

	public CfgGatewayPagamento removeCfgGatewayPagamento(CfgGatewayPagamento cfgGatewayPagamento) {
		getCfgGatewayPagamentos().remove(cfgGatewayPagamento);
		cfgGatewayPagamento.setCfgFornitoreGateway(null);

		return cfgGatewayPagamento;
	}

}