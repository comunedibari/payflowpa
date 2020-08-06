package it.nch.pagamenti.creditcard;


import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/**
 * Form che contiene i dati da e per la pagina di dettaglio della carta di credito.
 * Contiene anche i relativi metodi di validazione.
 *
 * @author VergolaniS
 *
 */
public class CreditcardContainerForm extends ActionForm
{
	private String tipoCarta;
	private String numeroCarta;
	private String codiceSicurezza;
	private String meseScadenza;
	private String annoScadenza;

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if (!numeroCarta.matches("[0-9]{16}")) errors.add("numeroCarta", new ActionMessage("creditcard.error.numerocarta"));
		if (!codiceSicurezza.matches("[0-9]{3}")) errors.add("codiceSicurezza", new ActionMessage("creditcard.error.codicesicurezza"));
		Calendar oggi = Calendar.getInstance();
		Calendar scadenza = Calendar.getInstance();
		scadenza.set(Integer.parseInt(annoScadenza), Integer.parseInt(meseScadenza), 1);
		scadenza.add(Calendar.MONTH, 1);
		scadenza.add(Calendar.DATE, -1);
		if (oggi.after(scadenza)) errors.add("meseScadenza", new ActionMessage("creditcard.error.datascadenza"));

		return errors;
	}

	/**
	 * @return
	 */
	public String getAnnoScadenza() {
		return annoScadenza;
	}

	/**
	 * @return
	 */
	public String getCodiceSicurezza() {
		return codiceSicurezza;
	}

	/**
	 * @return
	 */
	public String getMeseScadenza() {
		return meseScadenza;
	}

	/**
	 * @return
	 */
	public String getNumeroCarta() {
		return numeroCarta;
	}

	/**
	 * @return
	 */
	public String getTipoCarta() {
		return tipoCarta;
	}

	/**
	 * @param string
	 */
	public void setAnnoScadenza(String string) {
		annoScadenza = string;
	}

	/**
	 * @param string
	 */
	public void setCodiceSicurezza(String string) {
		codiceSicurezza = string;
	}

	/**
	 * @param string
	 */
	public void setMeseScadenza(String string) {
		meseScadenza = string;
	}

	/**
	 * @param string
	 */
	public void setNumeroCarta(String string) {
		numeroCarta = string;
	}

	/**
	 * @param string
	 */
	public void setTipoCarta(String string) {
		tipoCarta = string;
	}

}
