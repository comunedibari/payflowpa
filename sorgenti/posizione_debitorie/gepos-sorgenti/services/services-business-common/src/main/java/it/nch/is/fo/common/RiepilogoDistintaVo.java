package it.nch.is.fo.common;

/*
 * Oggetto che contiene tutti i dati di riepilogo di una distinta
 * Rossi - 30/10/2008 
 */

public class RiepilogoDistintaVo {
	
	private String  distinta;
	private String  divisa;
	private Integer numeroDisposizioni;
	private Double  totaleImporti;
	private Integer numeroDisposizioniErrate;
	
	private Integer numeroDisposizioniSenzaIBAN;
	
	/**
	 * Tipo di riepilogo generico.<br>
	 * Permette di caricare:<br>
	 * Distinta<br>
	 * Divisa<br>
	 * Numero Disposizioni<br>
	 * Numero Disposizioni Errate
	 */
	public static final int GENERICO = 0;
	
	/**
	 * Tipo di riepilogo con importi<br>
	 * Permette di caricare tutti i dati del GENERICO più:<br>
	 * Totale Importi
	 */
	public static final int IMPORTI = 1;
	
	/**
	 * Tipo di riepilogo con assenza iban<br>
	 * Permette di caricare tutti i dati del GENERICO, degli IMPORTI in più:<br>
	 * Totale Disposizioni Senza IBAN
	 */
	public static final int IBAN = 2;
	
	public String getDistinta() {
		return distinta;
	}

	public void setDistinta(String distinta) {
		this.distinta = distinta;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	public Integer getNumeroDisposizioni() {
		return numeroDisposizioni;
	}

	public void setNumeroDisposizioni(Integer numeroDisposizioni) {
		this.numeroDisposizioni = numeroDisposizioni;
	}

	public Double getTotaleImporti() {
		return totaleImporti;
	}

	public void setTotaleImporti(Double totaleImporti) {
		this.totaleImporti = totaleImporti;
	}

	public Integer getNumeroDisposizioniErrate() {
		return numeroDisposizioniErrate;
	}

	public void setNumeroDisposizioniErrate(Integer numeroDisposizioniErrate) {
		this.numeroDisposizioniErrate = numeroDisposizioniErrate;
	}

	public Integer getNumeroDisposizioniSenzaIBAN() {
		return numeroDisposizioniSenzaIBAN;
	}

	public void setNumeroDisposizioniSenzaIBAN(Integer numeroDisposizioniSenzaIBAN) {
		this.numeroDisposizioniSenzaIBAN = numeroDisposizioniSenzaIBAN;
	}
	
}
