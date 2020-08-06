package it.tasgroup.idp.rs.endpoints;

import static it.tasgroup.rest.utils.EndPointUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import it.tasgroup.idp.rs.model.Profilo;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaNotificaPagamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.exception.InvalidInputException;
import it.tasgroup.iris.dto.rest.filters.PagamentiFilter;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacadeLocal;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * Condizioni di Pagamento.
 */
@Path("auth/pagamento")
public class PagamentiEndpoint {

    @EJB
    DistintePagamentoFacadeLocal distintePagamentoFacade;
    
    /**
     * Ritorna la lista dei Pagamenti effettuati sulla piattaforma da parte di un debitore utilizzando il suo codice fiscale.
     *
     * @param codiceFiscaleDebitore           [Obbligatorio] il codice fiscale del debitore
     * @param catTributo                      [Opzionale] codice categoria del tributo, per filtrare le condizioni (e.g. "Categoria013", si ottengono solo le prestazioni sanitarie )
     * @param flagInCorso          			  [Opzionale] Se valorizzato (String, != null), restituisce anche i pagamenti in corso. Se null vengono esclusi i pagamenti in corso
     * 
     * @return Array di <a href="el_ns0_pagamento.html">Pagamento</a>
     * @throws Exception
     */
    @GET
    @Produces({"application/json","application/xml"})
    @Path("/effettuati")
    public List<Pagamento> getPagamenti(@QueryParam("codiceFiscaleDebitore") String codiceFiscaleDebitore,
    							 @QueryParam("catTributo") String catTributo,
    							 @QueryParam("flagInCorso") String flagInCorso,
    							 @QueryParam("idCondizione") String idCondizione) throws Exception {
    	
    	ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		boolean checkUser = cpl.getBooleanProperty("rest.api.auth.check.sso.user");
		
		//Recupero dati dell'utente loggato e controllo che siano congruenti
		//Con quelli passati come parametro dell'api rest
		
		//Attivare in base a properties rest.api.auth.check.sso.user
		Profilo loggedUser = new Profilo();
		ProfiloEndPoint profiloRest = new ProfiloEndPoint();
		loggedUser = profiloRest.getProfilo();
		
		if(checkUser) {
			if(!(codiceFiscaleDebitore.equals(loggedUser.getCodiceFiscale())))
				throw new Exception("User error"); 
		}

    	List<Pagamento> listaPagamenti = new ArrayList<Pagamento>();
        checkParameters(codiceFiscaleDebitore);
        //checkPagination(limit, offset);
        
        ContainerDTO inputDTO = new ContainerDTO();
        /*PagingCriteria pagingCriteria = new PagingCriteria();
        pagingCriteria.setRecordPosition(offset);
        pagingCriteria.setResultsPerPage(limit);
        pagingCriteria.setEnablePaging(true);
        inputDTO.setPagingCriteria(pagingCriteria);*/

        PagamentiFilter filter = new PagamentiFilter(
                codiceFiscaleDebitore,
                catTributo
        );
        inputDTO.setInputDTO(filter);

        ContainerDTO outputDTO = distintePagamentoFacade.getPagamentiEseguiti(inputDTO, flagInCorso, idCondizione);
        listaPagamenti = outputDTO.getOutputDTOList();
        //int totalRecords = outputDTO.getPagingData().getNumTotalRecords();
//        List<Pagamento> items = outputDTO.getOutputDTOList();
        
        return listaPagamenti;

        //return Response.ok(items).header("Access-Control-Expose-Headers", "X-Total-Count").header("X-Total-Count", totalRecords).build();
    }

    // todo spostare in classe utility
    private void checkPagination(int limit, int offset) throws InvalidInputException {
        if (limit <= 0) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String[]{"limit"}, "Deve essere maggiore di zero");
        }

        if (offset < 0) {
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String[]{"offset"}, "Deve essere maggiore o uguale a zero");
        }
    }

    private void checkParameters(String codiceFiscaleDebitore) throws InvalidInputException {
        if (isEmpty(codiceFiscaleDebitore))
            throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_WRONG_PARAMETERS, new String[]{"codiceFiscaleDebitore"}, "Parametro Obbligatorio");
    }

    /**
     * Ritorna la lista dei Pagamenti effettuati sulla piattaforma da parte di un debitore utilizzando il suo codice fiscale.
     *
     * @param codiceFiscaleDebitore           [Obbligatorio] il codice fiscale del debitore
     * @param catTributo                      [Opzionale] codice categoria del tributo, per filtrare le condizioni (e.g. "Categoria013", si ottengono solo le prestazioni sanitarie )
     * @param flagInCorso          			  [Opzionale] Se valorizzato (String, != null), restituisce anche i pagamenti in corso. Se null vengono esclusi i pagamenti in corso
     * 
     * @return Array di <a href="el_ns0_pagamento.html">Pagamento</a>
     * @throws Exception
     */
    @POST    
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/notificaRiconciliazione")
    public EsitoNDP notificaRiconciliazione(RichiestaNotificaPagamento request) throws Exception {
        EsitoNDP outputDTO = distintePagamentoFacade.notificaPagamento(request);
        return outputDTO;
    }

}
