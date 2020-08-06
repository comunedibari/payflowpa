package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumFrequenzaNotifiche implements MessageDescription{
	
	MAI("MAI","notifiche.frequenza.mai",""),
    AD_EVENTO("AD_EVENTO","notifiche.frequenza.adEvento",""),
    GIORNALIERO("GIORNALIERO","notifiche.frequenza.giornaliero",""),
    OGNI_12_ORE("OGNI_12_ORE","notifiche.frequenza.dodici.ore",""),
    OGNI_6_ORE("OGNI_6_ORE","notifiche.frequenza.sei.ore","");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumFrequenzaNotifiche(String chiave, String descrizione, String chiaveBundle) {
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
	
    public static List<EnumFrequenzaNotifiche> getValueList(){
    	
        List<EnumFrequenzaNotifiche> list = new ArrayList<EnumFrequenzaNotifiche>();
        
        list.addAll(Arrays.asList(EnumFrequenzaNotifiche.values()));
        
        return list;
    }
    
}
