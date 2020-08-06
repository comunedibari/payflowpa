package it.tasgroup.iris.persistence.dao.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class DaoUtil {
    
    public static String getStringTimestamp(String yy, String mm,String gg ){
        String sep = "-";
        
        if (mm.length()==1)
            mm = "0"+mm;
        
        if (gg.length()==1)
            gg = "0"+gg;
        
        return yy+sep+mm+sep+gg+" 00:00:00";
    }
    
    public static Timestamp getTimestampDa(String yy, String mm, String gg) {

        if (yy == null || mm == null || gg == null )
            return null;
        
        String text = getStringTimestamp(yy, mm, gg);
        Timestamp ts = Timestamp.valueOf(text);
        
        return ts;
    }


//  Questo dava errore sulla fine del mese.    
//    public static Timestamp getTimestampA(String yy,  String mm, String gg) {
//        
//        if (yy == null || mm == null || gg == null )
//            return null;
//        Integer gg1 = new Integer(gg) + 1;
//        
//        String text = getStringTimestamp(yy, mm, gg1.toString());
//        Timestamp ts = Timestamp.valueOf(text);
//        
//        return ts;
//    }
    
    public static Timestamp getTimestampA(String yy,  String mm, String gg) {
        
       if (yy == null || mm == null || gg == null )
            return null;

       Calendar c = new GregorianCalendar(Integer.parseInt(yy), Integer.parseInt(mm)-1, Integer.parseInt(gg)); 
       
       // Sommo un giorno per andare alle 00.00.00 del giorno successivo e includere il giorno finale indicato.
       c.add(Calendar.DAY_OF_MONTH, 1);
              
       Timestamp returnValue = new Timestamp(c.getTimeInMillis());
   
       return returnValue;
    }
    
    
    public static boolean isNotEmptyProperty(String property){
        
        return property != null && !property.trim().equals("");
    }
    
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

}
