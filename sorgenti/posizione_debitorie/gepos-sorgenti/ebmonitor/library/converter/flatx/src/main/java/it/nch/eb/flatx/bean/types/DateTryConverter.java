/**
 * Created on 27/mar/2009
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.flatx.exceptions.ConversionException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author gdefacci
 */
public class DateTryConverter implements Converter {
	
	private static final long	serialVersionUID	= -7069062439911267406L;
	private final DateFormat[] inputFormat;
	private final int length;
	
	public DateTryConverter(String[] inputFormats) {
		this.inputFormat = asSimpleDateFormat(inputFormats);
		int len = -1;
		for (int i = 0; i < inputFormats.length; i++){
			if (len == -1)
				len = getLengthFromDateFormat(inputFormats[i]);
			else {
				if (len != getLengthFromDateFormat(inputFormats[i])){
					StringBuffer sb = new StringBuffer();
					for (i = 0; i < inputFormats.length; i++){
						sb.append("\n");
						sb.append(inputFormats[i]);
					}
					throw new IllegalArgumentException("Not homogeneus length: " + sb);
				}
			}
		}
		this.length = len;
	}
	
	public int getLength(){
		return length;
	}
	
	private int getLengthFromDateFormat(String dateFormatStr){
		if (dateFormatStr == null) throw new NullPointerException("dateFormatStr");
		String tmpDateFormat = dateFormatStr.replaceAll("\\'\\'", "i");
		tmpDateFormat = tmpDateFormat.replaceAll("\\'", "");
		return tmpDateFormat.length();
	}
	
	
	public static DateFormat[] asSimpleDateFormat(String[] strFormat) {
		DateFormat[] res = new DateFormat[strFormat.length];
		for (int i = 0; i < res.length; i++) {
			String inp = strFormat[i];
			res[i] = asSimpleDateFormat(inp);
		}
		return res;
	}

	public static SimpleDateFormat asSimpleDateFormat(String inp) {
		return new SimpleDateFormat(inp);
	}
	
	/**
	 * returns the dateformat that could parse the input str
	 */
	public DateFormat getDateFormat(String input) {
		DateFormat res = internalGetDateFormat(input);		
		if (res==null) return null;
		else return (DateFormat) res.clone();
	}

	/**
	 * introduced to access <code>DateFormat[] inputFormat</code> directly, without cloning. 
	 * @param input
	 * @return
	 */
	private DateFormat internalGetDateFormat(String input) {
		DateFormat res = null;
		for (int i = 0; i < inputFormat.length && res == null; i++) {
			DateFormat df = inputFormat[i];
			synchronized (df) {
				try {
					df.parse(input);
					res = df;
				} catch (Exception e){
					res = null;  // uselss row
				}
			}
		}
		return res;
	}
	
	public String encode(String stringModel) {
		if (stringModel==null || "".equals(stringModel.trim())) return null;
		Date dt = null;
		try {
			DateFormat df = internalGetDateFormat(stringModel);
			if (df==null) return null;
			dt = parseDate(df, stringModel);				
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
		if (dt == null) throw new ConversionException("could not parse " + stringModel , this);
		return stringModel;
	}

	public synchronized Date parseDate(DateFormat df, String stringModel) throws ParseException {
		synchronized (df) {
			return df.parse(stringModel);
		}
	}	

}
