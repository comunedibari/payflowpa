package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.StatoEnum;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BuildGTARTNotification implements IBuildNotification {

	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Override
	public void build(List<NotifichePagamenti> listaNotifichePagamenti, String statoNotificaPagamento,DataStorageInterface dataTx, EntityManager em) throws Exception {
		
		logger.info(" creazione/spedizione notifiche terminata, "
				+ " N. PAGAMENTI = " + listaNotifichePagamenti.size() 
				+ " , STATONOTIFICA = " + statoNotificaPagamento);
		
		//BIZ LOGIC
		Iterator<NotifichePagamenti> iterLista = listaNotifichePagamenti.iterator();
		while (iterLista.hasNext()) {
			NotifichePagamenti notPagamento = (NotifichePagamenti) iterLista.next();
			
			logger.info("=============================================================================");
			logger.info("CREO MESSAGGIO GTART: ENTE = " + notPagamento.getCdEnte() + "TRB" + notPagamento.getCdTrbEnte() 
														+ "SIL " + notPagamento.getReceiversys() );
			
			//ad ogni salto codice creo un nuovo id per la memorizzazione del messaggio XML
			String id = GeneratoreIdUnivoci.GetCurrent().generaId(null);
			logger.debug("NOTIFICHE CART NEXT ID = " + id);
			
			String notifica = buildGTARTData(notPagamento.getIdPagamento(),em,id);
			
			NotificheCart notificheCart = new NotificheCart();
			
			notificheCart.setNotificaXml(notifica.getBytes(Charset.forName("US-ASCII")));
			NotificheCartPK notCartPK = new NotificheCartPK();
			//
			notCartPK.setE2emsgid(id);
			
			notCartPK.setReceiverid(notPagamento.getCdEnte());
			notCartPK.setReceiversys(notPagamento.getIdSystem());
			notificheCart.setId(notCartPK);
			notificheCart.setSenderid("RTIRIS");
			notificheCart.setSendersys("SIL_IRIS_ITR");
			notificheCart.setTipoNotifica(statoNotificaPagamento);			

			notificheCart.setStato(StatoEnum.DA_SPEDIRE);
			notificheCart.setOpInserimento("NOTIFIC_PAGAMENTO-TIMER");
			notificheCart.setTimestampInvio(new Timestamp(System.currentTimeMillis()));
			notificheCart.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			
			notificheCart.setTrtReceiverId(notPagamento.getTrtReceiverId());
			notificheCart.setTrtReceiverSys(notPagamento.getTrtReceiverSys());
			
			notificheCart.setIdEnte(notPagamento.getIdEnte());
			notificheCart.setCdTrbEnte(notPagamento.getCdTrbEnte());
			
			notPagamento.setE2emsgid(id);
			notPagamento.setReceiverid(notificheCart.getSenderid());
			notPagamento.setReceiversys(notificheCart.getSendersys());
			
			// la chiamata è in requires_new ed effettua insert ed update
			try {
			   //em.detach(notPagamento); // detach dall'entity manager al quale è associato
			   dataTx.persistNotificheCartAndUpdateStatoNotPagamenti(notificheCart,notPagamento);
			} catch (Throwable t) {
				logger.error("BuildGTARTNotification persistNotificheCartAndUpdateStatoNotPagamenti() rollback...");
				logger.info("BuildGTARTNotification go ahead...");
			}

		}
		logger.info(" creazione/spedizione notifica pagamento terminata " );
	}

    /*
     * 
     */
	private String buildGTARTData(String idPagamento,EntityManager manager,String generatedMsgId) {
		// retrieve the query 
		String sql = " select PAGAMENTI.TS_DECORRENZA, " +
                     "        PAGAMENTI.IM_PAGATO, " +     
                     "        DISTINTE_PAGAMENTO.IUV, " +
                     "        CFG_GATEWAY_PAGAMENTO.ID_CFG_MODALITA_PAGAMENTO, " +                     
                     "        JLTPEND.RIFERIMENTO, " +
                     "        JLTPEND.DE_CAUSALE, " + 
                     "        T_BOLLO_AUTO.CF_SOGG_PASSIVO, " +
                     "        COALESCE((SELECT JLTVOPD.IM_VOCE from JLTVOPD JLTVOPD LEFT OUTER JOIN PAGAMENTI PAGAMENTI ON (JLTVOPD.ID_CONDIZIONE = JLTCOPD.ID_CONDIZIONE)  LEFT OUTER JOIN JLTCOPD JLTCOPD  ON (JLTCOPD.ID_CONDIZIONE = PAGAMENTI.ID_CONDIZIONE) where JLTVOPD.TI_VOCE='SANZIONI' AND PAGAMENTI.ID=" +idPagamento + ") , 0.00), " +
                     "        COALESCE((SELECT JLTVOPD.IM_VOCE from JLTVOPD JLTVOPD LEFT OUTER JOIN PAGAMENTI PAGAMENTI ON (JLTVOPD.ID_CONDIZIONE = JLTCOPD.ID_CONDIZIONE)  LEFT OUTER JOIN JLTCOPD JLTCOPD  ON (JLTCOPD.ID_CONDIZIONE = PAGAMENTI.ID_CONDIZIONE) where JLTVOPD.TI_VOCE='IMPORTO_BASE' AND PAGAMENTI.ID=" +idPagamento + ") , 0.00), " +
                     "        COALESCE((SELECT JLTVOPD.IM_VOCE from JLTVOPD JLTVOPD LEFT OUTER JOIN PAGAMENTI PAGAMENTI ON (JLTVOPD.ID_CONDIZIONE = JLTCOPD.ID_CONDIZIONE)  LEFT OUTER JOIN JLTCOPD JLTCOPD  ON (JLTCOPD.ID_CONDIZIONE = PAGAMENTI.ID_CONDIZIONE) where JLTVOPD.TI_VOCE='INTERESSI' AND PAGAMENTI.ID=" +idPagamento + ") , 0.00) " + 
                     "   FROM PAGAMENTI PAGAMENTI LEFT OUTER JOIN DISTINTE_PAGAMENTO DISTINTE_PAGAMENTO ON (DISTINTE_PAGAMENTO.ID = PAGAMENTI.ID_DISTINTE_PAGAMENTO) " +
                     "        LEFT OUTER JOIN JLTCOPD JLTCOPD  ON (JLTCOPD.ID_CONDIZIONE = PAGAMENTI.ID_CONDIZIONE) " +
                     "        LEFT OUTER JOIN JLTPEND JLTPEND  ON (PAGAMENTI.ID_PENDENZA = JLTPEND.ID_PENDENZA) " +
                     "        LEFT OUTER JOIN T_BOLLO_AUTO T_BOLLO_AUTO ON (JLTPEND.ID_TRIBUTO_STRUTTURATO = T_BOLLO_AUTO.ID) " +
                     "        LEFT OUTER JOIN CFG_GATEWAY_PAGAMENTO CFG_GATEWAY_PAGAMENTO ON ( DISTINTE_PAGAMENTO.ID_CFG_GATEWAY_PAGAMENTO = CFG_GATEWAY_PAGAMENTO.ID) " +
                     "   WHERE PAGAMENTI.ID = " +idPagamento + " ";
		
		
		logger.info("Query da eseguire " + sql);
		Query queryNotifiche = manager.createNativeQuery(sql);
		Object[] res = (Object[]) queryNotifiche.getSingleResult() ;
		
		  
		if (res!=null) {
			//**************************************************************************************************
			// ATTENZIONE!!!! DATA_PAGAMENTO deve essere sempre in prima posizione!!!! (serve per discriminare 
			// in fase di delivery se la notifica deve essere inviata via WS o via JMS (GTART o RFC145)
			//**************************************************************************************************
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");			
			String out = "DATA_PAGAMENTO=" + (res[1]!=null?format.format(res[1]):"NULL") + ";" +
			             "MSG_ID="+generatedMsgId + ";" +
					     "TOTALE=" + res[2] + ";" +
					     "IUV=" + res[3] + ";" +
					     "MODALITA_PAG=" + res[4]+ ";" +
					     "ID_BOLLO=" + res[5] + ";" 
					      + res[6]+ ";" +
					     "CF_SOGG_PASSIVO=" + res[7]+ ";" +
					     "SANZIONI="+ res[8]+ ";" +
					     "TASSA=" + res[9]+ ";" +
					     "INTERESSI=" + res[10];
			
			logger.info("Contenuto notifica da inserire " + out);
			
			return out;
		}
		
		return null;
	}
	

}
