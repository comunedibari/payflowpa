/*
 * Created on 2-feb-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.utility.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author damiano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateTool {
	
	public static final String US_DATE_FORMAT = "MM/dd/yyyy";
	
	public static final String IT_DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String US_DATETIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	
	public static final String IT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	public static final String ISO_DATE_FORMAT = "yyyy/MM/dd";
	
	public static final String ISO_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public DateFormat commonDateFormat = new SimpleDateFormat(US_DATETIME_FORMAT);
	
	/**
	 * 
	 */
	public DateTool(String format) {
		super();
		commonDateFormat = new SimpleDateFormat(format);
	}


	public Date incrementDays(String date, int numberOfDays) throws ParseException{
		return incrementDays(commonDateFormat.parse(date),numberOfDays);
	} 

	public Date incrementDays(Date date, int numberOfDays){
		
		Calendar c = new GregorianCalendar();
		c.setTime (date);
		c.add(Calendar.HOUR_OF_DAY, numberOfDays*24);
		return c.getTime();
	} 
	
	
	public Date parse(String year, String month, String days) throws ParseException{
		
		return parse(year,month,days,"00","00","00");
	}
	
	
	public Date parse( String year,String month,String days, String hours, String minutes, String seconds) throws ParseException{
			
			if (days==null)
				throw new ParseException("cannot parse a null value/non posso convertire un valore null",0);
			if (month==null)
				throw new ParseException("cannot parse a null value/non posso convertire un valore null",0);
			if (year==null)
				throw new ParseException("cannot parse a null value/non posso convertire un valore null",0);
			
			hours = hours==null?"00":hours;
			minutes = minutes==null?"00":minutes;
			seconds = seconds==null?"00":seconds;
			//Utilizzo il calendar per mantenermi indipendente dal formato data utilizzato dalla classe
			Calendar c = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(days), Integer.parseInt(hours),Integer.parseInt(minutes),Integer.parseInt(seconds) );
			return c.getTime();
		}
	
	public Date parse(String date) throws ParseException{
		return this.commonDateFormat.parse(date);
	}
	
	public String getCurrentDate() {
		Date date = new Date();
        return this.commonDateFormat.format(date);
	}
	
	
}
