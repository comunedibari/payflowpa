package it.tasgroup.idp.xmlbillerservices.ws;



import it.tasgroup.idp.billerservices.controller.verificapagamento.VerificaPagamentoController;
import it.tasgroup.idp.xmlbillerservices.ws.verificapagamento.VerificaPagamento;
import it.tasgroup.idp.xmlbillerservices.ws.verificapagamento.VerificaPagamentoPortType;
import it.tasgroup.idp.xmlbillerservices.ws.verificapagamento.VerificaPagamentoResponse;

import javax.ejb.EJB;
import javax.jws.WebService;

@WebService(serviceName = "VerificaPagamentoService", 
endpointInterface = "it.tasgroup.idp.xmlbillerservices.ws.verificapagamento.VerificaPagamentoPortType", 
targetNamespace = "http://idp.tasgroup.it/xmlbillerservices/ws/VerificaPagamento", 
portName = "VerificaPagamentoPort",
wsdlLocation="/WEB-INF/wsdl/VerificaPagamento.wsdl")
public class VerificaPagamentoServiceImpl implements VerificaPagamentoPortType {

	@EJB(beanName="VerificaPagamentoControllerImpl")
	VerificaPagamentoController controller;

	@Override
	public VerificaPagamentoResponse verificaPagamento(VerificaPagamento request) {
		return controller.verificaPagamento(request);
	}
	

}
