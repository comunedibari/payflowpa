package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoFlussoRiconciliazione implements MessageDescription{
	
	RE("RE","Flusso di Rendicontazione","")
	//,
	//GC("GC", "GC", "")
	;
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoFlussoRiconciliazione(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
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
	
	public static EnumTipoFlussoRiconciliazione getByKey(String chiave) {
		
		EnumTipoFlussoRiconciliazione desiredItem = null; // Default   
		
		for (EnumTipoFlussoRiconciliazione item : EnumTipoFlussoRiconciliazione.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}
	

}
