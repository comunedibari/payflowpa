/**
 * 
 */
package it.tasgroup.iris.sessioncart;

import it.tasgroup.iris.facade.ejb.client.shoppingcart.ShoppingCartFacade;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCart;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Classe di supporto nella gestione della persistenza del carrello di sessione.
 * 
 * @author pazzik
 *
 */
public class ShoppingCartPersistenceHelper {
	
	final static public String CARRELLO_KEY = ShoppingCartSessionHelper.CARRELLO_KEY;
		
	
	private static ShoppingCartFacade getCartBean() {
		try{
			return  (ShoppingCartFacade) ServiceLocator.getSLSBProxy("shoppingCartFacadeBeanRemote");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

	public static void emptyCart(HttpServletRequest request){
	
		emptyCart(request.getSession());
		
	}
	
	public static void emptyCart(HttpSession session){
		
		emptyCart(session.getId(), session);
		
	}
	
	public static void emptyCart(String jSessionId, HttpSession session){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCartBean().removeAllItems(jSessionId);
		
		cart.setOpposizioneInvio730(null);
		
		ShoppingCartSessionHelper.setCartInSession(cart, session);
		
	}
	
	public static void removeCartItemList(HttpServletRequest request, List<String> idCondizioni){
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCartBean().removeCartItemList(request.getSession().getId(), idCondizioni);
		
		check730(cart);
		
		ShoppingCartSessionHelper.setCartInSession(cart, request.getSession());
	}

	public static void removeCartItemList(HttpSession session, String jSessionId, List<String> idCondizioni){
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCartBean().removeCartItemList(jSessionId, idCondizioni);
		check730(cart);
		ShoppingCartSessionHelper.setCartInSession(cart, session);
	}
	
	public static void removeCartItem(HttpServletRequest request, String idCondizione){
		
		removeCartItem(request.getSession(), request.getSession().getId(), idCondizione);
	}
	
	public static void removeCartItem(HttpSession session, String jSessionId, String idCondizione){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCartBean().removeCartItem(jSessionId, idCondizione);
		
		check730(cart);
		
		ShoppingCartSessionHelper.setCartInSession(cart, session);
		
	}
	
	public static SessionShoppingCart<SessionShoppingCartItemDTO> addCartItemListNoSave(String jSessionId, String operatore, HttpSession session, List<String> idCondizioni){
		
		List<SessionShoppingCartItemDTO> cartItems = getCartBean().readCartItemList(idCondizioni);

		return ShoppingCartSessionHelper.addCartItemList(session, cartItems);
		
	}
	
	/**
	 * Metodo che rilegge da DB tutte le condizioni del carrello anche se non pagabili.
	 * 
	 * Risulta utile per i controlli sullo stato di pagabilità delle condizioni, quando occorre 
	 * avvertire l'utente che qualche stato è cambiato.
	 * 
	 * 
	 * @param session
	 * @return
	 */
	public static List<SessionShoppingCartItemDTO> readFullCartItemList(HttpSession session){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> originalCart = ShoppingCartSessionHelper.getCart(session);
		
		if (originalCart.isEmpty())
			return originalCart.getItemList();
		
		List<String> idCondizioni = originalCart.getItemIdList();
		
		List<SessionShoppingCartItemDTO> cartItems = getCartBean().readFullCartItemList(idCondizioni);
		
		//GESTIONE OPPOSIZIONE 730
		List<SessionShoppingCartItemDTO> originalCartItems = originalCart.getItemList();
		
		Map<String, SessionShoppingCartItemDTO> originalCartItemsMap = new HashMap<String, SessionShoppingCartItemDTO>();
		for (SessionShoppingCartItemDTO originalCartItem : originalCartItems) 
			originalCartItemsMap.put(originalCartItem.getIdCondizione(), originalCartItem);
		
		for (SessionShoppingCartItemDTO cartItem : cartItems) {
			SessionShoppingCartItemDTO originalCartItemTmp = originalCartItemsMap.get(cartItem.getIdCondizione());
			cartItem.setItemOpposizione730(originalCartItemTmp.getItemOpposizione730());
			
			cartItem.setTipoSoggettoVers(originalCart.getTipoSoggettoVers()); 
            cartItem.setCodFiscaleVers(originalCart.getCodFiscaleVers());			
			cartItem.setAnagraficaVers(originalCart.getAnagraficaVers());
			cartItem.setIndirizzoVers(originalCart.getIndirizzoVers()); 
			cartItem.setNumeroCivicoVers(originalCart.getNumeroCivicoVers()); 
			cartItem.setCapVers(originalCart.getCapVers()); 
			cartItem.setLocalitaVers(originalCart.getLocalitaVers()); 
			cartItem.setProvinciaVers(originalCart.getProvinciaVers()); 
			cartItem.setNazioneVers(originalCart.getNazioneVers()); 			
			cartItem.setEmailVers(originalCart.getEmailVers());
			
			
		}

		return cartItems;
		
	}
	
	/**
	 * Metodo che rilegge le condizioni del carrello per un carrello non persistente 
	 * (necessario quindi per rinfrescare il carrello in modalità non autenticata).
	 * 
	 * Si differenzia dal metodo readCart perchè non necessita di un jsessionid 
	 * memorizzato su db da cui ricavare gli id delle condizioni associate.
	 * 
	 * @param session
	 * @return
	 */
	public static SessionShoppingCart<SessionShoppingCartItemDTO> refreshCart(HttpSession session){
		
		SessionShoppingCart<SessionShoppingCartItemDTO> originalCart = ShoppingCartSessionHelper.getCart(session);
		Boolean opposizione730Value = originalCart.getOpposizioneInvio730();
		
		if (originalCart.isEmpty())
			return originalCart;
		
		List<String> idCondizioni = originalCart.getItemIdList();
		
		List<SessionShoppingCartItemDTO> cartItems = getCartBean().readCartItemList(idCondizioni);
		
		 for (SessionShoppingCartItemDTO cartItem: cartItems) {
			 cartItem.setItemOpposizione730(originalCart.getCartItem(cartItem.getItemId()).getItemOpposizione730());
		 }
		
		ShoppingCartSessionHelper.emptyCart(session);
		
		SessionShoppingCart<SessionShoppingCartItemDTO> refreshedCart = ShoppingCartSessionHelper.addCartItemList(session, cartItems);
		
		refreshedCart.setOpposizioneInvio730(opposizione730Value);
		

		if(originalCart.getIsMassiveCart()){
		    SessionShoppingCartItemDTO item = refreshedCart.getItemList().get(0);
		    String causale = item.getTributo() + ": pagamento massivo (" + item.getEnte() + ")";
		    ShoppingCartSessionHelper.setMassiveCartInSession(refreshedCart, causale, session);
		}

		return refreshedCart;
		
	}
	
	public static SessionShoppingCart<SessionShoppingCartItemDTO> addCartItemList(String jSessionId, String operatore, HttpSession session, List<String> idCondizioni){
			
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCartBean().addCartItemList(jSessionId, operatore, idCondizioni);
		
		check730(cart);
		 
		ShoppingCartSessionHelper.setCartInSession(cart, session);
		
		return cart;

	}
	
	/**
	 * Legge il carrello da DB a partire da un jsessionId fornito in ingresso.
	 * Quindi, si basa sull'ipotesi che su DB il un carrello sia associato al jsessionid.
	 * 
	 * @param jSessionId
	 * @param session
	 * @return
	 */
	public static SessionShoppingCart<SessionShoppingCartItemDTO> readCart(String jSessionId, HttpSession session){	
		
		SessionShoppingCart<SessionShoppingCartItemDTO> cart = getCartBean().readCart(jSessionId);
		
		cart.setOpposizioneInvio730(ShoppingCartSessionHelper.getCart(session).getOpposizioneInvio730());
		
		ShoppingCartSessionHelper.setCartInSession(cart, session);
		
		return cart;

	}
	
	public static SessionShoppingCart<SessionShoppingCartItemDTO> readCart(HttpSession session){	
		
		return readCart(session.getId(),session);

	}
	
	private static void check730(SessionShoppingCart<SessionShoppingCartItemDTO> cart) {
		boolean invioDatiPagamentoSanita = false;
		Properties cpl = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
		String abilitaGestione730 = cpl.getProperty("paytas.abilita.flag.opposizione.730", "");
		if (Boolean.parseBoolean(abilitaGestione730)) {
	    	// Non ho ancora mostrato l'opzione all'utente
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
					cart.setOpposizioneInvio730(Boolean.FALSE);
				} 
			} else 	{
				cart.setOpposizioneInvio730(null);
			}
		} 
	}
}
