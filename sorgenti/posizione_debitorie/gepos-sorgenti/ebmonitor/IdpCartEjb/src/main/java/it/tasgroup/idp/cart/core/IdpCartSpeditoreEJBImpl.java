package it.tasgroup.idp.cart.core;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart.core.dao.IIdpCartDbManager;
import it.tasgroup.idp.cart.core.dto.CredenzialiPdDDTO;
import it.tasgroup.idp.cart.core.dto.SPCoopHeaderDTO;
import it.tasgroup.idp.cart.core.exception.IdpCartSpeditoreException;
import it.tasgroup.idp.cart.core.exception.IrisException;
import it.tasgroup.idp.cart.core.model.GestioneModel;
import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.MessaggioNonGestitoModel;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.cart.core.utils.EntitiesUtils;
import it.tasgroup.idp.cart.core.utils.IdPServizio;
import it.tasgroup.idp.cart.core.utils.IrisUtils;
import it.tasgroup.idp.cart.core.utils.MessageUtils;
import it.tasgroup.idp.cart.core.utils.PDInvoker;
import it.tasgroup.idp.cart.core.utils.ValidatoreSintattico;
import it.tasgroup.idp.util.IrisProperties;

/**
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: GestioneCredenzialiEJBImpl.java 358 2013-05-22 15:32:32Z nardi $
 */
@Stateless
public class IdpCartSpeditoreEJBImpl implements IdpCartSpeditoreEJB {

	private Log log;
	private String baseurl =  null; 
	private String username=null;
	private String password=null;
	
	// non mi piace codice di test in classi di business, pensare refactoring
	private String simulation = null;
	private String simulationUrlInformativaPagamento = null;
	private String simulationUrlAllineamentoPendenze = null;

	@EJB(beanName = "IdpCartDbManager")
	protected IIdpCartDbManager idpCartDbManager;
	private ValidatoreSintattico validatoreSintattico;

	public IdpCartSpeditoreEJBImpl() {
		this.log = LogFactory.getLog(this.getClass());

		try{
			this.baseurl = IrisProperties.getProperty("idpcart.spedizione.url");
			this.username = IrisProperties.getProperty("idpcart.spedizione.username");
			this.password = IrisProperties.getProperty("idpcart.spedizione.password");
			
			this.simulation = IrisProperties.getProperty("simulazione.ente");
			this.simulationUrlInformativaPagamento = IrisProperties.getProperty("simulazione.url.invioNotificaPagamento");
			this.simulationUrlAllineamentoPendenze = IrisProperties.getProperty("simulazione.url.comunicazionePosizioniDebitorieEsito");
			
			this.log.info("Servizio di spedizione si trova alla url ["+this.baseurl+"]");
		}catch(Exception e){
			this.log.error("Errore durante la lettura delle properties: "+e.getMessage(), e );
		}
	}

	private String gestisciRichiesta(String ente, String sil, String enteIntermediario, String silIntermediario, String msgId, String xml,String schemaSOAP,TipoMessaggio tipoMessaggio,
			TipoGestione tipoGestione,IdPServizio service, String soapAction, CredenzialiPdDDTO credenziali) throws IdpCartSpeditoreException{
		String idEgov = null;
		HttpURLConnection httpConn = null;
		IrisException eccezioneRilevata = null;
		URL url = null; 
		MessaggioModel messaggio = null;
		GestioneModel gestione = null;
		Date dataRicezione = new Date();
		try{
			this.log.info("Invoke "+IdpCartSpeditoreEJBImpl.class.getName() + " data [" + dataRicezione + "]");
			
			this.log.info("Parametri: Ente ["+ente+"], Sil, ["+sil+"], Ente Intermediario ["+enteIntermediario+"], Sil Intermediario ["+silIntermediario+"]"); 
			
			if(enteIntermediario == null){
				enteIntermediario = ente;
				this.log.info("Parametro Ente Intermediario non definito, verra' usato ["+enteIntermediario+"]");
			}
			
			if(silIntermediario == null){
				silIntermediario = sil;
				this.log.info("Parametro Sil Intermediario non definito, verra' usato ["+silIntermediario+"]");
			}
			
			if(service.equals(IdPServizio.IdpInformativaPagamento)){
				// In caso di invocazione del servizio di informativa bisogna aggiungere _NOTIFICA al servizioApplicativo.
				silIntermediario += "_NOTIFICA";
			}
			
			try {
				this.validatoreSintattico = new ValidatoreSintattico(schemaSOAP, log);
			} catch (Exception e) {
				log.error("[" + msgId + "] Impossibile ricostruire il messaggio di richiesta: " + e.getMessage(),e);
				IrisException ie =  new IrisException("CON001", "Impossibile accedere al messaggio di richiesta: " + e.getMessage(), "Impossibile costruire il messaggio SOAP: " + e.getMessage(), e);

				MessaggioNonGestitoModel messaggioNonGestito = EntitiesUtils.getMessaggioNonGestito(ente, sil, msgId, tipoMessaggio, ie, dataRicezione);
				try{
					this.idpCartDbManager.inserisciMessaggioNonGestito(messaggioNonGestito,"IdpCartSpeditoreEJB.gestisciRichiesta"+tipoMessaggio.value() );
				}catch(Exception ee){
					log.error("[" + msgId + "] Errore durante il salvataggio del messaggio di richiesta non gestito: " + e.getMessage(),e);
					throw new IrisException("IDP005", "Errore durante il salvataggio del messaggio di richiesta non gestito", "Errore durante il salvataggio del messaggio di richiesta non gestito", e);
				}

				throw ie;
			} 
			
			// Quando arriva una richiesta, se non c'e' gia, viene inserito un record in Messaggi di tipo AP/IP
			messaggio = EntitiesUtils.getMessaggio(ente, sil, msgId, tipoMessaggio, dataRicezione);
			//  Viene creato un record in Gestioni, linkato all'elemento in Messaggi, con il timestamp di inizio gestione di tipo OUTBOUND/INBOUND
			gestione = EntitiesUtils.getGestione(tipoGestione, messaggio,dataRicezione);

			log.info("Gestione Messaggio [" + msgId + "] in corso..." );

			try {
				this.idpCartDbManager.inserisciGestione(messaggio, gestione,"IdpCartSpeditoreEJB.gestisciRichiesta"+tipoMessaggio.value());
			} catch (Exception e) {
				log.error("Messaggio [" + msgId + "] Errore durante il salvataggio del messaggio: " + e.getMessage(),e);
				throw new IrisException("IDP001", "Errore durante il salvataggio del messaggio", "Errore durante il salvataggio del messaggio", e);
			} 

			// controllo url da invocare

			try{
				String senderId = "SPCRTIRIS";
				// si utilizza l'ente Intermediario
				String receiverId = "SPC"+ enteIntermediario;
				
				//FIXME non mi piace codice di test qui! pensare refactoring
				if (!simulation.equals("Y")) {
					url = new URL(baseurl + IrisUtils.getEsitoPD(senderId, receiverId, service)); 
					log.info("Url di invocazione della Porta delegata verso l'Ente: " + url );					
				} else {
					if (IdPServizio.IdpAllineamentoPendenzeEnteEsito == service) {
						url = new URL(simulationUrlAllineamentoPendenze);
					} else if (IdPServizio.IdpInformativaPagamento == service) {
						url = new URL(simulationUrlInformativaPagamento);
					}
				}
			} catch (MalformedURLException e) {
				eccezioneRilevata = new IrisException("CON002","Impossibile ricostruire la URL della Porta Delegata per la consegna dell'Esito","Impossibile ricostruire la URL della Porta Delegata per la consegna dell'Esito", e);
				aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, eccezioneRilevata);
				throw eccezioneRilevata;
			} catch (Exception e) {
				eccezioneRilevata = new IrisException("CON002","Impossibile ricostruire la URL della Porta Delegata per la consegna dell'Esito","Impossibile ricostruire la URL della Porta Delegata per la consegna dell'Esito", e);
				aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, eccezioneRilevata);
				throw eccezioneRilevata;
			}

			// Imbustamento del messaggio soap
			//  Viene inoltrato il messaggio alla Porta di Dominio

			SOAPMessage soapReq = null;

			try {
				String content = MessageUtils.xml2soap(xml, service.toString());
				soapReq = validatoreSintattico.build(new ByteArrayInputStream(content.getBytes()));
			} catch (Exception e) {
				eccezioneRilevata = new IrisException("CON001", "Impossibile accedere al messaggio di richiesta: " + e.getMessage(), "Impossibile costruire il messaggio SOAP: " + e.getMessage(), e);
				aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, eccezioneRilevata);
				throw eccezioneRilevata;
			}



			try {
				httpConn = PDInvoker.send(url, soapReq, credenziali, soapAction, msgId, silIntermediario, log); 
			} catch (IrisException e) {
				e.setFaultString("Errore nella consegna HTTP della richiesta al ProxyIRISCentrale");
				aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, e);
				throw e;
			}
			log.info("[" + msgId + "] Invocazione della Servizio Applicativo IRIS eseguito.");

			SPCoopHeaderDTO spcoopHeaderDTO = null;
			try{
				// dump delle informazioni 
				Map<String, List<String>> httpHeaders = httpConn.getHeaderFields();
				Set<String> httpHeadersName = httpHeaders.keySet();
				Iterator<String> itHeader = httpHeadersName.iterator();

				StringBuffer sb = new StringBuffer();
				while(itHeader.hasNext()){
					String headerName = (String) itHeader.next();
					List<String> httpHeaderValues = httpHeaders.get(headerName);
					Iterator<String> itValues = httpHeaderValues.iterator();
					StringBuffer valBuf = new StringBuffer();
					while(itValues.hasNext()){
						if(valBuf.length() > 0)
							valBuf.append(",");
						valBuf.append(itValues.next());
					}
					if(sb.length() > 0)
						sb.append("|");
					sb.append(headerName).append("#").append(valBuf.toString());
				}
				
				gestione.setHttpHeaders(sb.toString());
				gestione.setHttpResponseCode(httpConn.getResponseCode());
				
				spcoopHeaderDTO = new SPCoopHeaderDTO(httpConn, log, msgId);
				spcoopHeaderDTO.setSoapAction(soapAction);
				spcoopHeaderDTO.setHttpResponseCode(new Short("" + httpConn.getResponseCode()));
				spcoopHeaderDTO.setHttpResponseMessage(httpConn.getResponseMessage());
				//prendo l'idegov
				idEgov = spcoopHeaderDTO.getSpcoopId();
				gestione.setIdEgov(idEgov);  
			} catch (Exception e) {
				eccezioneRilevata =  new IrisException("CON003", "[" + msgId + "] Impossibile accedere alle informazioni di trasporto del messaggio inoltrato", "Risposta del Servizio Applicativo IRIS non corretta.", e);
				aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, eccezioneRilevata);
				throw eccezioneRilevata;
			}
			log.info("[" + msgId + "] Inserite informazioni del messaggio inoltrato.");

			try {
				if (spcoopHeaderDTO.getHttpResponseCode() >= 300) {
					SOAPMessage saajRes;
					try {
						saajRes = MessageUtils.soap2saaj(httpConn.getErrorStream());
						if(!saajRes.getSOAPBody().hasFault()) {
							throw new IrisException("CON004", "[" + msgId 
									+ "] Ricevuto errore dal Servizio Applicativo IRIS senza SOAPFault.", "Servizio Applicativo IRIS ha risposto con un codice di errore [HTTP Code "
											+spcoopHeaderDTO.getHttpResponseCode()+"], ma senza un SOAPFault.");
						}
					} catch(IrisException e){
						aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, e);
						throw e;
					}
					catch (Exception e) {
						eccezioneRilevata = new IrisException("CON004", "[" + msgId 
								+ "] Ricevuto errore dal Servizio Applicativo IRIS senza SOAPFault.", "Servizio Applicativo IRIS ha risposto con un codice di errore [HTTP Code "
										+spcoopHeaderDTO.getHttpResponseCode()+"], ma senza un SOAPFault.", e);
						aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, eccezioneRilevata);
						throw eccezioneRilevata;
					}

					String faultString = IrisUtils.getFaultString(saajRes);
					log.info("[" + msgId + "] Ricevuto SOAPFault nell'inoltro del messaggio: " + faultString);
					String faultCode = IrisUtils.getFaultCode(saajRes);
					eccezioneRilevata = new IrisException(faultCode, faultString,faultString);
					aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, eccezioneRilevata);
					throw eccezioneRilevata;
				} else {
					log.info("[" + msgId + "] Esito inoltrato con successo.");
					
					switch(tipoMessaggio){
					case ALLINEAMENTO_PENDENZE:
						// Al ritorno del controllo, se e' andata bene imposto Messaggio.esitoConsegnato a true
						messaggio.setEsitoConsegnato(true);
						break;
					case INFORMATIVA_PAGAMENTO:
					default:
						// Al ritorno del controllo, se e' andata bene imposto Messaggio.richiestaConsegnata a true
						messaggio.setRichiestaConsegnata(true);
						break;
					}
					aggiornaMessaggioGestione(messaggio, gestione, msgId, dataRicezione, eccezioneRilevata);
					return idEgov;
				}
			}
			catch (IrisException e) {
				throw e;
			}
		}catch(IrisException e) {
			log.error("Si e' verificato un errore durante la gestione del messaggio: "+ e.getMessage(),e);
			throw new IdpCartSpeditoreException(e, idEgov); 
		} catch(Exception e) {
			log.error("Si e' verificato un errore durante la gestione del messaggio: "+ e.getMessage(),e);
			throw new IdpCartSpeditoreException(e, idEgov);
		} finally {
			if (httpConn != null) { httpConn.disconnect(); }
		}
	}
	
	@Override
	public String processAPE(String ente, String sil, String msgId, String xml) throws IdpCartSpeditoreException {
		return processAPE(ente, sil, null, null, msgId, xml); 
	}
	
	@Override
	public String processIP(String ente, String sil, String msgId, String xml) throws IdpCartSpeditoreException {
		return processIP(ente, sil, null, null, msgId, xml); 
	}

	@Override
	public String processAPE(String ente, String sil, String enteIntermediario, String silIntermediario, String msgId, String xml) throws IdpCartSpeditoreException {
		String soapAction = "IdpAllineamentoPendenzeEnte.Esito";
		CredenzialiPdDDTO credenziali = null;
		String schemaSOAP = ValidatoreSintattico.IDPAPSOAP_ESITO;
		TipoMessaggio tipoMessaggio = TipoMessaggio.ALLINEAMENTO_PENDENZE;
		TipoGestione tipoGestione = TipoGestione.OUTBOUND;
		//IdPServizio service = IdPServizio.IdpAllineamentoPendenze; 
		//il servizio e' l'esito non allineamento pendenze in questo caso
		IdPServizio service = IdPServizio.IdpAllineamentoPendenzeEnteEsito; 

		if(this.username != null && this.password !=null){
			credenziali = new CredenzialiPdDDTO();
			credenziali.setUsername(this.username);
			credenziali.setPassword(this.password);
		}

		return this.gestisciRichiesta(ente, sil,enteIntermediario, silIntermediario, msgId, xml, schemaSOAP, tipoMessaggio, tipoGestione, service, soapAction, credenziali);
	}

	@Override
	public String processIP(String ente, String sil,String enteIntermediario, String silIntermediario, String msgId, String xml) throws IdpCartSpeditoreException {
		String soapAction = "";
		CredenzialiPdDDTO credenziali = null;
		String schemaSOAP = ValidatoreSintattico.IDPIPPSOAP;
		TipoMessaggio tipoMessaggio = TipoMessaggio.INFORMATIVA_PAGAMENTO;
		TipoGestione tipoGestione = TipoGestione.OUTBOUND;
		IdPServizio service = IdPServizio.IdpInformativaPagamento;

		if(this.username != null && this.password !=null){
			credenziali = new CredenzialiPdDDTO();
			credenziali.setUsername(this.username);
			credenziali.setPassword(this.password);
		}

		return this.gestisciRichiesta(ente, sil,enteIntermediario, silIntermediario, msgId, xml, schemaSOAP, tipoMessaggio, tipoGestione, service, soapAction, credenziali);	 
	}



	public void aggiornaMessaggioGestione(MessaggioModel messaggio, GestioneModel gestione, String seqMsgric, Date dataRicezione,IrisException eccezioneRilevata) throws IrisException {
		EntitiesUtils.aggiornaMessaggioGestione(this.idpCartDbManager, log, messaggio, gestione, seqMsgric, dataRicezione, eccezioneRilevata,
				TipoGestione.OUTBOUND,"IdpCartSpeditoreEJB.aggiornaEsito"+messaggio.getTipo().value(),false);
	}

}
