package it.tasgroup.iris2.enums;

public enum EnumIuvSequenceType {

	ENTE 		("E", "Unica per ente", "ENTE"),
	TRIBUTOENTE	("T", "Una per tributo", "TRIBUTO");
	
	private String chiave;
	private String descrizione;
	private String propertyValue;

	private EnumIuvSequenceType(String chiave, String descrizione,String propertyValue) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.propertyValue = propertyValue;
	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
	public String getPropertyValue() {
		return propertyValue;
	}

	public static EnumIuvSequenceType getByKey(String chiave) {
		EnumIuvSequenceType desiredItem = null; // Default   
		for (EnumIuvSequenceType item : EnumIuvSequenceType.values()) {
			if (item.getChiave().equals(chiave)) {  
				desiredItem = item;   
				break;            
				}             
			}  
		return desiredItem; 
	}
	
	public static String getDescriptionByKey(String key) {
		return getByKey(key) != null && getByKey(key).getDescrizione() != null ? getByKey(key).getDescrizione() : "";
	}
	
	public static EnumIuvSequenceType getByDescription(String description) {
		EnumIuvSequenceType desiredItem = null; // Default   
		for (EnumIuvSequenceType item : EnumIuvSequenceType.values()) {
			if (item.getDescrizione().equals(description)) {  
				desiredItem = item;   
				break;            
				}             
			}  
		return desiredItem;
	}
	
	public static String getKeyByDescription(String description) {
		return getByDescription(description).getChiave();
	}
	
	public static EnumIuvSequenceType getByPropertyValue(String propertyValue) {
		EnumIuvSequenceType desiredItem = null; // Default   
		for (EnumIuvSequenceType item : EnumIuvSequenceType.values()) {
			if (item.getPropertyValue().equals(propertyValue)) {  
				desiredItem = item;   
				break;            
				}             
			}  
		return desiredItem; 
	}
	
	public static String getDescriptionByPropertyValue(String propertyValue) {
		return getByPropertyValue(propertyValue).getDescrizione();
	}
	
	public static String getKeyByPropertyValue(String propertyValue) {
		return getByPropertyValue(propertyValue).getChiave();
	}
	
	

}
