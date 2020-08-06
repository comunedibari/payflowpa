package it.tasgroup.idp.proxyndp.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NodoPagamentiUtils {

	public static Timestamp stringToTimestamp(String stringDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");//TODO che formato e' la data sul messaggio ????
		Date date = sdf.parse(stringDate);
		return new Timestamp(date.getTime());
	}

	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static Date today() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
}
