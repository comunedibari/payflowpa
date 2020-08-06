/**
 * 
 */
package it.tasgroup.backoffice.ente.form;

import org.apache.struts.action.ActionForm;

/**
 * @author pazzik
 *
 */
public class SmartProxyForm extends ActionForm{
	
	private String idTrasmissione;
	
	private String esitoTrasmissione;
	
	private String esitoBreve;

	public String getIdTrasmissione() {
		return idTrasmissione;
	}

	public void setIdTrasmissione(String idTrasmissione) {
		this.idTrasmissione = idTrasmissione;
	}

	public String getEsitoTrasmissione() {
		return esitoTrasmissione;
	}

	public void setEsitoTrasmissione(String esitoTrasmissione) {
		this.esitoTrasmissione = esitoTrasmissione;
	}

	public String getEsitoBreve() {
		return esitoBreve;
	}

	public void setEsitoBreve(String esitoBreve) {
		this.esitoBreve = esitoBreve;
	}
}
