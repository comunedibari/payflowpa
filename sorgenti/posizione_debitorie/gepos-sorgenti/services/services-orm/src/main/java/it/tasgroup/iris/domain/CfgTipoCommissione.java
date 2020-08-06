package it.tasgroup.iris.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CFG_TIPO_COMMISSIONE database table.
 * 
 */
@Entity
@Table(name="CFG_TIPO_COMMISSIONE")
public class CfgTipoCommissione extends BaseIdEntity {
	private static final long serialVersionUID = 1L;
	private String bundleKey;
	private String descrizione;
	private String flStato;
	private String opAggiornamento;
	private String opInserimento;
	private String stRiga;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
//	private Set<CfgCommissionePagamento> cfgCommissionePagamenti;

    public CfgTipoCommissione() {
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="cfg_tipocommissione_pagamento_pk_gen")	
	@SequenceGenerator(
		    name="cfg_tipocommissione_pagamento_pk_gen",
		    sequenceName="CFG_TIPO_COMMISSIONE_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		


	@Column(name="BUNDLE_KEY", nullable=false)
	public String getBundleKey() {
		return this.bundleKey;
	}

	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}


	@Column(nullable=false)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}




	@Column(name="FL_STATO", nullable=false)
	public String getFlStato() {
		return this.flStato;
	}

	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}


	@Column(name="OP_AGGIORNAMENTO")
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


	//bi-directional many-to-one association to CfgCommissionePagamento
//	@OneToMany(mappedBy="cfgTipoCommissione")
//	public Set<CfgCommissionePagamento> getCfgCommissionePagamenti() {
//		return this.cfgCommissionePagamenti;
//	}
//
//	public void setCfgCommissionePagamenti(Set<CfgCommissionePagamento> cfgCommissionePagamenti) {
//		this.cfgCommissionePagamenti = cfgCommissionePagamenti;
//	}
	
}