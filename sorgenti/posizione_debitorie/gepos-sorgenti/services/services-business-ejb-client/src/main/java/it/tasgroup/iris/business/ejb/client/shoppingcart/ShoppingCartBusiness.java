/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.shoppingcart;

import it.tasgroup.iris.domain.CondizionePagamento;

import java.util.List;


/**
 * @author PazziK
 *
 */
public interface ShoppingCartBusiness
{
	
	public List<CondizionePagamento> readPurchasableCartItemList(List<String> idCondizioni);	
	
	public List<CondizionePagamento> readPurchasableCartItemListByJSessionId(String jSessionId);	
		
	public void removeCartItem(String idCondizione);
	  
	public void removeCartItemList(List<String> idCondizioni);

	public void removeAllItems(String jSessionId);
	
	public List<CondizionePagamento> addCartItemList(String jSessionId, String operatore, List<String> idCondizioni, String applicationId, String systemId, Boolean isSpontaneous);
	
	public List<CondizionePagamento> readFullCartItemList(List<String> idCondizioni);
}
