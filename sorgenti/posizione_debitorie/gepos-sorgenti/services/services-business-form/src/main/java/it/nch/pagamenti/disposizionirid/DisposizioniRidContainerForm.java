package it.nch.pagamenti.disposizionirid;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * Form che contiene i dati da e per la pagina di dettaglio della carta di credito.
 * Contiene anche i relativi metodi di validazione.
 *
 * @author VergolaniS
 *
 */
public class DisposizioniRidContainerForm extends ActionForm{

	private String dataScadenzaStr;
	private Date dataScadenza;
	private String[] selectedIds;
	public String[] getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		
		

//		if (!numeroCarta.matches("[0-9]{16}")) errors.add("numeroCarta", new ActionMessage("creditcard.error.numerocarta"));
//		if (!codiceSicurezza.matches("[0-9]{3}")) errors.add("codiceSicurezza", new ActionMessage("creditcard.error.codicesicurezza"));
//		Calendar oggi = Calendar.getInstance();
//		Calendar scadenza = Calendar.getInstance();
//		scadenza.set(Integer.parseInt(annoScadenza), Integer.parseInt(meseScadenza), 1);
//		scadenza.add(Calendar.MONTH, 1);
//		scadenza.add(Calendar.DATE, -1);
//		if (oggi.after(scadenza)) errors.add("meseScadenza", new ActionMessage("creditcard.error.datascadenza"));

		return errors;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getDataScadenzaStr() {
		return dataScadenzaStr;
	}
	public void setDataScadenzaStr(String dataScadenzaStr) {
		this.dataScadenzaStr = dataScadenzaStr;
		
		try {
			DateFormat formatter ;
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			this.dataScadenza = (Date)formatter.parse(dataScadenzaStr);
		} catch (ParseException e){
			System.out.println("Exception :"+e);
		}  
		
	}

}
