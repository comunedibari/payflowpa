package it.tasgroup.idp.rs.endpoints;

import it.tasgroup.idp.rs.enums.EnumModelloPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;
import it.tasgroup.idp.rs.model.PrestatoreServizio;
import it.tasgroup.iris.facade.ejb.client.confpagamenti.ConfPagamentiFacadeLocal;
import it.tasgroup.iris.facade.ejb.restapi.PaymentAPIFacadeBeanLocal;
import org.apache.axis.encoding.ser.DateSerializer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Prestatori di Servizio (PSP).
 */
@Path("/prestatori_servizi_pagamento")
public class PrestatoriServiziPagamentoEndpoint {
    private static final Logger LOGGER = LogManager.getLogger(PrestatoriServiziPagamentoEndpoint.class);

    @EJB
    ConfPagamentiFacadeLocal confPagamentiFacade;


    @EJB
    PaymentAPIFacadeBeanLocal paymentAPI;
    
    
    /**
     * Ritorna la lista degli strumenti di pagamento utilizzabili per pagare. <br>
     * Se vengono passati i criteri di filtraggio (<em>modelloPagamento</em> o <em>tipoVersamento</em>) la query include solo gli strumenti di pagamento che corrispondono a tali criteri.<br>
     * Se viene passato inoltre un "carrello" di identificativi delle condizioni di pagamento (<em>idCondizionePagamento</em>) il sistema
     * calcola la lista dei sistemi di pagamento effettivamente utilizzabili per pagare il carrello passato.
     *
     * @param tipoVersamento    	[Opzionale] tipo del versamento (tipo: <a href="ns0_enumTipoVersamento.html">EnumTipoVersamento</a>)
     * @param modelloPagamentoList 	[Opzionale] lista (0..n) dei modelli pagamento da includere nel risultato  (elementi di tipo: <a href="ns0_enumModelloPagamento.html">EnumModelloPagamento</a>).
     * @param idCondizionePagamentoList [Opzionale] lista (0..n) degli identificativi delle condizioni di pagamento che si intendono pagare (carrello).
     * @return Array di <a href="ns0_prestatoreServizio.html">PrestatoreServizio</a>
     */
    @GET
    @Produces("application/json")
    @Path("/")
    public List<PrestatoreServizio> getListaPSP(@QueryParam("tipoVersamento") EnumTipoVersamento tipoVersamento,
                                                @QueryParam("modelloPagamento") List<EnumModelloPagamento> modelloPagamentoList,
                                                @QueryParam("idCondizionePagamento") List<String> idCondizionePagamentoList) {
        List<PrestatoreServizio> listaPSP;
        try {
            listaPSP = paymentAPI.getListaPSP(tipoVersamento, modelloPagamentoList, idCondizionePagamentoList);
            return listaPSP;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
        }
    }


}
