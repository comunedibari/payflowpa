/**
 * 
 */
package it.tasgroup.iris.sessioncart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCart;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

/**
 * Classe di supporto nella gestione del carrello in sessione.
 * 
 * Tale classe � indifferente alla persistenza o meno del carrello su DB.
 * 
 * @author pazzik 
 *
 */
public class ShoppingCartSessionHelper { 
	
	final static public String CARRELLO_KEY ="sessionShoppingCart";
	
	final static public String CARRELLO_KEY_BACKUP ="sessionShoppingCartBackup";
	

	public static void emptyCart(HttpServletRequest request) {
	
		emptyCart(request.getSession()); 
	}
	
	public static void emptyCart(HttpSession session){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(session);

		cart.removeAllItems();
	}
	
	public static void removeCartItem(HttpServletRequest request, String idCondizione) {
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(request);
		
		cart.removeItem(idCondizione);
		
		check730(cart);

	}
	
	public static void removeCartItemList(HttpServletRequest request, ArrayList<String> idCondizioni){
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(request);
		cart.removeItemList(idCondizioni);
		check730(cart);
		ShoppingCartSessionHelper.setCartInSession(cart, request.getSession());
	}

	
	public static void addCartItem(HttpServletRequest request, SessionShoppingCartItemDTO item){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(request);

		cart.addItem(item);
		
		check730(cart);
	}
	
	public static void setOpposizione730(HttpServletRequest request, Boolean opposizione730){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(request);

		cart.setOpposizioneInvio730(opposizione730);
		

	}
	
	private static void check730(SessionShoppingCart<SessionShoppingCartItemDTO> cart) {
		boolean invioDatiPagamentoSanita = false;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		Properties cpl = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
		String abilitaGestione730 = cpl.getProperty("paytas.abilita.flag.opposizione.730", "");
		if (Boolean.parseBoolean(abilitaGestione730)) {
//			if(cart.getOpposizioneInvio730() == null) { 
				// Controllo se nel carrello sono presenti prestazioni sanitarie
				for (int i = 0; i < cart.getItemList().size() && !invioDatiPagamentoSanita; i++) {
					if ("Categoria013".equals(cart.getItemList().get(i).getIdTributo())) {
						invioDatiPagamentoSanita = true;
					}
			    }
				if (invioDatiPagamentoSanita) {
					// nel carrello sono presenti prestazioni sanitarie -> il sistema ricarica la pagina di visualizzazione del carrello mostrando 
					// una nuova sezione sotto al riepilogo del carrello 
					if(cart.getOpposizioneInvio730() == null) { 
						setOpposizione730(request, Boolean.FALSE);
					}
				} else 	{
					setOpposizione730(request, null);
				}
//			}
		} 
	}

	
	public static Boolean setItemOpposizione730(HttpServletRequest request, String itemId, String itemOpposizione730){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(request);
		List<SessionShoppingCartItemDTO> cartItemList = cart.getItemList();
		boolean found = false;
		for (SessionShoppingCartItemDTO item : cartItemList) {
			if(item.getItemId().equals(itemId) && !found) {
				item.setItemOpposizione730(itemOpposizione730);
				found = true;
			}	
		}
		return found;
		
	}

	public static SessionShoppingCart<SessionShoppingCartItemDTO> addCartItemList(HttpSession session, List<SessionShoppingCartItemDTO> itemList){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(session);

		cart.addItemList(itemList);
		
		check730(cart);
		
		return cart;
	}
	
	public static List<SessionShoppingCartItemDTO> getCartItemList(HttpServletRequest request){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(request);

		return cart.getItemList();
	}
	
	public static SessionShoppingCart<SessionShoppingCartItemDTO> getCart(HttpServletRequest request){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(request.getSession());
		
		return cart;
		
	}
	
	public static SessionShoppingCart<SessionShoppingCartItemDTO> getCart(HttpSession session){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = (SessionShoppingCart<SessionShoppingCartItemDTO>) session.getAttribute(CARRELLO_KEY);
		
		if (cart == null) {
			
			cart = new SessionShoppingCart<SessionShoppingCartItemDTO>();
			
			setCartInSession(cart, session);
		}
		
		return cart;
		 
	}
	
	public static void setCartInSession(SessionShoppingCart<SessionShoppingCartItemDTO> cart, HttpServletRequest req) {
		
		setCartInSession(cart, req.getSession());
		
	}
	
	public static void setCartInSession(SessionShoppingCart<SessionShoppingCartItemDTO> cart, HttpSession session){
		
		session.setAttribute(CARRELLO_KEY, cart);
		
	}
	
	public static void setMassiveCartInSession(SessionShoppingCart<SessionShoppingCartItemDTO> cart, String causale, HttpServletRequest req) {
        
        setMassiveCartInSession(cart, causale, req.getSession());
        
    }
	
	public static void setMassiveCartInSession(SessionShoppingCart<SessionShoppingCartItemDTO> cart, String causale, HttpSession session){
	    if(cart.getIsMassiveCart()){
	        cart.setMassiveCartItem(getMassiveCartItem(cart.getMassiveScadenza(), causale, cart.getCartTotalAmount()));    	    
	    }
	    
	    session.setAttribute(CARRELLO_KEY, cart);
        
    }
	
	public static void removeCartFromSession(HttpServletRequest request){
		
		//
		// prima di rimuovere il carrello dalla sessione 
		// lo salvo (insieme al totale) nella request in modo
		// da poterlo mostrare nella pagina di esito
		//
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCart(request.getSession());
		List<SessionShoppingCartItemDTO> pagamentiWIP = cart.getItemList();
		// calcolo il totale
		BigDecimal importoTotalePagamenti = new BigDecimal(0);
		for (SessionShoppingCartItemDTO pagamentoWIP : pagamentiWIP) {
			importoTotalePagamenti = importoTotalePagamenti.add(pagamentoWIP.getImporto());
		}
		request.setAttribute("importoTotalePagamenti", importoTotalePagamenti);
		request.setAttribute(CARRELLO_KEY, cart);
		
		request.getSession().removeAttribute(CARRELLO_KEY);
		
	}

	private static SessionShoppingCartItemDTO getMassiveCartItem(Date scadenza, String causale, BigDecimal importoTot) {        
        SessionShoppingCartItemDTO item = new SessionShoppingCartItemDTO();
        item.setScadenza(scadenza);
        item.setCausale(causale);
        item.setCodFiscaleDebitore("-");
        item.setImporto(importoTot);
        
        return item;
    }
	
}
