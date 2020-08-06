package it.tasgroup.idp.cart.ws.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart.core.exception.IrisException;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.cart.core.utils.IdPServizio;
import it.tasgroup.idp.cart.core.utils.ValidatoreSemantico;
import it.tasgroup.idp.cart.core.utils.ValidatoreSintattico;

/** 
 * Servlet per la ricezione delle richieste per il servizio Allineamento Pendenze. 
 *
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: AllineamentoPendenzeServlet.java 408 2013-07-12 14:35:04Z nardi $
 */

public class AllineamentoPendenzeOTFServlet extends IdpCartOTFServlet {

	private static final long serialVersionUID = 1L;

	public AllineamentoPendenzeOTFServlet() throws Exception {
		this.log = LogFactory.getLog(this.getClass());
		this.service = IdPServizio.IdpAllineamentoPendenze;
		this.serviceWrapper = "IdpAllineamentoPendenzeEnteOTF"; //this.service.toString()+"OTF";
		this.wrapperEsito = "IdpAllineamentoPendenzeEnteOTF.Esito";
		this.validatoreSintattico = new ValidatoreSintattico(ValidatoreSintattico.IDPAPOTFSOAP, log);
		this.validatoreSemantico = new ValidatoreSemantico(false, this.service.toString());
		this.tipoMessaggio = TipoMessaggio.ALLINEAMENTO_PENDENZE;
		this.tipoGestione = TipoGestione.INBOUND;
		this.servletName = this.service.getNomeEsecutore() + "OTF";
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String seqMsgric   =null;
		Date dataRicezione = new Date();
		
		try {
			this.log.info("Invoke "+AllineamentoPendenzeOTFServlet.class.getName() + " data [" + dataRicezione + "]");
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
