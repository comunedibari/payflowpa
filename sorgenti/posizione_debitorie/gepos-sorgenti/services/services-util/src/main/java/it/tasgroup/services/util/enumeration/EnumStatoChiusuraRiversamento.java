package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoChiusuraRiversamento implements MessageDescription{
	
	CHIUSA("CHIUSA", "Chiusa", "",(short)1, "on"),	
	APERTA("APERTA", "Aperta", "", (short)0, "off");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private short value;
	private String checkValue;

	private EnumStatoChiusuraRiversamento(String chiave, String descrizione, String chiaveBundle, short value, String checkValue) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.value = value;
		this.checkValue = checkValue;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public void setChiaveBundle(String chiaveBundle) {
		this.chiaveBundle = chiaveBundle;
	}

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}
	
	public static EnumStatoChiusuraRiversamento getByCheckValue(String checkValue) {
		
		EnumStatoChiusuraRiversamento desiredItem = null; // Default 
		
		for (EnumStatoChiusuraRiversamento item : EnumStatoChiusuraRiversamento.values()) {      
			
			if (item.getCheckValue().equals(checkValue)) {           
				
				desiredItem = item; 
				
				break;          
				
				}   
			
			}       
		
		return desiredItem; 
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	

}
