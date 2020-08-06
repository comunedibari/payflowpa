package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoTributario implements MessageDescription{
	
	TRIBUTARIO("T", "Tributario","backoffice.tributi.searchCategoriaTributo.TIPOENTRATA.TRIBUTARIA"),
	EXTRA_TRIBUTARIO("E", "Extra Tributario", "backoffice.tributi.searchCategoriaTributo.TIPOENTRATA.EXTRATRIBUTARIA");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoTributario(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumTipoTributario getByKey(String chiave) {
		
		EnumTipoTributario desiredItem = null; // Default   
		
		for (EnumTipoTributario item : EnumTipoTributario.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}
	

}
