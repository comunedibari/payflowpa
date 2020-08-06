/*
 * File: MathConstants.java
 * Package: com.etnoteam.service.math
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 10-ott-03 - 3.00.23
 * Created by: finsaccanebbia (Etnoteam)
 */
package it.nch.fwk.fo.base.constants;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author finsaccanebbia
 *
 * <br>
 * La classe MathConstants contiene le variabili "matematiche"
 * del progetto
 * 
**/
public interface MathConstants {
	public static final BigDecimal BIG_DEC_ZERO=new BigDecimal("0");
	public static final BigDecimal BIG_DEC_ONE =new BigDecimal("1");
	public static final BigDecimal BIG_DEC_TEN =new BigDecimal("10");
	public static final BigDecimal BIG_DEC_CENT=new BigDecimal("100");
	public static final BigDecimal BIG_DEC_ONE_CENT =new BigDecimal("0.01");
    public static final BigDecimal BIG_DEC_MINUS_ONE = new BigDecimal("-1");
	public static final BigDecimal BIG_DEC_MAX = new BigDecimal(Double.MAX_VALUE);
		
	public static final BigInteger BIG_INT_ZERO=new BigInteger("0");	
	public static final BigInteger BIG_INT_ONE =new BigInteger("1");
	public static final BigInteger BIG_INT_TEN =new BigInteger("10");
	public static final BigInteger BIG_INT_CENT=new BigInteger("100");
	
	public static final int DEFAULT_SCALE = 4;
	
	public static final Integer INTEGER_ZERO = new Integer(0);
}
