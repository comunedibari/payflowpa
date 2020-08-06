package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoPredeterminato implements MessageDescription{
	
	NON_PREDETERMINATO("N","Libero","backoffice.tributi.searchCategoriaTributo.PREDETERMINABILE.NO"),
	PREDEDERMINATO("Y", "Predeterminato", "backoffice.tributi.searchCategoriaTributo.PREDETERMINABILE.SI");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoPredeterminato(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumTipoPredeterminato getByKey(String chiave) {
		
		EnumTipoPredeterminato desiredItem = null; // Default   
		
		for (EnumTipoPredeterminato item : EnumTipoPredeterminato.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}
	

}
