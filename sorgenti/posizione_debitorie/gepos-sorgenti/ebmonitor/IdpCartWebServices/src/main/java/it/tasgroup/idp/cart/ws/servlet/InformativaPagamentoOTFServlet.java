package it.tasgroup.idp.cart.ws.servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart.core.IdpCartInterscambioEJB;
import it.tasgroup.idp.cart.core.exception.IrisException;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.cart.core.utils.IdPServizio;
import it.tasgroup.idp.cart.core.utils.ValidatoreSemantico;
import it.tasgroup.idp.cart.core.utils.ValidatoreSintattico;

/**
 * Servlet per la ricezione degli esiti del servizio InformativaPagamento.
 * 
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: InformativaPagamentoEsitoServlet.java 475 2014-01-10 08:32:03Z nardi $
 */

public class InformativaPagamentoOTFServlet extends IdpCartOTFServlet {

	private static final long serialVersionUID = 1L;

	@EJB(beanName = "IdpCartInterscambioEJBImpl")
	private IdpCartInterscambioEJB idpCartInterscambioEJB;

	public InformativaPagamentoOTFServlet() throws Exception {
		this.log = LogFactory.getLog(this.getClass());
		this.service = IdPServizio.IdpInformativaPagamento;
		this.serviceWrapper = "IdpVerificaStatoPagamenti"; // this.service.toString()+"OTF";
		this.wrapperEsito = "IdpVerificaStatoPagamenti.Esito";
		this.validatoreSintattico = new ValidatoreSintattico(ValidatoreSintattico.IDPIPPOTFSOAP, log);
		this.validatoreSemantico = new ValidatoreSemantico(false, this.service.toString());
		this.tipoMessaggio = TipoMessaggio.INFORMATIVA_PAGAMENTO;
		this.tipoGestione = TipoGestione.INBOUND;
		this.servletName = this.service.getNomeEsecutore() + "OTF";
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String seqMsgric   =null;
		Date dataRicezione = new Date();
		
		try {
			this.log.info("Invoke "+InformativaPagamentoOTFServlet.class.getName() + " data [" + dataRicezione + "]");
			gestisciRichiesta(req,res,seqMsgric, dataRicezione);
		}catch (IrisException e) {
			sendFault(seqMsgric, res, e);
			return;
		} catch (Exception e) {
			sendFault(seqMsgric, res, new IrisException("ECC001", e.getMessage(), e.getMessage(), e));
			return;
		}
	}
}
