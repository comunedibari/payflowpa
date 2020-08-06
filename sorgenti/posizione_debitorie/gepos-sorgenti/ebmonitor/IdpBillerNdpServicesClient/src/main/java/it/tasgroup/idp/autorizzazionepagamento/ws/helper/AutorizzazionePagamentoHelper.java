package it.tasgroup.idp.autorizzazionepagamento.ws.helper;

import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.autorizzazionepagamento.AttivaPagamentoRequestType;
import it.tasgroup.idp.autorizzazionepagamento.AttivaPagamentoResponseType;
import it.tasgroup.idp.autorizzazionepagamento.AutorizzazionePagamento;
import it.tasgroup.idp.autorizzazionepagamento.AutorizzazionePagamento_Service;
import it.tasgroup.idp.autorizzazionepagamento.FaultType;
import it.tasgroup.idp.autorizzazionepagamento.RequestBase;
import it.tasgroup.idp.autorizzazionepagamento.VerificaPagamentoResponseType;
import it.tasgroup.idp.autorizzazionepagamento.exception.AutorizzazionePagamentoException;
import it.tasgroup.idp.autorizzazionepagamento.servlet.AutorizzazionePagamentoVO;
import it.tasgroup.idp.util.IrisProperties;

public class AutorizzazionePagamentoHelper {
	private static Log logger = LogFactory.getLog(AutorizzazionePagamentoHelper.class);
	
	
	public static VerificaPagamentoResponseType verificaPagamento(AutorizzazionePagamentoVO datiRichiesta ) throws Exception{
		try{
			AutorizzazionePagamento port = initWsConnection(datiRichiesta.getUrlWebServices());
			RequestBase parameters = new RequestBase();
			parameters.setCodiceContestoPagamento(datiRichiesta.getCodiceContestoPagamento());
			parameters.setIdentificativoDominio(parameters.getIdentificativoDominio());
			parameters.setIdentificativoUnivocoVersamento(parameters.getIdentificativoUnivocoVersamento());
			VerificaPagamentoResponseType response = port.verificaPagamento(parameters);
			logger.info(" !! Webservice AUTORIZZAZIONE PAGAMENTO called !! ");
			FaultType er = response.getFault();
			if (er!=null && er.getFaultCode()!=null && !er.getFaultCode().equals("")){
				logger.debug(" ========== LA CHIAMATA A WEBSERVICES AUTORIZZAZIONE PAGAMENTO HA RESTITUITO UN ERRORE CON CODICE =  " + er.getFaultCode());
				throw new AutorizzazionePagamentoException(er.getFaultCode(), er.getFaultDescription());
			}
			List<String> headers = (List<String>)((BindingProvider) port) .getRequestContext().get( MessageContext.HTTP_RESPONSE_HEADERS); 
			
			return response;
		} catch (RemoteException e) {
			logger.error("[AutorizzazionePagamentoHelper::verificaPagamento] got RemoteException: "+e.getMessage());
			throw new WebServiceException(e.getMessage());
		}
		
	
	}
	
	public static AttivaPagamentoResponseType attivaPagamento(AttivaPagamentoRequestType parameters, String endPoint) throws Exception{
		try{
			
			
			AutorizzazionePagamento port = initWsConnection(endPoint);
			
	     
			AttivaPagamentoResponseType response = port.attivaPagamento(parameters);
			logger.info(" !! Webservice ATTTIVA PAGAMENTO called !! ");
			FaultType er = response.getFault();
			if (er!=null && er.getFaultCode()!=null && !er.getFaultCode().equals("")){
				logger.debug(" ========== LA CHIAMATA A WEBSERVICES ATTIVA PAGAMENTO HA RESTITUITO UN ERRORE CON CODICE =  " + er.getFaultCode());
				throw new AutorizzazionePagamentoException(er.getFaultCode(), er.getFaultDescription());
			}
			
			return response;
		} catch (RemoteException e) {
			logger.error("[AutorizzazionePagamentoHelper::attivaPagamento] got RemoteException: "+e.getMessage());
			throw new WebServiceException(e.getMessage());
		}
		
	
	}
	
	private static AutorizzazionePagamento initWsConnection(String endPoint) throws Exception{
		//String urlTemp= "http://localhost:8080/IdpBillerWSSimulatorWeb/AutorizzazionePagamentoService";
		AutorizzazionePagamento_Service ndp = new AutorizzazionePagamento_Service();
		endPoint = URLDecoder.decode(endPoint, "UTF-8");
		AutorizzazionePagamento port = ndp.getAutorizzazionePagamentoSOAP();
		BindingProvider bindingProvider = (BindingProvider) port;
		
		
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endPoint);	 
		
        logger.info(" ========== URL PER CHIAMATA A WEBSERVICES VERIFICA PAGAMENTO =  " + endPoint);
        return port;
	}
}
