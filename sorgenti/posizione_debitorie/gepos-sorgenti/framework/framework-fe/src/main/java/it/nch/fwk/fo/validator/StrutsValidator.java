/*
 * Creato il 17-feb-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.validator;

import it.nch.fwk.fo.common.constants.CommonConstants;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.util.WebUtil;
import it.nch.fwk.fo.web.util.text.CodiceFiscale;
import it.nch.fwk.fo.web.util.text.PartitaIva;
import it.nch.fwk.fo.web.util.text.UtilityDate;
import it.nch.fwk.fo.web.util.text.UtilityNumberFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.Resources;



/**
 * @author EE10800
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class StrutsValidator implements Serializable
{

	public static boolean validateDateBeforeToday(
		    Object bean,
		    ValidatorAction va, 
		    Field field,
		    ActionMessages errors,
		    HttpServletRequest request) {
		
			Tracer.info("---------------->StrutsValidator","validateTwoFields","validatooooor",null);
			Date inputDate=null;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date dateToday=cal.getTime();
		    String stringDate = ValidatorUtils.getValueAsString(bean, field.getProperty());
		    if ((!GenericValidator.isBlankOrNull(stringDate))&&(!"N.d.".equalsIgnoreCase(stringDate)))
		    	inputDate=UtilityDate.getBackEndDate(stringDate,inputDate,"/");
		    {
		    	Tracer.debug("---------------->StrutsValidator","validateDateBeforeToday","inputDate " + inputDate,null);
		    	Tracer.debug("---------------->StrutsValidator","validateDateBeforeToday","dateToday " + dateToday,null);
		    	Tracer.debug("---------------->StrutsValidator","validateDateBeforeToday","!dateToday.before(inputDate)" + !dateToday.before(inputDate),null);
		    	Tracer.debug("---------------->StrutsValidator","validateDateBeforeToday","inputDate.before(dateToday)" + inputDate.before(dateToday),null);
		       		 if(inputDate != null && inputDate.before(dateToday))
		       		 {
		 		    	Tracer.debug("---------------->StrutsValidator","validateDateBeforeToday","esito falso" ,null);
		       		 	errors.add(field.getKey(), Resources.getActionMessage(request,va,field));
		       		 	return false;
		       		 }
		    }

		    	Tracer.debug("---------------->StrutsValidator","validateDateBeforeToday","esito vero" ,null);
		    return true;
		}
	
	public static boolean compareDates(
		    Object bean,
		    ValidatorAction va, 
		    Field field,
		    ActionMessages errors,
		    HttpServletRequest request) {
		
			Tracer.debug("---------------->StrutsValidator","validateTwoFields","validatooooor",null);
			Date startDate=null;
			Date endDate=null;
		    String stringDate = ValidatorUtils.getValueAsString(bean, field.getProperty());
		    Tracer.debug("---------------->StrutsValidator","compareDates","data fine inserita: " + stringDate,null);
		    if (!GenericValidator.isBlankOrNull(stringDate)) 
		    {
		    	endDate=UtilityDate.getBackEndDate(stringDate,endDate,"/");
		       		 
		    }
		    
		    stringDate = ValidatorUtils.getValueAsString(bean, field.getVarValue("dataInizio"));
		    Tracer.debug("---------------->StrutsValidator","compareDates","data inizio inserita: " + stringDate,null);
		    
		    if (!GenericValidator.isBlankOrNull(stringDate)) 
		    {
		    	startDate=UtilityDate.getBackEndDate(stringDate,startDate,"/");
		       		 
		    }
		    
		    if(endDate != null && endDate.before(startDate))
      		 {
      		 	errors.add(field.getKey(), Resources.getActionMessage(request,va,field));
      		 	return false;
      		 }
		    return true;
		}
	
	
	public static boolean dataValuta(
		    Object bean,
		    ValidatorAction va, 
		    Field field,
		    ActionMessages errors,
		    HttpServletRequest request) {
		
			Calendar dataEsecuzione=null;
			Date dataValuta=null;
		    String stringDataValuta = ValidatorUtils.getValueAsString(bean, field.getProperty());
		    String stringDataEsecuzione = ValidatorUtils.getValueAsString(bean, field.getVarValue("dataEsecuzuone"));
		    String idRapporto = ValidatorUtils.getValueAsString(bean, field.getVarValue("idRapporto"));
		    String abiAccredito = ValidatorUtils.getValueAsString(bean, field.getVarValue("abiAccredito"));
		    ArrayList rapporti = (ArrayList)request.getSession().getAttribute(CommonConstants.RAPPORTI);
		    String value = null;
		    Iterator it = rapporti.iterator();
		    while (it.hasNext() && value == null)
		    {
		    	LabelValueBean rapporto = (LabelValueBean) it.next();
		    	if (idRapporto.equals(rapporto.getValue()))
		    		value = rapporto.getLabel();
		    }
		    String abiAddebito = value.substring(0,5);
		    List listaAbiBancaAttiva = (List) request.getSession().getAttribute("abiAccentratori");
		    if (!(GenericValidator.isBlankOrNull(stringDataValuta) 
	    			|| GenericValidator.isBlankOrNull(stringDataEsecuzione))) 
		    {
			    dataValuta=UtilityDate.getBackEndDate(stringDataValuta,dataValuta,"/");
		    	dataEsecuzione=UtilityDate.getCalendar(stringDataEsecuzione,"/");
		    	if (listaAbiBancaAttiva.contains(abiAddebito) && listaAbiBancaAttiva.contains(abiAccredito))
	    		{
			    	if(dataValuta != null && (
			    			dataValuta.before(UtilityDate.getRelativeCalendar(dataEsecuzione,180, false).getTime())
			    			|| (dataValuta.after(UtilityDate.getRelativeCalendar(dataEsecuzione,365, true).getTime()))))
		    		{
		    			errors.add(field.getKey(), Resources.getActionMessage(request,va,field));
		    			return false;
		    		}
	    		}
			    else if ((dataValuta.before(UtilityDate.getRelativeCalendar(dataEsecuzione,180, false).getTime())) 
			    		|| dataValuta.after(UtilityDate.getRelativeCalendar(dataEsecuzione,20, true).getTime()))
			    {
			    	errors.add(field.getKey(), Resources.getActionMessage(request,va,field));
	    			return false;
			    }
		    }
		    return true;
		}
	
	public static boolean importoMassimo(
		    Object bean,
		    ValidatorAction va, 
		    Field field,
		    ActionMessages errors,
		    HttpServletRequest request) {
			boolean retVal=true;
			int importoInt=0;
			String importoDec=ValidatorUtils.getValueAsString(bean, field.getVarValue("dec"));
			String importoMax=(String)field.getVarValue("max");
			int max = UtilityNumberFormatter.numberAsStringToInt(importoMax);
			int dec = UtilityNumberFormatter.numberAsStringToInt(importoDec);
			Tracer.debug("StrutsValidator","importoMassimo","validate max import",null);
		    String importo = ValidatorUtils.getValueAsString(bean, field.getProperty());
		    Tracer.debug("StrutsValidator","importoMassimo","importo da form: " + importo,null);
		    if (!GenericValidator.isBlankOrNull(importo)) 
		    {
		    	importoInt=UtilityNumberFormatter.numberAsStringToInt(importo);
		    	Tracer.debug("StrutsValidator","importoMassimo","importo pulito: " + importoInt,null);
		    	if(importoInt>max || (importoInt==max && dec > 0))
		    	{
			    	errors.add(field.getKey(),Resources.getActionMessage(request,va,field));
			    	retVal=false;
		    	}
		    }
		    
		    return retVal;
		}
	
	public static boolean rangeImporti(
		    Object bean,
		    ValidatorAction va, 
		    Field field,
		    ActionMessages errors,
		    HttpServletRequest request) {
		
			Tracer.debug("---------------->StrutsValidator","validateTwoFields","validatooooor",null);
			int importoMin=0;
			int importoMax=0;
		    String stringImporto = ValidatorUtils.getValueAsString(bean, field.getProperty());
		    Tracer.debug("---------------->StrutsValidator","compareDates","importo minimo inserito: " + stringImporto,null);
		    if (!GenericValidator.isBlankOrNull(stringImporto)) 
		    {
		    	importoMin=UtilityNumberFormatter.numberAsStringToInt(stringImporto);
		       		 
		    }
		    
		    stringImporto = ValidatorUtils.getValueAsString(bean, field.getVarValue("importoMax"));
		    Tracer.debug("---------------->StrutsValidator","compareDates","importo massimo inserito: " + importoMin,null);
		    
		    if (!GenericValidator.isBlankOrNull(stringImporto)) 
		    {
		    	importoMax=UtilityNumberFormatter.numberAsStringToInt(stringImporto);
		       		 
		    }
		    
		    if(importoMax != 0 && importoMin >= importoMax)
      		 {
      		 	errors.add(field.getKey(), Resources.getActionMessage(request,va,field));
      		 	return false;
      		 }
		    return true;
		}

	/**
	 * 
	 * Il metodo controlla se la selezione della provincia è stata
	 * effettuata in tal caso restituisce true 
	 * Se va in errore false
	 */
	public static boolean selezioneProvincia
		(
		    Object bean,
		    ValidatorAction va, 
		    Field field,
		    ActionMessages errors,
		    HttpServletRequest request
		) 
	{
		Tracer.debug(StrutsValidator.class.getName(),"selezioneProvincia","Inizio");
		String codiceProvincia = ValidatorUtils.getValueAsString(bean, field.getProperty());
		//
		Tracer.debug(StrutsValidator.class.getName(),"selezioneProvincia","codiceProvincia: " + codiceProvincia);
		//
		if( codiceProvincia.trim().equals("0") )
		{
			errors.add( field.getKey(),Resources.getActionMessage(request,va,field) );
			request.setAttribute("errorKey_selezioneProvincia", errors);
			return false;
		}
		Tracer.debug(StrutsValidator.class.getName(),"selezioneProvincia","Fine");
		//
		return true;
		//
	}//end selezioneProvincia
	//
	/**
	 * Controlla la P.IVA o il Codice fiscale a seconda la 
	 * lunghezza del valore del field passato
	 * per default è false.
	 * @return false se i controlli effettuati non sono andati a buon fine
	 */
	public static boolean controlloCodiceFiscalePIVA
		(
		    Object bean,
		    ValidatorAction va, 
		    Field field,
		    ActionMessages errors,
		    HttpServletRequest request
		)
	{
		//
		boolean objReturn = false; // il controllo, per default, NON è andato a buon fine
		//
		Tracer.debug(StrutsValidator.class.getName(),"controlloCodiceFiscalePIVA","Inizio");
		//
		String objValore = ValidatorUtils.getValueAsString( bean, field.getProperty()).trim();
		//
		Tracer.debug(StrutsValidator.class.getName(),"controlloCodiceFiscalePIVA"," valore passato: " + objValore);
		//
		Tracer.debug(StrutsValidator.class.getName(),"controlloCodiceFiscalePIVA"," lunghezza valore passato: " + objValore.length());
		//
		if( objValore.length() == 11 )
		{
			//Sono nella partita iva
			//
			objReturn = PartitaIva.isValid(objValore); 
			//
		}
		else if( objValore.length() == 16 )
		{
			//Sono nel codice fiscale
			objReturn = CodiceFiscale.isValid(objValore) ;
			//
		}
		//
		if( objReturn == false )
		{
			errors.add( field.getKey(), Resources.getActionMessage( request, va, field) );
			request.setAttribute("errorKey_CodiceFiscalePiva", errors);	
		}
		//
		Tracer.debug(StrutsValidator.class.getName(),"controlloCodiceFiscalePIVA","Fine");
		//
		return objReturn;
		//
	}//end controlloCodiceFiscalePIVA 
	//
	/**
	 * TODO
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @param request
	 * @return
	 */
	public static boolean checkTipologiaControparte(
		    Object bean,
		    ValidatorAction va, 
		    Field field,
		    ActionMessages errors,
		    HttpServletRequest request) {
		
		Tracer.debug(StrutsValidator.class.getName(),"checkTipologiaControparte","BEGIN");
		
		boolean check = false;
		
		Tracer.debug(StrutsValidator.class.getName(),"checkTipologiaControparte", "FIELD= " + field.toString());
		Tracer.debug(StrutsValidator.class.getName(),"checkTipologiaControparte", "FIELD= " + field.getFieldOrder());
		Tracer.debug(StrutsValidator.class.getName(),"checkTipologiaControparte", "FIELD= " + field.getIndexedListProperty());
		Tracer.debug(StrutsValidator.class.getName(),"checkTipologiaControparte", "FIELD= " + field.getIndexedProperty());
		Tracer.debug(StrutsValidator.class.getName(),"checkTipologiaControparte", "FIELD= " + field.getKey());
		
		
		String checkTipologiaControparte = ValidatorUtils.getValueAsString(bean, field.getProperty());
		Tracer.debug(StrutsValidator.class.getName(),"checkTipologiaControparte","tipologia= " + checkTipologiaControparte);
		
		
		errors.add(field.getKey(),Resources.getActionMessage(request,va,field));
		
		Tracer.debug(StrutsValidator.class.getName(),"checkTipologiaControparte","END");
		return check;
	}
	
	/**
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @param request
	 * @return
	 */
	public static boolean validatePartitaIva(
		    	Object bean,
		    	ValidatorAction va, 
		    	Field field,
		    	ActionMessages errors,
		    	Validator validator,
		    	HttpServletRequest request) {
		
		Tracer.debug(StrutsValidator.class.getName(),"checkPartitaIva","BEGIN");
		boolean isValid = false;
		WebUtil.traceObject(field);
		Tracer.debug(StrutsValidator.class.getName(),"checkPartitaIva", "field name= " + field.getProperty());
		
		String fieldValue = ValidatorUtils.getValueAsString( bean, field.getProperty()).trim();
		Tracer.debug(StrutsValidator.class.getName(),"checkPartitaIva", "field value= " + fieldValue);
		if(fieldValue.length() > 0 ){
			isValid = PartitaIva.isValid(fieldValue) ;
			if(!isValid){
				Tracer.debug(StrutsValidator.class.getName(),"checkPartitaIva", "piva is not valid");
				errors.add(field.getKey(), Resources.getActionMessage(request,va,field));	
			}
			else
				Tracer.debug(StrutsValidator.class.getName(),"checkPartitaIva", "piva is ok");
		}
		else{
			Tracer.debug(StrutsValidator.class.getName(),"checkPartitaIva", "not valorized");
			isValid = true;
		}
		Tracer.debug(StrutsValidator.class.getName(),"checkPartitaIva","END");
		return isValid;
	}
	
	public static boolean validateCF(
	    	Object bean,
	    	ValidatorAction va, 
	    	Field field,
	    	ActionMessages errors,
	    	Validator validator,
	    	HttpServletRequest request) {
	
	Tracer.debug(StrutsValidator.class.getName(),"validateCF","BEGIN");
	boolean isValid = false;
	WebUtil.traceObject(field);
	Tracer.debug(StrutsValidator.class.getName(),"validateCF", "field name= " + field.getProperty());
	
	String fieldValue = ValidatorUtils.getValueAsString( bean, field.getProperty()).trim();
	Tracer.debug(StrutsValidator.class.getName(),"validateCF", "field value= " + fieldValue);
	if(fieldValue.length() > 0 ){
		Tracer.debug(StrutsValidator.class.getName(),"validateCF", "check cf");
		isValid = CodiceFiscale.isValid(fieldValue) ;
		if(!isValid){
			Tracer.debug(StrutsValidator.class.getName(),"validateCF", "cf is not valid");
			errors.add(field.getKey(), Resources.getActionMessage(request,va,field));	
		}
		else
			Tracer.debug(StrutsValidator.class.getName(),"validateCF", "cf is ok");
	}
	else{
		Tracer.debug(StrutsValidator.class.getName(),"validateCF", "not valorized");
		isValid = true;
	}
		
	
	Tracer.debug(StrutsValidator.class.getName(),"validateCF","END");
	return isValid;
}
	
}