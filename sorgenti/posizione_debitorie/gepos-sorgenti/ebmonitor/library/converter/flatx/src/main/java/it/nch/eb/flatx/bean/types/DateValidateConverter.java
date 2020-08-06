/**
 * Created on 06/dic/07
 */
package it.nch.eb.flatx.bean.types;


import it.nch.eb.flatx.exceptions.ConversionException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * validate a date against a dateformat. if a string doesnt match the format a ParseException is thrown, otherwise the 
 * same string is returned.
 * @author gdefacci
 */
public class DateValidateConverter implements Converter {
	
	private static final long	serialVersionUID	= 1933576003471432250L;
	private final DateFormat	dateformat;
	private final String formatDescription;

	public DateValidateConverter(String dateRegexp) {
		this( new SimpleDateFormat(dateRegexp), dateRegexp );
	}
	
	public DateValidateConverter(DateFormat dateformat) {
//		this.dateformat = (DateFormat) dateformat.clone();
//		this.dateformat.setLenient(false);
		this(dateformat, dateformat.toString());
	}
	
	public DateValidateConverter(DateFormat dateformat, String dateFrmtDesc) {
		this.dateformat = (DateFormat) dateformat.clone();
		this.dateformat.setLenient(false);
		this.formatDescription = dateFrmtDesc;
	}

	public String encode(String stringModel) {
		if (stringModel==null || "".equals(stringModel.trim()) || "000000".equals(stringModel.trim())) return null;
		try {
			parseDate(stringModel);				
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
		return stringModel;
	}

	public synchronized Date parseDate(String stringModel) throws ParseException {
		synchronized (dateformat) {
			return dateformat.parse(stringModel);
		}
	}

	public String getFormatDescription() {
		return formatDescription;
	}

}
