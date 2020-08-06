/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;
import it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter;


/**
 * @author gdefacci
 */
public class ToYearConverter extends TypeSafeToObjectConverter implements ToObjectConverter {

	private static final long serialVersionUID = -8619622661523054104L;

	public ToYearConverter() {
		super(Integer.class);
	}

	public Object safeConvert(String str) {
		
		String testYear = "2013+02:00";
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("yyyy");
		Date data;
		Calendar cal = Calendar.getInstance();
		try {
			data = format.parse(str);			
			//gestisco come calendar
			cal.setTime(data);
					
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return Integer.valueOf(cal.get(cal.YEAR));
	}
	
}
