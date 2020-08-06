package it.nch.eb.stringtemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.antlr.stringtemplate.AttributeRenderer;

public class BigDecimalRenderer implements AttributeRenderer {

	@Override
	public String toString(Object arg0) {
		// TODO Auto-generated method stub
		return arg0.toString();
	}

	@Override
	public String toString(Object arg0, String format) {

		BigDecimal bigDformat = ((BigDecimal)arg0).multiply(new BigDecimal("100"));

		NumberFormat nf = NumberFormat.getNumberInstance();
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern("#############.##");
		String value = df.format(bigDformat);
		System.out.println("value format = " + value + "format = " + format);


		if (format.equalsIgnoreCase("importo")) {
				String newValue = String.format("%1$#" + 13 + "s", value);
				return newValue.replace(" ", "0");
		} else if (format.equalsIgnoreCase("importoTotale")) {
				String newValue = String.format("%1$#" + 15 + "s", value);
				return newValue.replace(" ", "0");
		} else {
			return (String)arg0;
		}

	}

}
