package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoIniziativa implements MessageDescription{
	
	SPONTANEO("Y","SPONTANEO","tributi.tributoEnte.INIZIATIVA.SI"),
	RICHIESTO("N", "RICHIESTO", "tributi.tributoEnte.INIZIATIVA.NO");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoIniziativa(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumTipoIniziativa getByKey(String chiave) {
		
		EnumTipoIniziativa desiredItem = null; // Default   
		
		for (EnumTipoIniziativa item : EnumTipoIniziativa.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}
	

}
