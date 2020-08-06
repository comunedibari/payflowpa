package it.tasgroup.idp.billerservices.api;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.datatype.XMLGregorianCalendar;

import it.tasgroup.idp.domain.messaggi.NotificheCart;

public class GestoreNotifiche {
	
	public static List<NotificheCart> getListaPaginataNotifiche(String senderId, List<String> senderSysList, String tipoDebito, String tipoNotifica, XMLGregorianCalendar dataCreazioneDa, XMLGregorianCalendar dataCreazioneA, String msgId, int offset, int limit, EntityManager em) {

		Query q = getQueryNotifiche(false /* countQuery */, senderId,  senderSysList, tipoDebito, tipoNotifica, dataCreazioneDa, dataCreazioneA, msgId, em);

		if (offset>=0)
		  q.setFirstResult(offset);
		
		if (limit>=0)
		  q.setMaxResults(limit);

		List<NotificheCart> resultList = q.getResultList();
		
		return resultList;
    }
    // senderid;
	// sendersys;
	// stato;
	// tipoNotifica;
	private static Query getQueryNotifiche(boolean countQuery, String senderid, List<String> senderSysList, String tipoDebito,  String  tipoNotifica, XMLGregorianCalendar dataCreazioneDa, XMLGregorianCalendar dataCreazioneA, String msgId, EntityManager em) {

		
		String queryString = " from NotificheCart t where receiverid=:senderid  and stato IN ('DISPONIBILE','INVIATO') ";
		
		if (senderSysList != null && !senderSysList.isEmpty()) {
			queryString = queryString +" and receiversys in :senderSysList ";
		}		
		if (tipoNotifica!=null) {
			queryString = queryString +" and tipoNotifica=:tipoNotifica ";
		}
		if (msgId!=null) {
			queryString = queryString + " and id.e2emsgid=:msgId ";
		}
		if (tipoDebito!=null) {
			queryString = queryString + " and cdTrbEnte=:tipoDebito ";
		}
		if (dataCreazioneDa!=null) {
			queryString = queryString +  " and tsInserimento >=:dataCreazioneDa ";
		}
		if (dataCreazioneA!=null) {
			queryString = queryString +  " and tsInserimento <=:dataCreazioneA ";
		}
		if (!countQuery) {
		   queryString = queryString + " order by timestampInvio desc";
		}
		
		if (countQuery) {
			queryString = "select count(t.id.e2emsgid) "+queryString;
		} else {
			queryString = "select t "+queryString;
		}
		
		Query q = em.createQuery(queryString);
		
		q.setParameter("senderid", senderid);
		if (senderSysList != null && !senderSysList.isEmpty()) {
		  q.setParameter("senderSysList", senderSysList);
		}  
		if (tipoNotifica!=null) {
			q.setParameter("tipoNotifica", tipoNotifica);
		}
		if (msgId!=null) {
			q.setParameter("msgId", msgId);
		}
		if (tipoDebito!=null) {
			q.setParameter("tipoDebito",tipoDebito);
		}
		if (dataCreazioneDa!=null) {
			Timestamp timestamp = new Timestamp(dataCreazioneDa.toGregorianCalendar().getTimeInMillis());
			q.setParameter("dataCreazioneDa",timestamp);
		}
		if (dataCreazioneA!=null) {
			Timestamp timestamp = new Timestamp(dataCreazioneA.toGregorianCalendar().getTimeInMillis());
			q.setParameter("dataCreazioneA",timestamp);
		}
		return q;
		
	}
	
    public static int  getCountNotifiche(String senderid, List<String> senderSysList, String tipoDebito, String tipoNotifica,XMLGregorianCalendar dataDa,XMLGregorianCalendar dataA,  String msgId,  EntityManager em) {
		
		Query q = getQueryNotifiche(true /* countQuery */,   senderid,  senderSysList, tipoDebito,   tipoNotifica, dataDa, dataA, msgId,  em);
		
		int count = ((Number)q.getSingleResult()).intValue();
		
		return count;
		
	}
    
    public static int updateStatoNotifiche(String senderId, List<String> senderSysList, String e2eMsgId, String nuovoStato, EntityManager em){

    	String queryString = "update NotificheCart set stato=:stato where receiverid=:senderid  and id.e2emsgid=:msgId ";
    	if (senderSysList != null && !senderSysList.isEmpty()) {
    		queryString = queryString + " and receiversys in :senderSysList ";
    	}
    	Query q = em.createQuery(queryString);
    	
    	q.setParameter("stato",nuovoStato);
    	q.setParameter("senderid", senderId);
    	if (senderSysList != null && !senderSysList.isEmpty()) {
    	   q.setParameter("senderSysList", senderSysList);
    	}
    	q.setParameter("msgId", e2eMsgId);
    	
    	int numRow=q.executeUpdate();
		return numRow;
		
	}
}
