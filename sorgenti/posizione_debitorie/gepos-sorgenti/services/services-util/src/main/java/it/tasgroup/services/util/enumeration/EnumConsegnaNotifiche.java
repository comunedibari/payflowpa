package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumConsegnaNotifiche implements MessageDescription{
	
	PUSH_CART("PUSH_CART","notifiche.consegna.push.cart",""),
	PUSH_WS_GTART("PUSH_WS_GTART","notifiche.consegna.push.ws.gtart",""),
	PULL_NOTIFICATION_WS("PULL_WS","notifiche.consegna.pull.ws",""),	
	PUSH_WS_RFC145("PUSH_WS_RFC145","notifiche.consegna.push_ws_rfc145",""),
	RECUP_WS_PAGAMENTO("RECUP_WS","notifiche.consegna.recup_ws_pagamento",""),
	PUSH_WS_NOT_PAG("PUSH_WS_NOT_PAG","notifiche.consegna.push.ws.standard","");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumConsegnaNotifiche(String chiave, String descrizione, String chiaveBundle) {
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
	
    public static List<EnumConsegnaNotifiche> getValueList(){
    	
        List<EnumConsegnaNotifiche> list = new ArrayList<EnumConsegnaNotifiche>();
        
        list.addAll(Arrays.asList(EnumConsegnaNotifiche.values()));
        
        return list;
    }
    
}
