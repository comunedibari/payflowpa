package it.tasgroup.iris.domain;

import it.nch.is.fo.profilo.Intestatari;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the CFG_UTE_MODALITA_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="CFG_UTE_MODALITA_PAGAMENTO")
@NamedQueries(
{
		
@NamedQuery(
		name="getCfgUtenteModalitaPagamentoByKey",
		query="select utenteModalitaPagamento from CfgUtenteModalitaPagamento utenteModalitaPagamento "+
			  "where utenteModalitaPagamento.cfgUtenteModalitaPagamentoId.applicationId=:appID and utenteModalitaPagamento.cfgUtenteModalitaPagamentoId.systemId=:sysID and utenteModalitaPagamento.cfgUtenteModalitaPagamentoId.codiceFiscale=:codFiscale")
		,
@NamedQuery(
		name="listCfgUtenteModalitaPagamentoByCodiceFiscale",
		query="select utenteModalitaPagamento from CfgUtenteModalitaPagamento utenteModalitaPagamento "+
			  "where utenteModalitaPagamento.cfgUtenteModalitaPagamentoId.codiceFiscale=:codFiscale" +
			  " order by utenteModalitaPagamento.gatewayPagamento.cfgModalitaPagamento.descrizione asc  ")
		}


)
public class CfgUtenteModalitaPagamento implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;

	private String opAggiornamento;
	private String opInserimento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	/*** PK Reference ***/
	private CfgUtenteModalitaPagamentoId cfgUtenteModalitaPagamentoId;
	
	private CfgGatewayPagamento gatewayPagamento;

    public CfgUtenteModalitaPagamento() {
    }

    @Id
    public CfgUtenteModalitaPagamentoId getCfgUtenteModalitaPagamentoId() {
		return cfgUtenteModalitaPagamentoId;
	}

	public void setCfgUtenteModalitaPagamentoId(
			CfgUtenteModalitaPagamentoId cfgUtenteModalitaPagamentoId) {
		this.cfgUtenteModalitaPagamentoId = cfgUtenteModalitaPagamentoId;
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

	@OneToOne( fetch=FetchType.LAZY)
    @JoinColumn(name="ID_CFG_GATEWAY_PAGAMENTO")
//    @Transient
	public CfgGatewayPagamento getGatewayPagamento() {
		return gatewayPagamento;
	}

	public void setGatewayPagamento(CfgGatewayPagamento gatewayPagamento) {
		this.gatewayPagamento = gatewayPagamento;
	}

	




	
}