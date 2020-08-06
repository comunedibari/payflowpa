/**
 * Created on 09/gen/08
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.flatx.exceptions.ConversionException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * convert a string including a date expressed in source format, to a string which represent the date in the getDateFormat
 * FIXME: rename  
 * @author gdefacci
 */
public class DateToDateConverter implements ReversibleConverter, Converter { 
//extends DateConverter implements ReversibleConverter {
	
	private static final String YYYY_MM_DD = "yyyy-MM-dd";

	private static final long	serialVersionUID	= 1768421538554642265L;

	/** source format */
	private static final DateFormat defaultDateFormat = new SimpleDateFormat( YYYY_MM_DD );
	
	private final DateFormat sourceFormat;
	private final DateFormat targetFormat;

	private final String sourceFormatDescription;
	private final String targetFormatDescription;

//	public DateToDateConverter(DateFormat dateformat) {
//		this( defaultDateFormat, df(dateformat));
//	}

	public DateToDateConverter(String dateRegexp) {
		this( YYYY_MM_DD, dateRegexp);
	}
	
	public DateToDateConverter(String sourceFormat, String dateformat) {
//		this( df(sourceFormat), df(dateformat));
		this.sourceFormatDescription = sourceFormat;
		this.targetFormatDescription = dateformat;
		this.sourceFormat = df(sourceFormat);
		this.targetFormat = (DateFormat) df(dateformat).clone();
	}
	
//	public DateToDateConverter(String sourceFormat, DateFormat dateformat) {
//		this( df(sourceFormat), dateformat);
//	}
//	private DateToDateConverter(DateFormat sourceFormat, String dateformat) {
//		this(sourceFormat, df(dateformat));
//	}
	
//	private DateToDateConverter(DateFormat sourceFormat, DateFormat dateformat) {
//		this.sourceFormat = df(sourceFormat);
//		this.targetFormat = df((DateFormat) dateformat.clone());
//	}
	
	private static DateFormat df(String dateFormat) {
		SimpleDateFormat res = new SimpleDateFormat(dateFormat);
		res.setLenient(false);
		return res;
	}
	
	private static DateFormat df(DateFormat df) {
//		DateFormat res = (DateFormat) df.clone();
		DateFormat res = df;
		res.setLenient(false);
		return res;
	}

	public String encode(String stringModel) {
		if (stringModel==null || "".equals(stringModel.trim())) return null;
		Date dt = null;
		try {
			synchronized (sourceFormat) {
				dt = sourceFormat.parse(stringModel);				
			}
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
		String res;
		synchronized (targetFormat) {
			res = targetFormat.format(dt); 	
		}
		return res;
	}
	
	public Converter getReverse() {
		synchronized (sourceFormat) {
			synchronized (targetFormat) {
				return new DateToDateConverter(targetFormatDescription, sourceFormatDescription);				
			}	
		}
	}

	public String getSourceFormatDescription() {
		return sourceFormatDescription;
	}

	public String getTargetFormatDescription() {
		return targetFormatDescription;
	}
	
	
}
