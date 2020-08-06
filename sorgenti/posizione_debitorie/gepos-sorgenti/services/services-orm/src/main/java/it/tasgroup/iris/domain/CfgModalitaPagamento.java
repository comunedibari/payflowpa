package it.tasgroup.iris.domain;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the CFG_MODALITA_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="CFG_MODALITA_PAGAMENTO")
@NamedQueries(
{
@NamedQuery(
	name="listAllActiveCfgModalitaOld",
	query="select cmp from CfgModalitaPagamento cmp "+
		  "join cmp.cfgGatewayPagamenti cgp " +
		  "where cmp.stRiga = 'V' " +
		  "and cgp.stato = :stato " +
		  "and cgp.dataInizioValidita <= :dtInizioValidita " +
		  "and cgp.dataFineValidita >= :dtFineValidita "),

@NamedQuery(
		name="listAllActiveCfgModalita",
		query="select new CfgModalitaPagamento(cgp.id, cmp.descrizione, cgp.systemName)  from CfgModalitaPagamento cmp "+
			  "join cmp.cfgGatewayPagamenti cgp " +
			  "where cmp.stRiga = 'V' " +
			  "and cgp.stato = :stato " +
			  "and cgp.dataInizioValidita <= :dtInizioValidita " +
			  "and cgp.dataFineValidita >= :dtFineValidita "),

@NamedQuery(
		name="listAllCfgModalita",
		query="select new CfgModalitaPagamento(cgp.id, cmp.descrizione, cgp.systemName)  from CfgModalitaPagamento cmp "+
			  "join cmp.cfgGatewayPagamenti cgp " +
			  "where cmp.stRiga = 'V' and cgp.systemName is not null  order by cgp.cfgFornitoreGateway.id, cgp.systemName, cgp.descGateway desc")
}
)
public class CfgModalitaPagamento extends BaseIdEntity {
	private static final long serialVersionUID = 1L;
	private String bundleKey;
	private String descrizione;
	private String opAggiornamento;
	private String opInserimento;
	private String stRiga;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private Set<CfgGatewayPagamento> cfgGatewayPagamenti;
	private int paymentMethod;
	private Boolean enabled;

    public CfgModalitaPagamento() {
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="cfg_modalita_pagamento_pk_gen")	
	@SequenceGenerator(
		    name="cfg_modalita_pagamento_pk_gen",
		    sequenceName="CFG_MODALITA_PAGAMENTO_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	          
	public void setId(Long id) {		
		this.id = id;	 
	} 	
	
	public CfgModalitaPagamento(Long id, String descrizione) {
    	super();
    	setId(id);
    	setDescrizione(descrizione);
    }

    public CfgModalitaPagamento(Long id, String descrizione, String systemName) {
    	super();
    	setId(id);
    	if (systemName!=null && !systemName.equals("")) {
    		setDescrizione(descrizione.toLowerCase() + " - " + systemName);
    	}
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


	//bi-directional many-to-one association to CfgGatewayPagamento
	@OneToMany(mappedBy="cfgModalitaPagamento")
	public Set<CfgGatewayPagamento> getCfgGatewayPagamenti() {
		return this.cfgGatewayPagamenti;
	}

	public void setCfgGatewayPagamenti(Set<CfgGatewayPagamento> cfgGatewayPagamenti) {
		this.cfgGatewayPagamenti = cfgGatewayPagamenti;
	}

	
	@Column(name="PAYMENT_METHOD")
	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Transient
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}