package it.tasgroup.idp.webservices;

import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart3.VerificaStatoPagamentoOTFCommonUse;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamenti;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamentiEsito;

@Interceptors(MonitoringInterceptor.class)
//@HandlerChain(file = "/handler-chain.xml")
@WebService(endpointInterface = "it.toscana.rete.cart.servizi.iris_1_1.ws.idpinformativapagamento.VerificaStatoPagamento", 
            portName = "VerificaStatoPagamento")
//            targetNamespace="http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy",
//            wsdlLocation="WEB-INF/wsdl/InvioNotificaPagamento.wsdl")
public class VerificaStatoPagamentoOTFImpl implements it.toscana.rete.cart.servizi.iris_1_1.ws.idpinformativapagamento.VerificaStatoPagamento {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public IdpVerificaStatoPagamentiEsito idpVerificaStatoPagamenti(IdpVerificaStatoPagamenti idpVerificaStatoPagamenti) {
		logger.info("VerificaStatoPagamentoOTFImpl:idpVerificaStatoPagamenti START");
		VerificaStatoPagamentoOTFCommonUse vspocu = new VerificaStatoPagamentoOTFCommonUse();
		IdpVerificaStatoPagamentiEsito esito = vspocu.idpVerificaStatoPagamenti(idpVerificaStatoPagamenti);
		logger.info("VerificaStatoPagamentoOTFImpl:idpVerificaStatoPagamenti END");
		return esito;
	}
	
}
