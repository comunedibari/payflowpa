package it.tasgroup.idp.billerservices.api.impl;

import static  it.tasgroup.idp.billerservices.api.impl.BusinessComparatorHelper.areEquals;

import java.lang.reflect.Method;

import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.VociPagamento;

public class VociComparator {

	public boolean  compare(VociPagamento v1, VociPagamento v2) {
				
		if (!areEquals(v1.getCoAccertamento(),v2.getCoAccertamento())) return false;
		if (!areEquals(v1.getCoCapbilancio(),v2.getCoCapbilancio())) return false;
		if (!areEquals(v1.getCoVoce(),v2.getCoVoce())) return false;
		if (!areEquals(v1.getDeVoce(),v2.getDeVoce())) return false;
		if (!areEquals(v1.getImVoce(),v2.getImVoce())) return false;

// Campi gestionali non considerati			
//		if (!areEquals(v1.getPrVersione(),v2.getPrVersione())) return false;
//		if (!areEquals(v1.getStRiga(),v2.getStRiga())) return false;
//		if (!areEquals(v1.getTiVoce(),v2.getTiVoce())) return false;
//		if (!areEquals(v1.getTsDecorrenza(),v2.getTsDecorrenza())) return false;
//		if (!areEquals(v1.getCondizioniPagamento(),v2.getCondizioniPagamento())) return false;
//		if (!areEquals(v1.getOpInserimento(),v2.getOpInserimento())) return false;
//		if (!areEquals(v1.getOpAggiornamento(),v2.getOpAggiornamento())) return false;
//		if (!areEquals(v1.getTsInserimento(),v2.getTsInserimento())) return false;
//		if (!areEquals(v1.getTsAggiornamento(),v2.getTsAggiornamento())) return false;		
//		if (!areEquals(v1.getIdVoce(),v2.getIdVoce())) return false;
//		if (!areEquals(v1.getIdPendenza(),v2.getIdPendenza())) return false;
		
		return true;
	}
	
	
	public static void main(String[] args) {
		
		String  template = "if (!areEquals(v1.$1(),v2.$1())) return false;";
		
		for (Method m:VociPagamento.class.getMethods()) {
			if (m.getName().startsWith("get")) {
				System.out.println(template.replaceAll("[$][1]", m.getName()));
			}	
		}
		
	}

	
}
