package it.nch.pagamenti.nodopagamentispc;

import java.io.Serializable;



public class DatiSingolaRevocaVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String identificativoUnivocoRiscossione;   
    private String causaleRevoca;    
    private String datiAggiuntiviRevoca;
    
	public String getIdentificativoUnivocoRiscossione() {
		return identificativoUnivocoRiscossione;
	}
	public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
		this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
	}
	public String getCausaleRevoca() {
		return causaleRevoca;
	}
	public void setCausaleRevoca(String causaleRevoca) {
		this.causaleRevoca = causaleRevoca;
	}
	public String getDatiAggiuntiviRevoca() {
		return datiAggiuntiviRevoca;
	}
	public void setDatiAggiuntiviRevoca(String datiAggiuntiviRevoca) {
		this.datiAggiuntiviRevoca = datiAggiuntiviRevoca;
	}
    
    
    
}
