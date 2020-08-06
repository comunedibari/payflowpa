package it.nch.is.fo.web;

public class ComboProperty {
	String description;
	String value;
	
	public ComboProperty(){
		
	}
	
	public ComboProperty(String value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static ComboProperty emptyComboProperty() {
		ComboProperty res = new ComboProperty();
		res.value = "";
		res.description = "";
		return res;
	}
	
	@Override
	public String toString() {
		return "ComboProperty{" +
		"description='" + description + '\'' +
		", value='" + value + '\'' +
		'}';
	}
}
