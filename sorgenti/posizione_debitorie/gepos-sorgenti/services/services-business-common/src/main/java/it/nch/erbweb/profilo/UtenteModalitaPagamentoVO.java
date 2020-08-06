

package it.nch.erbweb.profilo;


import java.io.Serializable;
import java.sql.Timestamp;


public class UtenteModalitaPagamentoVO implements Serializable{

	private String applicationId;
	private String systemId;
	private String codiceFiscale;
	private String opAggiornamento;
	private String opInserimento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private Long idCfgGatewayPagamento;


	
	public UtenteModalitaPagamentoVO(){
		
	}



	public UtenteModalitaPagamentoVO(String applicationId, String systemId,
			String codiceFiscale, String opAggiornamento, String opInserimento,
			Timestamp tsAggiornamento, Timestamp tsInserimento, Long idCfgGatewayPagamento) {
		super();
		this.applicationId = applicationId;
		this.systemId = systemId;
		this.codiceFiscale = codiceFiscale;
		this.opAggiornamento = opAggiornamento;
		this.opInserimento = opInserimento;
		this.tsAggiornamento = tsAggiornamento;
		this.tsInserimento = tsInserimento;
		this.idCfgGatewayPagamento=idCfgGatewayPagamento;
	}



	public UtenteModalitaPagamentoVO(String codFiscale) {
		this.codiceFiscale = codFiscale;
	}



	public String getApplicationId() {
		return applicationId;
	}



	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}



	public String getSystemId() {
		return systemId;
	}



	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}



	public String getCodiceFiscale() {
		return codiceFiscale;
	}



	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}



	public String getOpAggiornamento() {
		return opAggiornamento;
	}



	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}



	public Long getIdCfgGatewayPagamento() {
		return idCfgGatewayPagamento;
	}



	public void setIdCfgGatewayPagamento(Long idCfgGatewayPagamento) {
		this.idCfgGatewayPagamento = idCfgGatewayPagamento;
	}



	public String getOpInserimento() {
		return opInserimento;
	}



	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}



	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}



	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}



	public Timestamp getTsInserimento() {
		return tsInserimento;
	}



	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

   

}
