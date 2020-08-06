/**
 * 
 */
package it.nch.eb.common.converter;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.DateSizedConverterImpl;
import it.nch.eb.flatx.flatmodel.ConverterFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

final class DateToDateConverterFactory implements ConverterFactory {
	private static final long serialVersionUID = -5664817485996486576L;
	private DateFormat outputFormat;

	public DateToDateConverterFactory(DateFormat output) {
		this.outputFormat = output;
	}
	
	synchronized String formatOutput(Date data) {
		return outputFormat.format(data);
	}

	synchronized Date parseInput(DateFormat inputFormat, String stringModel) {
		try {
			Date res = null;
			res = inputFormat.parse(stringModel);
			return res;
		} catch (ParseException e) {
			throw new RuntimeException("error parsing " + stringModel + "using " + inputFormat,  e);			
		} 
	}

	public Converter createConverter(Converter converter) {
		if (!(converter instanceof DateSizedConverterImpl )) throw new IllegalStateException("Not a " + DateSizedConverterImpl.class.getName());
		final DateSizedConverterImpl theCnv = (DateSizedConverterImpl) converter;
		return new Converter() {

			private static final long serialVersionUID = 2446614619043404606L;

			public String encode(String stringModel) {
				if (stringModel == null)
					return null;
				DateFormat df = theCnv.getDateConverter().getDateFormat(stringModel);
				if (df == null)
					return null;
				Date dt = parseInput(df, stringModel);
				String res = formatOutput(dt);
				return res;
			}
			
		};
	}

}