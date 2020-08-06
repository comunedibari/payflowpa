/**
 * 
 */
package it.tasgroup.iris.dto.confpagamenti;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author PazziK
 *
 */
public class ConfPagamentoDTO implements Serializable{
	
	private String confPagamento;
	private BigDecimal maxImporto;
	private String flStato;
	private String gateway;
	private String commissione;
	private String strPagamento;
	private String circuito;
	private String stRiga;
	private String tipoCommissione;
	private String descrTipoPagamento;
	private String importoTotaleCommissione;
	private String commissioneSingolaDescr;
	
	private StrumentoPagamentoDTO strumentoPagamento;
	
	private CommissionePagamentoDTO commissionePagamento;
	
	private List<ScaglioneCommissioniDTO> scaglioniCommissioni;
	
	private boolean enabled = true;
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getConfPagamento() {
		return confPagamento;
	}
	public void setConfPagamento(String confPagamento) {
		this.confPagamento = confPagamento;
	}
	public BigDecimal getMaxImporto() {
		return maxImporto;
	}
	public void setMaxImporto(BigDecimal maxImporto) {
		this.maxImporto = maxImporto;
	}
	public String getFlStato() {
		return flStato;
	}
	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getCommissione() {
		return commissione;
	}
	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}
	public String getStrPagamento() {
		return strPagamento;
	}
	public void setStrPagamento(String strPagamento) {
		this.strPagamento = strPagamento;
	}
	public String getCircuito() {
		return circuito;
	}
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	public String getStRiga() {
		return stRiga;
	}
	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	public String getTipoCommissione() {
		return tipoCommissione;
	}
	public void setTipoCommissione(String tipoCommissione) {
		this.tipoCommissione = tipoCommissione;
	}
	public String getDescrTipoPagamento() {
		return descrTipoPagamento;
	}
	public void setDescrTipoPagamento(String descrTipoPagamento) {
		this.descrTipoPagamento = descrTipoPagamento;
	}
	
	public List getScaglioniCommissioni() {
		return scaglioniCommissioni;
	}
	public void setScaglioniCommissioni(List<ScaglioneCommissioniDTO> scaglionicommissioni) {
		this.scaglioniCommissioni = scaglionicommissioni;
	}
	
	public boolean addScaglioneCommissioni(ScaglioneCommissioniDTO scaglione){
		
		return this.scaglioniCommissioni.add(scaglione);
		
	}
	public StrumentoPagamentoDTO getStrumentoPagamento() {
		return strumentoPagamento;
	}
	public void setStrumentoPagamento(StrumentoPagamentoDTO strumentoPagamento) {
		this.strumentoPagamento = strumentoPagamento;
	}
	public CommissionePagamentoDTO getCommissionePagamento() {
		return commissionePagamento;
	}
	public void setCommissionePagamento(CommissionePagamentoDTO commissionePagamento) {
		this.commissionePagamento = commissionePagamento;
	}
	
	public void setCommissioneSingolaDescr(String commissioneSingolaDescr) {
		this.commissioneSingolaDescr = commissioneSingolaDescr;
		
	}
	public void setImportoTotaleCommissione(String importoTotaleCommissione) {
		this.importoTotaleCommissione = importoTotaleCommissione;
		
	}
	

}
