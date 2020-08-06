/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.extractconverters;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.ReversibleConverter;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.exceptions.ConversionException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * convert a string <code>str</code> containing a date <code>d</code> in a string containig a long <code>l</code> 
 * corersponding the timestamp of the date
 * 
 * @author gdefacci
 */
public class DateExtractConverter implements SizedConverter, ReversibleConverter {

	private static final long	serialVersionUID	= -2853139295476621528L;
	final DateFormat 	format;
	final Integer		length;
	
	public DateExtractConverter(String dateFormat) {
		this( new SimpleDateFormat( dateFormat ), new Integer(dateFormat.length()));
	}
	
	public DateExtractConverter(DateFormat format, Integer length) {
		this.format = format;
		this.format.setLenient(false);
		this.length = length;
	}

	public String encode(String stringModel) {
		if (isEmptyOrNull(stringModel)) return null;  // FIXME: riutilizzare logica in it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter
		Date d = null;
		try {
			synchronized (format) {
				d = format.parse(stringModel);				
			}
		} catch (ParseException e) {
			throw new ConversionException("could not convert to date the string " + stringModel, e, this);
		}
		return String.valueOf(d.getTime());
	}

	private boolean isEmptyOrNull(String stringModel) {
		return stringModel==null || "".equals(stringModel.trim());
	}

	public Integer getLength() {
		return length;
	}

	public String toString() {
		return StringUtils.getSimpleName(getClass());
	}

	public Converter getReverse() {
		final DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss \'CEST\' yyyy", Locale.ENGLISH);
		return new Converter() {

			private static final long	serialVersionUID	= 1977007667983473488L;

			public String encode(String stringModel) {
				try {
					if (isEmptyOrNull(stringModel)) return null;
					Date date = df.parse(stringModel);
					return format.format(date);
				} catch (ParseException e) {
					throw new ConversionException(e);
				}
			}
			
		};
	}

}
