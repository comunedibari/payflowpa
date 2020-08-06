/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.IrisGatewayClientShopCart;
import it.tasgroup.iris.persistence.dao.interfaces.IrisGatewayClientShopCartDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author pazzik
 *
 */
@Stateless(name="IrisGatewayClientShopCartDAO") 
public class IrisGatewayClientShopCartDAOImpl extends DaoImplJpaCmtJta<IrisGatewayClientShopCart> implements IrisGatewayClientShopCartDAO{
	
	private static final Logger LOGGER = LogManager.getLogger(IrisGatewayClientShopCartDAOImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<IrisGatewayClientShopCart> readAllIrisGatewayClientShopCartBySessionId(String sessionId) {
		
		List<IrisGatewayClientShopCart> cartItems = null;
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("sessionId", sessionId);
		
		try {
			
			cartItems = (List<IrisGatewayClientShopCart>) listByQuery("getIrisGatewayClientShopCartBySessionId",parameters);
			
			for (IrisGatewayClientShopCart item : cartItems)
				item.getCondizionePagamento().updateStatoPagamentoCalcolato();
				
		} catch (Exception e) {
			LOGGER.error("error on readAllIrisGatewayClientShopCartBySessionId "+ parameters, e);
			throw new DAORuntimeException(e);
		}
		
		return cartItems;
	}
	
	@Override
	public void removeIrisGatewayClientShopCartItem(String idCondizione) {
		
		try {
			
			Query deleteCartItemQuery = createDeleteItemQuery(idCondizione);
			
			int result = deleteCartItemQuery.executeUpdate();
			
		} catch (Exception e) {
			
			LOGGER.error("error on removeIrisGatewayClientShopCartItem "+ idCondizione, e);
			
			throw new DAORuntimeException(e);
			
		}
	}

	@Override
	public void removeAllIrisGatewayClientShopCart(String sessionId) {
		
		try {
			
			Query deleteCartItemsQuery = createDeleteCartItemsQuery(sessionId);
			
			int result = deleteCartItemsQuery.executeUpdate();
			
		} catch (Exception e) {
			
			LOGGER.error("error on removeAllIrisGatewayClientShopCart "+ sessionId, e);
			
			throw new DAORuntimeException(e);
			
		}
	}
	
	private Query createDeleteCartItemsQuery(String sessionId) {
			
		String selectFromWhere = "delete from IRIS_GATEWAY_CLIENT_SHOP_CART where SESSION_ID = ?";
		
		Query query= em.createNativeQuery(selectFromWhere);
			
		query.setParameter(1, sessionId);
		
		return query;
	}
	
	private Query createDeleteItemQuery(String itemId) {
		
		String selectFromWhere = "delete from IRIS_GATEWAY_CLIENT_SHOP_CART where ID_CONDIZIONE = ?";
		
		Query query= em.createNativeQuery(selectFromWhere);
			
		query.setParameter(1, itemId);
		
		return query;
	}

	@Override
	public void deleteIrisGatewayClientShopCartList(List<CondizionePagamento> condizioni) {	
		
		List<String> idCondizioni = new ArrayList<String>();
		
		try {
			
			for (CondizionePagamento cond : condizioni){
				
				idCondizioni.add(cond.getIdCondizione());
			}
			
			Query deleteCartItemsQuery = createDeleteCartItemsQuery(idCondizioni);
			
			int result = deleteCartItemsQuery.executeUpdate();
			
		} catch (Exception e) {
			
			LOGGER.error("error on deleteIrisGatewayClientShopCartList "+ idCondizioni, e);
			
			throw new DAORuntimeException(e);
			
		}
		
	}
	
	private Query createDeleteCartItemsQuery(List<String> idCondizioni) {
		
		StringBuffer deleteFromWhere = new StringBuffer("delete from IRIS_GATEWAY_CLIENT_SHOP_CART where ID_CONDIZIONE in (");
		
		appendInCondition(deleteFromWhere, idCondizioni);
		
		Query query= em.createNativeQuery(deleteFromWhere.toString(), IrisGatewayClientShopCart.class);
			
		addInParameters(query, idCondizioni, 0);
		
		return query;
	}

}
