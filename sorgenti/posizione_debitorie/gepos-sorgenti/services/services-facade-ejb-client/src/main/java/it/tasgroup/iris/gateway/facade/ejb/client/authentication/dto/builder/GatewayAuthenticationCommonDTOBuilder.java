/**
 * 
 */
package it.tasgroup.iris.gateway.facade.ejb.client.authentication.dto.builder;

import it.tasgroup.iris.dto.PendenzaDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;
import it.tasgroup.iris.dto.gateway.PaymentAuthenticationDTO;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class GatewayAuthenticationCommonDTOBuilder {

	public static PaymentAuthenticationDTO populatePaymentAuthenticationDTO(String token, BigDecimal decimalAmountToCheck, String url_ok, String url_ko, String url_annulla, String flagOpposizione730, String redirectOnly){
		
		PaymentAuthenticationDTO dto = new PaymentAuthenticationDTO();
		
		dto.setToken(token);
		dto.setAmountToCheck(decimalAmountToCheck);		
		dto.setUrl_ok(url_ok);
		dto.setUrl_ko(url_ko);
		dto.setUrl_annulla(url_annulla);
		dto.setFlagOpposizione730(flagOpposizione730);
		dto.setRedirectOnly(redirectOnly);
		return dto;
	}
	
	public static TestataMessaggioDTO populateTestataMessaggioDTO( String sessionId, String senderId, String senderSIL, String receiverId, String receiverSIL){
		
		TestataMessaggioDTO requestHeaderDTO = new TestataMessaggioDTO();
		
	    requestHeaderDTO.setSenderSys(senderId);
	    
	    requestHeaderDTO.setSenderSil(senderSIL);
	    
	    requestHeaderDTO.setReceiverSys(receiverId);
	    
	    requestHeaderDTO.setReceiverSil(receiverSIL);
	    
	    SessionIdDTO sessionDTO = new SessionIdDTO();
	    
	    sessionDTO.setToken(sessionId);
	    
	    // TODO PAZZIK: settare anche data e ora della richiesta di accesso?
	    
	    requestHeaderDTO.setSession(sessionDTO);
	    
		return requestHeaderDTO;
	}
	
public static RichiestaAutorizzazioneDTO populateRichiestaAutorizzazioneDTO(List<SessionShoppingCartItemDTO> listacondizioni){
	
		
		RichiestaAutorizzazioneDTO richAutDTO = new RichiestaAutorizzazioneDTO();
		
		PendenzaDTO pendDTO = new PendenzaDTO();
		
		BigDecimal totalAmount = new BigDecimal(0);
		
		for(SessionShoppingCartItemDTO item : listacondizioni){
			
			pendDTO.getCondizioni().add(item.getIdCondizione());
			
			pendDTO.getImporti().add(item.getImporto());
			
			pendDTO.getListaItemFlagOpposizione().add(item.getItemOpposizione730());
			
			totalAmount = totalAmount.add(item.getImporto()).setScale(2);
		}
		
		richAutDTO.getPendenze().add(pendDTO);
		
		richAutDTO.setTotalAmount(totalAmount.setScale(2));
		
		return richAutDTO;
		
	}

}
