package it.tasgroup.idp.billerservices.api.impl;

import java.math.BigDecimal;

public class BusinessComparatorHelper {
	
	/**
	 * Null safe/aware equals..
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean areEquals(Object o1, Object o2) {
		
		
		if (o1==null && o2==null) { 
			return true;
		}
		if (o1==null && o2!=null) { 
			return false;
		}
		if (o1!=null && o2==null) { 
			return false;
		}
	
		if (o1 instanceof BigDecimal) {
			return ((BigDecimal) o1).compareTo((BigDecimal) o2) == 0;
		}
		
		return o1.equals(o2);
	}

}
