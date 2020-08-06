package it.tasgroup.iris.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class NumberUtils {
    public static DecimalFormat format = new DecimalFormat();
    public static final int RE_SCALE = 2;
    public static char DECIMAL_SEPARATOR;
 
    static {
        format.setParseBigDecimal(true);
        DECIMAL_SEPARATOR = format.getDecimalFormatSymbols().getDecimalSeparator();
    }
 
    public static BigDecimal parseBigDecimal(String s) throws ParseException {
    	String head = s.substring(0, s.length() - RE_SCALE -1).replaceAll("\\.", "").replaceAll("\\,", "");
    	String tail = s.substring(s.length() - RE_SCALE );
    	BigDecimal bigDecimal = new BigDecimal(head + DECIMAL_SEPARATOR + tail);
    	bigDecimal.setScale(RE_SCALE);
        return bigDecimal.setScale(RE_SCALE);
    }
    
}