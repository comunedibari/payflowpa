package it.tasgroup.idp.cart.ws.servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;

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

public class InformativaPagamentoEsitoServlet extends IdpCartServlet {

	private static final long serialVersionUID = 1L;

	@EJB(beanName = "IdpCartInterscambioEJBImpl")
	private IdpCartInterscambioEJB idpCartInterscambioEJB;

	public InformativaPagamentoEsitoServlet() throws Exception {
		this.log = LogFactory.getLog(this.getClass());
		this.service = IdPServizio.IdpInformativaPagamento;
		this.serviceWrapper = "IdpEsito";
		this.validatoreSintattico = new ValidatoreSintattico(ValidatoreSintattico.IDPIPPSOAP_ESITO, log);
		this.validatoreSemantico = new ValidatoreSemantico(false, this.service.toString());
//		this.idpCartDbManager = new IdpCartDbManager();
		this.tipoMessaggio = TipoMessaggio.INFORMATIVA_PAGAMENTO;
		this.tipoGestione = TipoGestione.INBOUND;
		this.servletName = this.service.getNomeEsecutore() + ".Esito";
	}

	/***
	 * 
	 * 
    Quando arriva una richiesta, se non c'e' gia, viene inserito un record in Messaggi di tipo AP
    Viene creato un record in Gestioni, linkato all'elemento in Messaggi, con il timestamp di inizio gestione di tipo INBOUND ed idEgov della richiesta
    Viene validato il messaggio (vedi gestione analoga vecchio proxy).
    Viene inoltrato il messaggio all'EJB
    Al ritorno del controllo, se e' andata bene imposto Messaggio.richiestaConsegnata a true
    Al ritorno del controllo, se e' andata male, imposto Messaggio.richiestaConsegnata a false, codErrore con il tipo di Eccezione e descErrore con il messaggio.
    In entrambi i casi valorizzo Messaggio.dataUltimaRichiestaConsegnata, tempoGestione in millisecondi

	 * 
	 */

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String seqMsgric   =null;
		Date dataRicezione = new Date();
		
		try {
			this.log.info("Invoke "+InformativaPagamentoEsitoServlet.class.getName() + " data [" + dataRicezione + "]");
			seqMsgric = gestisciRichiesta(req,seqMsgric, dataRicezione);

			res.setContentType("text/xml");
			
			MessageFactory.newInstance().createMessage().writeTo(res.getOutputStream());
			res.getOutputStream().close();

		}catch (IrisException e) {
			sendFault(seqMsgric, res, e);
			return;
		} catch (Exception e) {
			sendFault(seqMsgric, res, new IrisException("ECC001", e.getMessage(), e.getMessage(), e));
			return;
		}
	}
}
