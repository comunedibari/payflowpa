package it.nch.eb.stringtemplate;

import it.nch.eb.common.utils.StringUtils;

import org.antlr.stringtemplate.AttributeRenderer;

public class IntegerRenderer implements AttributeRenderer {

	@Override
	public String toString(Object arg0) {
		// TODO Auto-generated method stub
		return arg0.toString();
	}

	@Override
	public String toString(Object arg0, String format) {
		// TODO Auto-generated method stub
		System.out.println("integer format = " + format);
		Integer integ = (Integer)arg0;
		String value = integ.toString();

		StringUtils util = new StringUtils();


		if (format.equalsIgnoreCase("padLeft")) {
			String newValue = String.format("%1$#" + 7 + "s", value);
			return newValue.replace(" ", "0");
		} else if (format.equalsIgnoreCase("importo")) {
			String newValue = String.format("%1$#" + 13 + "s", value);
			return newValue.replace(' ', '0');
//			return util.leftPad(value.toString(), 13, '0');
		} else if (format.equalsIgnoreCase("padNumDispo")) {
			String newValue = String.format("%1$#" + 7 + "s", value);
			return newValue.replace(" ", "0");
		} else if (format.equalsIgnoreCase("padNumRecord")) {
			String newValue = String.format("%1$#" + 6 + "s", value);
			return newValue.replace(" ", "0");
		} else {
			return (String)arg0;
		}
	}

}
