package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoRiconciliazione implements MessageDescription{
	
	INCORSO("INCORSO","Riconciliazione In Corso","",(short)3),
	NON_RICONCILIATO("NON_RICONCILIATO","Non Riconciliato","",(short)2),
	RICONCILIATO("RICONCILIATO","Riconciliazione eseguita","",(short)1),
	DA_ANALIZZARE("DA_ANALIZZARE","Da Analizzare","",(short)0);
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private short shortValue;

	private EnumStatoRiconciliazione(String chiave, String descrizione, String chiaveBundle, short shortValue) {
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.shortValue = shortValue;
		
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
	
	public static EnumStatoRiconciliazione getByKey(String chiave) {
		
		EnumStatoRiconciliazione desiredItem = null; // Default   
		
		for (EnumStatoRiconciliazione item : EnumStatoRiconciliazione.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}        
		
		return desiredItem; 
	}
	
	public static EnumStatoRiconciliazione getByShortValue(short shortValue) {
		
		EnumStatoRiconciliazione desiredItem = null; // Default   
		
		for (EnumStatoRiconciliazione item : EnumStatoRiconciliazione.values()) {
			
			if (item.getShortValue() == shortValue) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}        
		
		return desiredItem; 
	}

	public short getShortValue() {
		return shortValue;
	}

	public void setShortValue(short shortValue) {
		this.shortValue = shortValue;
	}
	

}
