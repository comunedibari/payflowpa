package it.nch.erbweb.web.util;


public class ComboOption  {
	private String label;
	private String value;
	
	public ComboOption() {
		this.label = " ";
		this.value = "";
	}

	public ComboOption(String value, String description) {
		this.label = description;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}
	public String getValue() {
		return value;
	}
}
