package it.tasgroup.idp.billerservices.controller.loader;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni.EnumStatoTrasmissione;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.api.plugin.DatiPiazzaturaFlusso;
import it.tasgroup.idp.billerservices.jobs.AllineamentoPendenzeJob;
import it.tasgroup.idp.billerservices.util.ObjectToXml;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.loader.AllineamentoPendenzeRequest;
import it.tasgroup.idp.loader.AllineamentoPendenzeResponse;
import it.tasgroup.idp.loader.CountTrasmissioniRequest;
import it.tasgroup.idp.loader.CountTrasmissioniResponse;
import it.tasgroup.idp.loader.FaultType;
import it.tasgroup.idp.loader.GetEsitoRequest;
import it.tasgroup.idp.loader.GetEsitoResponse;
import it.tasgroup.idp.loader.GetFileTrasmissioneRequest;
import it.tasgroup.idp.loader.GetFileTrasmissioneResponse;
import it.tasgroup.idp.loader.GetStatoRequest;
import it.tasgroup.idp.loader.GetStatoResponse;
import it.tasgroup.idp.loader.ListaTrasmissioniRequest;
import it.tasgroup.idp.loader.ListaTrasmissioniResponse;
import it.tasgroup.idp.loader.StatoEsitoType;
import it.tasgroup.idp.loader.TrasmissioneType;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Stateless
public class LoaderControllerImpl implements LoaderController {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@EJB(beanName = "AllineamentoPendenzeJobImpl")
	AllineamentoPendenzeJob job;

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AllineamentoPendenzeResponse allineamentoPendenze(AllineamentoPendenzeRequest parameters) {
		
		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());
		
		Boolean generatedIdPagamento = parameters.getProcessSpecification().isGeneratedIdPagamento();
		String e2eMsgId = parameters.getMsgId();
		
		if (e2eMsgId == null || generatedIdPagamento == null || !generatedIdPagamento) {
			// Assegno un id univoco.
			e2eMsgId = GeneratoreIdUnivoci.GetCurrent().generaId(null);
		}
		AllineamentoPendenzeResponse resp = new AllineamentoPendenzeResponse();
		logger.info(" Ricevuto messaggio, assegnato ID = "+e2eMsgId);
		
		String fileAsString=null;
		EnumReturnValues faultCode =null;
		String faultDescription=null;
		
		// Recupero il file dalla request.
		// Deve esistere.
	
		if (parameters.getFile().getBase64File()!=null) {
			try {
				fileAsString = new String(parameters.getFile().getBase64File(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				faultCode  = EnumReturnValues.ERRORE_GENERICO;
				faultDescription = e.getMessage();
			}
		} else if (parameters.getFile().getTextFile()!=null) {
			fileAsString  = parameters.getFile().getTextFile();
		} else {
			faultCode  = EnumReturnValues.ERRORE_GENERICO;
			faultDescription = "Nessun file in ingresso";
		}
		
		
		if (fileAsString==null) {
			resp.setResult(StatoEsitoType.ELABORATO_KO);
			FaultType f = new FaultType();
			f.setFaultCode(faultCode.getKey());
			f.setFaultString(faultCode.toString());
			f.setFaultDescription(faultDescription);
			resp.setFault(f);
			return resp;
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("File Ricevuto");
			logger.debug("-------------");
			logger.debug(fileAsString);
			logger.debug("-------------");
		}
		
		// Inizio elaborazione del file
		
		try { 

			DatiPiazzaturaFlusso piazzatura=job.piazzaturaFlusso(fileAsString, parameters.getProcessSpecification().getFileType());
			piazzatura.e2eMsgId=e2eMsgId;
			piazzatura.smartReplace=parameters.getProcessSpecification().isSmartReplace();
			
			logger.info("Dati piazzatura estratti da messaggio:");
			logger.info(ObjectToXml.toXml(piazzatura));
			
			// Controllo di coerenza dei dati di piazzatura con gli equivalenti dati passati a livello di ws
			if (!parameters.getSenderId().equals(piazzatura.senderId)) {
				throw new LoaderException(EnumReturnValues.SENDER_ID_NON_VALIDO,"SenderId trovato nel flusso :"+piazzatura.senderId+" non coerente con valore passato a livello di Web Service: "+parameters.getSenderId());
			}
			
			if (senderSysList != null && !senderSysList.isEmpty() && !senderSysList.contains(piazzatura.senderSys)) {
				throw new LoaderException(EnumReturnValues.SENDER_SYS_NON_VALIDO,"SenderId trovato nel flusso :"+piazzatura.senderSys+" non coerente con valore passato a livello di Web Service: "+parameters.getSenderSys());
			}
			
			logger.info("Controlli di coerenza superati. Inizio salvataggio su PENDENZE_CART");
			
			job.registrazioneTrasmissione(piazzatura, fileAsString);

			logger.info("Messaggio salvato. Fork asincrono del processo di elaborazione");

			// Fork asynch, uso un Timer in modo improprio, su JEE6 sarebbe tutto molto più banale, basterebbe fare un metodo asincrono
			job.fork(piazzatura, 10);   // Posso Partire anche dopo pochi millisecondi, importante è triggerare un fork asincrono
		
			resp.setMsgId(e2eMsgId);
			resp.setResult(StatoEsitoType.ELABORATO_OK);
			
		} catch (LoaderException e1) {
			logger.error(e1.getMessage(),e1);
			resp.setResult(StatoEsitoType.ELABORATO_KO);
			FaultType f = new FaultType();
			f.setFaultCode(e1.getErrorCode().getKey());
			f.setFaultString(e1.getErrorCode().toString());
			f.setFaultDescription(e1.getDescription() != null ? e1.getDescription() : e1.getErrorCode().getDescription());
			resp.setFault(f);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			resp.setResult(StatoEsitoType.ELABORATO_KO);
			FaultType f = new FaultType();
			f.setFaultCode(EnumReturnValues.ERRORE_GENERICO.getKey());
			f.setFaultString(EnumReturnValues.ERRORE_GENERICO.getKey().toString());
			f.setFaultDescription(e.getMessage());
			resp.setFault(f);			
		}
		return resp;
	
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public GetStatoResponse getStato(GetStatoRequest parameters) {
		
		//
		List<PendenzeCart> outLista = null;
		GetStatoResponse response = new GetStatoResponse();
		try {
			
			List<String> senderSysList = getSenderSysList(parameters.getSenderSys());
			
			outLista = GestoreTrasmissioni.getListaPaginataTrasmissioni(parameters.getSenderId(), 
																		senderSysList,
					                                                    parameters.getMsgId(), null, -1,
					                                                    2, em);
			if (outLista!=null && outLista.size() > 0) {
				if (outLista.size() == 1) {
					
					PendenzeCart trasmissione = (PendenzeCart)outLista.get(0);
					TrasmissioneType toutput=buildTrasmissioneType(trasmissione,em);					
					response.setNumPagamenti(toutput.getNumPagamenti());
					response.setNumPagamentiOk(toutput.getNumPagamentiOk());
					response.setStatoEsito(toutput.getStatoEsito());					
					
					
				} else {
					response.setStatoEsito(StatoEsitoType.SCONOSCIUTO); // TODO cambiare wsdl Piu di un flusso identificato dal criterio di filtro adottato (cambiare enumeration stato)
					
				}
			} else { 
				response.setStatoEsito(StatoEsitoType.SCONOSCIUTO); // Non trovato TODO cambiare wsdl
				FaultType fault = new FaultType();
				fault.setFaultCode(EnumReturnValues.NOTIFICA_NON_PRESENTE.getKey());
				fault.setFaultDescription("Notifica non presente");
				fault.setFaultString(EnumReturnValues.NOTIFICA_NON_PRESENTE.toString());
				//response.setFault(fault);
			}
			
		} catch (Exception e) {
			response.setStatoEsito(StatoEsitoType.SCONOSCIUTO);  // TODO cambiare wsdl
			FaultType fault = new FaultType();
			fault.setFaultCode(EnumReturnValues.ERRORE_GENERICO.getKey());
			fault.setFaultDescription("Errore generico");
			fault.setFaultString(EnumReturnValues.ERRORE_GENERICO.toString());
			//response.setFault(fault);
		}
		logger.info(" Fine elaborazione getFileNotifica"); 
		return response;	
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public GetEsitoResponse getEsito(GetEsitoRequest parameters) {

		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());
		
		List<PendenzeCart> outLista = null;
		GetEsitoResponse response = new GetEsitoResponse();
		try {
			outLista = GestoreTrasmissioni.getListaPaginataTrasmissioni(parameters.getSenderId(), 
					                                                    senderSysList,
					                                                    parameters.getMsgId(), null, -1,
					                                                    2, em);
			if (outLista!=null && outLista.size() > 0) {
				if (outLista.size() == 1) {
					
					PendenzeCart trasmissione = (PendenzeCart)outLista.get(0);
					TrasmissioneType toutput=buildTrasmissioneType(trasmissione,em);					
					response.setNumPagamenti(toutput.getNumPagamenti());
					response.setNumPagamentiOk(toutput.getNumPagamentiOk());
					response.setStatoEsito(toutput.getStatoEsito());
					
					response.setEsito(GestoreTrasmissioni.getEsitoTrasmissioneAsBytes(trasmissione.getPk().getSenderid(), trasmissione.getPk().getSendersys(), trasmissione.getPk().getE2emsgid(), em));
					
					
				} else {
					response.setStatoEsito(StatoEsitoType.SCONOSCIUTO); // TODO cambiare wsdl Piu di un flusso identificato dal criterio di filtro adottato (cambiare enumeration stato)
					
				}
			} else { 
				response.setStatoEsito(StatoEsitoType.SCONOSCIUTO); // Non trovato TODO cambiare wsdl
				FaultType fault = new FaultType();
				fault.setFaultCode(EnumReturnValues.NOTIFICA_NON_PRESENTE.getKey());
				fault.setFaultDescription("Notifica non presente");
				fault.setFaultString(EnumReturnValues.NOTIFICA_NON_PRESENTE.toString());
				//response.setFault(fault);
			}
			
		} catch (Exception e) {
			response.setStatoEsito(StatoEsitoType.SCONOSCIUTO);  // TODO cambiare wsdl
			FaultType fault = new FaultType();
			fault.setFaultCode(EnumReturnValues.ERRORE_GENERICO.getKey());
			fault.setFaultDescription("Errore generico");
			fault.setFaultString(EnumReturnValues.ERRORE_GENERICO.toString());
			//response.setFault(fault);
		}
		logger.info(" Fine elaborazione getFileNotifica"); 
		return response;
		
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ListaTrasmissioniResponse listaTrasmissioni(ListaTrasmissioniRequest parameters) {
		long now = new Date().getTime();
		Date dataDa = new Date(now - 3 * 24 * 3600000); // 3 giorni fa
		return listaTrasmissioni(parameters, dataDa);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ListaTrasmissioniResponse listaTrasmissioniSenzaLimitazioneData(ListaTrasmissioniRequest parameters) {
		return listaTrasmissioni(parameters, null);
	}

	private ListaTrasmissioniResponse listaTrasmissioni(ListaTrasmissioniRequest parameters, Date dataDa) {
		
		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());
		
		logger.info("listaTrasmissioni - begin");
		ListaTrasmissioniResponse response = new ListaTrasmissioniResponse();
		response.setResult(StatoEsitoType.ELABORATO_OK);
		List<PendenzeCart> trasmissioni = null;
		try {
		     trasmissioni = GestoreTrasmissioni.getListaPaginataTrasmissioni(parameters.getSenderId(), 
				                                                                           senderSysList,
				                                                                           (String)null, 
				                                                                           dataDa,
				                                                                           parameters.getOffset()==null?-1:parameters.getOffset(),
				                                                                           parameters.getLimit()==null?-1:parameters.getLimit(),
		 		                                                                           em);
	
		     logger.info("listaTrasmissioni - numero trasmissioni: " + trasmissioni.size());
		
		     for (PendenzeCart trasmissione:trasmissioni) {

			    TrasmissioneType toutput=buildTrasmissioneType(trasmissione,em);
				
			    response.getTrasmissioni().add(toutput);
		     }
		} catch(Exception e){
			response.setResult(StatoEsitoType.ELABORATO_KO);
		}
		logger.info("listaTrasmissioni - return: " + response);
		return response;
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public GetFileTrasmissioneResponse getFileTrasmissione(GetFileTrasmissioneRequest parameters) {
		
		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());

		GetFileTrasmissioneResponse response = new GetFileTrasmissioneResponse();
		List<PendenzeCart> outLista = null;
		try {
			outLista = GestoreTrasmissioni.getListaPaginataTrasmissioni(parameters.getSenderId(), 
					                                                    senderSysList,
					                                                    parameters.getMsgId(), null, -1,
					                                                    2, em);
			if (outLista!=null && outLista.size() > 0) {
				if (outLista.size() == 1) {
					
					PendenzeCart trasmissione = (PendenzeCart)outLista.get(0);
					response.setFile(GestoreTrasmissioni.getTrasmissioneAsBytes(trasmissione.getPk().getSenderid(), trasmissione.getPk().getSendersys(), trasmissione.getPk().getE2emsgid(), em));
					response.setResult(StatoEsitoType.ELABORATO_OK);
					
				} else {
					response.setResult(StatoEsitoType.ELABORATO_KO);
					
				}
			} else { 
				response.setResult(StatoEsitoType.ELABORATO_KO); // Non trovato TODO cambiare wsdl
				FaultType fault = new FaultType();
				fault.setFaultCode(EnumReturnValues.NOTIFICA_NON_PRESENTE.getKey());
				fault.setFaultDescription("Notifica non presente");
				fault.setFaultString(EnumReturnValues.NOTIFICA_NON_PRESENTE.toString());
				response.setFault(fault);
			}
			
		} catch (Exception e) {
			response.setResult(StatoEsitoType.ELABORATO_KO);  
			FaultType fault = new FaultType();
			fault.setFaultCode(EnumReturnValues.ERRORE_GENERICO.getKey());
			fault.setFaultDescription("Errore generico");
			fault.setFaultString(EnumReturnValues.ERRORE_GENERICO.toString());
			//response.setFault(fault);
		}
		logger.info(" Fine elaborazione getFileNotifica"); 
		return response;
		
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CountTrasmissioniResponse countTrasmissioni(CountTrasmissioniRequest parameters) {
		long now = new Date().getTime();
		Date dataDa = new Date(now - 3 * 24 * 3600000); // 3 giorni fa
		return countTrasmissioni(parameters, dataDa);
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CountTrasmissioniResponse countTrasmissioniSenzaLimitazioneData(CountTrasmissioniRequest parameters) {
		return countTrasmissioni(parameters, null);
	}

	private CountTrasmissioniResponse countTrasmissioni(CountTrasmissioniRequest parameters, Date dataDa) {
		
		List<String> senderSysList = getSenderSysList(parameters.getSenderSys());
		
		CountTrasmissioniResponse response = new CountTrasmissioniResponse();
		
		try {
		   response.setNumTrasmissioni(GestoreTrasmissioni.getCountTrasmissioni(parameters.getSenderId(), senderSysList, dataDa, em ));
		   response.setResult(StatoEsitoType.ELABORATO_OK);
		} catch (Exception e) {
		   response.setResult(StatoEsitoType.ELABORATO_KO);
		}
		return response;
	}

	
	
	private TrasmissioneType buildTrasmissioneType(PendenzeCart trasmissione, EntityManager em) {
		
		DatatypeFactory df;
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}

		
		TrasmissioneType toutput = new TrasmissioneType();
		
		toutput.setMsgId(trasmissione.getPk().getE2emsgid());
		toutput.setSenderId(trasmissione.getPk().getSenderid());
		toutput.setSenderSys(trasmissione.getPk().getSendersys());
		toutput.setNumPagamenti(trasmissione.getNumPendenze());
		
		Date timestampRicezione = new Date(trasmissione.getTimestampRicezione().getTime());
		GregorianCalendar timestampRicezioneGC = new GregorianCalendar();
		timestampRicezioneGC.setTime(timestampRicezione);
		
		toutput.setTimestampRicezione(df.newXMLGregorianCalendar(timestampRicezioneGC));

		int pagamentiOk = GestoreTrasmissioni.getCountEsitiTrasmissione(trasmissione.getPk().getSenderid(),
				trasmissione.getPk().getSendersys(),
				trasmissione.getPk().getE2emsgid(), 
				false  /*includeErrors*/, 
				true /*includeWarnings*/, 
				true /*includeSuccess*/, 
				em);
		
		toutput.setNumPagamentiOk(pagamentiOk);

		
		String statoAsString = trasmissione.getStato();
		try  {
			EnumStatoTrasmissione stato = EnumStatoTrasmissione.valueOf(statoAsString);
			
			switch (stato) {
			case DA_ELABORARE_LOADER:
				toutput.setStatoEsito(StatoEsitoType.DA_ELABORARE);
				toutput.setNumPagamentiElaborati(0);
				break;
			case VALIDATO_OK_LOADER:
				toutput.setStatoEsito(StatoEsitoType.VALIDATO);
				
				int pagamentiElaborati = GestoreTrasmissioni.getCountEsitiTrasmissione(trasmissione.getPk().getSenderid(),
						trasmissione.getPk().getSendersys(),
						trasmissione.getPk().getE2emsgid(), 
						true /*includeErrors*/, 
						true /*includeWarnings*/, 
						true /*includeSuccess*/, 
						em);
				
				toutput.setNumPagamentiElaborati(pagamentiElaborati);
				
				
				break;
			case VALIDATO_KO_LOADER:
				toutput.setStatoEsito(StatoEsitoType.ELABORATO_KO);
				toutput.setNumPagamentiElaborati(0);
				break;
			case ESITATO_LOADER:
				if (pagamentiOk==0) {
					toutput.setStatoEsito(StatoEsitoType.ELABORATO_KO);
				} else if (pagamentiOk==trasmissione.getNumPendenze()) {
					toutput.setStatoEsito(StatoEsitoType.ELABORATO_OK);
					toutput.setNumPagamentiElaborati(trasmissione.getNumPendenze());
				} else {
					toutput.setStatoEsito(StatoEsitoType.ELABORATO_OK_PARZIALE);
					toutput.setNumPagamentiElaborati(trasmissione.getNumPendenze());
				}
				break;
			}
			
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(),e);
			toutput.setStatoEsito(StatoEsitoType.SCONOSCIUTO);
		}
		
		return toutput;
	}
	
	private List<String> getSenderSysList(String senderSysConcatenation) {
	
		/*
		 *  con l'introduzione della versione rest del loader (SmartProxy like)
		 *  il campo senderSys puo' contenere una serie di sys, concatenati e separati da punto e virgola
		 */
		List<String> senderSysList = null;
		if (senderSysConcatenation != null) {
			senderSysList = new ArrayList<String>();
			String[] senderSysArray = senderSysConcatenation.trim().split(";");
			for (String senderSys : senderSysArray) {
				senderSysList.add(senderSys.trim());
			}
		}
		return senderSysList;
		
	}
	
	
}
