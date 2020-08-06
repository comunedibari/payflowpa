package it.tasgroup.idp.notification.sender;

import it.tasgroup.idp.bean.ResultKOException;
import it.tasgroup.idp.cart.ISenderNotificationQueueDemux;
import it.tasgroup.idp.cart.JAXBMarshalling;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.StatoEnum;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpInformativaPagamento;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.StatoMessaggio;
import it.tasgroup.idp.xmlbillerservices.ws.notificapagamento.NotificaPagamento;
import it.tasgroup.idp.xmlbillerservices.ws.notificapagamento.NotificaPagamentoPortType;
import it.tasgroup.idp.xmlbillerservices.ws.notificapagamento.NotificaPagamentoResponse;
import it.tasgroup.idp.xmlbillerservices.ws.notificapagamento.NotificaPagamento_Service;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StandardWSNotificaPagamentoNotification implements ISenderNotificationQueueDemux {

	private final Log logger = LogFactory.getLog(this.getClass());	
	private static String  PAYTAS_STD_NOTIF_URL = "paytas.standard.notif.url";
	
public enum EnumEsitiInformativaPagamentoWS  {
		
		ESITO_OK                 ( "","pagamento elaborato con successo","OK"),
		PAGAMENTO_NON_PRESENTE   ( "I0000001","IdPagamento non presente in DB Ente","KO"),
		IMPORTO_PAG_INSUFF       ( "I0000002","Pagamento Irregolare: l'importo pagato � inferiore a quello dovuto","KO"),
		ERRORE_NON_CLASSIF       ( "UNK" ,"Errore non classificato","KO");
		
		
		private String  key;
		private String  description;
		private String  behaviour;

		EnumEsitiInformativaPagamentoWS(String key, String descrizione, String behavior) {
			this.key = key;
			this.description = descrizione;
			this.behaviour = behavior;
		}

		
		public String getKey(){
			return key;
		}
		
		public String getDescription() {
			return description;
		}

		public String getBehaviour() {
			return this.behaviour;
		}
			
		public static EnumEsitiInformativaPagamentoWS getByIdpInformativaResp(NotificaPagamentoResponse idpResp) {
			
			
			StatoMessaggio stato =idpResp.getEsito().getStato();
			if (StatoMessaggio.ELABORATO_OK.equals(stato)) {
				return ESITO_OK;
			} else {
				
				
				
				if (idpResp.getEsito().getCodice()!=null) {
					if (PAGAMENTO_NON_PRESENTE.getKey().equals(idpResp.getEsito().getCodice())) {
						return PAGAMENTO_NON_PRESENTE;
					}
					else {
						if (IMPORTO_PAG_INSUFF.getKey().equals(idpResp.getEsito().getCodice())) {
							return IMPORTO_PAG_INSUFF;
					    }
					}
				}
				
				
				return ERRORE_NON_CLASSIF;
			}			
			
		}
		@Override
		public String toString() {
			return key +" " + description + " " +behaviour;
		}

	}
	@Override 
	public Object delivery(NotificheCart nc,EntityManager manager) throws Exception { 
		
		logger.info(this.getClass().getSimpleName() + " delivery() BEGIN " );
		try {
						
			String temp = new String(nc.getNotificaXml());
	        
	        logger.info(this.getClass().getSimpleName() + " delivery() xml  to parse "+temp );
	        
	        @SuppressWarnings("unchecked")
			JAXBElement<IdpInformativaPagamento> idpJaxb = (JAXBElement<IdpInformativaPagamento>) JAXBMarshalling.getObject("it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento:it.tasgroup.idp.xmlbillerservices.common:it.tasgroup.idp.xmlbillerservices.header:it.tasgroup.idp.xmlbillerservices.pendenze:it.tasgroup.idp.xmlbillerservices.informativapagamento", temp);
	        IdpInformativaPagamento infoPag = idpJaxb.getValue();
	        	        
	        NotificaPagamento_Service inp = new NotificaPagamento_Service();
		    NotificaPagamentoPortType port = inp.getNotificaPagamentoPort();
		    
		     
	        BindingProvider bindingProvider = (BindingProvider) port;
	        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,IrisProperties.getProperty(PAYTAS_STD_NOTIF_URL));	        
			
	        //infopagProxy.setIdpInformativaPagamento(infoPag);
	        //logger.info(" !! before calling Webservice RFC145DirectWSNotification ...");
	        NotificaPagamento notificaPagamentoRequest =new NotificaPagamento();
	        notificaPagamentoRequest.setIdpInformativaPagamento(infoPag);
			NotificaPagamentoResponse resp = port.notificaPagamento(notificaPagamentoRequest);
			
			
			logger.info(" !! Webservice StandardDirectWSNotification called !! ");
            
			EnumEsitiInformativaPagamentoWS esito=EnumEsitiInformativaPagamentoWS.getByIdpInformativaResp(resp);
			if (esito== null) {
				logger.error("Valore di ritorno non previsto...rollback!!!!");
				throw new RuntimeException();
			} else {
				logger.info(this.getClass().getSimpleName() + " delivery() Descrizione = "+ esito.getDescription());
				logger.info(this.getClass().getSimpleName() + " delivery() Comportamento previsto = " + esito.getBehaviour());
				logger.info(this.getClass().getSimpleName() + " delivery() esito = "+esito );
				// 
				if (esito.getBehaviour().equals("KO"))
					throw new ResultKOException(esito);
				if (esito.getBehaviour().equals("RETRY"))
					throw new RuntimeException();
				return esito;
			}
		} catch (ResultKOException e){
			logger.info("Stato finale KO..");
			throw e;
			
		} catch (JAXBException j) {
			logger.info("Errore di sistema ... rollback...");
			throw new RuntimeException(j);   
		} catch (Throwable e){
			logger.info("Errore di sistema ... rollback...");
			throw new RuntimeException(e); 
		} finally {
		    logger.info(this.getClass().getSimpleName() + " delivery() END " );		   
		}
		
	}

	
}
