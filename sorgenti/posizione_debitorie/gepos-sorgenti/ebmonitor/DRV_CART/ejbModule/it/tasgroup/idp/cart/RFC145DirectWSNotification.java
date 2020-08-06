package it.tasgroup.idp.cart;

import it.tasgroup.idp.bean.ResultKOException;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.StatoEnum;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esito;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.StatoMessaggio;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.IdpInformativaPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.ws.idpinformativapagamentoente.InvioNotificaPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.ws.idpinformativapagamentoente.InvioNotificaPagamentoService;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamentoente.IdpInformativaPagamentoResponse;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamentoente.ObjectFactory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RFC145DirectWSNotification implements ISenderNotificationQueueDemux {

	private final Log logger = LogFactory.getLog(this.getClass());	
	private static String  RFC145_WS_NOTIFICATION_URL = "iris.rfc145.notif.url";
	
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
			
		public static EnumEsitiInformativaPagamentoWS getByIdpInformativaResp(IdpInformativaPagamentoResponse idpResp) {
			
			StatoMessaggio stato =idpResp.getInfoMessaggio().getStato();
			if (StatoMessaggio.ELABORATO_CORRETTAMENTE.equals(stato)) {
				return ESITO_OK;
			} else {
				if (idpResp.getInfoMessaggio().getEsiti()!=null) {
					List<Esito> esitoList= idpResp.getInfoMessaggio().getEsiti().getEsito();
					Esito esito=esitoList.get(0); // analizzo solo il primo elemento
					if (PAGAMENTO_NON_PRESENTE.getKey().equals(esito.getCodice())) {
						return PAGAMENTO_NON_PRESENTE;
					}
					else {
						if (IMPORTO_PAG_INSUFF.getKey().equals(esito.getCodice())) {
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
			JAXBElement<IdpInformativaPagamento> idpJaxb = (JAXBElement<IdpInformativaPagamento>) JAXBMarshalling.getObject("it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento:it.toscana.rete.cart.servizi.iris_1_1.idpheader:it.toscana.rete.cart.servizi.iris_1_1.idpinclude", temp);
	        IdpInformativaPagamento infoPag = idpJaxb.getValue();
	        	        
		    InvioNotificaPagamentoService inp = new InvioNotificaPagamentoService();
		    InvioNotificaPagamento port = inp.getInvioNotificaPagamento();
		    
		     
	        BindingProvider bindingProvider = (BindingProvider) port;
	        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,IrisProperties.getProperty(RFC145_WS_NOTIFICATION_URL));	        
			
	        it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamentoente.IdpInformativaPagamento infopagProxy = (new ObjectFactory()).createIdpInformativaPagamento();
	        infopagProxy.setIdpInformativaPagamento(infoPag);
	        logger.info(" !! before calling Webservice RFC145DirectWSNotification ...");
			IdpInformativaPagamentoResponse resp = port.idpInformativaPagamento(infopagProxy);
			
			
			logger.info(" !! Webservice RFC145DirectWSNotification called !! ");
            
			EnumEsitiInformativaPagamentoWS esito=EnumEsitiInformativaPagamentoWS.getByIdpInformativaResp(resp);
			if (esito== null) {
				logger.error("Valore di ritorno non previsto...rollback!!!!");
				throw new RuntimeException();
			} else {
				logger.info(this.getClass().getSimpleName() + " delivery() Descrizione = "+ esito.getDescription());
				logger.info(this.getClass().getSimpleName() + " delivery() Comportamento previsto = " + esito.getBehaviour());
				logger.info(this.getClass().getSimpleName() + " delivery() esito = "+esito );
				// 
				if (esito.getBehaviour().equals("KO")) {
					logger.info("Stato finale KO..");
					throw new ResultKOException(esito);
				}
				if (esito.getBehaviour().equals("RETRY"))
					throw new RuntimeException();
				return esito;
			}
		} catch (JAXBException j) {
			logger.info("Errore di sistema ... rollback...");
			throw new RuntimeException(j);   
		} catch (Exception e){
			logger.info("Errore di sistema ... rollback...");
			if (e instanceof RuntimeException) throw (RuntimeException) e;
			throw new RuntimeException(e); 
		} finally {
		    logger.info(this.getClass().getSimpleName() + " delivery() END " );		   
		}
		
	}

	
}
