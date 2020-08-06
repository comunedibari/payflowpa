package it.tasgroup.idp.webservices;

import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart3.CartWsImpl;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.toscana.rete.cart.servizi.iris_1_1.ws.idpallineamentopendenze.ComunicazionePosizioniDebitorieOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTFEsito;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTFEsito;

@Interceptors(MonitoringInterceptor.class)
//@HandlerChain(file = "/handler-chain.xml")
@WebService(endpointInterface = "it.toscana.rete.cart.servizi.iris_1_1.ws.idpallineamentopendenze.ComunicazionePosizioniDebitorieOTF", 
            portName="ComunicazionePosizioniDebitorieOTFPort")  
//            targetNamespace="http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy",
//            wsdlLocation="WEB-INF/wsdl/ComunicazionePosizioniDebitorie.wsdl")
public class ComunicazionePosizioniDebitorieOTFImpl implements ComunicazionePosizioniDebitorieOTF {
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Override
	public IdpAllineamentoPendenzeEnteOTFEsito idpAllineamentoPendenzeEnteOTF(IdpAllineamentoPendenzeEnteOTF idpAllineamentoPendenzeOTF) {		
		logger.info("ComunicazionePosizioniDebitorieOTFImpl:idpAllineamentoPendenzeEnteOTF START");
		CartWsImpl cartWsImpl = new CartWsImpl();
		IdpAllineamentoPendenzeEnteOTFEsito esito = cartWsImpl.idpAllineamentoPendenzeEnteOTF(idpAllineamentoPendenzeOTF);
		logger.info("ComunicazionePosizioniDebitorieOTFImpl:idpAllineamentoPendenzeEnteOTF END");
		return esito;
		
	}



	@Override
	public IdpAllineamentoPendenzeMultiEnteOTFEsito idpAllineamentoPendenzeMultiEnteOTF(
			IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF) {

		IdpServiceLocator locator =  IdpServiceLocator.getInstance();
		ComunicazionePosizioniDebitorieOTF controller = (ComunicazionePosizioniDebitorieOTF)locator.getServiceByName("ComunicazionePosizioniDebitorieControllerImpl");
		return controller.idpAllineamentoPendenzeMultiEnteOTF(allineamentoPendenzeOTF);

			
	}



}
