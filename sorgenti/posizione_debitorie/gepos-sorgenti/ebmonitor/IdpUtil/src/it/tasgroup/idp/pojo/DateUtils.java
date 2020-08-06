package it.tasgroup.idp.pojo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	
	public static String PATTERN = "ddMMyy";
	public static String TS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static Calendar parseTimestamp(String timestamp){

		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN, Locale.ITALIAN);
		Date d;
		try {
			d = sdf.parse(timestamp);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			return cal;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Timestamp getIeri(String data){
		return getgiorni(data, -1);
	}
	
	public static Timestamp getOggi(String data){
		return getgiorni(data, 0);
	}
	
	public static Timestamp getDomani(String data){
		return getgiorni(data, 1);
	}
	/**
	 * 
	 * @param numGiorniFa numero di giorni indietro (è un intero positivo)
	 * @return
	 */
	public static Timestamp getNgiorniFa(Integer numGiorniFa){
		
		Date d = new Date(System.currentTimeMillis());
		
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN, Locale.ITALIAN);
		
		String data = sdf.format(d);
		
		return getgiorni(data, numGiorniFa*(-1));
	}
	

	/**
	 * 
	 * @param data
	 * @param amount
	 * @return
	 */
	public  static Timestamp getMinuti(int amount){
		
		//now
		Timestamp timeReal = new Timestamp(System.currentTimeMillis());
//		System.out.println(" TIME NOW !! = " + timeReal.toGMTString() );
		
		//ok
		long t=timeReal.getTime();
		long m=amount*60*1000;
		Timestamp theNewTimestamp=new Timestamp(t-m);
//		System.out.println(" TIME LIMIT OK !! = " + theNewTimestamp.toGMTString() );
		
		return theNewTimestamp;
	}	
	
	/**
	 * 
	 * @param data
	 * @param amount
	 * @return
	 */
	private static Timestamp getgiorni(String data,int amount){
		Calendar cpiu = DateUtils.parseTimestamp(data);
		cpiu.add(Calendar.DAY_OF_MONTH, amount);
		return new Timestamp(cpiu.getTimeInMillis());
	}
	
	public static Calendar atEndOfDay(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar;
	}

	public static Calendar atStartOfDay(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar;
	}
	
	public static Calendar yesterday(Calendar cal) {
	    cal.add(Calendar.DATE, -1);
	    return cal;
	}
	
	public static Calendar  getMonthFirstDate(Calendar c){
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    return c;
	}
	
	public static String formatTS(Calendar c) {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat(TS_PATTERN);
		return sdf.format(c.getTime());
	}

	/**
	 * 
	 * @param a
	 * @throws Exception
	 */
	public static void main (String a[]) throws Exception{
		String timestampToParse = "240212";
		System.out.println("Timestamp : " +  timestampToParse);

		Calendar c = parseTimestamp(timestampToParse);

		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Calendar : " + sdf.format(c.getTime()));

		System.out.println("Calendar : " + sdf.format(getOggi(timestampToParse)));

		System.out.println("Calendar ieri: " + sdf.format(getIeri(timestampToParse)));

		c.add(Calendar.DAY_OF_MONTH, -2);
		System.out.println("Calendar domani: " + sdf.format(getDomani(timestampToParse)));


		/*
   output :
     Timestamp : 24-Feb-1998 17:39:35
     Calendar : 1998-02-24 17:39:35
		 */
	}
	
	

}