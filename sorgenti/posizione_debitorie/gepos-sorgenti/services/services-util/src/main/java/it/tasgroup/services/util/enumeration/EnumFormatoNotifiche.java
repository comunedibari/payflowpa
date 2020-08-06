package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumFormatoNotifiche implements MessageDescription{
	
	RFC_145_V010300("RFC_145_V010300","notifiche.formato.rfc145_v010300",""),
	RFC_145_V010302("RFC_145_V010302","notifiche.formato.rfc145_v010302",""),
	RFC_145_V010303("RFC_145_V010303","notifiche.formato.rfc145_v010303",""),	
	CSV_BASIC_V3("CSV_BASIC_V3","notifiche.formato.csv.basic.v3",""),
	CSV_BASIC_V2("CSV_BASIC_V2","notifiche.formato.csv.basic.v2",""),
	GTART("GTART","notifiche.formato.gtart",""),
	RECUP_PAGAMENTO("RECUP_PAGAMENTO","notifiche.formato.recup",""),
	NOT_PAG_V0100("NOT_PAG_V0100","notifiche.formato.v0100","");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumFormatoNotifiche(String chiave, String descrizione, String chiaveBundle) {
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
	
    public static List<EnumFormatoNotifiche> getValueList(){
    	
        List<EnumFormatoNotifiche> list = new ArrayList<EnumFormatoNotifiche>();
        
        list.addAll(Arrays.asList(EnumFormatoNotifiche.values()));
        
        return list;
    }
    
}
