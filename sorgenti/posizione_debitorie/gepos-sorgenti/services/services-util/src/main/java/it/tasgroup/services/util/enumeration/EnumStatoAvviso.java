package it.tasgroup.services.util.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum EnumStatoAvviso  {

	INSERITO	 (0,"I", "Inserito",         "??"),
	PRENOTATO    (1,"B", "Prenotato", "??"),
	INVIATO_KO   (2,"K", "Elaborato con Errore",                     "?"),
	IN_SPEDIZ    (3,"P", "In Elaborazione",                  "??"),
    INVIATO_OK   (4,"O", "OK",                     "??"),
    SKIPPED      (5,"N", "Da Non Notificare","??"),    
	ERROR        (-1,"E", "In Errore"                       ,                     "??");
	
	protected int  weigth;
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatoAvviso(int weigth, String chiave, String descrizione,String chiaveBundle) {
		this.weigth = weigth;
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public static EnumStatoAvviso getByKey(String chiave) {
		EnumStatoAvviso desiredItem = null;   // Default
		for (EnumStatoAvviso item : EnumStatoAvviso.values()) {
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
	
	public static EnumStatoAvviso getByDescription(String description) {
		EnumStatoAvviso desiredItem = null; // Default   
		for (EnumStatoAvviso item : EnumStatoAvviso.values()) {
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
	
	public static List<EnumStatoAvviso> getValueList(){
        List<EnumStatoAvviso> list = new ArrayList<EnumStatoAvviso>();
        list.addAll(Arrays.asList(EnumStatoAvviso.values()));
        
        return list;
    }
}	
	




