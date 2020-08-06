package it.tasgroup.addon.api.authentication;


import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCart;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.net.MalformedURLException;


public interface AuthenticationGateway {
    public String authenticateAnonymous(SessionShoppingCart<SessionShoppingCartItemDTO> cart)
        throws MalformedURLException;

    public String authenticateIrisUser(String session, SessionShoppingCart<SessionShoppingCartItemDTO> cart)
        throws MalformedURLException;
}
