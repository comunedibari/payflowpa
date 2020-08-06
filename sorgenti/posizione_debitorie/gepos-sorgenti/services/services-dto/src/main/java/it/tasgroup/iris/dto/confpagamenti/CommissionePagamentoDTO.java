/**
 * 
 */
package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author PazziK
 *
 */
public class CommissionePagamentoDTO implements Serializable {
	
	private String commissione;
	private String tipo;
	private BigDecimal valore;
	private String divisa;
	private String flStato;
	private String stRiga;
	
	public String getCommissione() {
		return commissione;
	}
	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getValore() {
		return valore;
	}
	public void setValore(BigDecimal valore) {
		this.valore = valore;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
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
	

}
