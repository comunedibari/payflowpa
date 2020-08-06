/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.gateway;

import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.tasgroup.iris.domain.SessioneGateway;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.exception.GatewayAuthenticationException;
import it.tasgroup.iris.dto.gateway.PaymentAuthenticationDTO;

import java.io.Serializable;
import java.rmi.RemoteException;


public interface GatewayAuthenticationBusiness extends Serializable{
	
	/**
	 * @param richAutDTO
	 * @return
	 * @throws GatewayAuthenticationException 
	 */
	public SessioneGateway saveGatewaySession(SessioneGateway sessioneGateway) throws GatewayAuthenticationException;
	
	/**
	 * @param autorizzazioneDto
	 * @return
	 */
	public EsitoOperazionePagamentoDTO checkPaymentGatewayShoppingCart(RichiestaAutorizzazioneDTO autorizzazioneDto);
	
	
	/**
	 * @param token
	 * @param amountToCheck 
	 * @return
	 * @throws GatewayAuthenticationException 
	 */
	public SessioneGateway authenticateForPayment(PaymentAuthenticationDTO dto) throws GatewayAuthenticationException;
	
	/**
	 * @param token
	 * @return
	 * @throws GatewayAuthenticationException 
	 */
	public SessioneGateway getSessioneGateway(String token) throws GatewayAuthenticationException;

	/**
	 * @param sessioneGateway
	 * @return
	 * @throws GatewayAuthenticationException
	 * @throws ServiceLocatorException 
	 * @throws RemoteException 
	 */
	public FrontEndContext populateFEC(SessioneGateway sessioneGateway) throws GatewayAuthenticationException, ServiceLocatorException, RemoteException;

}
