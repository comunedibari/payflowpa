/**
 * 
 */
package it.tasgroup.iris.shared.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * @author pazzik
 *
 */
public class UtilBigDecimal {
	
	/**
	 * @param importo
	 * @return
	 */
	public static String getStringFromBigDecimal(BigDecimal importo) {
		String retVal = "";

		if (importo == null)
			return retVal;

		importo = importo.setScale(2, RoundingMode.HALF_UP);
		BigInteger tmp = importo.unscaledValue();
		retVal = tmp.toString();

		return retVal;
	}

}
