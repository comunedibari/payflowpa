package it.tasgroup.idp.billerservices.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.billerservices.controller.notification.NotificationController;
import it.tasgroup.idp.billerservices.rest.Commons.DataValidationException;
import it.tasgroup.idp.billerservices.rest.Commons.EnteSil;
import it.tasgroup.idp.billerservices.rest.Commons.MalformedRequestException;
import it.tasgroup.idp.billerservices.rest.Commons.TrasmissioneNotFoundException;
import it.tasgroup.idp.billerservices.rest.Commons.UserNotFoundException;
import it.tasgroup.idp.notification.AckFileRequest;
import it.tasgroup.idp.notification.AckFileResponse;
import it.tasgroup.idp.notification.GetFileNotificaRequest;
import it.tasgroup.idp.notification.GetFileNotificaResponse;
import it.tasgroup.idp.notification.ListaNotificheRequest;
import it.tasgroup.idp.notification.ListaNotificheResponse;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.toscana.rete.cart.servizi.iris.smartproxy.services.rest.xsd.InformativaPagamentiList;

/*
 * NOTA: questa classe implementa le stesse funzionalita' di quella omonima dello SmartProxy
 *       pero' richiamando l'implementazione del Loader
 */

@Stateless
@Path("/proxyiris-smart/ws/rest/informativapagamenti")
public class InformativaPagamenti {

	@EJB(beanName = "NotificationControllerImpl")
	private NotificationController controller;
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("/list")
	public Response list(@HeaderParam("authorization") String authString) throws Exception {
		String idServizioRest = "InformativaPagamenti.list";
		logger.info(idServizioRest);
		try {
			List<EnteSil> listaEnteSil = Commons.checkUserAuthentication(authString, idServizioRest, em, logger);

			ListaNotificheRequest request = WSAdapter.buildGetListaNotificheRequest(listaEnteSil);
			ListaNotificheResponse response = controller.listaNotifiche(request);
			InformativaPagamentiList lista = WSAdapter.build(response);

			return Response.ok(lista).build();
			
		} catch (UserNotFoundException unfe) {
			logger.error(idServizioRest + " Utente non trovato. [" + unfe.getMessage() + "]", unfe);
			return Response.status(Response.Status.UNAUTHORIZED).entity("Utente non trovato.").build(); // return Response.status(401).header("WWW-Authenticate", "Basic").entity("Utente non trovato.").build();
		} catch (Exception e) {
			logger.error(idServizioRest + " Errore generico. [" + e.getMessage() + "]", e);
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Errore generico.").build();
		}
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/get")
	public Response get(@QueryParam("idTrasmissione") String idTrasmissione, @HeaderParam("authorization") String authString) throws Exception {
		String idServizioRest = "InformativaPagamenti.get";
		logger.info(idServizioRest + ": idTrasmissione [" + idTrasmissione + "]");
		try {

			if (idTrasmissione == null || idTrasmissione.trim().isEmpty()) {
				throw new MalformedRequestException();
			}

			List<EnteSil> listaEnteSil = Commons.checkUserAuthentication(authString, idServizioRest, em, logger);

			GetFileNotificaRequest getFileNotificaRequest = WSAdapter.buildGetFileNotificaRequest(idTrasmissione, listaEnteSil);
			GetFileNotificaResponse getFileNotificaResponse = controller.getFileNotifica(getFileNotificaRequest);
			String esito = WSAdapter.build(getFileNotificaResponse);

			try {
				AckFileRequest ackFileRequest = WSAdapter.buildAckFileRequest(idTrasmissione, listaEnteSil);
				AckFileResponse ackFileResponse = controller.ackFileNotifica(ackFileRequest); // attualmente la risposta e' sempre ok
			} catch (Exception e) {
				logger.warn(idServizioRest + " [" + e.getMessage() + "]", e);
			}
			
			return Response.ok(esito).build();
			
		} catch (MalformedRequestException mre) {
			logger.error(idServizioRest + " Richiesta malformata. [" + mre.getMessage() + "]", mre);
			return Response.status(Response.Status.BAD_REQUEST).entity("Richiesta malformata.").build();
		} catch (DataValidationException dve) {
			logger.error(idServizioRest + " [" + dve.getMessage() + "]", dve);
			return Response.status(Response.Status.BAD_REQUEST).entity(dve.getMessage()).build();
		} catch (UserNotFoundException unfe) {
			logger.error(idServizioRest + " Utente non trovato. [" + unfe.getMessage() + "]", unfe);
			return Response.status(Response.Status.UNAUTHORIZED).entity("Utente non trovato.").build(); // return Response.status(401).header("WWW-Authenticate", "Basic").entity("Utente non trovato.").build();
		} catch (TrasmissioneNotFoundException tnfe) {
			logger.error(idServizioRest + " Trasmissione non trovata. [" + tnfe.getMessage() + "]", tnfe);
			return Response.status(Response.Status.NOT_FOUND).entity("Trasmissione non trovata.").build();
		} catch (Exception e) {
			logger.error(idServizioRest + " Errore generico. [" + e.getMessage() + "]", e);
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Errore generico.").build();
		}
	}

}
