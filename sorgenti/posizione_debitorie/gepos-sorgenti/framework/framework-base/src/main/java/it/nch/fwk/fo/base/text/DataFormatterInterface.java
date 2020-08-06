package it.nch.fwk.fo.base.text;
/*
 * File: DataFormatterInterface.java
 * Package: com.etnoteam.service.text
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 21-lug-03 - 10.02.45
 * Created by: dcerri (Etnoteam)
 */
/**
 * @author dcerri
 *
 * <br>
 * Interfaccia DataFormatter
 * 
**/
public interface DataFormatterInterface {
	
	/* separatore costruzione chiavi */
	char SEP = '_';
	
	/* XML nomi tag */
	String TAG_FORMATTER = "DATAFORMATTER";
	String TAG_FACTORY = "FACTORY";
	String TAG_CLASS = "CLASS";		
	String TAG_DECIMAL = "DECIMAL";		
	String TAG_DECIMAL3 = "DECIMAL3";	
	String TAG_DATE = "DATE";
	String TAG_DATEYYYY = "DATEYYYY";
	
	String TAG_TIME = "TIME";
	String TAG_DATETIME = "DATETIME";
    String TAG_LITERALDATE = "LITERALDATE";
    String TAG_MONTHNAME ="MONTHNAME";
	String TAG_PRICE = "PRICE";
	String TAG_TRUNC_PRICE = "TRUNC_PRICE";
	String TAG_STOCKPRICE = "STOCKPRICE";
	String TAG_FUNDPRICE = "FUNDPRICE";
	String TAG_CCY = "CCY";
	String TAG_CREDIT = "CREDIT";
	String TAG_DEBIT = "DEBIT";		
	String TAG_PERC = "PERCENTAGE";
	String TAG_POS_PERC = "POSITIVE_PERC";
	String TAG_PERCUNIT = "PERCUNIT";
	String TAG_PERC_DEC = "PERC_DEC";
	String TAG_EXCHANGE_RATE = "EXCHANGERATE";
	String TAG_QTY_DEC = "QTY_DEC";
	String TAG_QTY_INT = "QTY_INT";
	String TAG_QTY = "QTY";
	String TAG_TOTAL_SIGNED = "TOTAL_SIGNED";
	String TAG_DAYMONTH = "DAYMONTH";
	String TAG_MONTHYEAR = "MONTHYEAR";
    
	/* XML nomi attributi */
	String ATTRIB_TYPE = "type";	
	String ATTRIB_PATTERN = "pattern";
	String ATTRIB_ROUNDING = "rounding";
	String ATTRIB_DECIMALS = "decimals";
	String ATTRIB_GROUPING = "grouping";
	String ATTRIB_SEPARATOR = "separator";
	String ATTRIB_CODE = "code";
	
	/* chiavi ausiliarie */
	String KEY_LOCALE = "locale";
}
