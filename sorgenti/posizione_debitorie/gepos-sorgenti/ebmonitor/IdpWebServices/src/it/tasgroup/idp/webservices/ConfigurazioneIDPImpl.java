package it.tasgroup.idp.webservices;

import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.toscana.rete.cart.servizi.iris_1_1.ws.idpconfigurazione.ConfigurazioneIDP;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnte;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnteEsito;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneTributi;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneTributiEsito;

@Interceptors(MonitoringInterceptor.class)
//@HandlerChain(file = "/handler-chain.xml")
@WebService(endpointInterface = "it.toscana.rete.cart.servizi.iris_1_1.ws.idpconfigurazione.ConfigurazioneIDP",
portName="ConfigurazioneIDPPort")  

public class ConfigurazioneIDPImpl implements ConfigurazioneIDP {
	
	private final Log logger = LogFactory.getLog(this.getClass());

	

	@Override
	public IdpConfigurazioneEnteEsito idpConfigurazioneEnte(IdpConfigurazioneEnte idpConfigurazioneEnte) {
		logger.info("ConfigurazioneIDPImpl:idpConfigurazioneEnte START");
		IdpServiceLocator locator =  IdpServiceLocator.getInstance(); 
		//ConfigurazioneEnteControllerImpl
		ConfigurazioneIDP controller = (ConfigurazioneIDP)locator.getServiceByName("ConfigurazioneEnteControllerImpl");
		
		IdpConfigurazioneEnteEsito esito = controller.idpConfigurazioneEnte(idpConfigurazioneEnte);
		logger.info("ConfigurazioneIDPImpl:idpConfigurazioneEnte END");
		return esito;	
	}

	@Override
	public IdpConfigurazioneTributiEsito idpConfigurazioneTributi(IdpConfigurazioneTributi idpConfigurazioneTributi) {
		logger.info("ConfigurazioneIDPImpl:idpConfigurazioneTributi START");
		IdpServiceLocator locator =  IdpServiceLocator.getInstance(); 
		ConfigurazioneIDP controller = (ConfigurazioneIDP)locator.getServiceByName("ConfigurazioneEnteControllerImpl");
		
		IdpConfigurazioneTributiEsito esito = controller.idpConfigurazioneTributi(idpConfigurazioneTributi);
		logger.info("ConfigurazioneIDPImpl:idpConfigurazioneTributi END");
		return esito;	
	}

	
	
}
