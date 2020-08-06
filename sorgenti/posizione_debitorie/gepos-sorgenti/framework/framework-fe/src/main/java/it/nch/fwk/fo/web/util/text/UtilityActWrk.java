/*
 * Creato il 14-feb-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.util.text;

import it.nch.fwk.fo.dto.criteria.DTOPreCondition;
import it.nch.fwk.fo.util.Tracer;

import java.text.NumberFormat;
import java.util.Iterator;

import org.apache.struts.util.LabelValueBean;

/**
 * @author EE10800
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class UtilityActWrk 
{
	
	private static String condition;
	
	public static String convertLabelValueBeanIteratorToCondition(Iterator iterator)
	{
		condition = "";
		 while (iterator.hasNext()) 
		    {
		    	LabelValueBean element = (LabelValueBean) iterator.next();
				
		    	condition += "'" + element.getValue() + "',";
		    }
		 if (condition.length() > 0)
			 condition = "(" + condition.substring(0, condition.length() - 1) + ")";
			 
		return condition;
	}	
	
	public static String convertStringIteratorToCondition(Iterator iterator)
	{
		condition = "";
		 while (iterator.hasNext()) 
		    {
		    	String element = (String) iterator.next();
				
		    	condition += "'" + element + "',";
		    }
		 if (condition.length() > 0)
			 condition = "(" + condition.substring(0, condition.length() - 1) + ")";
			 
		return condition;
	}	
	
	
	public static String convertArrayToCondition(String [] array)
	{
		condition = "";
		for(int i=0; i<array.length; i++){
			Tracer.debug(UtilityActWrk.class.getName(),"convertArrayToCondition","array[" + i +"]= "+ array[i],null);
			if(array[i] != null)
				condition += "'" + array[i] + "',";
		}
		if (condition.length() > 0)
			 condition = "(" + condition.substring(0, condition.length() - 1) + ")";
		Tracer.debug(UtilityActWrk.class.getName(),"condition= "+ condition,null);	 
		return condition;
	}	
	
	public static String convertStringToCondition(String s)
	{
		if(s != null && !s.trim().equals(""))
			return "('" + s.trim() + "')";
		else
			return "";
	}	
	
	public static String convertStringToUpper(String s)
	{
		if(s != null && !s.trim().equals(""))
			return "upper('%" + s.trim() + "%')";
		else
			return "";
	}
	
	public static String quoteSubstring(String s)
	{
		if(s != null && !s.trim().equals(""))
			return "'%" + s.trim() + "%'";
		else
			return "";
	}	
	
	public static String convertStringToDateCondition(String s)
	{
		if(s != null && !s.equals(""))
			return "to_date('"+ s +"','dd/mm/yyyy')";
		else
			return "";
	}	
	
	public static String convertStringToNumberCondition(String s)
	{
		if(s != null && !s.equals("")){

			Number n=null;
			
			try {
				n=NumberFormat.getInstance().parse(s);
			}
			catch (Exception e){
				return null;
			}
			
			return (new Float(n.floatValue())).toString();
		}
		else
			return "";
	}	
	
	public static void buildCondition (DTOPreCondition preCond, String logicOper,  String column,String clause, String value){
		try{
		if(value != null && !value.trim().equals(""))
			preCond.addCondition(logicOper,column,clause,value);
		}catch (Exception e){
			
		}
	}
	
}
