/*
 * Creato il 16-feb-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.util.text;

import it.nch.fwk.fo.util.Tracer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author EE10800
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class UtilityNumberFormatter 
{
	public static double utilityDouble(String numValue)
	{
		Number n=null;
		
		try 
		{
			n=NumberFormat.getInstance().parse(numValue);
		}
		catch (ParseException e) 
		{
			// TODO Blocco catch generato automaticamente
			e.printStackTrace();
		}
		
		return n.doubleValue();
		
	}
	
	public static float utilityStringFloat(String numValue)
	{
		Number n=null;
		
		try 
		{
			n=NumberFormat.getInstance().parse(numValue);
		}
		catch (ParseException e) 
		{
			// TODO Blocco catch generato automaticamente
			e.printStackTrace();
		}
		
		return n.floatValue();
		
	}
	
	public static String utilityFloatString(Float numValue)
	{
		float d=numValue.floatValue();
		String n=NumberFormat.getInstance().format(numValue);
		
		return n;	
	}
	/*
	 * il metodo ritorna un importro (pulito) 
	 * da separatori migliaia.
	 */
	public static int numberAsStringToInt(String param){
		int retVal=0;
		NumberFormat nf = NumberFormat.getNumberInstance( Locale.ITALIAN);
		if(param.equals(""))
			return 0;
		try{
			Number n=nf.parse(param);retVal=n.intValue();
		}catch (ParseException e) {
			Tracer.error("UtilityNumberFormatter","numberAsStringToInt","importo da form: " + e,null);
		}
		Tracer.debug("UtilityNumberFormatter","numberAsStringToInt",param + " >> " +retVal,null);
		
		return retVal;
	}
	

	public static BigDecimal numberAsStringToBigDecimal(String param) {
		BigDecimal bd = BigDecimal.ZERO;
		if(param!=null && !"".equals(param)) {  
			String convPattern = "###,###,###,###,###,##0.00";	
			DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(Locale.US);
			df.setParseBigDecimal(true);
			df.applyPattern(convPattern);		
			try {
				bd = (BigDecimal) df.parse(param);
			} catch (ParseException e) {
				Tracer.error("UtilityNumberFormatter","numberAsStringToBigDecimal","importo da form: " + e,null);
			}	
		}
		return bd;
	}
	
	/*
	 * il metodo ritorna un importro (pulito) 
	 * da separatori migliaia.
	 */
	public static String numberAsBigDecimalToString(BigDecimal param){
		if(param == null)
			return "";
		
		String convPattern = "###,###,###,###,###,##0.00";
		
		if (param.compareTo(BigDecimal.ZERO) == 0){
			return "0";
		}
		else{
			DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(Locale.US);
			 df.setParseBigDecimal(true);
			 df.applyPattern(convPattern);
			 return df.format(param);
//			String temp = param.toString();
//			if(temp.lastIndexOf(".")==-1 && temp.lastIndexOf(".")==-1)
//				return temp;
//			while(temp.lastIndexOf("0")!=-1 && temp.lastIndexOf("0")==(temp.length()-1)){
//				temp = temp.substring(0, temp.lastIndexOf("0"));
//			}
//			if(temp.lastIndexOf(".")==(temp.length()-1))
//				temp = temp.substring(0, temp.lastIndexOf("."));
//			else				
//				temp = temp.replace('.', ',');
//			return temp;
		}
		
	}
	
}
