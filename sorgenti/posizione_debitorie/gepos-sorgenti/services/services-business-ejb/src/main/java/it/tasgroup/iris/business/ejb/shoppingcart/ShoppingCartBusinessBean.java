package it.tasgroup.iris.business.ejb.shoppingcart;

import it.tasgroup.iris.business.ejb.client.shoppingcart.ShoppingCartBusinessLocal;
import it.tasgroup.iris.business.ejb.client.shoppingcart.ShoppingCartBusinessRemote;
import it.tasgroup.iris.domain.CfgIrisGatewayClient;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.IrisGatewayClientShopCart;
import it.tasgroup.iris.domain.Sessione;
import it.tasgroup.iris.persistence.dao.interfaces.CfgIrisGatewayClientDAO;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.IrisGatewayClientShopCartDAO;
import it.tasgroup.iris.persistence.dao.interfaces.SessioneDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "ShoppingCartBusiness")
public class ShoppingCartBusinessBean implements ShoppingCartBusinessLocal, ShoppingCartBusinessRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(ShoppingCartBusinessBean.class);	
	
	private static final String TO_CART = "toCart";
	
	private static final String TO_DELETE = "toDelete";
	
	@EJB(name = "IrisGatewayClientShopCartDAO")
	IrisGatewayClientShopCartDAO shoppingCartDAO;
	
	@EJB(name = "CondizioniPagamentoDao")
	CondizioniPagamentoDao condizioniPagamentoDAO;
	
	@EJB(name = "SessioneDAO")
	SessioneDAO sessioneDAO;
	
	@EJB(name = "CfgIrisGatewayClientDAO")
	CfgIrisGatewayClientDAO irisGatewayClientDAO;
	
	@Override
	public List<CondizionePagamento> readFullCartItemList(List<String> idCondizioni) {
		
		List<CondizionePagamento> condizioni = condizioniPagamentoDAO.getCondizioniByIdList(idCondizioni);
		
		return condizioni;
		
	}

	@Override
	public List<CondizionePagamento> readPurchasableCartItemList(List<String> idCondizioni) {
		
		List<CondizionePagamento> condizioni = condizioniPagamentoDAO.getCondizioniByIdList(idCondizioni);
		
		return getPurchasableItems(condizioni);
		
	}
	
	private Map<String,List<CondizionePagamento>> splitItems(List<CondizionePagamento> items){
		
		List<CondizionePagamento> filteredItems = new ArrayList<CondizionePagamento>();
		
		List<CondizionePagamento> discardedItems = new ArrayList<CondizionePagamento>();
		
		for (CondizionePagamento item : items){
			
			if (item.isDaPagare())
				
				filteredItems.add(item);			
			else 				
				discardedItems.add(item);
		}
		
		
		Map<String,List<CondizionePagamento>> returnMap = new HashMap<String,List<CondizionePagamento>>();
		
		returnMap.put(TO_CART, filteredItems);
		
		returnMap.put(TO_DELETE, discardedItems);
		
		return returnMap;
		
	}

	@Override
	public List<CondizionePagamento> readPurchasableCartItemListByJSessionId(String jSessionId) {
		
		// TODO PAZZIK MIGLIORARE PRESTAZIONI e IMPLEMENTAZIONE AD OGGETTI 
		// DOPO AVER CHIARITO SE SI VUOLE IL CARRELLO SULLA SESSIONE O SULL'OPERATORE
		
		List<IrisGatewayClientShopCart> cartItems = shoppingCartDAO.readAllIrisGatewayClientShopCartBySessionId(jSessionId);
		
		List<CondizionePagamento> condizioni = new ArrayList<CondizionePagamento>();
		
		for (IrisGatewayClientShopCart item : cartItems)
			
			condizioni.add(item.getCondizionePagamento());
		
		return getPurchasableItems(condizioni);
				
	}

	private List<CondizionePagamento> getPurchasableItems(List<CondizionePagamento> condizioni) {
		
		Map<String,List<CondizionePagamento>> returnMap =  splitItems(condizioni);
		
		List<CondizionePagamento> inCarrello = returnMap.get(TO_CART);
		
		List<CondizionePagamento> daEliminare = returnMap.get(TO_DELETE);
		
		if (!daEliminare.isEmpty())
		
			shoppingCartDAO.deleteIrisGatewayClientShopCartList(daEliminare);
		
		return inCarrello;
	}

	@Override
	public List<CondizionePagamento>  addCartItemList(String jSessionId, String operatore, List<String> idCondizioni, String applicationId, String systemId, Boolean isSpontaneous) {
		
		List<CondizionePagamento> condizioni = readPurchasableCartItemList(idCondizioni);
		
		Map<String,List<CondizionePagamento>> condizioniSplitted = splitItems(condizioni);
		
		List<CondizionePagamento> condizioniPagabili = condizioniSplitted.get(TO_CART);
		
		Sessione sessione = sessioneDAO.retrieveSessionById(jSessionId);
		
		CfgIrisGatewayClient cfgIrisGatewayClient = irisGatewayClientDAO.retrieveGatewayClient(applicationId, systemId);
		
		for (CondizionePagamento cond : condizioniPagabili){
			
			IrisGatewayClientShopCart item = new IrisGatewayClientShopCart();
			
			item.setCondizionePagamento(cond);
			
			item.setImTotale(cond.getImTotale());
			
			item.setTiSpontaneo(isSpontaneous?"V":"F");
			
			item.setCfgIrisGatewayClient(cfgIrisGatewayClient);
			
			item.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			
			item.setOpInserimento(operatore);
			
			item.setSessione(sessione);
			
			if (!sessione.getIrisGatewayClientShopCarts().contains(item))
							sessione.getIrisGatewayClientShopCarts().add(item);
		}
		
		sessioneDAO.updateSession(sessione);
		
		return condizioniPagabili;
	}
	
//	@Override
//	public void addCartItemList(IProfileManager profile, List<String> idCondizioniList) {
//		
//		return shoppingCartDAO.addIrisGatewayClientShopCart(idCondizioniList);
//	}

	@Override
	public void removeCartItem(String idCondizione) {
		
		shoppingCartDAO.removeIrisGatewayClientShopCartItem(idCondizione);
	}

	@Override
	public void removeAllItems(String jSessionId) {
		
		shoppingCartDAO.removeAllIrisGatewayClientShopCart(jSessionId);
	}

	@Override
	public void removeCartItemList(List<String> idCondizioni) {

		for (String idCondizione : idCondizioni)
			shoppingCartDAO.removeIrisGatewayClientShopCartItem(idCondizione);
		
		
	}

}
