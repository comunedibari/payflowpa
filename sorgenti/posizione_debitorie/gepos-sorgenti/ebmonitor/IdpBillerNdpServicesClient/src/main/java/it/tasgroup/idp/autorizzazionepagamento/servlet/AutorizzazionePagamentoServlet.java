package it.tasgroup.idp.autorizzazionepagamento.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.autorizzazionepagamento.AttivaPagamentoRequestType;
import it.tasgroup.idp.autorizzazionepagamento.AttivaPagamentoResponseBodyType;
import it.tasgroup.idp.autorizzazionepagamento.AttivaPagamentoResponseType;
import it.tasgroup.idp.autorizzazionepagamento.AutorizzazionePagamentoController;
import it.tasgroup.idp.autorizzazionepagamento.VerificaPagamentoResponseBodyType;
import it.tasgroup.idp.autorizzazionepagamento.VerificaPagamentoResponseType;
import it.tasgroup.idp.autorizzazionepagamento.exception.AutorizzazionePagamentoException;
import it.tasgroup.idp.autorizzazionepagamento.utils.AutorizzazionePagamentoUtils;
import it.tasgroup.idp.autorizzazionepagamento.utils.AutorizzazionePagamentoVOBuilder;
import it.tasgroup.idp.autorizzazionepagamento.ws.helper.AutorizzazionePagamentoHelper;




/**
 * Servlet implementation class AutorizzazionePagamentoServlet
 */
public class AutorizzazionePagamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/** Logger utilizzato per debug. */
	protected Log log =LogFactory.getLog(this.getClass()); 
    /**
     * @see HttpServlet#HttpServlet()
     * 
     * 
     */
	
	@EJB(beanName="AutorizzazionePagamentoControllerImpl")
	AutorizzazionePagamentoController controller;
	
    public AutorizzazionePagamentoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info(this.getClass().getName()+": GET Method not allowed");
		String testParam = request.getParameter("isTest");
		response.setContentType("text/html");
		String resp =null;
		if (testParam!=null){
			try {
				eseguiTest(request,response);
				//response.setStatus(500);
				//response.getOutputStream().write(resp.getBytes());
				//response.getOutputStream().close();
			}catch (AutorizzazionePagamentoException e) {
				sendFault("", response, e);
				return;
			} catch (Exception e) {
				sendFault("", response, new AutorizzazionePagamentoException("ECC001", e.getMessage(), e.getMessage(), e));
				return;
			}
			
		}else{
		
			resp = "<html><body>GET Method not allowed</body></html>";
			response.setStatus(500);
			response.getOutputStream().write(resp.getBytes());
			response.getOutputStream().close();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String seqMsgric   =null;
		Date dataRicezione = new Date();
		
		try {
			log.info("Invoke "+AutorizzazionePagamentoServlet.class.getName() + " data [" + dataRicezione + "]");
			gestisciRichiesta(request,response,seqMsgric, dataRicezione);
		}
		
		catch (AutorizzazionePagamentoException e) {
			sendFault(seqMsgric, response, e);
			return;
		} catch (Exception e) {
			sendFault(seqMsgric, response, new AutorizzazionePagamentoException("ECC001", e.getMessage(), e.getMessage(), e));
			return;
		}
	}
	
	protected void gestisciRichiesta(HttpServletRequest req, HttpServletResponse res, String seqMsgric, Date dataRicezione) throws AutorizzazionePagamentoException {
		
		
		String xmlResponse = new String();
		PrintWriter out =null;
		res.setContentType("text/xml;charset=UTF-8");
	
		try {
			
		
			AutorizzazionePagamentoVO datiRichiesta = AutorizzazionePagamentoVOBuilder.buildVO(req);
			
			
			
			if (datiRichiesta.getTipoOperazione().equals("V")){
				log.debug("Richiesta verifica pagamento con identificativo: "+ datiRichiesta.getIdentificativoUnivocoVersamento() + " idDominio: "  + datiRichiesta.getIdentificativoDominio());
				VerificaPagamentoResponseType response = AutorizzazionePagamentoHelper.verificaPagamento(datiRichiesta);
				VerificaPagamentoResponseBodyType bodyResponse = response.getBody();
				datiRichiesta.setCausaleVersamento(bodyResponse.getCausaleVersamento());
				datiRichiesta.setImportoPagamento(bodyResponse.getImportoPagamento());
				xmlResponse = AutorizzazionePagamentoUtils.createVerificaResponse(datiRichiesta);
				SOAPMessage msg = null;
				out = res.getWriter();
				log.debug("Risposta alla  verifica pagamento con identificativo: "+ datiRichiesta.getIdentificativoUnivocoVersamento() + " idDominio: "  + datiRichiesta.getIdentificativoDominio() + " : " +xmlResponse );
	            out.write(xmlResponse);
				
			}else if (datiRichiesta.getTipoOperazione().equals("A")){
				log.debug("Richiesta attiva pagamento con identificativo: "+ datiRichiesta.getIdentificativoUnivocoVersamento() + " idDominio: "  + datiRichiesta.getIdentificativoDominio() );
				AttivaPagamentoRequestType parameters = new AttivaPagamentoRequestType();
				parameters.setCodiceContestoPagamento(datiRichiesta.getCodiceContestoPagamento());
				parameters.setIdentificativoDominio(datiRichiesta.getIdentificativoDominio());
				parameters.setIdentificativoUnivocoVersamento(datiRichiesta.getIdentificativoUnivocoVersamento());
				parameters.setImportoVersamento(datiRichiesta.getImportoPagamento());
				AttivaPagamentoResponseType resp =AutorizzazionePagamentoHelper.attivaPagamento(parameters,datiRichiesta.getUrlWebServices());
				AttivaPagamentoResponseBodyType respBody = resp.getBody();
				controller.attivaPagamento(parameters,resp);
				xmlResponse = AutorizzazionePagamentoUtils.createAttivaResponse(respBody);
				log.debug("Risposta a attiva pagamento con identificativo: "+ datiRichiesta.getIdentificativoUnivocoVersamento() + " idDominio: "  + datiRichiesta.getIdentificativoDominio() + " : " +xmlResponse );
				out = res.getWriter();
	            out.write(xmlResponse);
				
			}else{
				throw new Exception("Tipo Richiesta non valida");
			}
			
			
			
		} catch (AutorizzazionePagamentoException e) {
     			sendFault(seqMsgric, res, e);
     			return;
     		} catch (Exception e) {
     			sendFault(seqMsgric, res, new AutorizzazionePagamentoException("ERRORE GENERICO", e.getMessage(), e.getMessage(), e));
     			return;
     		}
		
	}

	
	protected void sendFault(String idRequest, HttpServletResponse res, AutorizzazionePagamentoException e) {
		try{
			log.error("[" + idRequest + "] Code: ["+ e.getCodiceErrore()+"] Message: [" + e.getMessage() + "]", e);
		} catch (Exception ee) {
			log.error("[" + idRequest + "] Errore nell'invio del SOAPFault: " + e, ee);
		} finally {
			try {
				res.setContentType("text/xml");
				res.setStatus(500);
				res.getOutputStream().print(AutorizzazionePagamentoUtils.createXMLFault(e));
			} catch (Exception ee) {
				log.error("[" + idRequest + "] Errore nell'invio del SOAPFault: " + e, ee);
			}
		}
	}
	
	private void eseguiTest(HttpServletRequest req,HttpServletResponse res) throws AutorizzazionePagamentoException{
		
		String xmlResponse = new String();
		PrintWriter out =null;
		res.setContentType("text/xml;charset=UTF-8");
	
		try {
			
			

			

			AutorizzazionePagamentoVO datiRichiesta = new AutorizzazionePagamentoVO();
			String tipoOperazione = req.getParameter("tipoOperazione");
			datiRichiesta.setTipoOperazione(tipoOperazione);
			datiRichiesta.setCodiceContestoPagamento("CP12345OK1");
			datiRichiesta.setIdentificativoDominio("84006890481");
			datiRichiesta.setIdentificativoUnivocoVersamento("C1458319494185013");
			
			if (datiRichiesta.getTipoOperazione().equals("V")){
				log.debug("Richiesta verifica pagamento con identificativo: "+ datiRichiesta.getIdentificativoUnivocoVersamento()  );
				VerificaPagamentoResponseType response = AutorizzazionePagamentoHelper.verificaPagamento(datiRichiesta);
				VerificaPagamentoResponseBodyType bodyResponse = response.getBody();
				datiRichiesta.setCausaleVersamento(bodyResponse.getCausaleVersamento());
				datiRichiesta.setImportoPagamento(bodyResponse.getImportoPagamento());
				xmlResponse = AutorizzazionePagamentoUtils.createVerificaResponse(datiRichiesta);
				
			}else if (datiRichiesta.getTipoOperazione().equals("A")){
				datiRichiesta.setImportoPagamento(new BigDecimal(100));
				
				AttivaPagamentoRequestType parameters = new AttivaPagamentoRequestType();
				parameters.setCodiceContestoPagamento(datiRichiesta.getCodiceContestoPagamento());
				parameters.setIdentificativoDominio(datiRichiesta.getIdentificativoDominio());
				
				parameters.setIdentificativoUnivocoVersamento(datiRichiesta.getIdentificativoUnivocoVersamento());
				parameters.setImportoVersamento(datiRichiesta.getImportoPagamento());
				AttivaPagamentoResponseType resp =AutorizzazionePagamentoHelper.attivaPagamento(parameters, datiRichiesta.getUrlWebServices());
				AttivaPagamentoResponseBodyType respBody = resp.getBody();
				controller.attivaPagamento(parameters,resp);
				xmlResponse = AutorizzazionePagamentoUtils.createAttivaResponse(respBody);
			}else{
				throw new Exception("Tipo Richiesta non valida");
			}
			out = res.getWriter();
             out.write(xmlResponse);
			
			
		} catch (AutorizzazionePagamentoException e) {
     			sendFault("", res, e);
     			
     		} catch (Exception e) {
     			sendFault("", res, new AutorizzazionePagamentoException("ERRORE GENERICO", e.getMessage(), e.getMessage(), e));
     			
     		}
		
	}
	
	
}
