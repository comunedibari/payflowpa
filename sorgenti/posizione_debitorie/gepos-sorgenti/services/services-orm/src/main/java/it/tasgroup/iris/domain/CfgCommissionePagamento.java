package it.tasgroup.iris.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the CFG_COMMISSIONE_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="CFG_COMMISSIONE_PAGAMENTO")
public class CfgCommissionePagamento extends BaseIdEntity {
	private static final long serialVersionUID = 1L;
	private BigDecimal importoA;
	private BigDecimal importoDa;
	private String divisa;
	private BigDecimal valore;
	private String opAggiornamento;
	private String opInserimento;
	private String stRiga;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private String descrizione;
	private CfgGatewayPagamento cfgGatewayPagamento;
	private CfgTipoCommissione cfgTipoCommissione;
	
	private BigDecimal importoCalcolato;

    public CfgCommissionePagamento() {
    }

	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="cfg_commissione_pagamento_pk_gen")	
	@SequenceGenerator(
		    name="cfg_commissione_pagamento_pk_gen",
		    sequenceName="CFG_COMMISSIONE_PAGAMEN_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	        
	public void setId(Long id) {		
		this.id = id;	 
	} 		

	@Column(name="IMPORTO_A", nullable=false)
	public BigDecimal getImportoA() {
		return this.importoA;
	}

	public void setImportoA(BigDecimal importoA) {
		this.importoA = importoA;
	}


	@Column(name="IMPORTO_DA", nullable=false)
	public BigDecimal getImportoDa() {
		return this.importoDa;
	}

	public void setImportoDa(BigDecimal importoDa) {
		this.importoDa = importoDa;
	}


	@Column(name="OP_AGGIORNAMENTO", length=80)
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO", nullable=false)
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="ST_RIGA", nullable=false)
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	//bi-directional many-to-one association to CfgGatewayPagamento
    @ManyToOne
	@JoinColumn(name="ID_CFG_GATEWAY_PAGAMENTO")
	public CfgGatewayPagamento getCfgGatewayPagamento() {
		return this.cfgGatewayPagamento;
	}

	public void setCfgGatewayPagamento(CfgGatewayPagamento cfgGatewayPagamento) {
		this.cfgGatewayPagamento = cfgGatewayPagamento;
	}
	

	//bi-directional many-to-one association to CfgTipoCommissione
    @ManyToOne
	@JoinColumn(name="ID_CFG_TIPO_COMMISSIONE", nullable=false)
	public CfgTipoCommissione getCfgTipoCommissione() {
		return this.cfgTipoCommissione;
	}

	public void setCfgTipoCommissione(CfgTipoCommissione cfgTipoCommissione) {
		this.cfgTipoCommissione = cfgTipoCommissione;
	}
	
	@Column(name="DIVISA")
	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	@Column(nullable=false)
	public BigDecimal getValore() {
		return this.valore;
	}

	public void setValore(BigDecimal valore) {
		this.valore = valore;
	}
	
	@Transient
	public BigDecimal getImportoCalcolato() {
		return importoCalcolato;
	}


	public void setImportoCalcolato(BigDecimal importoCalcolato) {
		this.importoCalcolato = importoCalcolato;
	}

	@Column(name="DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	
}