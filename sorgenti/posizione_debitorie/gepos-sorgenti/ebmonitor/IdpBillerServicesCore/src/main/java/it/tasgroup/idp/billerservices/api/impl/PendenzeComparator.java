package it.tasgroup.idp.billerservices.api.impl;

import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.iuv.IUVPosizEnteMap;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import static  it.tasgroup.idp.billerservices.api.impl.BusinessComparatorHelper.areEquals;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;

public class PendenzeComparator {
	
	boolean compareOnlyMainFields=true;
	
	public PendenzeComparator(boolean compareOnlyMainFields) {
		this.compareOnlyMainFields=compareOnlyMainFields;
	}


    // p1 e la pendenza memorizzata su database 
	// p2 e la pendenza costruita utilizzando i dati della chiamata
	// 
	// quindi se iuv generato da piattaforma le condizioni in p1 che contengono in idPagamento lo iuv le non 
	// il valore fornito dall ente. Per effettuare il confronto p1-p2  devo in questo caso recuperare preventivamente
	// il valore idPagamento dalla tabella IUVPosizEnteMap
	public boolean compare(Pendenze p1, Pendenze p2, boolean includeStatus, boolean recursive,EntityManager em, TributiEnti te) {

		
// Confronto i principali campi di business		
		if (!areEquals(p1.getAnnoRiferimento(),p2.getAnnoRiferimento())) return false;
		if (!areEquals(p1.getCdTrbEnte(),p2.getCdTrbEnte())) return false;
		if (!areEquals(p1.getDeCausale(),p2.getDeCausale())) return false;
		if (!areEquals(p1.getDvRiferimento(),p2.getDvRiferimento())) return false;
		if (!areEquals(p1.getFlModPagamento(),p2.getFlModPagamento())) return false;
		if (!areEquals(p1.getIdEnte(),p2.getIdEnte())) return false;
		if (!areEquals(p1.getIdPendenzaente(),p2.getIdPendenzaente())) return false;
		if (!areEquals(p1.getIdSystem(),p2.getIdSystem())) return false;
		if (!areEquals(p1.getIdTributo(),p2.getIdTributo())) return false;
		if (!areEquals(p1.getRiferimento(),p2.getRiferimento())) return false;
		if (!areEquals(p1.getImTotale(),p2.getImTotale())) return false;
		if (!areEquals(p1.getNote(),p2.getNote())) return false;


// Confronto opzionalmente anche gli altri campi di business (nei formati semplificati questi campi NON vengono passati e 
// quindi non ha senso confrontarli.
		if (!compareOnlyMainFields) {
			if (!areEquals(p1.getCoAbi(),p2.getCoAbi())) return false;
			if (!areEquals(p1.getCoRiscossore(),p2.getCoRiscossore())) return false;
			if (!areEquals(p1.getDeMittente(),p2.getDeMittente())) return false;
			if (!areEquals(p1.getIdMittente(),p2.getIdMittente())) return false;
			if (!areEquals(p1.getTsCreazioneente(),p2.getTsCreazioneente())) return false;
			if (!areEquals(p1.getTsDecorrenza(),p2.getTsDecorrenza())) return false;
			if (!areEquals(p1.getTsEmissioneente(),p2.getTsEmissioneente())) return false;
			if (!areEquals(p1.getTsModificaente(),p2.getTsModificaente())) return false;
			if (!areEquals(p1.getTsPrescrizione(),p2.getTsPrescrizione())) return false;
		}
		
		
		// Confronto dei destinatari
		
		if (p1.getDestinatariPendenze().size() != p2.getDestinatariPendenze().size()) return false;
		
		// Creo una mappa dei destinatari di p2 per rapida ricerca per "codice fiscale".
		
		HashMap<String, DestinatariPendenze> p2DestinatariMap = new HashMap<String, DestinatariPendenze>();
		for (DestinatariPendenze d:p2.getDestinatariPendenze())  {
			p2DestinatariMap.put(d.getCoDestinatario(), d);
		}
		
		// Ciclo sui destinatari di p1 e cerco corrispondenza con quelli di p2, mon appena non la trovo esco.
		
		for (DestinatariPendenze d1:p1.getDestinatariPendenze()) {
			DestinatariPendenze d2 = p2DestinatariMap.get(d1.getCoDestinatario());
			if (d2==null) return false;
			boolean destinatariUguali = (new DestinatariComparator()).compare(d1,d2);
			if (!destinatariUguali)  return false;
		}
		
		// Opzionalmente: confronto anche lo stato
		
		if (includeStatus) {
			if (!areEquals(p1.getStPendenza(),p2.getStPendenza())) return false;
		}
	
		// Opzionamente: confronto ricorsivamente le condizioni
		
		if (recursive) {
			
			if (p1.getCondizioniPagamento().size() != p2.getCondizioniPagamento().size()) return false;
			
			// Creo una mappa delle condizioni di p2 per rapida ricerca per "id_pagamento".
			
			HashMap<String, CondizioniPagamento> p2CondizioniMap = new HashMap<String, CondizioniPagamento>();
			for (CondizioniPagamento c:p2.getCondizioniPagamento())  {
				p2CondizioniMap.put(c.getIdPagamento(), c);
			}
			
			// Ciclo sulle condizioni di p1 e cerco corrispondenza con quelle di p2, mon appena non la trovo esco.
			
			for (CondizioniPagamento c1:p1.getCondizioniPagamento()) {
				// confronto le condizioni recupero l'idPagamento in caso in cui lo iuv sia generato
				String c1IdPagamento = getIdPagamento(c1.getIdPagamento(),te,em);
				CondizioniPagamento c2 = p2CondizioniMap.get(c1IdPagamento);
				if (c2==null) return false;
				boolean condizioniUguali = (new CondizioniComparator()).compare(c1,c2,includeStatus);
				if (!condizioniUguali)  return false;
			}
			
		}
		
		
		
// Campi gestionali: MAI controllati in quanto non hanno significato di business
//		if (!areEquals(p1.getIdPendenza(),p2.getIdPendenza())) return false;
//		if (!areEquals(p1.getOpAnnullamento(),p2.getOpAnnullamento())) return false;
//		if (!areEquals(p1.getPrVersione(),p2.getPrVersione())) return false;
//		if (!areEquals(p1.getStRiga(),p2.getStRiga())) return false;
//		if (!areEquals(p1.getTsAnnullamento(),p2.getTsAnnullamento())) return false;
//		if (!areEquals(p1.getIdTributoStrutturato(),p2.getIdTributoStrutturato())) return false;
//		if (!areEquals(p1.getTsAnnullamentoMillis(),p2.getTsAnnullamentoMillis())) return false;
//		if (!areEquals(p1.getOpInserimento(),p2.getOpInserimento())) return false;
//		if (!areEquals(p1.getOpAggiornamento(),p2.getOpAggiornamento())) return false;
//		if (!areEquals(p1.getTsInserimento(),p2.getTsInserimento())) return false;
//		if (!areEquals(p1.getTsAggiornamento(),p2.getTsAggiornamento())) return false;

// Per le condizioni di pagamento  ed i destinatari si usa il comparator specifico
//		if (!areEquals(p1.getCondizioniPagamento(),p2.getCondizioniPagamento())) return false;

		//		if (!areEquals(p1.getDestinatariPendenze(),p2.getDestinatariPendenze())) return false;
			
		return true;
	}
	
	
	

	public static String getIdPagamento(String idPagamento, TributiEnti te, EntityManager em) {
		if (!te.getFlNdpIuvGenerato().equals("0")) {
			Query q= em.createNamedQuery("IUVPosizEnteMapByIdEnteAndIuv");
			q.setParameter("iuv",idPagamento);
			q.setParameter("idEnte", te.getId().getIdEnte());
			List<IUVPosizEnteMap> lIuvMapList = (List<IUVPosizEnteMap>) q.getResultList();
			if (lIuvMapList.size()>0) {		    	
			    return lIuvMapList.get(0).getIdPagamento();
			} else {
			    return null;
			}
			
		} else {
			return idPagamento;
		}
		
	}


	public static void main(String[] args) {
		
		String  template = "if (!areEquals(p1.$1(),p2.$1())) return false;";
		
		for (Method m:Pendenze.class.getMethods()) {
			if (m.getName().startsWith("get")) {
				System.out.println(template.replaceAll("[$][1]", m.getName()));
			}	
		}
		
	}
	
}
