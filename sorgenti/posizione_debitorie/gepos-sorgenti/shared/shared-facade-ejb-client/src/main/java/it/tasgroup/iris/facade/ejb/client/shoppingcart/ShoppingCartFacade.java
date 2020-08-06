/**
 * 
 */
package it.tasgroup.iris.facade.ejb.client.shoppingcart;

import it.nch.profile.IProfileManager;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCart;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.util.List;


/**
 * @author PazziK
 *
 */
public interface ShoppingCartFacade
{
	public List<SessionShoppingCartItemDTO> readFullCartItemList(List<String> idCondizioni);
	
	public SessionShoppingCart<SessionShoppingCartItemDTO> createNewCart(IProfileManager profile, List<String> idCondizioni);	
	
	public SessionShoppingCart<SessionShoppingCartItemDTO> readCart(String jsessionId);
	
	public SessionShoppingCart<SessionShoppingCartItemDTO> addCartItemList(String jSessionId, String operatore, List<String> idCondizioni);
	
	public List<SessionShoppingCartItemDTO> readCartItemList(List<String> idCondizioni);
	
	public  SessionShoppingCart<SessionShoppingCartItemDTO> removeCartItem(String jSessionId, String idCondizione);

	public  SessionShoppingCart<SessionShoppingCartItemDTO> removeCartItemList(String jSessionId, List<String> idCondizioni);
	  
	public  SessionShoppingCart<SessionShoppingCartItemDTO> removeAllItems(String jSessionId);
	
}
