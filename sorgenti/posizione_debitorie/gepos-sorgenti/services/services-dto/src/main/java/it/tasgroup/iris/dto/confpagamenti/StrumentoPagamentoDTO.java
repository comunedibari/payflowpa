/**
 * 
 */
package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;


/**
 * @author PazziK
 *
 */
public class StrumentoPagamentoDTO implements Serializable{
	
	private String strPagamento;
	private String deStrPagamento;
	private String flStato;
	private String stRiga;
	private Integer prVersione;
	private String tipoStrumento;
	
	public String getStrPagamento() {
		return strPagamento;
	}
	public void setStrPagamento(String strPagamento) {
		this.strPagamento = strPagamento;
	}
	public String getDeStrPagamento() {
		return deStrPagamento;
	}
	public void setDeStrPagamento(String deStrPagamento) {
		this.deStrPagamento = deStrPagamento;
	}
	public String getFlStato() {
		return flStato;
	}
	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}
	public String getStRiga() {
		return stRiga;
	}
	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	public Integer getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(Integer prVersione) {
		this.prVersione = prVersione;
	}
	public String getTipoStrumento() {
		return tipoStrumento;
	}
	public void setTipoStrumento(String tipoStrumento) {
		this.tipoStrumento = tipoStrumento;
	}

}
