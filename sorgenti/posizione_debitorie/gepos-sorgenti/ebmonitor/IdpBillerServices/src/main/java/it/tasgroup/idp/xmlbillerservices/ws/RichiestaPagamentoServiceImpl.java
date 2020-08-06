package it.tasgroup.idp.xmlbillerservices.ws;

import it.tasgroup.idp.billerservices.controller.richiestapagamento.RichiestaPagamentoController;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamento;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamentoPortType;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamentoResponse;

import javax.ejb.EJB;
import javax.jws.WebService;

@WebService(serviceName = "RichiestaPagamentoService", 
            endpointInterface = "it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamentoPortType", 
            targetNamespace = "http://idp.tasgroup.it/xmlbillerservices/ws/RichiestaPagamento", 
            portName = "RichiestaPagamentoPort", 
            wsdlLocation="/WEB-INF/wsdl/RichiestaPagamento.wsdl")
public class RichiestaPagamentoServiceImpl implements RichiestaPagamentoPortType {

	@EJB(beanName="RichiestaPagamentoControllerImpl")
	RichiestaPagamentoController controller;
	
	@Override
	public RichiestaPagamentoResponse richiestaPagamento(
			RichiestaPagamento request) {
		
		return controller.richiestaPagamento(request);
		

	}

}
