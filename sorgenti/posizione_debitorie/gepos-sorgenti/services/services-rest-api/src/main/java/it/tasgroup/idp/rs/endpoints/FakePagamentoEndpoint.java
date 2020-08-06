package it.tasgroup.idp.rs.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import it.tasgroup.idp.rs.model.CondizionePagamento;
import it.tasgroup.idp.rs.model.InfoAperturaPagamento;
import it.tasgroup.idp.rs.model.PrestatoreServizio;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.rest.utils.MessageInfo;

/**
 * Fake Endpoint
 *
 */
@Path("/fake_endpoint")
public class FakePagamentoEndpoint {

    @GET
    @Produces("application/json")
    @Path("/condizione")
    public CondizionePagamento getFakeCondizione() {
    	return  null;
    }

    @GET
    @Produces("application/json")
    @Path("/prestatore")
    public PrestatoreServizio getFakePrestatore() {
    	return  null;
    }

    @GET
    @Produces("application/json")
    @Path("/messageInfo")
    public MessageInfo getMessageInfo() {
        return  null;
    }

    @GET
    @Produces("application/json")
    @Path("/infoAperturaPagamento")
    public InfoAperturaPagamento getFakeInfoAperturaPagamento() {
        List<String> idCondizioni = new ArrayList<String>();
        idCondizioni.add("1232");
        idCondizioni.add("1211");
        idCondizioni.add("1232");
        return  new InfoAperturaPagamento("BCITITMM-00799960158_01-CP",idCondizioni,"RPTSFN70M14A390N","stf.tt@mail.com","SR","IT32939423040000");
    }
    
    @GET
    @Produces("application/json")
    @Path("/pagamento")
    public Pagamento getFakePagamento() {
        return  null;
    }

}
