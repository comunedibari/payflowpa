package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumCategoriaIntestatario implements MessageDescription { 

	CI("CI","Cittadino","enum.categoria.intestatario.cittadino"),
	EN("EN","Ente","enum.categoria.intestatario.ente"),
	AZ("AZ","Azienda","enum.categoria.intestatario.azienda"),
	BO("BO", "Backoffice", "enum.categoria.intestatario.backoffice");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumCategoriaIntestatario(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}
	
	@Override
	public String getChiave() {
		return this.chiave;
	}
	
	@Override
	public String getDescrizione() {
		return this.descrizione;
	}
	
	@Override
	public String getChiaveBundle() {
		return this.chiaveBundle;
	}
	
	public static EnumCategoriaIntestatario getByKey(String chiave) {
	    EnumCategoriaIntestatario desiredItem = null; // Default
        for (EnumCategoriaIntestatario item : EnumCategoriaIntestatario.values()) {
            if (item.getChiave().equals(chiave)) {
                desiredItem = item;
                break;
            }
        }
        return desiredItem;
    }
}