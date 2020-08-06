package it.tasgroup.idp.cart.ws.servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.logging.Log;

import it.tasgroup.idp.cart.core.IdpCartInterscambioEJB;
import it.tasgroup.idp.cart.core.dao.IIdpCartDbManager;
import it.tasgroup.idp.cart.core.dto.IdpHeaderDTO;
import it.tasgroup.idp.cart.core.dto.SPCoopHeaderDTO;
import it.tasgroup.idp.cart.core.exception.IrisException;
import it.tasgroup.idp.cart.core.exception.MessaggioDuplicatoException;
import it.tasgroup.idp.cart.core.exception.MsgIdDuplicatoException;
import it.tasgroup.idp.cart.core.model.ErroreComponenteModel;
import it.tasgroup.idp.cart.core.model.GestioneModel;
import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.MessaggioNonGestitoModel;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.cart.core.utils.EntitiesUtils;
import it.tasgroup.idp.cart.core.utils.IdPServizio;
import it.tasgroup.idp.cart.core.utils.IrisUtils;
import it.tasgroup.idp.cart.core.utils.MessageUtils;
import it.tasgroup.idp.cart.core.utils.ValidatoreSemantico;
import it.tasgroup.idp.cart.core.utils.ValidatoreSintattico;

public class IdpCartOTFServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** Logger utilizzato per debug. */
	protected Log log;

	protected ValidatoreSintattico validatoreSintattico;
	protected ValidatoreSemantico validatoreSemantico;

	@EJB(beanName = "IdpCartDbManager")
	protected IIdpCartDbManager idpCartDbManager;

	protected IdPServizio service;
	protected TipoMessaggio tipoMessaggio;
	protected TipoGestione tipoGestione;

	@EJB(beanName = "IdpCartInterscambioEJBImpl")
	protected IdpCartInterscambioEJB idpCartInterscambioEJB;
	protected String servletName;
	protected String serviceWrapper;
	protected String wrapperEsito;

	protected void gestisciRichiesta(HttpServletRequest req, HttpServletResponse res, String seqMsgric, Date dataRicezione) throws IrisException {
		MessaggioModel messaggio;
		GestioneModel gestione;
		IdpHeaderDTO idpHeaderDTO = null; 
		SOAPMessage soapReq = null;
		IrisException eccezioneRilevata = null;
		String e2eMsgId = null;
		SPCoopHeaderDTO spcoopHeaderDTO = new SPCoopHeaderDTO(req, log);

		seqMsgric = spcoopHeaderDTO.getSpcoopId();
		log.info("Header integrazione PdD: " + spcoopHeaderDTO);

		//Ricostruisco il messaggio di richiesta

		
		try {
			log.info("[" + seqMsgric + "] Validazione sintattica in corso..." );

			soapReq = validatoreSintattico.validate(req.getInputStream());

			log.info("Validazione sintattica completata con successo." );

			idpHeaderDTO = new IdpHeaderDTO(soapReq, ""+seqMsgric, log);
			e2eMsgId = idpHeaderDTO.getE2eMsgId();
			
			log.info("[" + seqMsgric + "] Ricevuta richiesta con riferimento IdP [" + idpHeaderDTO.getSenderIdE() + "][" + idpHeaderDTO.getSenderSysE() + "][" + e2eMsgId + "]." );
		} catch (Exception e) {
			log.error("[" + seqMsgric + "] Impossibile ricostruire il messaggio di richiesta: " + e.getMessage(),e);
			IrisException ie =  new IrisException("SIN001", "Impossibile accedere al messaggio di richiesta: " + e.getMessage(), "Impossibile costruire il messaggio SOAP: " + e.getMessage(), e);

			MessaggioNonGestitoModel messaggioNonGestito = EntitiesUtils.getMessaggioNonGestito(req, spcoopHeaderDTO, tipoMessaggio, ie, dataRicezione);
			try{
				this.idpCartDbManager.inserisciMessaggioNonGestito(messaggioNonGestito,this.servletName);
			}catch(Exception ee){
				log.error("[" + seqMsgric + "] Errore durante il salvataggio del messaggio di richiesta non gestito: " + e.getMessage(),e);
				throw new IrisException("IDP005", "Errore durante il salvataggio del messaggio di richiesta non gestito", "Errore durante il salvataggio del messaggio di richiesta non gestito", e);
			}

			throw ie;
		} 

		// Quando arriva una richiesta, se non c'e' gia, viene inserito un record in Messaggi di tipo AP/IP
		messaggio = EntitiesUtils.getMessaggio(req,spcoopHeaderDTO,idpHeaderDTO,tipoMessaggio,tipoGestione,dataRicezione);
		//  Viene creato un record in Gestioni, linkato all'elemento in Messaggi, con il timestamp di inizio gestione di tipo INBOUND/OUTBOUND ed idEgov della richiesta
		gestione = EntitiesUtils.getGestione(req, spcoopHeaderDTO, idpHeaderDTO, tipoGestione, messaggio,dataRicezione);
		
		try {
			this.idpCartDbManager.inserisciGestione(messaggio, gestione,this.servletName);
		} catch (Exception e) {
			log.error("[" + seqMsgric + "] Errore durante il salvataggio del messaggio di richiesta: " + e.getMessage(),e);
			throw new IrisException("IDP001", "Errore durante il salvataggio del messaggio di richiesta", "Errore durante il salvataggio del messaggio di richiesta", e);
		} 
		
//		try {
//			gestione.setId(this.idpCartDbManager.inserisciGestione(messaggio, gestione,this.servletName));
//		} catch (Exception e) {
//			log.error("[" + seqMsgric + "] Errore durante il salvataggio dei dati relativi alla gestione: " + e.getMessage(),e);
//			eccezioneRilevata = new IrisException("IDP002", "Errore durante il salvataggio dei dati relativi alla gestione", "Errore durante il salvataggio dei dati relativi alla gestione", e);
//			aggiornaMessaggioGestione(messaggio, gestione, seqMsgric, dataRicezione, eccezioneRilevata);
//			throw eccezioneRilevata;
//		}

		// Viene validato il messaggio (vedi gestione analoga vecchio proxy).
		try{
			log.info("[" + seqMsgric + "] Validazione semantica in corso..." );
			this.validatoreSemantico.validate(spcoopHeaderDTO, idpHeaderDTO);
			log.info("[" + seqMsgric + "] Validazione semantica completata con successo." );
		}catch(IrisException e){
			log.error("[" + seqMsgric + "] Validazione del messaggio di richiesta fallita : " + e.getMessage(),e);
			aggiornaMessaggioGestione(messaggio, gestione, seqMsgric, dataRicezione, e);
			throw e;
		}

		String ejbMethodName = null;
		String ejbResponse = null;
		// Viene inoltrato il messaggio all'EJB 
		try{
			log.info("[" + seqMsgric + "] Invoke IdpCartInterscambioEjb in corso..." );

			String xmlMessaggio = MessageUtils.saaj2xml(soapReq,  this.serviceWrapper);
			log.debug("------------ Messaggio OTF -------------");
			log.debug(xmlMessaggio);
			log.debug("------------ END Messaggio OTF -------------");
			switch(tipoMessaggio){
			case ALLINEAMENTO_PENDENZE:
				ejbMethodName = "IdpCartInterscambioEjb.processAPOTF";
				ejbResponse = this.idpCartInterscambioEJB.processAPOTF(idpHeaderDTO.getSenderIdE(), idpHeaderDTO.getSenderSysE(),idpHeaderDTO.getSenderId(), idpHeaderDTO.getSenderSys(), e2eMsgId, null, xmlMessaggio);
				break;
			case INFORMATIVA_PAGAMENTO:
			default:
				ejbMethodName = "IdpCartInterscambioEjb.processIPOTF";
				ejbResponse =  this.idpCartInterscambioEJB.processIPOTF(idpHeaderDTO.getSenderIdE(), idpHeaderDTO.getSenderSysE(),idpHeaderDTO.getSenderId(), idpHeaderDTO.getSenderSys(), e2eMsgId, null, xmlMessaggio);
				break;
			}

			log.info("[" + seqMsgric + "] Invoke IdpCartInterscambioEjb completata con successo." );
			log.debug("------------ Messaggio Risposta OTF -------------");
			log.debug(ejbResponse); 
			log.debug("------------ END Messaggio Risposta OTF -------------");	
			// Al ritorno del controllo, se e' andata bene imposto Messaggio.richiestaConsegnata a true
			messaggio.setRichiestaConsegnata(true);
		}catch(IrisException e){
			log.error("[" + seqMsgric + "] Errore durante l'invocazione "+ejbMethodName+": " + e.getMessage(),e);
			//				e.setFaultString("Errore durante l'invocazione IdpCartInterscambioEjb.processAP");
			aggiornaMessaggioGestione(messaggio, gestione, seqMsgric, dataRicezione, e);
			throw e;
		}catch(MessaggioDuplicatoException e){
			log.error("[" + seqMsgric + "] Errore durante l'invocazione "+ejbMethodName+": " + e.getMessage(),e);
			eccezioneRilevata = new  IrisException("DUP001", "Ricevuta richiesta IdP duplicata ["+e2eMsgId+"]", 
					"Il messaggio contiene un id applicativo gia' processato dal ProxyIRIS.", e);
			aggiornaMessaggioGestione(messaggio, gestione, seqMsgric, dataRicezione, eccezioneRilevata);
			throw eccezioneRilevata;
		} catch(MsgIdDuplicatoException e ){
			log.error("[" + seqMsgric + "] Errore durante l'invocazione "+ejbMethodName+": " + e.getMessage(),e);
			eccezioneRilevata = new  IrisException("DUP001", "Ricevuta richiesta IdP duplicata ["+e2eMsgId+"]", 
					"Il messaggio contiene un id applicativo gia' processato dal ProxyIRIS.", e);
			aggiornaMessaggioGestione(messaggio, gestione, seqMsgric, dataRicezione, eccezioneRilevata);
			throw eccezioneRilevata;
		}

		aggiornaMessaggioGestione(messaggio, gestione, seqMsgric, dataRicezione, eccezioneRilevata);


		// creazione del messaggio di risposta
		SOAPMessage soapRes = null;
		try {
			// se il messaggio di risposta contiene l'esito Wrapper lo imbusto senza aggiungere il wrapper
			if(ejbResponse.contains(this.wrapperEsito))
				soapRes = MessageUtils.xml2saaj(ejbResponse, null);
			else 
				soapRes = MessageUtils.xml2saaj(ejbResponse, this.wrapperEsito); 
		} catch (Exception e) {
			log.error("[" + seqMsgric + "] Impossibile ricostruire il messaggio di esito: " + e.getMessage(),e);
			IrisException ie =  new IrisException("CON005", "Impossibile accedere al messaggio di esito: " + e.getMessage(), "Impossibile costruire il messaggio SOAP: " + e.getMessage(), e);
			aggiornaMessaggioGestioneEsito(messaggio, gestione, seqMsgric, dataRicezione, ie);
			throw ie;
		}

		// Segnalo la consegna dell'esito e finisco
		messaggio.setEsitoConsegnato(true);
		gestione.setHttpResponseCode(200);
		
		try{
			res.setContentType("text/xml");
			
			soapRes.writeTo(res.getOutputStream());
			res.getOutputStream().close();
		}catch(Exception e){
			log.error("[" + seqMsgric + "] Impossibile spedire il messaggio di esito: " + e.getMessage(),e);
			IrisException ie =  new IrisException("CON010", "Impossibile spedire il messaggio di esito: " + e.getMessage(), "Impossibile spedire il messaggio di esito: " + e.getMessage(), e);
			aggiornaMessaggioGestioneEsito(messaggio, gestione, seqMsgric, dataRicezione, ie);
			throw ie;
		}

		aggiornaMessaggioGestioneEsito(messaggio, gestione, seqMsgric, dataRicezione, eccezioneRilevata);

	}


	/** Invia un fault in risposta http.
	 *
	 * @param res Servlet per la risposta
	 * @param status Codice di errore
	 * @param msg Faultstring
	 * @param idApp Id applicativo del messaggio
	 * @param e Eccezione sollevata
	 */
	protected void sendFault(String idRequest, HttpServletResponse res, IrisException e) {
		try{
			log.error("[" + idRequest + "] Code: ["+ e.getCodiceErrore()+"] Message: [" + e.getMessage() + "]", e);
		} catch (Exception ee) {
			log.error("[" + idRequest + "] Errore nell'invio del SOAPFault: " + e, ee);
		} finally {
			try {
				res.setContentType("text/xml");
				res.setStatus(500);
				res.getOutputStream().print(IrisUtils.createXMLFault(e));
			} catch (Exception ee) {
				log.error("[" + idRequest + "] Errore nell'invio del SOAPFault: " + e, ee);
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.log.info(this.getClass().getName()+": GET Method not allowed");
		res.setContentType("text/html");
		String resp = "<html><body>GET Method not allowed</body></html>";
		res.setStatus(500);
		res.getOutputStream().write(resp.getBytes());
		res.getOutputStream().close();

	}

	protected void aggiornaMessaggioGestione(MessaggioModel messaggio, GestioneModel gestione, String seqMsgric, Date dataRicezione,IrisException eccezioneRilevata) throws IrisException {
		if(eccezioneRilevata!= null){
			gestione.setHttpResponseCode(500);
			
			StringBuffer sb = new StringBuffer();
			
			ErroreComponenteModel err = EntitiesUtils.getErroreComponenteInbound(eccezioneRilevata);
			
			sb.append("CodErrore").append("#").append(err.getCodErrore());
			sb.append("|");
			sb.append("DescrizioneErrore").append("#").append(err.getDescrErrore());
			gestione.setHttpHeaders(sb.toString()); 
		}
		
		EntitiesUtils.aggiornaMessaggioGestione(this.idpCartDbManager, log, messaggio, gestione, seqMsgric, dataRicezione, eccezioneRilevata, TipoGestione.INBOUND,this.servletName,true);
	}

	protected void aggiornaMessaggioGestioneEsito(MessaggioModel messaggio, GestioneModel gestione, String seqMsgric, Date dataRicezione,IrisException eccezioneRilevata) throws IrisException {
		if(eccezioneRilevata!= null){
			gestione.setHttpResponseCode(500);
			StringBuffer sb = new StringBuffer();
			
			ErroreComponenteModel err = EntitiesUtils.getErroreComponenteOutbound(eccezioneRilevata);
			
			sb.append("CodErrore").append("#").append(err.getCodErrore());
			sb.append("|");
			sb.append("DescrizioneErrore").append("#").append(err.getDescrErrore());
			gestione.setHttpHeaders(sb.toString()); 
		}
		
		EntitiesUtils.aggiornaMessaggioGestione(this.idpCartDbManager, log, messaggio, gestione, seqMsgric, dataRicezione, eccezioneRilevata, TipoGestione.OUTBOUND,this.servletName,true);
	}

}
