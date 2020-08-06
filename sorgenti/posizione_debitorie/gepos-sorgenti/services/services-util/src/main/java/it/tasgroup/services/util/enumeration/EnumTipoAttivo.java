package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoAttivo implements MessageDescription{
	
	ATTIVO("A", "Attivo","backoffice.confPagModify.STATO_ATTIVO","testonormale"),
	DISATTIVO("D", "Non Attivo", "backoffice.confPagModify.STATO_DISATTIVO","testodisattivo");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private String styleClass;

	private EnumTipoAttivo(String chiave, String descrizione, String chiaveBundle, String styleClass) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.styleClass = styleClass;
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

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public static EnumTipoAttivo getByKey(String chiave) {
		
		EnumTipoAttivo desiredItem = EnumTipoAttivo.DISATTIVO; // Default   
		
		for (EnumTipoAttivo item : EnumTipoAttivo.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}

	public boolean isAttivo() {
		
		return this.equals(ATTIVO);
	}
	
	public boolean isDisattivo() {
		
		return this.equals(DISATTIVO);
	}
	

}
