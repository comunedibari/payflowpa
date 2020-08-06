/*
 * Creato il 14-feb-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.util.text;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * @author EE10800
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class UtilityDate 
{
	public static Date getBackEndDate(String data, Date property, String separatore)
	{
		data=data.concat(separatore);
		StringTokenizer s=new StringTokenizer(data,separatore);
		String g=s.nextToken();
		String m=s.nextToken();
		String a=s.nextToken();
		GregorianCalendar v=new GregorianCalendar(Integer.parseInt(a),Integer.parseInt(m)-1,Integer.parseInt(g));
		property=v.getTime();
		return property;
	}	
	
	public static Calendar getCalendar(String data, String separatore)
	{
		data=data.concat(separatore);
		StringTokenizer s=new StringTokenizer(data,separatore);
		String g=s.nextToken();
		String m=s.nextToken();
		String a=s.nextToken();
		GregorianCalendar v=new GregorianCalendar(Integer.parseInt(a),Integer.parseInt(m)-1,Integer.parseInt(g));
		return v;
	}	
	
	/*
	 * Converte una data in una stringa secondo il pattern definito internamente
	 */

	public static String getFrontEndDate()
	{
		return getFrontEndDate(Calendar.getInstance().getTime());
	}	
	
	/*
	 * Restituisce la data odierna secondo il pattern inserito
	 */

	public static String getFrontEndDate(String pattern)
	{
		return getFrontEndDate(Calendar.getInstance().getTime(), pattern);
	}	
	
	
	public static String getFrontEndDate(Date data)
	{
		return sdf.format(data);
	}	
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	/*
	 * Converte una data in una stringa secondo il pattern passato come parametro
	 */
	public static String getFrontEndDate(Date data, String pattern)
	{
		SimpleDateFormat sdfByPattern = new SimpleDateFormat(pattern);
		return sdfByPattern.format(data);
	}
	
	public static String getYesterday()
	{
		GregorianCalendar calendar =new GregorianCalendar();		
		dataDayBack(calendar,1);					
		return sdf.format(calendar.getTime());
	}
	
	public static String getThreeDaysAgo()
	{
		GregorianCalendar calendar =new GregorianCalendar();	
		dataDayBack(calendar,3);					
		return sdf.format(calendar.getTime());
	} 
	
	//	 TODO: megliorare la gestione del lastWorkingDay
	public static String getFrontEndTwoDaysBeforeLastWorkingDay()
	{	
		GregorianCalendar calendar =new GregorianCalendar();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		// IMPORTANT: at the moment the method DOES NOT return the last working day - 2, but just
		// 2 days before yesterday. Temporary decision by specs
		switch (dayOfWeek) { 
		// lunedi
		// case 2: dataDayBack(calendar,5); break;
		// domenica
		// case 1: dataDayBack(calendar,4); break;
		// sabado
		// case 7: dataDayBack(calendar,3); break;
		default: dataDayBack(calendar,3);
		}
		
		return sdf.format(calendar.getTime());
	}
	
	//	 TODO: megliorare la gestione del lastWorkingDay
	public static String getFrontEndLastWorkingDay()
	{
		GregorianCalendar calendar =new GregorianCalendar();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    	
		// IMPORTANT: at the moment the method DOES NOT return the last working day, but just
		// yesterday. Temporary decision by specs
		switch (dayOfWeek) {
		// lunedi
		// case 2: dataDayBack(calendar,3); break;
		// domenica
		// case 1: dataDayBack(calendar,2); break;
		// sabado
		// case 7: dataDayBack(calendar,1); break;
		default: dataDayBack(calendar,1);
		}
		
		return sdf.format(calendar.getTime());
	}	
	
	
	public static Date getFrontEndLastWorkingDayD()
	{
//		System.out.print("getFrontEndDateYestardayDefaultRicercaSaldi");
		GregorianCalendar calendar =new GregorianCalendar();
		Date dataResult=calendar.getTime();		
//		System.out.print("format..........in name day........");
		SimpleDateFormat sdfdow = new SimpleDateFormat("E");
		String giornoWeek=sdfdow.format(dataResult);

		if (giornoWeek.equalsIgnoreCase("Mon"))
		{
			dataDayBack(calendar,3);			
		}
		if (giornoWeek.equalsIgnoreCase("Sun"))
		{
			dataDayBack(calendar,2);
		}
		if (giornoWeek.equalsIgnoreCase("Sat"))
		{			
			dataDayBack(calendar,1);
		}
		else
		{
			dataDayBack(calendar,1);
		}
		return calendar.getTime();
	}	
	
	
	
	public static Calendar getRelativeCalendar(Calendar calendar,long giorni, boolean forward)
	{
		long currentLong=calendar.getTimeInMillis();
		Calendar relative = Calendar.getInstance();
		relative.setTimeInMillis(currentLong);
		long datelong= 0l;
		if (forward)
			datelong = currentLong + (giorni*1000*24*60*60);
		else
			datelong = currentLong - (giorni*1000*24*60*60);
		relative.setTimeInMillis(datelong);
		return relative;
	}
	
	
	private static void dataDayBack(Calendar calendar,long giorni )
	{
		long currentLong=calendar.getTimeInMillis();
		long datelong=currentLong- (giorni*1000*24*60*60);
		calendar.setTimeInMillis(datelong);
		
	}
	
	public static boolean isBeforeToday(String param){
		
		Date inputDate = null;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date dateToday=cal.getTime();
		inputDate=UtilityDate.getBackEndDate(param,inputDate,"/");

	    return inputDate.before(dateToday);
		
	}
	
	public static String getStringDate(String data)
	{
		if (data==null) return "";
		if (data.length()>11)
			return data.substring(0,11);
		else
			return data;
	}	
	
	public static Date getDateXFromDate (Date date,long x )
	{
		
		Calendar calendar =new GregorianCalendar();		
		calendar.setTime(date);
		dataDayBack(calendar,x);
		
		return calendar.getTime();
	}
	
	 
	
	
	
}