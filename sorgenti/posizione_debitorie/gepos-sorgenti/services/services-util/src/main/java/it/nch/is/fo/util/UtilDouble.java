package it.nch.is.fo.util;

import java.math.BigDecimal;

public class UtilDouble {
	
	public static Double round_up(Double value, int decimalPlace){ 
	    BigDecimal bd = new BigDecimal(value.doubleValue()); 
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_UP); 
	    Double ret =new Double(bd.doubleValue()); 
	    return ret;
	}
	
	public static Double round_Halfup(Double value, int decimalPlace){ 
	    BigDecimal bd = new BigDecimal(value.doubleValue()); 
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP); 
	    Double ret =new Double(bd.doubleValue()); 
	    return ret;
	}
}
