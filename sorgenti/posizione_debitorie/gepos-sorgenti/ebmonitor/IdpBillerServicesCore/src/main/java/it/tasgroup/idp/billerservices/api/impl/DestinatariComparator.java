package it.tasgroup.idp.billerservices.api.impl;

import static it.tasgroup.idp.billerservices.api.impl.BusinessComparatorHelper.areEquals;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;

import java.lang.reflect.Method;

public class DestinatariComparator {
	
	public boolean compare(DestinatariPendenze d1, DestinatariPendenze d2) {
		
		if (!areEquals(d1.getCoDestinatario(),d2.getCoDestinatario())) return false;
		if (!areEquals(d1.getTiDestinatario(),d2.getTiDestinatario())) return false;
		if (!areEquals(d1.getDeDestinatario(),d2.getDeDestinatario())) return false;

// Campi ignorati in quanto gestionali
//		if (!areEquals(d1.getIdDestinatario(),d2.getIdDestinatario())) return false;
//		if (!areEquals(d1.getTsDecorrenza(),d2.getTsDecorrenza())) return false;
//		if (!areEquals(d1.getPendenze(),d2.getPendenze())) return false;
//		if (!areEquals(d1.getOpInserimento(),d2.getOpInserimento())) return false;
//		if (!areEquals(d1.getOpAggiornamento(),d2.getOpAggiornamento())) return false;
//		if (!areEquals(d1.getTsInserimento(),d2.getTsInserimento())) return false;
//		if (!areEquals(d1.getTsAggiornamento(),d2.getTsAggiornamento())) return false;
//		if (!areEquals(d1.getPrVersione(),d2.getPrVersione())) return false;
//		if (!areEquals(d1.getStRiga(),d2.getStRiga())) return false;
		
		return true;
	}

	public static void main(String[] args) {
		
		String  template = "if (!areEquals(d1.$1(),d2.$1())) return false;";
		
		for (Method m:DestinatariPendenze.class.getMethods()) {
			if (m.getName().startsWith("get")) {
				System.out.println(template.replaceAll("[$][1]", m.getName()));
			}	
		}
		
	}
	
}
