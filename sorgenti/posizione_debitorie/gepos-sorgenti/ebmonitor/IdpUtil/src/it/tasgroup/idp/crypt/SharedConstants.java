/**
 * 
 */
package it.tasgroup.idp.crypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pazzik
 *
 */
public class SharedConstants {
	
	final static public String IDFLUSSO="idflusso";
	final static public String IDPAGAMENTO="idpaga";
	final static public String CODPAGAMENTO="codpagamento";
	final static public String CODPAGANTE="codpagante";
	final static public String CRYPTEDPARAMS="c";
	final public static Date NO_EXPIRE = initNoExpireDate();
	
	private static final Date initNoExpireDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date result = null;
		try {
			result = sdf.parse("31/12/2099");
		} catch (ParseException e) {
			// do nothing
		}
		return result;
	}
}
