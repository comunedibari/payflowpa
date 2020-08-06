package it.nch.eb.stringtemplate;

import java.text.SimpleDateFormat;

import org.antlr.stringtemplate.AttributeRenderer;

public class DateRenderer implements AttributeRenderer {

	@Override
	public String toString(Object arg0) {
		// TODO Auto-generated method stub
		return arg0.toString();
	}

	@Override
	public String toString(Object arg0, String format) {

		SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyy");
		String value = formatDate.format((java.sql.Timestamp)arg0);

		if (format.equalsIgnoreCase("data")) {
			   return value;
		} else {
			return (String)arg0;
		}
	}

}
