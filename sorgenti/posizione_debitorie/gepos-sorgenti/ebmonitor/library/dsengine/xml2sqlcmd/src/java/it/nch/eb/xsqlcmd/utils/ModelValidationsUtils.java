/**
 * 14/set/2009
 */
package it.nch.eb.xsqlcmd.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gdefacci
 */
public class ModelValidationsUtils {
	
	public static boolean checkCodiceFiscale(String cf) {
	    int i, s, c;
	    String cf2;
	    int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
	        11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	    if( cf.length() == 0 ) return false;
	    if( cf.length() != 16 ) return false;   
	    cf2 = cf.toUpperCase();
	    for( i=0; i<16; i++ ){
	        c = cf2.charAt(i);
	        if( ! ( c>='0' && c<='9' || c>='A' && c<='Z' ) )
	            return false;
	    }
	    s = 0;
	    for( i=1; i<=13; i+=2 ){
	        c = cf2.charAt(i);
	        if( c>='0' && c<='9' )
	            s = s + c - '0';
	        else
	            s = s + c - 'A';
	    }
	    for( i=0; i<=14; i+=2 ){
	        c = cf2.charAt(i);
	        if( c>='0' && c<='9' )     c = c - '0' + 'A';
	        s = s + setdisp[c - 'A'];
	    }
	    if( s%26 + 'A' != cf2.charAt(15) )
	        return false;
	    return true;
	}
	
	/**
	 * 
	 * @param cf
	 * @return
	 */
	public static boolean checkPartitaIva(String cf) {

		long piva = 0;
		try {
			piva = Long.parseLong(cf);
		} catch (NumberFormatException e) {
			return false;
		}
		  
		if (piva>0 && cf!=null && cf.length()==11) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @param idDestinatario
	 * @return
	 */
	public static boolean checkCodiceStp(String idDestinatario) {
		
		Pattern p = Pattern.compile("[Ss][Tt][Pp][0-9]{13}");
		Matcher m = p.matcher(idDestinatario);
		
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * 
	 * @param idDestinatario
	 * @return
	 */
	public static boolean checkCodiceEni(String idDestinatario) {
		
		Pattern p = Pattern.compile("[Ee][Nn][Ii][0-9]{13}");
		Matcher m = p.matcher(idDestinatario);
		
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
		
	}	
	
	
	/**
	 * 
	 * @param idDestinatario
	 * @return
	 */
	public static boolean checkCodiceFiscaleAnonimoAGID(String idDestinatario) {
		
		if ("ANONIMO".equals(idDestinatario)) {
			return true;
		} else {
			return false;
		}
		
	}		
	


}
