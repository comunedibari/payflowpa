/**
 * Created on 09/mar/2009
 */
package it.nch.eb.flatx.xmlrecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.ReversibleConverter;


/**
 * @author gdefacci
 */
public class DateToMillisConverter implements ReversibleConverter, Converter {
	
	private static final long	serialVersionUID	= 4269316962863382294L;
	private final DateFormat dateFormat;
	
	public DateToMillisConverter(String dateFormat) {
		this(new SimpleDateFormat(dateFormat));	
	}
	private DateToMillisConverter(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Converter getReverse() {
		return new Converter() {

			private static final long	serialVersionUID	= -5504832375430487953L;

			public String encode(String stringModel) {
				synchronized (dateFormat) {
					try {
						return dateFormat.format( new Date( Long.valueOf(stringModel).longValue() ) );
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException(stringModel + " is not a valid millis");
					}						
				}
			}

		};
	}

	public String encode(String stringModel) {
		try {
			Date dt = null; 
			synchronized (dateFormat) {
				dt = dateFormat.parse(stringModel);			
			}
			return String.valueOf(dt.getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException("error parsing " + stringModel + " using " + dateFormat);
		}
	}
	

	public String toString() {
		// TODO Auto-generated method stub
		return DateToMillisConverter.class.getName() + "(" + dateFormat + ")";
	}
}
