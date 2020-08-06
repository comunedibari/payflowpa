package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.IrisGatewayClientShopCart;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

public interface IrisGatewayClientShopCartDAO extends Dao<IrisGatewayClientShopCart>{
	
	public List<IrisGatewayClientShopCart> readAllIrisGatewayClientShopCartBySessionId(String sessionId);
	
	public void removeIrisGatewayClientShopCartItem(String idCondizione);
	
	public void removeAllIrisGatewayClientShopCart(String sessionId);

	public void deleteIrisGatewayClientShopCartList(List<CondizionePagamento> list);

	//public List<IrisGatewayClientShopCart> addIrisGatewayClientShopCartList(List<IrisGatewayClientShopCart> cartItem);

}
