package it.tasgroup.idp.rs.endpoints;

import it.tasgroup.idp.rs.model.Profilo;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.iris.sso.manager.SSOManager;
import it.tasgroup.iris.sso.manager.SSOManagerFactory;
import it.tasgroup.iris.sso.manager.SSOUser;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("/")
public class ProfiloEndPoint {

	@Context private HttpServletRequest servletRequest;
	
	/**
     * Restituisce il profilo dell'utente autenticato (CF, email, anagrafica).
     * Si integra con la funzionalita' SSO gestita dall'applicazione.
     * @return <a href="el_ns0_profilo.html">Profilo</a>
     */
    @GET
    @Produces("application/json")
    @Path("auth/profilo")
    public Profilo getProfilo() {
    	

		try {
			
			SSOManager ssoManager = SSOManagerFactory.getSSOManager();
			
			SSOUser authenticatedUser = ssoManager.getAuthenticatedUser(servletRequest);
			
			return buildProfilo(authenticatedUser);
			
		} catch(Exception e) {
			
			return buildProfiloAnonimo();
		}

    }
    
    
    private Profilo buildProfilo(SSOUser authenticatedUser) {
    	Profilo returnValue = new Profilo();
    	returnValue.setAnagrafica(authenticatedUser.getSSOName()+" "+authenticatedUser.getSSOSurname());
    	returnValue.setCodiceFiscale(authenticatedUser.getSSOCodiceFiscale());
    	//TODO: provare a recuperare la mail ?
    	return returnValue;
    	
    }
    
    private Profilo buildProfiloAnonimo() {
       	Profilo returnValue = new Profilo();
    	returnValue.setAnagrafica("ANONIMO");
    	returnValue.setCodiceFiscale("ANONIMO");
    	return returnValue;
 
    }

}
