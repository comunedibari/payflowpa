package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BuildGART_RFC145DemuxNotification implements IBuildNotification {

	private final Log logger = LogFactory.getLog(this.getClass());
	@Override
	public void build(List<NotifichePagamenti> listaNotifichePagamenti,
			String statoNotificaPagamento, DataStorageInterface dataTx,
			EntityManager em) throws Exception {
		
		List<NotifichePagamenti> listaNotificheGTART = new ArrayList<NotifichePagamenti>();
		List<NotifichePagamenti> listaNotificheRFC145 = new ArrayList<NotifichePagamenti>();
		
		//BIZ LOGIC
		Iterator<NotifichePagamenti> iterLista = listaNotifichePagamenti.iterator();
		while (iterLista.hasNext()) {
			NotifichePagamenti notPagamento = (NotifichePagamenti) iterLista.next();
					
	        logger.info("=============================================================================");
	        if (isLoadedByMomIris(notPagamento,em)) {
	        	listaNotificheRFC145.add(notPagamento);
	        } else {
	        	listaNotificheGTART.add(notPagamento);
	        }			
		} 
		IBuildNotification rfc145 = BuildNotificationFactory.createNotificationBuilder("RFC_145_V010300");
		if (!listaNotificheRFC145.isEmpty()) {
		   rfc145.build(listaNotificheRFC145, statoNotificaPagamento, dataTx, em);
		}
		
		IBuildNotification gtart = BuildNotificationFactory.createNotificationBuilder("GTART_DIRECT");
		if (!listaNotificheGTART.isEmpty()) {
		  gtart.build(listaNotificheGTART, statoNotificaPagamento, dataTx, em);
		}
	}
	
	
    private boolean isLoadedByMomIris(NotifichePagamenti n, EntityManager em) throws Exception {
    	String sqlQuery =  "select  JLTPEND.RIFERIMENTO as ID_BOLLO "+ 
                           " FROM PAGAMENTI  "+
                           " LEFT OUTER JOIN JLTPEND   ON (PAGAMENTI.ID_PENDENZA = JLTPEND.ID_PENDENZA) " +
                           " WHERE PAGAMENTI.ID =  "+n.getIdPagamento()+" ";
    	
    	sqlQuery =  " select count(*) FROM PAGAMENTI   LEFT OUTER JOIN JLTPEND " +
    	            " ON (PAGAMENTI.ID_PENDENZA = JLTPEND.ID_PENDENZA)  WHERE PAGAMENTI.ID =  "+n.getIdPagamento()+
    	            " AND JLTPEND.RIFERIMENTO IS NOT NULL";

    	
    	logger.info("isLoadedByMomIris query = "+ sqlQuery);
    	
    	try {
    	   Query q = em.createNativeQuery(sqlQuery); 
    	   List l = q.getResultList();
    	   Iterator iter = l.iterator();
    	   if (iter.hasNext()) {
    		   Integer v = (Integer)iter.next();
    		   if (v.intValue()>0) {
    			   logger.info("============= Non caricato da MOM IRIS -> Notifica GTART =========================");
    			   return false;
    		   } else {
    			   logger.info("============= Caricato da MOM IRIS -> Notifica RFC145 =========================");
    			   return true;
    		   }
    	   }
    	   else {
    		   return false;
    	   } 
    	} catch (Exception t) {
    		logger.error("isLoadedByMomIris" + t);
    		logger.error("isLoadedByMomIris" + t.getMessage());
    		throw t;
    	}
    	
    	
    }
}
