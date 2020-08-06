package it.nch.pagamenti.nodopagamentispc;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EsitoNodoPagamentiSPCForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	public static final String ESITO_OK = "OK";
	public static final String ESITO_ERRORE = "ERROR";
	public static final String ESITO_DIFFERITO = "DIFFERITO";
		
	private String idDominio;
	private String idSession;
	private String esito;
		

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
//		ActionErrors errors = new ActionErrors();
//		if (!numeroCarta.matches("[0-9]{16}"))
//			errors.add("numeroCarta", new ActionMessage("creditcard.error.numerocarta"));
//		if (!codiceSicurezza.matches("[0-9]{3}"))
//			errors.add("codiceSicurezza", new ActionMessage("creditcard.error.codicesicurezza"));
//		return errors;

		return super.validate(mapping, request);
	}


	public String getIdDominio() {
		return idDominio;
	}


	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}


	public String getIdSession() {
		return idSession;
	}


	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}


	public String getEsito() {
		return esito;
	}


	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	
	
}
