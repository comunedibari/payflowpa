package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoIncassoRid implements MessageDescription {

	ORDINARIO ("O", "RID ORDINARIO", ""),
	VELOCE ("V", "RID VELOCE", "");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumTipoIncassoRid(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}
	
	@Override
	public String getChiave() {
		return chiave;
	}

	@Override
	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}
	
	public static EnumTipoIncassoRid getByKey(String chiave) {
		EnumTipoIncassoRid desiredItem = null; // Default             
		for (EnumTipoIncassoRid item : EnumTipoIncassoRid.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				desiredItem = item;                     
				break;                 
				}             
			}             
		return desiredItem; 
	}

}
