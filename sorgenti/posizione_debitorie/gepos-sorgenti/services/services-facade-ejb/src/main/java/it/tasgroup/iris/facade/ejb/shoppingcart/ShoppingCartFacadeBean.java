package it.tasgroup.iris.facade.ejb.shoppingcart;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.business.ejb.client.shoppingcart.ShoppingCartBusinessLocal;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.facade.ejb.anonymous.dto.builder.SessionShoppingCartItemDTOBuilder;
import it.tasgroup.iris.facade.ejb.client.shoppingcart.ShoppingCartFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.shoppingcart.ShoppingCartFacadeRemote;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCart;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "ShoppingCartFacade")
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ShoppingCartFacadeBean implements ShoppingCartFacadeLocal, ShoppingCartFacadeRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(ShoppingCartFacadeBean.class);	
	
	@EJB
	private ShoppingCartBusinessLocal cartBusinessBean;
	
	@Override
	public List<SessionShoppingCartItemDTO> readFullCartItemList(List<String> idCondizioni) {
		
		if (idCondizioni == null)
			throw new IllegalStateException("Impossibile leggere una lista di condizioni nulla");
		
		if (idCondizioni.isEmpty())
			throw new IllegalStateException("Impossibile leggere una lista di condizioni vuota");
		
		List<CondizionePagamento> condizioni = cartBusinessBean.readFullCartItemList(idCondizioni);
				
		List<SessionShoppingCartItemDTO> cartItems = SessionShoppingCartItemDTOBuilder.populateSessionShoppingCartItemDTOList(condizioni, StatiPagamentiIris.IN_CORSO.getPagaMapping());
		
		return cartItems;
	}
	
	@Override
	public List<SessionShoppingCartItemDTO> readCartItemList(List<String> idCondizioni) {
		
		if (idCondizioni == null)
			throw new IllegalStateException("Impossibile leggere una lista di condizioni nulla");
		
		if (idCondizioni.isEmpty())
			throw new IllegalStateException("Impossibile leggere una lista di condizioni vuota");
		
		List<CondizionePagamento> condizioni = cartBusinessBean.readPurchasableCartItemList(idCondizioni);
				
		List<SessionShoppingCartItemDTO> cartItems = SessionShoppingCartItemDTOBuilder.populateSessionShoppingCartItemDTOList(condizioni, StatiPagamentiIris.IN_CORSO.getPagaMapping());
		
		return cartItems;
	}

	@Override
	public SessionShoppingCart<SessionShoppingCartItemDTO> createNewCart(IProfileManager profile, List<String> idCondizioni) {
			
		List<CondizionePagamento> condizioni = cartBusinessBean.readPurchasableCartItemList(idCondizioni);
				
		List<SessionShoppingCartItemDTO> cartItems = SessionShoppingCartItemDTOBuilder.populateSessionShoppingCartItemDTOList(condizioni, StatiPagamentiIris.IN_CORSO.getPagaMapping());
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = new SessionShoppingCart<SessionShoppingCartItemDTO>();
		
		cart.setItemList(cartItems);
		
		return cart;
	}
	
	@Override
	public SessionShoppingCart<SessionShoppingCartItemDTO> addCartItemList(String jSessionId, String operatore, List<String> idCondizioni) {
		
		// autenticato in generale con SSO, non solo con ARPA,
    	//TODO per generalizzarne il naming occorre  modificare contemporaneamnete il valore censito in tabella CFG_IRIS_GATEWAY_CLIENT)
		cartBusinessBean.addCartItemList(jSessionId, operatore, idCondizioni, "IRIS_ARPA_WA", "IRIS", false);
				
		return readCart(jSessionId);
	}

	@Override
	public SessionShoppingCart<SessionShoppingCartItemDTO> readCart(String jSessionId) {
		
		List<CondizionePagamento> condizioniPagabili = cartBusinessBean.readPurchasableCartItemListByJSessionId(jSessionId);
		
		List<SessionShoppingCartItemDTO> cartItems = SessionShoppingCartItemDTOBuilder.populateSessionShoppingCartItemDTOList(condizioniPagabili, StatiPagamentiIris.IN_CORSO.getPagaMapping());
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = new SessionShoppingCart<SessionShoppingCartItemDTO>();
		
		cart.setItemList(cartItems);
		
		return cart;
	}	

	@Override
	public  SessionShoppingCart<SessionShoppingCartItemDTO> removeCartItem(String jSessionId, String idCondizione) {
		
		cartBusinessBean.removeCartItem(idCondizione);
		
		return readCart(jSessionId);
	}

	@Override
	public  SessionShoppingCart<SessionShoppingCartItemDTO> removeAllItems(String jSessionId) {
		
		cartBusinessBean.removeAllItems(jSessionId);
		
		return readCart(jSessionId);
	}
	
	@Override
	public  SessionShoppingCart<SessionShoppingCartItemDTO> removeCartItemList(String jSessionId, List<String> idCondizioni) {

		cartBusinessBean.removeCartItemList(idCondizioni);
		
		return readCart(jSessionId);
		
	}
	
}
