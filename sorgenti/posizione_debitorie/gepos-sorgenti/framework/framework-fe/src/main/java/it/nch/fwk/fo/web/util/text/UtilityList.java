/*
 * Creato il 3-apr-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.util.text;

import it.nch.fwk.fo.util.Tracer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class UtilityList {

	/**
	 * Data una lista di valori restituisce una
	 * stringa di valori distinti separati da virgola
	 * @author Marcello Gonzato
	 * @param list
	 * @param nome
	 * @return String
	 * 
	 * 
	 */
	
	public static String getStringList(List list,String nome)
	{
		
		Tracer.info("UtilityList","getStringList","inizio..   nome.........:="+ nome);
		if (list.size()==0) return "";
		
		String stringResult="";		
		List newList=new ArrayList();
		int max=list.size();
		int i=0;
		
		int r=0;
		while(i<max)
		{
			String newString=(String) list.get(i);	
			Tracer.info("UtilityList","getStringList","newString.."+ newString);
			if (newString==null) newString="";
			int j=0;
			if (newList.size()==0)
			{
				newList.add(newString);
				continue;
			}
			boolean present=false;
			while(j<newList.size())
			{					
				String nexti=(String)newList.get(j);
							
				if (nexti==null) nexti="";
				Tracer.info("UtilityList","getStringList","nexti.."+ nexti);
				if (nexti.equalsIgnoreCase(newString))
				{					
					
				}
				else
				{
					Tracer.info("UtilityList","getStringList","add...newString"+ newString);
					present=true;
					
					
				}
				j++;
			}
			if (present) newList.add(newString);
			i++;
		}	
		Tracer.info("UtilityList","getStringList","medio..   nome.........:="+ nome);
		
		newList.toArray();
		int z=0;
		while (z<newList.size())
		{
			if (z==0)
			{
				stringResult=(String)newList.get(0);
			}
			else
			{
				stringResult= stringResult + "," + (String)newList.get(z);
			}
			z++;
		}			
		
		Tracer.info("UtilityList","getStringList","stringResult.........: "+stringResult + "...nome=" + nome , null);
		
		return stringResult;
		
	}
	
	/**
	 * Data una lista di valori restituisce una
	 * lista di valori distinti 
	 * @author Marcello Gonzato
	 * @param list
	 * @param nome
	 * @return String
	 * 
	 * 
	 */
	
	public static List getUniqueList(List list,String nome)
	{
		
		Tracer.info("UtilityList","getUniqueList","inizio..   nome.........:="+ nome);
		if (list.size()==0) return null;
						
		List newList=new ArrayList();
		int max=list.size();
		int i=0;
		
		int r=0;
		while(i<max)
		{
			String newString=(String) list.get(i);	
			Tracer.info("UtilityList","getUniqueList","newString.."+ newString);
			if (newString==null) newString="";
			boolean present=false;
			if (newList.size()==0)
			{
				newList.add(newString);
				i++;
				continue;
			}
			else
			{	
				int j=0;				
				while(j<newList.size())
				{					
					String nexti=(String)newList.get(j);							
					if (nexti==null) nexti="";
					Tracer.info("UtilityList","getUniqueList","nexti.."+ nexti);
					if (nexti.equalsIgnoreCase(newString))
					{	
						present=true;	
						break;
					}
					else
					{
						Tracer.info("UtilityList","getUniqueList","add...newString"+ newString);
									
					}
					j++;
				}	
			}
			if (!present) newList.add(newString);
			i++;
		}	
		Tracer.info("UtilityList","getUniqueList","medio..   nome.........:="+ nome);
		int z=0;
		String result="";
		while(z<newList.size())
		{
			 result=result +(String) newList.get(z);
			 z++;
		}
		Tracer.info("UtilityList","getUniqueList","contenuto list "+ nome + "=" + result );
		
		return newList;
		
	}
}
