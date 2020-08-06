package it.tasgroup.idp.billerservices.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
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
import it.tasgroup.idp.billerservices.rest.result.InformativaPagamentiResultList;
import it.tasgroup.idp.notification.AckFileRequest;
import it.tasgroup.idp.notification.AckFileResponse;
import it.tasgroup.idp.notification.GetFileNotificaRequest;
import it.tasgroup.idp.notification.GetFileNotificaResponse;
import it.tasgroup.idp.notification.ListaNotificheRequest;
import it.tasgroup.idp.notification.ListaNotificheResponse;
import it.tasgroup.idp.util.ServiceLocalMapper;

/*
 * NOTA: questa classe implementa le  funzionalita' di quella omonima dello SmartProxy
 *       richiamando però l'implementazione del Loader ed esponendo un'interfaccia senza 
 *       la basic authentication 
 */

@Stateless
@Path("/informativa_pagamenti")
public class InformativaPagamenti2 {

	@EJB(beanName = "NotificationControllerImpl")
	private NotificationController controller;
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/list")
	public Response list( @QueryParam("senderId") String senderId, @QueryParam("senderSys") String senderSys) throws Exception {
		String idServizioRest = "InformativaPagamenti2.list";
		logger.info(idServizioRest);
		try {
			List<EnteSil> listaEnteSil = Commons.checkEnteSil(senderId,senderSys,idServizioRest, em, logger);

			ListaNotificheRequest request = WSAdapter2.buildGetListaNotificheRequest(listaEnteSil);
			ListaNotificheResponse response = controller.listaNotifiche(request);
			InformativaPagamentiResultList lista = WSAdapter2.build(response);

			return Response.ok(lista).build();
			
		} catch (UserNotFoundException unfe) {
			logger.error(idServizioRest + " Utente non trovato. [" + unfe.getMessage() + "]", unfe);
			return Response.status(Response.Status.NOT_FOUND).entity("Utente non trovato.").build(); 
		} catch (Exception e) {
			logger.error(idServizioRest + " Errore generico. [" + e.getMessage() + "]", e);
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Errore generico.").build();
		}
	}
	
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/get")
	public Response get(@QueryParam("idTrasmissione") String idTrasmissione, @QueryParam("senderId") String senderId, @QueryParam("senderSys") String senderSys) throws Exception {
		String idServizioRest = "InformativaPagamenti2.get";
		logger.info(idServizioRest + ": idTrasmissione [" + idTrasmissione + "]");
		try {

			if (idTrasmissione == null || idTrasmissione.trim().isEmpty()) {
				throw new MalformedRequestException();
			}

			List<EnteSil> listaEnteSil = Commons.checkEnteSil(senderId,senderSys,idServizioRest, em, logger);

			GetFileNotificaRequest getFileNotificaRequest = WSAdapter2.buildGetFileNotificaRequest(idTrasmissione, listaEnteSil);
			GetFileNotificaResponse getFileNotificaResponse = controller.getFileNotifica(getFileNotificaRequest);
			String esito = WSAdapter2.build(getFileNotificaResponse);

			try {
				AckFileRequest ackFileRequest = WSAdapter2.buildAckFileRequest(idTrasmissione, listaEnteSil);
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
			return Response.status(Response.Status.NOT_FOUND).entity("Utente non trovato.").build(); 
		} catch (TrasmissioneNotFoundException tnfe) {
			logger.error(idServizioRest + " Trasmissione non trovata. [" + tnfe.getMessage() + "]", tnfe);
			return Response.status(Response.Status.NOT_FOUND).entity("Trasmissione non trovata.").build();
		} catch (Exception e) {
			logger.error(idServizioRest + " Errore generico. [" + e.getMessage() + "]", e);
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Errore generico.").build();
		}
	}

}
