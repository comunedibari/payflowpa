package it.tasgroup.idp.billerservices.plugin.csv;

public class CSVBasicConfig {

	private char fieldSeparator;
	private char quote;
	private char complexFieldSeparator;

	public CSVBasicConfig(char fieldSeparator, char quote, char complexFieldSeparator) {
		this.fieldSeparator = fieldSeparator;
		this.quote = quote;
		this.complexFieldSeparator = complexFieldSeparator;
	}

	public char getFieldSeparator() {
		return fieldSeparator;
	}

	private void setFieldSeparator(char fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	public char getComplexFieldSeparator() {
		return complexFieldSeparator;
	}

	private void setComplexFieldSeparator(char complexFieldSeparator) {
		this.complexFieldSeparator = complexFieldSeparator;
	}

	public char getQuote() {
		return quote;
	}

	private void setFieldEscape(char quote) {
		this.quote = quote;
	}

}
