package it.tasgroup.idp.billerservices.controller.notification;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.GestoreNotifiche;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.notification.AckFileRequest;
import it.tasgroup.idp.notification.AckFileResponse;
import it.tasgroup.idp.notification.CountNotificheRequest;
import it.tasgroup.idp.notification.CountNotificheResponse;
import it.tasgroup.idp.notification.DownloadReportResultType;
import it.tasgroup.idp.notification.EsitoType;
import it.tasgroup.idp.notification.FaultType;
import it.tasgroup.idp.notification.GetFileNotificaRequest;
import it.tasgroup.idp.notification.GetFileNotificaResponse;
import it.tasgroup.idp.notification.ListaNotificheRequest;
import it.tasgroup.idp.notification.ListaNotificheResponse;
import it.tasgroup.idp.notification.NotificaType;
import it.tasgroup.idp.notification.TipoNotificaType;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



@Stateless
public class NotificationControllerImpl implements NotificationController {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public CountNotificheResponse countNotifiche(CountNotificheRequest parameters) {
		
		logger.info(" Inizio elaborazione countNotifiche");
		
		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());
		
		CountNotificheResponse response = new CountNotificheResponse();
		
		try {
			int num = GestoreNotifiche.getCountNotifiche(parameters.getSenderId(), 
					senderSysList, 
					parameters.getTipoDebito()!=null?parameters.getTipoDebito():null,
					parameters.getTipoNotifica()!=null?parameters.getTipoNotifica().name():null,
					parameters.getDataCreazione()!=null?parameters.getDataCreazione().getDataCreazioneDa():null,
					parameters.getDataCreazione()!=null?parameters.getDataCreazione().getDataCreazioneA():null,
					null, em);
		
		    response.setResult("OK");
		    response.setNumNotifiche(Integer.valueOf(num));
		} catch (Exception e) {
			response.setResult("KO");
			FaultType fault = new FaultType();
			fault.setFaultCode(EnumReturnValues.ERRORE_GENERICO.getKey());
			fault.setFaultDescription("Errore generico");
			fault.setFaultString(EnumReturnValues.ERRORE_GENERICO.toString());
			response.setFault(fault);
		}
		logger.info(" Fine elaborazione countNotifiche");
		return response;
	}
 
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public GetFileNotificaResponse getFileNotifica(GetFileNotificaRequest parameters) {
		
		logger.info(" Inizio elaborazione getFileNotifica"); 
		
		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());
		
		GetFileNotificaResponse response = new GetFileNotificaResponse();
		
		List<NotificheCart> outLista = null;
		try {
			outLista = GestoreNotifiche.getListaPaginataNotifiche(parameters.getSenderId(), 
						senderSysList, 
						null,
						null,
						null,
						null,
						parameters.getMsgId(), 
						-1, 
						-1, em);
			if (outLista!=null && outLista.size() > 0) {
				if (outLista.size() == 1) {
					response.setResult(DownloadReportResultType.DISPONIBILE);
					response.setFile(outLista.get(0).getNotificaXml());
					//GestoreNotifiche.updateStatoNotifiche(parameters.getSenderId(), parameters.getSenderSys(), parameters.getMsgId(), "INVIATO", em);
					
				} else {
					response.setResult(DownloadReportResultType.IN_ERRORE);
					FaultType fault = new FaultType();
					fault.setFaultCode(EnumReturnValues.NOTIFICA_NON_UNIVOCA.getKey());
					fault.setFaultDescription("Notifica non univoca");
					fault.setFaultString(EnumReturnValues.NOTIFICA_NON_UNIVOCA.toString());
					response.setFault(fault);
					
				}
			} else {
				response.setResult(DownloadReportResultType.IN_CORSO);
				FaultType fault = new FaultType();
				fault.setFaultCode(EnumReturnValues.NOTIFICA_NON_PRESENTE.getKey());
				fault.setFaultDescription("Notifica non presente");
				fault.setFaultString(EnumReturnValues.NOTIFICA_NON_PRESENTE.toString());
				response.setFault(fault);
			}
			
		} catch (Exception e) {
			response.setResult(DownloadReportResultType.IN_ERRORE);;
			FaultType fault = new FaultType();
			fault.setFaultCode(EnumReturnValues.ERRORE_GENERICO.getKey());
			fault.setFaultDescription("Errore generico");
			fault.setFaultString(EnumReturnValues.ERRORE_GENERICO.toString());
			response.setFault(fault);
		}
		logger.info(" Fine elaborazione getFileNotifica"); 
		return response;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public ListaNotificheResponse listaNotifiche(ListaNotificheRequest parameters) {
		
		logger.info(" Inizio elaborazione listaNotifiche"); 
		
		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());
		
		DatatypeFactory df;
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		
		ListaNotificheResponse response = new ListaNotificheResponse();
		
		List<NotificheCart> outLista = null; 
		try {
		    outLista = GestoreNotifiche.getListaPaginataNotifiche(parameters.getSenderId(), 
		    			senderSysList, 
		    			parameters.getTipoDebito()!=null?parameters.getTipoDebito():null,
		    			parameters.getTipoNotifica()!=null?parameters.getTipoNotifica().name():null,
		    			parameters.getDataCreazione()!=null?parameters.getDataCreazione().getDataCreazioneDa():null,
		    			parameters.getDataCreazione()!=null?parameters.getDataCreazione().getDataCreazioneA():null,
		    			null, 
		    			parameters.getOffset()!=null?parameters.getOffset().intValue():-1, 
		    			parameters.getLimit()!=null?parameters.getLimit().intValue():-1, em);
		
		    response.setResult("OK");
		    Iterator<NotificheCart> iterNC = outLista.iterator();
		    while (iterNC.hasNext()) {	
		      NotificheCart nc = iterNC.next();
		      
		      NotificaType  nt = new NotificaType();
		      nt.setMsgId(nc.getId().getE2emsgid());
		      nt.setSenderId(nc.getId().getReceiverid());
		      nt.setSenderSys(nc.getId().getReceiversys());
		      if (nc.getTipoNotifica() != null) { // patch per vecchi record notifiche_cart.tipo_notifica == null
		    	  nt.setTipoNotifica(TipoNotificaType.fromValue(nc.getTipoNotifica()));
		      }

				Date timestampCreazione = new Date(nc.getTsInserimento().getTime());
				GregorianCalendar timestampCreazioneGC = new GregorianCalendar();
				timestampCreazioneGC.setTime(timestampCreazione);
		      
		      nt.setTimestampCreazione(df.newXMLGregorianCalendar(timestampCreazioneGC));
		      
		      response.getNotifiche().add(nt);
		    }
		} catch (Exception e) {
			logger.error("Errore in NotificationControllerImpl.listaNotifiche()", e);
			response.setResult("KO");
			FaultType fault = new FaultType();
			fault.setFaultCode(EnumReturnValues.ERRORE_GENERICO.getKey());
			fault.setFaultDescription("Errore generico");
			fault.setFaultString(EnumReturnValues.ERRORE_GENERICO.toString());
			response.setFault(fault);
		}
		logger.info(" Fine elaborazione listaNotifiche"); 
		return response;
	}

	@Override
	public AckFileResponse ackFileNotifica(AckFileRequest parameters) {
		
		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());

		logger.info(" Inizio elaborazione ackFileNotifica"); 
		GestoreNotifiche.updateStatoNotifiche(parameters.getSenderId(), senderSysList, parameters.getMsgId(), "ELABORATO OK", em);
		AckFileResponse response = new AckFileResponse();
		response.setResult(EsitoType.OK);
		
		//TODO: gestire KO
		
		logger.info(" Fine elaborazione ackFileNotifica"); 
		return response;
	}

	private List<String> getSenderSysList(String senderSysConcatenation) {
		
		/*
		 *  con l'introduzione della versione rest del loader (SmartProxy like)
		 *  il campo senderSys puo' contenere una serie di sys, concatenati e separati da punto e virgola
		 */
		List<String> senderSysList = null;
		if (senderSysConcatenation != null) {
			senderSysList = new ArrayList<String>();
			String[] senderSysArray = senderSysConcatenation.trim().split(";");
			for (String senderSys : senderSysArray) {
				senderSysList.add(senderSys.trim());
			}
		}
		return senderSysList;
		
	}
	

}
