package it.tasgroup.idp.billerservices.api.impl;

import static  it.tasgroup.idp.billerservices.api.impl.BusinessComparatorHelper.areEquals;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.VociPagamento;

public class CondizioniComparator {
	
	public boolean compare(CondizioniPagamento c1, CondizioniPagamento c2, boolean includeStatus) {
	
		if (!areEquals(c1.getCdTrbEnte(),c2.getCdTrbEnte())) return false;
		if (!areEquals(c1.getCoCip(),c2.getCoCip())) return false;
		if (!areEquals(c1.getDtFinevalidita(),c2.getDtFinevalidita())) return false;
		if (!areEquals(c1.getDtIniziovalidita(),c2.getDtIniziovalidita())) return false;
		if (!areEquals(c1.getDtScadenza(),c2.getDtScadenza())) return false;
		if (!areEquals(c1.getIdEnte(),c2.getIdEnte())) return false;
		//if (!areEquals(c1.getIdPagamento(),c2.getIdPagamento())) return false; // sono uguali in quanto il confronto basao su questa uguaglianza
		if (!areEquals(c1.getImTotale(),c2.getImTotale())) return false;
		if (!areEquals(c1.getTiCip(),c2.getTiCip())) return false;
		if (!areEquals(c1.getTiPagamento(),c2.getTiPagamento())) return false;
		if (!areEquals(c1.getIbanBeneficiario(),c2.getIbanBeneficiario())) return false;
		if (!areEquals(c1.getRagioneSocialeBeneficiario(),c2.getRagioneSocialeBeneficiario())) return false;
		if (!areEquals(c1.getCausalePagamento(),c2.getCausalePagamento())) return false;

		// Confronto delle voci
			
		if (isEmpty(c1.getVociPagamento())  && !isEmpty(c2.getVociPagamento()) ) return false;
		if (!isEmpty(c1.getVociPagamento())  && isEmpty(c2.getVociPagamento()) ) return false;

		if (c1.getVociPagamento()!=null && c2.getVociPagamento()!=null) {

			if (c1.getVociPagamento().size() != c2.getVociPagamento().size()) return false;
			
		 
			// Creo una mappa dei destinatari di p2 per rapida ricerca per "codice fiscale".
			
			HashMap<String, VociPagamento> c2VociMap = new HashMap<String, VociPagamento>();
			for (VociPagamento v:c2.getVociPagamento())  {
				c2VociMap.put(v.getCoVoce(), v);
			}
			
			// Ciclo sulle voci di p1 e cerco corrispondenza con quelli di p2, mon appena non la trovo esco.
			
			for (VociPagamento v1:c1.getVociPagamento()) {
				VociPagamento v2 = c2VociMap.get(v1.getCoVoce());
				if (v2==null) return false;
				boolean vociUguali = (new VociComparator()).compare(v1,v2);
				if (!vociUguali)  return false;
			}
		
		}

		// Opzionalmente includo lo stato nella comparazione.
		if (includeStatus) {
			if (!areEquals(c1.getStPagamento(),c2.getStPagamento())) return false;
			if (!areEquals(c1.getImPagamento(),c2.getImPagamento())) return false;
			if (!areEquals(c1.getDeCanalepag(),c2.getDeCanalepag())) return false;
			if (!areEquals(c1.getDeMezzopagamento(),c2.getDeMezzopagamento())) return false;
			if (!areEquals(c1.getDeNotepagamento(),c2.getDeNotepagamento())) return false;
			if (!areEquals(c1.getDtPagamento(),c2.getDtPagamento())) return false;			
		}
		
		
//  Campi ignorati in quanto gestionali		
//		if (!areEquals(c1.getTsDecorrenza(),c2.getTsDecorrenza())) return false;
//		if (!areEquals(c1.getPendenze(),c2.getPendenze())) return false;
//		if (!areEquals(c1.getTsAnnullamentoMillis(),c2.getTsAnnullamentoMillis())) return false;
//		if (!areEquals(c1.getOpInserimento(),c2.getOpInserimento())) return false;
//		if (!areEquals(c1.getOpAggiornamento(),c2.getOpAggiornamento())) return false;
//		if (!areEquals(c1.getTsInserimento(),c2.getTsInserimento())) return false;
//		if (!areEquals(c1.getTsAggiornamento(),c2.getTsAggiornamento())) return false;
//		if (!areEquals(c1.getIdCondizione(),c2.getIdCondizione())) return false;
//		if (!areEquals(c1.getOpAnnullamento(),c2.getOpAnnullamento())) return false;
//		if (!areEquals(c1.getPrVersione(),c2.getPrVersione())) return false;
//		if (!areEquals(c1.getStRiga(),c2.getStRiga())) return false;
//		if (!areEquals(c1.getTsAnnullamento(),c2.getTsAnnullamento())) return false;


// Sulle voci e allegati comparator specifici. 
//		if (!areEquals(c1.getAllegatiPendenza(),c2.getAllegatiPendenza())) return false;
//		if (!areEquals(c1.getVociPagamento(),c2.getVociPagamento())) return false;
		
			
		return true;
	}
	
	
	private boolean isEmpty(Set<VociPagamento> setVoci) {
		return setVoci==null  || setVoci.size()==0;
	}

	public static void main(String[] args) {
		
		String  template = "if (!areEquals(c1.$1(),c2.$1())) return false;";
		
		for (Method m:CondizioniPagamento.class.getMethods()) {
			if (m.getName().startsWith("get")) {
				System.out.println(template.replaceAll("[$][1]", m.getName()));
			}	
		}
		
	}

	
}
