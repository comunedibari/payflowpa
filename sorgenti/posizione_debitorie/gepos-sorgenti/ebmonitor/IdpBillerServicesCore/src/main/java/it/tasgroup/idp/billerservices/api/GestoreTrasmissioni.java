package it.tasgroup.idp.billerservices.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.tasgroup.idp.billerservices.api.plugin.DatiPiazzaturaFlusso;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.domain.iuv.IUVPosizEnteMap;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;

/**
 * Classe Helper che gestisce lo stato delle trasmissioni.
 * Tutte le trasmissioni sono loggate sulla tabella PENDENZE_CART
 * 
 * @author tasgroup
 *
 */
public class GestoreTrasmissioni {
	
	public static enum EnumStatoTrasmissione {
		DA_ELABORARE_LOADER,
		VALIDATO_OK_LOADER,
		VALIDATO_KO_LOADER,
		ESITATO_LOADER
	}
	
	public static enum EnumStatoEsitoTrasmissione {
		DA_SPEDIRE_LOADER,
		INVIATO_LOADER
	}

	public static enum EnumStatoEsitoPendenza {
		OK,
		KO,
		WARN
	}

	/**
	 * Carica una trasmissione sulla tabella PENDENZE_CART
	 * 
	 * @param senderId
	 * @param senderSys
	 * @param tipoTrasmissione
	 * @param file
	 * @return
	 */
	public static void insertNewTrasmissione (DatiPiazzaturaFlusso datiPiazzaturaFlusso, 
			                             String file,           // TODO: gestire in streaming 
			                             EntityManager em) {     
		
		PendenzeCartMessage trasmissione = new PendenzeCartMessage();
		PendenzeCartPK pk = new PendenzeCartPK();
		pk.setE2emsgid(datiPiazzaturaFlusso.e2eMsgId);
		pk.setSenderid(datiPiazzaturaFlusso.senderId);
		pk.setSendersys(datiPiazzaturaFlusso.senderSys);
		trasmissione.setPk(pk);
		
		trasmissione.setNumPendenze(datiPiazzaturaFlusso.numeroPosizioni);
		trasmissione.setStato(EnumStatoTrasmissione.DA_ELABORARE_LOADER.toString());
		trasmissione.setTimestampRicezione(new Timestamp(System.currentTimeMillis()));
		trasmissione.setOpInserimento("LOADER");
		trasmissione.setPrVersione(0);
		trasmissione.setReceiverid("LOADER");
		trasmissione.setReceiversys("LOADERSIL");
		trasmissione.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		trasmissione.setMessaggioXml(file.getBytes());  //TODO: UTF-8
		trasmissione.setTipoOperazione("Mixed");
		trasmissione.setTipoMessaggio(datiPiazzaturaFlusso.tipoFile);
		
		em.persist(trasmissione);
	
	}

	/**
	 * Recupera il file associato ad una trasmissione come stream 
	 * @param senderId
	 * @param senderSys
	 * @param e2emsgid
	 * @return
	 */
	public static InputStream getTrasmissioneAsStream(String senderId, String senderSys, String e2eMsgId, EntityManager em){
		PendenzeCartPK pk = new PendenzeCartPK();

		pk.setE2emsgid(e2eMsgId);
		pk.setSenderid(senderId);
		pk.setSendersys(senderSys);
		
		PendenzeCartMessage trasmissione =em.find(PendenzeCartMessage.class, pk);
		
		if (trasmissione==null) {
			return null;
		} else {
			return new ByteArrayInputStream(trasmissione.getMessaggioXml());
		}
		
		
	}
	
	
	public static byte[] getTrasmissioneAsBytes(String senderId, String senderSys, String e2eMsgId, EntityManager em){
		PendenzeCartPK pk = new PendenzeCartPK();

		pk.setE2emsgid(e2eMsgId);
		pk.setSenderid(senderId);
		pk.setSendersys(senderSys);
		
		PendenzeCartMessage trasmissione =em.find(PendenzeCartMessage.class, pk);
		
		if (trasmissione==null) {
			return null;
		} else {
			return trasmissione.getMessaggioXml();
		}
			
	}
	

	public static byte[] getEsitoTrasmissioneAsBytes(String senderId, String senderSys, String e2eMsgId, EntityManager em){
		
		EsitiCartPK pk = new EsitiCartPK();

		pk.setE2emsgid(e2eMsgId);
		pk.setSenderid(senderId);
		pk.setSendersys(senderSys);
		
		EsitiCart esitoTrasmissione =em.find(EsitiCart.class, pk);
		
		if (esitoTrasmissione==null) {
			return null;
		} else {
			return esitoTrasmissione.getEsitoXml();
		}
			
	}
	
	public static PendenzeCart getTrasmissione(String senderId, String optional_senderSys, String e2eMsgId, EntityManager em){
	
		String queryString = "select t from PendenzeCart t where pk.e2emsgid=:e2eMsgId and pk.senderid=:senderId ";
		if (optional_senderSys!=null) {
			queryString = queryString +" and pk.sendersys=:senderSys";
		}
		queryString = queryString + " order by timestampRicezione desc";
		
		Query q = em.createQuery(queryString);

		q.setParameter("e2eMsgId", e2eMsgId);
		q.setParameter("senderId", senderId);
		if (optional_senderSys!=null) {
			q.setParameter("senderSys", optional_senderSys);
		}
		
		List<PendenzeCart> result = q.getResultList();
		if (result==null || result.size()==0) {
			return null;
		}
		else {
			return result.get(0);
		}
				
		
	}

	
	
	public static void updateStatoTrasmissione(String senderId, String senderSys, String e2eMsgId, EnumStatoTrasmissione nuovoStato, EntityManager em){
		PendenzeCartPK pk = new PendenzeCartPK();

		pk.setE2emsgid(e2eMsgId);
		pk.setSenderid(senderId);
		pk.setSendersys(senderSys);
		
		PendenzeCart trasmissione =em.find(PendenzeCart.class, pk);
		
		if (trasmissione==null) {
			throw new RuntimeException("Trasmissione (senderId="+senderId+"senderSys="+senderSys+",e2emsgid="+e2eMsgId+") non trovata");
		}
		
		trasmissione.setStato(nuovoStato.toString());
		trasmissione.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
		
	}

	
	public static int getCountEsitiTrasmissione(String senderId, String senderSys, String idTrasmissione, boolean includeErrors, boolean includeWarnings, boolean includeSuccess, EntityManager em) {
		Query q = em.createQuery(" select count(esitiPendenza.idEsitoPendenza) from EsitiPendenza esitiPendenza where esitiPendenza.pendenzeCart=:pendenzeCart and esitiPendenza.esitoPendenza in (:listaStati) ");
	
		//creo criterio di selezione
		PendenzeCart cart = new PendenzeCart();
		PendenzeCartPK cartPk = new PendenzeCartPK();
		cartPk.setE2emsgid(idTrasmissione);
		cartPk.setSenderid(senderId);
		cartPk.setSendersys(senderSys);
		cart.setPk(cartPk);
		
		q.setParameter("pendenzeCart", cart);
		
		List<String> listaStati = new ArrayList<String>();
		if (includeErrors) {
			listaStati.add("KO");
		}
		if (includeWarnings) {
			listaStati.add("WARN");
		}
		if (includeSuccess) {
			listaStati.add("OK");
		}
		
		if (listaStati.size()==0) {
			return 0;   //Inutile fare la query con set stati vuoto
		}
		
		q.setParameter("listaStati", listaStati);
	
		int count = ((Number)q.getSingleResult()).intValue();
		
		return count;
		
	}
	
	public static List<EsitiPendenza> getEsitiTrasmissione(String senderId, String senderSys, String idTrasmissione, boolean includeErrors, boolean includeWarnings, boolean includeSuccess, EntityManager em) {
		
		Query q = em.createQuery(" select esitiPendenza from EsitiPendenza esitiPendenza left join fetch esitiPendenza.erroriEsitiPendenzaCollection where esitiPendenza.pendenzeCart=:pendenzeCart and esitiPendenza.esitoPendenza in (:listaStati) order by esitiPendenza.prVersione asc");		
		
		//creo criterio di selezione
		PendenzeCart cart = new PendenzeCart();
		PendenzeCartPK cartPk = new PendenzeCartPK();
		cartPk.setE2emsgid(idTrasmissione);
		cartPk.setSenderid(senderId);
		cartPk.setSendersys(senderSys);
		cart.setPk(cartPk);
		
		q.setParameter("pendenzeCart", cart);
		
		List<String> listaStati = new ArrayList<String>();
		if (includeErrors) {
			listaStati.add("KO");
		}
		if (includeWarnings) {
			listaStati.add("WARN");
		}
		if (includeSuccess) {
			listaStati.add("OK");
		}
		
		if (listaStati.size()==0) {
			return new ArrayList<EsitiPendenza>();   //Inutile fare la query con set stati vuoto
		}
		
		q.setParameter("listaStati", listaStati);
		
		List<EsitiPendenza> l = (List<EsitiPendenza>)q.getResultList();
		
		return getIdPagamentoAndIUVFromCondPagamento(l,em);
		
	}
	
	private static List<EsitiPendenza> getIdPagamentoAndIUVFromCondPagamento(List<EsitiPendenza> listaEsiti,EntityManager em ) {
		
		for (EsitiPendenza esiti:listaEsiti) {
			// recupero in ogni caso id_pagamento da condizioni
			Query q=em.createNamedQuery("CondizioniPagamentoByIdPendenza");
			q.setParameter("idPendenza", esiti.getIdPendenza());
			CondizioniPagamento cp = null;
			try { 
				cp = (CondizioniPagamento)q.getSingleResult();
			}		
			catch (javax.persistence.NoResultException e) {
				break;
			}	
			// verifico se il tributo in esame ha iuv generato dalla piattaforma
			TributiEntiPK pk= new TributiEntiPK();
			pk.setIdEnte(cp.getIdEnte());
			pk.setCdTrbEnte(cp.getCdTrbEnte());
			TributiEnti te = em.find(TributiEnti.class,pk);
			if (te.getFlNdpIuvGenerato().equals("0")) {
				// lo iuv e stato fornito dall ente
				esiti.setIdPagamento(cp.getIdPagamento());
				esiti.setIuv(null);
			} else {
				Query q1= em.createNamedQuery("IUVPosizEnteMapByIdEnteAndIuv");
				q1.setParameter("iuv",cp.getIdPagamento());
				q1.setParameter("idEnte", te.getId().getIdEnte());
				List<IUVPosizEnteMap> lIuvMapList = (List<IUVPosizEnteMap>) q1.getResultList();
				if (lIuvMapList.size()>0) {		    	
					esiti.setIdPagamento(lIuvMapList.get(0).getIdPagamento());
					esiti.setIuv(cp.getIdPagamento());
				} else {
				    ;//????
				}
			}
			
			
		}
		
		return listaEsiti;
	}
	
	public static void insertNewEsito(DatiPiazzaturaFlusso datiPiazzaturaFlusso, String msgEsito, EntityManager em) {
	
		// Recupero della posizione
		// -----
		PendenzeCartPK pkp = new PendenzeCartPK();
		pkp.setSenderid(datiPiazzaturaFlusso.senderId);
		pkp.setSendersys(datiPiazzaturaFlusso.senderSys);
		pkp.setE2emsgid(datiPiazzaturaFlusso.e2eMsgId);

		PendenzeCart pCart  = em.find(PendenzeCart.class, pkp);
		
		if (pCart==null) {
			throw new RuntimeException("Il flusso da esitare non esiste");
		}
		
		// Preparazione dell'esito
		// -----		
		EsitiCartPK pke = new EsitiCartPK();
		pke.setSenderid(datiPiazzaturaFlusso.senderId);
		pke.setSendersys(datiPiazzaturaFlusso.senderSys);
		pke.setE2emsgid(datiPiazzaturaFlusso.e2eMsgId);
		
		EsitiCart esitoCart = new EsitiCart();
		esitoCart.setPk(pke);
		esitoCart.setTimestampInvio(new Timestamp(System.currentTimeMillis()));
		
		byte[] msgEsitoBytes;
		try {
			msgEsitoBytes = msgEsito.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore encoding",e);
		}
		
		esitoCart.setEsitoXml(msgEsitoBytes); 
		esitoCart.setPendenzeCart(pCart);
		esitoCart.setStato(EnumStatoEsitoTrasmissione.INVIATO_LOADER.toString());
		pCart.setStato(EnumStatoTrasmissione.ESITATO_LOADER.toString());
		 
		em.persist(esitoCart);
		
	}
	

	public static int  getCountTrasmissioni(String senderId, List<String> senderSysList, Date optional_dataDa, EntityManager em) {
		
		Query q = getQueryTrasmissioni(true /* countQuery */,  senderId, senderSysList, null, optional_dataDa, em);
		
		int count = ((Number)q.getSingleResult()).intValue();
		
		return count;
		
	}

	
	public static List<PendenzeCart> getListaPaginataTrasmissioni(String senderId, List<String> senderSysList, String optional_msgId, Date optional_dataDa, int offset, int limit, EntityManager em) {

		try {

			Query q = getQueryTrasmissioni(false /* countQuery */, senderId, senderSysList, optional_msgId, optional_dataDa, em);

			if (offset != -1) // se -1 significa che non è stato specificato nella chiamata
				q.setFirstResult(offset);

			if (limit != -1) // se -1 significa che non è stato specificato nella chiamata
				q.setMaxResults(limit);

			List<PendenzeCart> resultList = q.getResultList();

			return resultList;
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		
	}	
	
	private static Query getQueryTrasmissioni(boolean countQuery, String senderId, List<String> senderSysList, String optional_msgId, Date optional_dataDa, EntityManager em) {

		String queryString = " from PendenzeCart t where t.pk.senderid=:senderId  and t.receiverid=:receiverId ";
		
		if (senderSysList != null && !senderSysList.isEmpty()) {
			queryString = queryString +" and t.pk.sendersys in :senderSysList";
		}
		if (optional_msgId!=null) {
			queryString = queryString +" and t.pk.e2emsgid=:e2eMsgId";
		}
		if (optional_dataDa != null) {
			queryString = queryString +" and t.timestampRicezione >= :dataDa";
		}
		
		
		if (!countQuery) {
		   queryString = queryString + " order by t.timestampRicezione desc";
		}
		
		if (countQuery) {
			queryString = "select count(t.pk.e2emsgid) "+queryString;
		} else {
			queryString = "select t "+queryString;
		}
		
		Query q = em.createQuery(queryString);
		
		q.setParameter("senderId", senderId);
		q.setParameter("receiverId", "LOADER");
		if (senderSysList != null && !senderSysList.isEmpty()) {
			q.setParameter("senderSysList", senderSysList);
		}
        if (optional_msgId!=null) {
        	q.setParameter("e2eMsgId", optional_msgId);
		}
		if (optional_dataDa != null) {
        	q.setParameter("dataDa", optional_dataDa);
		}
		return q;
		
	}
	
}
