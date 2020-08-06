/**
 * Created on 09/mar/2009
 */
package it.nch.eb.flatx.xmlrecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.xpath.internal.VariableStack;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.ReversibleConverter;


/**
 * @author gdefacci
 */
public class StringtoBooleanIntegerConverter implements ReversibleConverter, Converter {
	
	private static final long	serialVersionUID	= 4269316962863382294L;
	private String value = "";
	
	
	public StringtoBooleanIntegerConverter() {
//		this.value = value;	
	}
//	private StringtoBooleanIntegerConverter(DateFormat dateFormat) {
//		this.dateFormat = dateFormat;
//	}

	public Converter getReverse() {
		return new Converter() {

			private static final long	serialVersionUID	= -5504832375430487953L;

			public String encode(String stringModel) {
				synchronized (value) {
					try {
						
						if ("true".equals(value) || "1".equals(stringModel)) {
							return  "1";
						} else {
							return  "0";
						}
						
//						return dateFormat.format( new Date( Long.valueOf(stringModel).longValue() ) );
						
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException(stringModel + " is not a valid millis");
					}						
				}
			}

		};
	}

	public String encode(String stringModel) {
//		try {
			Date dt = null; 
			synchronized (value) {
				
				if ("true".equals(stringModel) || "1".equals(stringModel)) {
					return  "1";
				} else {
					return  "0";
				}
				
			}
//			return String.valueOf(dt.getTime());
//		} catch (ParseException e) {
//			throw new IllegalArgumentException("error parsing " + stringModel + " using " + dateFormat);
//		}
	}
	

	public String toString() {
		// TODO Auto-generated method stub
		return StringtoBooleanIntegerConverter.class.getName() ;
	}
}
