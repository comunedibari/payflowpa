package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumCanaleCbill implements MessageDescription{
	
	WEB("Web","WEB",""),
    ATM("ATM","ATM",""),
    BRANCH("Branch","FILIALE BANCA","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumCanaleCbill(String chiave, String descrizione, String chiaveBundle) {
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
	
    public static List<EnumCanaleCbill> getValueList(){
        List<EnumCanaleCbill> list = new ArrayList<EnumCanaleCbill>();
        list.addAll(Arrays.asList(EnumCanaleCbill.values()));
        
        return list;
    }
}
