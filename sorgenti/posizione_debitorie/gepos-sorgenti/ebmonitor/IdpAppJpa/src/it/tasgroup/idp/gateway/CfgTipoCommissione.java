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
 * The persistent class for the CFG_TIPO_COMMISSIONE database table.
 * 
 */
@Entity
@Table(name="CFG_TIPO_COMMISSIONE")
@NamedQuery(name="CfgTipoCommissione.findAll", query="SELECT c FROM CfgTipoCommissione c")
public class CfgTipoCommissione extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String bundleKey;
	private String descrizione;
	private String flStato;
	private String stRiga;
	private List<CfgCommissionePagamento> cfgCommissionePagamentos;

	public CfgTipoCommissione() {
	}

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="cfg_tipo_commissione_pk_gen")	
	@SequenceGenerator(
	    name="cfg_tipo_commissione_pk_gen",
	    sequenceName="CFG_TIPO_COMMISSIONE_ID_SEQ",
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


	@Column(name="FL_STATO")
	public String getFlStato() {
		return this.flStato;
	}

	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}


	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	//bi-directional many-to-one association to CfgCommissionePagamento
	@OneToMany(mappedBy="cfgTipoCommissione")
	public List<CfgCommissionePagamento> getCfgCommissionePagamentos() {
		return this.cfgCommissionePagamentos;
	}

	public void setCfgCommissionePagamentos(List<CfgCommissionePagamento> cfgCommissionePagamentos) {
		this.cfgCommissionePagamentos = cfgCommissionePagamentos;
	}

	public CfgCommissionePagamento addCfgCommissionePagamento(CfgCommissionePagamento cfgCommissionePagamento) {
		getCfgCommissionePagamentos().add(cfgCommissionePagamento);
		cfgCommissionePagamento.setCfgTipoCommissione(this);

		return cfgCommissionePagamento;
	}

	public CfgCommissionePagamento removeCfgCommissionePagamento(CfgCommissionePagamento cfgCommissionePagamento) {
		getCfgCommissionePagamentos().remove(cfgCommissionePagamento);
		cfgCommissionePagamento.setCfgTipoCommissione(null);

		return cfgCommissionePagamento;
	}

}