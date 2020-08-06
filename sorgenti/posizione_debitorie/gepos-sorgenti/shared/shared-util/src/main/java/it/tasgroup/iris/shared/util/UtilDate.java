package it.tasgroup.iris.shared.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilDate {
	
	/**
	 * Il metodo restituisce il mese della data passata.<br>
	 * 1 (Gennaio)<br>
	 * 2 (Febbraio)<br>
	 * 3 (Marzo)<br>
	 * 4 (Aprile)<br>
	 * 5 (Maggio)<br>
	 * 6 (Giugno)<br>
	 * 7 (Luglio)<br>
	 * 8 (Agosto)<br>
	 * 9 (Settembre)<br>
	 * 10 (Ottobre)<br>
	 * 11 (Novembre)<br>
	 * 12 (Dicembre)
	 * 
	 * @param data
	 * @return
	 */
	public static String getMounthNumber(Date data){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(data.getTime());
		int m = gc.get(GregorianCalendar.MONTH)+1;
		return String.valueOf(m);
	}
	
	
	public static Date now(){
		return new Date();
	}
	
	public static String now2StringDefaultFormat(){
		return now2StringFormat("dd/MM/yyyy");
	}
	
	public static String now2StringFormat(String format){
		Format formatter;
		formatter = new SimpleDateFormat(format);
		return formatter.format(now());
	}
	
	
	public static String date2StringFormat(String format, Date date){
		Format formatter;
		formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	/**
	 * Aggiunge i giorni indicati alla data.<br>
	 * Se il valore dei giorni è negativo, li sottrae.
	 * 
	 * @param data
	 * @param giorni
	 * @return
	 */
	public static Date addDay(Date data, int giorni){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		gc.add(GregorianCalendar.DAY_OF_MONTH, giorni);
		return gc.getTime();
	}
	
	/**
	 * Aggiunge i minuti indicati alla data.<br>
	 * Se il valore dei minuti è negativo, li sottrae.
	 * 
	 * @param data
	 * @param minuti
	 * @return
	 */
	public static Date addMinutes(Date data, int minuti){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		gc.add(GregorianCalendar.MINUTE, minuti);
		return gc.getTime();
	}
	
	
	/**
	 * Aggiunge le ore indicati alla data.<br>
	 * Se il valore delle ore è negativo, li sottrae.
	 * 
	 * @param data
	 * @param ore
	 * @return
	 */
	public static Date addHour(Date data, int ore){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		gc.add(GregorianCalendar.HOUR_OF_DAY, ore);
		return gc.getTime();
	}
	
	
	/**
	 * Imposta alla data passata l'ora, minuti, secondi e millisecondi indicati.<br>
	 * <b>Note: </b>l'orario è considerato in 24 ore.
	 * 
	 * @param data
	 * @param ore
	 * @param min
	 * @param sec
	 * @param msec
	 * @return
	 */
	public static Date setOrario(Date data, int ore, int min, int sec, int msec){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		gc.set(GregorianCalendar.HOUR_OF_DAY, ore);
		gc.set(GregorianCalendar.MINUTE, min);
		gc.set(GregorianCalendar.SECOND, sec);
		gc.set(GregorianCalendar.MILLISECOND, msec);
		return gc.getTime();
	}
	
	/**
	 * Imposta alla data passata l'ora 23:59:59.9 <br>
	 * <b>Note: </b>l'orario è considerato in 24 ore.
	 * 
	 * @param data
	 * @return
	 */
	public static Date setOrarioEndOfDay(Date data){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
		gc.set(GregorianCalendar.MINUTE, 59);
		gc.set(GregorianCalendar.SECOND, 59);
		gc.set(GregorianCalendar.MILLISECOND, 9);
		return gc.getTime();
	}
	
	/**
	 * Il metodo restituisce la data con il primo giorno del mese impostato.
	 * @param month
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public static Date getDateFirstDay(String month, String year) throws Exception {
		if(month == null) throw new Exception("Mese non valido");
		if(year == null) throw new Exception("Anno non valido");
		
		try{
			Integer.parseInt(month);
		}catch(Exception ex){
			throw new Exception("Mese non valido");
		}
		try{
			Integer.parseInt(year);
		}catch(Exception ex){
			throw new Exception("Anno non valido");
		}
		
		int d = getFirstDayOfMonth(month, year);
		
		return getDateFromString(String.valueOf(d + "/" + month + "/" + year));
	}
	
	/**
	 * Il metodo restituisce la data con l'ultimo giorno del mese impostato.
	 * @param month
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public static Date getDateLastDay(String month, String year) throws Exception {
		if(month == null) throw new Exception("Mese non valido");
		if(year == null) throw new Exception("Anno non valido");
		
		try{
			Integer.parseInt(month);
		}catch(Exception ex){
			throw new Exception("Mese non valido");
		}
		try{
			Integer.parseInt(year);
		}catch(Exception ex){
			throw new Exception("Anno non valido");
		}
		
		int d = getLastDayOfMonth(month, year);
		
		return getDateFromString(String.valueOf(d + "/" + month + "/" + year));
	}
	
	public static Date getDateFromString(String dateAsString) throws Exception {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateAsString);
		} catch (ParseException e) {
			throw new Exception("Unable to apply date pattern " + pattern + " to string " + dateAsString, e);
		}
	}
	
	/**
	 * Restituisce l'ultimo giorno del mese (per l'anno specifico)
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public static int getLastDayOfMonth(String month, String year){
		GregorianCalendar gc = new GregorianCalendar();
		// Ottengo il primo giorno del mese
		gc.set(GregorianCalendar.MONTH, Integer.parseInt(month));
		gc.set(GregorianCalendar.YEAR, Integer.parseInt(year));
		int lastDay = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		return lastDay;
	}
	
	/**
	 * Restituisce il primo giorno del mese
	 * @param month
	 * @param year
	 * @return
	 */
	public static int getFirstDayOfMonth(String month, String year){
		GregorianCalendar gc = new GregorianCalendar();
		// Ottengo il primo giorno del mese
		gc.set(GregorianCalendar.MONTH, Integer.parseInt(month));
		gc.set(GregorianCalendar.YEAR, Integer.parseInt(year));
		int firstDay = gc.getActualMinimum(GregorianCalendar.DAY_OF_MONTH);
		return firstDay;
	}
	
	/**
	 * Restituisce una stringa rappresentante la data.<br>
	 * Il giorno ed il mese verranno impostati sempre di 2 cifre e saranno separati dalla stringa <code>delimeter</code>
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @param delimeter
	 * @return
	 */
	public static String getDateWithFormat(String day,String month,String year,String delimeter){
		if (!"".equals(day) && !"".equals(month) && !"".equals(year)) {

			String date="";
			if(day.length()==1){			
				day="0"+day;
			}
			if(month.length()==1){
				month="0"+month;
			}
			date=day+delimeter+month+delimeter+year;
			
			return date;		

		} else {
			return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("Mese: " + getMounthNumber(new Date()));
//		System.out.println("NOW: " + now2StringFormat("dd/MM/yyyy"));
//		System.out.println("NOW: " + now2StringFormat("yyyy.MM.dd"));
		System.out.println("NOW: " + now());
		System.out.println("NOW: " + now2StringDefaultFormat());
	}
	
	
	/**
	 * Restituisce true se la data passata come 
	 * parametro è minore o uguale alla data di oggi
	 * 
	 * @param when
	 */
	public static boolean dateLessThanToday(Date when) {
        return setOrarioEndOfDay(when).compareTo(setOrarioEndOfDay((new GregorianCalendar()).getTime()))<=0;
    }
	
	/**
	 * Restituisce true se la data passata come 
	 * parametro è maggiore o uguale alla data di oggi
	 * 
	 * @param when
	 */
	public static boolean dateGreaterThanToday(Date when) {
        return setOrarioEndOfDay(when).compareTo(setOrarioEndOfDay((new GregorianCalendar()).getTime()))>=0;
    }
	
	public static String formatDate(Date date) {
		
		String result = "";
		
		try {
			
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				result = sdf.format(date);
				
		} catch (Exception e) {
			
			return "";
		}
			
		return result;

	}

}
