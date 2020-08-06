package it.tasgroup.idp.proxyndp.bean;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;

import gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta;
import it.tasgroup.idp.gateway.CfgGatewayPagamento;
import it.tasgroup.idp.proxyndp.exception.NDPComunicationException;

public interface ICatalogoPspDelegator {

	void aggiornaGatewayPagamento(List<CfgGatewayPagamento> gatewayAttuali, Object listaInformativePSPObj,
			EntityManager manager);

	/**
	 * 
	 * @param fault
	 * @param xmlInformativa
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 * @throws NDPComunicationException
	 */
	Object readResult(NodoChiediInformativaPSPRisposta risposta)
			throws IOException, JAXBException, NDPComunicationException;
	
	int catalogoSize();

}