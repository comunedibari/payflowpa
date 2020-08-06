package it.nch.pagamenti.creditcard;

import java.io.Serializable;

/**
 * Contenitore di colloquio con il gateway dei pagamenti con carta di credito
 * 
 * @author TamboriniP
 *
 */
public class PagamentoCartaCreditoGatewayVO implements Serializable {
	/* Per ora non usati. Passati nella prima chiamata al gateway dei pagamenti con credit card (iniziotx.php)*/
	private String urlGateway;
	private String linguaCliente;
	private String divisa;
	private String tipoPagamento;
	private String formaPagamento;
	private String idNegozio;
	
	/*Passati nella seconda chiamata per l'aggancio alla pagina della banca.
	 * I parametri vengono restituiti dalla prima chiamata al gateway*/
	private String urlCreditCardBanca;
	private String firma;
	private String timeOut;
	
	
	
	public String getUrlGateway() {
		return urlGateway;
	}
	public void setUrlGateway(String urlGateway) {
		this.urlGateway = urlGateway;
	}
	public String getLinguaCliente() {
		return linguaCliente;
	}
	public void setLinguaCliente(String linguaCliente) {
		this.linguaCliente = linguaCliente;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public String getIdNegozio() {
		return idNegozio;
	}
	public void setIdNegozio(String idNegozio) {
		this.idNegozio = idNegozio;
	}
	public String getUrlCreditCardBanca() {
		return urlCreditCardBanca;
	}
	public void setUrlCreditCardBanca(String urlCreditCardBanca) {
		this.urlCreditCardBanca = urlCreditCardBanca;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	
	
}
