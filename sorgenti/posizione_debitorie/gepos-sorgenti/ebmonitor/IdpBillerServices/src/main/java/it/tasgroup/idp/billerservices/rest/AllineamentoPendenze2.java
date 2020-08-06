package it.tasgroup.idp.billerservices.rest;

import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import it.tasgroup.idp.billerservices.controller.loader.LoaderController;
import it.tasgroup.idp.billerservices.rest.Commons.DataValidationException;
import it.tasgroup.idp.billerservices.rest.Commons.EnteSil;
import it.tasgroup.idp.billerservices.rest.Commons.MalformedRequestException;
import it.tasgroup.idp.billerservices.rest.Commons.TrasmissioneNotFoundException;
import it.tasgroup.idp.billerservices.rest.Commons.UserNotFoundException;
import it.tasgroup.idp.billerservices.rest.result.GetStatoResultList;
import it.tasgroup.idp.billerservices.rest.result.SendResult;
import it.tasgroup.idp.loader.AllineamentoPendenzeRequest;
import it.tasgroup.idp.loader.AllineamentoPendenzeResponse;
import it.tasgroup.idp.loader.GetEsitoRequest;
import it.tasgroup.idp.loader.GetEsitoResponse;
import it.tasgroup.idp.loader.GetStatoRequest;
import it.tasgroup.idp.loader.GetStatoResponse;
import it.tasgroup.idp.loader.ListaTrasmissioniRequest;
import it.tasgroup.idp.loader.ListaTrasmissioniResponse;
import it.tasgroup.idp.util.ServiceLocalMapper;

/*
 * NOTA: questa classe implementa le  funzionalita' di quella omonima dello SmartProxy
 *       richiamando però l'implementazione del Loader ed esponendo un'interfaccia senza 
 *       la basic authentication 
 */

@Stateless
@Path("/allineamento_pendenze")
public class AllineamentoPendenze2 {

	@EJB(beanName = "LoaderControllerImpl")
	private LoaderController controller;
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Path("/send")
	public Response send(Map<String, String> formData, @QueryParam("senderId") String senderId, @QueryParam("senderSys") String senderSys) throws Exception {
		String idServizioRest = "AllineamentoPendenze2.send";
		logger.info(idServizioRest);
		try {
			List<EnteSil> listaEnteSil = Commons.checkEnteSil(senderId,senderSys,idServizioRest, em, logger);

			/*
			 * a parte "flusso" (qui rinominato in FILE) gli altri form-data presenti nella versione SmartProxy non sono utilizzati
			 * non lavoriamo di stream/attachment sul flusso perche comunque
			 * - anche nel codice originale dello SmartProxy si prendeva un InputStream per poi comunque metterlo in ujn byte[]
			 * - la primitiva dell'interfaccia WS vuole una stringa
			 * - ci risparmiamo la dipendenza org.apache.cxf / cxf-rt-frontend-jaxrs / 2.7.11
			 * TODO testare per flussi grandi
			*/
			String flusso = formData.get("FILE");
			if (flusso == null || flusso.trim().isEmpty()) {
				throw new MalformedRequestException();
			}
			
			AllineamentoPendenzeRequest request = WSAdapter2.buildAllineamentoPendenzeRequest(flusso, listaEnteSil);
			AllineamentoPendenzeResponse response = controller.allineamentoPendenze(request);
			SendResult result = WSAdapter2.build(response);
			
			return Response.ok(result).build();

		} catch (MalformedRequestException mre) {
			logger.error(idServizioRest + " Richiesta malformata. [" + mre.getMessage() + "]", mre);
			return Response.status(Response.Status.BAD_REQUEST).entity("Richiesta malformata.").build();
		} catch (DataValidationException dve) {
			logger.error(idServizioRest + " [" + dve.getMessage() + "]", dve);
			return Response.status(Response.Status.BAD_REQUEST).entity(dve.getMessage()).build();
		} catch (UserNotFoundException unfe) {
			logger.error(idServizioRest + " Utente non trovato. [" + unfe.getMessage() + "]", unfe);
			return Response.status(Response.Status.NOT_FOUND).entity("Utente non trovato.").build(); 
		} catch (Exception e) {
			logger.error(idServizioRest + " Errore generico. [" + e.getMessage() + "]", e);
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Errore generico.").build();
		}
	}
	
	

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Path("/getStato")
	public Response getStato(@QueryParam("idTrasmissione") String idTrasmissione,  @QueryParam("senderId") String senderId, @QueryParam("senderSys") String senderSys) throws Exception {
		String idServizioRest = "AllineamentoPendenze2.getStato";
		logger.info(idServizioRest + ": idTrasmissione [" + idTrasmissione + "]");
		try {
			List<EnteSil> listaEnteSil = Commons.checkEnteSil(senderId,senderSys,idServizioRest, em, logger);

			GetStatoResultList retlist = null;
			if (idTrasmissione == null) {
				ListaTrasmissioniRequest request = WSAdapter2.buildListaTrasmissioniRequest(listaEnteSil);
				ListaTrasmissioniResponse response = controller.listaTrasmissioniSenzaLimitazioneData(request);
				retlist = WSAdapter2.build(response);
			} else {
				GetStatoRequest request = WSAdapter2.buildGetStatoRequest(idTrasmissione, listaEnteSil);
				GetStatoResponse response = controller.getStato(request);
				retlist = WSAdapter2.build(idTrasmissione,response);
			}
			return Response.ok(retlist).build();
			
		} catch (UserNotFoundException unfe) {
			logger.error(idServizioRest + " Utente non trovato. [" + unfe.getMessage() + "]", unfe);
			return Response.status(Response.Status.NOT_FOUND).entity("Utente non trovato.").build(); 
		} catch (Exception e) {
			logger.error(idServizioRest + " Errore generico. [" + e.getMessage() + "]", e);
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Errore generico.").build();
		}
	}
	
		
	
	@GET
	@Produces({MediaType.TEXT_PLAIN})
	@Path("/getEsito")
	public Response getEsito(@QueryParam("idTrasmissione") String idTrasmissione, @QueryParam("senderId") String senderId, @QueryParam("senderSys") String senderSys) throws Exception {
		String idServizioRest = "AllineamentoPendenze2.getEsito";
		logger.info(idServizioRest + ": idTrasmissione [" + idTrasmissione + "]");
		try {

			if (idTrasmissione == null || idTrasmissione.trim().isEmpty()) {
				throw new MalformedRequestException();
			}

			List<EnteSil> listaEnteSil = Commons.checkEnteSil(senderId,senderSys,idServizioRest, em, logger);

			GetEsitoRequest request = WSAdapter2.buildGetEsitoRequest(idTrasmissione, listaEnteSil);
			GetEsitoResponse response = controller.getEsito(request);
			String esito = WSAdapter2.build(response);

			return Response.ok(esito).build();
			
		} catch (MalformedRequestException mre) {
			logger.error(idServizioRest + " Richiesta malformata. [" + mre.getMessage() + "]", mre);
			return Response.status(Response.Status.BAD_REQUEST).entity("Richiesta malformata.").build();
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
