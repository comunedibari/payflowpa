package it.tasgroup.idp.gateway;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CFG_COMMISSIONE_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="CFG_COMMISSIONE_PAGAMENTO")
@NamedQuery(name="CfgCommissionePagamento.findAll", query="SELECT c FROM CfgCommissionePagamento c")
public class CfgCommissionePagamento extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String descrizione;
	private String divisa;
	private BigDecimal importoA;
	private BigDecimal importoDa;
	private String stRiga;
	private BigDecimal valore;
	private CfgGatewayPagamento cfgGatewayPagamento;
	private CfgTipoCommissione cfgTipoCommissione;

	public CfgCommissionePagamento() {
	}

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="cfg_commissione_pagamento_pk_gen")	
	@SequenceGenerator(
	    name="cfg_commissione_pagamento_pk_gen",
	    sequenceName="CFG_COMMISSIONE_PAGAMEN_ID_SEQ",
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


	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}


	@Column(name="IMPORTO_A")
	public BigDecimal getImportoA() {
		return this.importoA;
	}

	public void setImportoA(BigDecimal importoA) {
		this.importoA = importoA;
	}


	@Column(name="IMPORTO_DA")
	public BigDecimal getImportoDa() {
		return this.importoDa;
	}

	public void setImportoDa(BigDecimal importoDa) {
		this.importoDa = importoDa;
	}


	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	public BigDecimal getValore() {
		return this.valore;
	}

	public void setValore(BigDecimal valore) {
		this.valore = valore;
	}





	//bi-directional many-to-one association to CfgGatewayPagamento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_GATEWAY_PAGAMENTO")
	public CfgGatewayPagamento getCfgGatewayPagamento() {
		return this.cfgGatewayPagamento;
	}

	public void setCfgGatewayPagamento(CfgGatewayPagamento cfgGatewayPagamento) {
		this.cfgGatewayPagamento = cfgGatewayPagamento;
	}


	//bi-directional many-to-one association to CfgTipoCommissione
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_TIPO_COMMISSIONE")
	public CfgTipoCommissione getCfgTipoCommissione() {
		return this.cfgTipoCommissione;
	}

	public void setCfgTipoCommissione(CfgTipoCommissione cfgTipoCommissione) {
		this.cfgTipoCommissione = cfgTipoCommissione;
	}

}