/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.proxyndp.bean;

import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta;
import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.gateway.EsitiNdp;
import it.tasgroup.idp.pojo.StoricoData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.enums.EnumFlagElaborazione;
import it.tasgroup.iris2.enums.EnumFlagRiconciliazione;
import it.tasgroup.iris2.pagamenti.CasellarioInfo;
import it.tasgroup.iris2.pagamenti.Rendicontazioni;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.flussoriversamento.CtDatiSingoliPagamenti;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.flussoriversamento.CtFlussoRiversamento;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EsitiNdpBlobInputManager implements ExecutorLocal {
//extends AbstractExecutorRemote implements ExecutorLocal {
	

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;	
 	
	private final Log logger = LogFactory.getLog(this.getClass());

		
//	@Interceptors(MonitoringInterceptor.class)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public StoricoData executeApplicationTransaction(Object data) {
		// Monitoring data
		StoricoData storicoData = new StoricoData();
		// Fill object for interceptor
		storicoData.setTipoOperazione("PARSER NDP ED GESTIONE ERRORI");
		
		storicoData.setProdotto("NDP");
		// Recupero i dati di input
		CasellarioInfo casellarioInfo = (CasellarioInfo)data;
		
		// Local Variables
		boolean esitoKo = false;
		StringBuilder esitoKoString = new StringBuilder();
		CasellarioInfo model = new CasellarioInfo();
		
		//variables
		CtFlussoRiversamento result = null;
		StringBuilder descErroreComposto = new StringBuilder();
		
		try {
			
			//SET ID CASELLARIO INFO !! PER SALVATAGGIO RELAZIONE
			//model.setIdCasellarioInfo( new Integer(casellarioInfo.getId().intValue()) );
			//se il primo parsing è superato allora invio per l'inserimento
			//EsitiRctInputManagerProxy.executeApplicationTransaction((RendicontazioneSerializableModel)model);
			//=============================================================================================		
			//costruisco oggetto per inserimento rendicontazione (testata)
			//recupero il flussone
			String flussoneNdp = new String(casellarioInfo.getFlussoCbi(), Charset.forName("UTF-8"));
			logger.info(" CasellarioInfo, Ho trovato questo flusso.... (id="+ casellarioInfo.getId() + "  == \n " + flussoneNdp );
			
			//eseguo il marshall
			logger.info(" Eseguo il marshall verso ctFlussoRiversamento " );
			InputStream isMsg = new ByteArrayInputStream(casellarioInfo.getFlussoCbi());
			JAXBContext context = JAXBContext.newInstance(it.tasgroup.ndpmodel.nodopagamentispc.payloads.flussoriversamento.ObjectFactory.class);
			Unmarshaller um = context.createUnmarshaller();
			JAXBElement<CtFlussoRiversamento> unmarshal = (JAXBElement<CtFlussoRiversamento>)um.unmarshal(isMsg);
			result = unmarshal.getValue();
			logger.info(" Marshall verso ctFlussoRiversamento ESEGUITO " );
			
		} catch (RuntimeException e) {
			logger.error(this.getClass().getName() + " ERROR UNEXPECTED = " + e.getMessage());
			logger.error(e);
			throw new RuntimeException(e);
		} catch (JAXBException e) {
			logger.error(this.getClass().getName() + " ERROR UNMARSHALLING = " + e.getMessage());
			logger.error(e);
			throw new RuntimeException(e);			
		}
		
		if (result==null) {
			logger.info(" Data has not been unmarshalled towards ctFlussoRiversamento  ");	
			//UTILIZZO IL SECONDO METODO DI LETTURA
			//MA E' DA CHIARIRE....PERCHE' IL SIMULATORE INTERNO TAS RESTITUISCE QUESTA RISPOSTA
			//RISPETTO A QUELLA RESTITUITA DAL PROXYNDP
			result = readFlussoRendicontazioneRisposta(casellarioInfo, result);			
			
		} else {
			logger.info(" Data HAS BEEN unmarshalled towards ctFlussoRiversamento  ");
		}		
			
		//OK se ho eseguito l'unmarshall
		if (result!=null) {				
			//questa trafila va fatta per ogni Flusso
			//....sarebbe meglio... fare un BEAN ad hoc !!!
			//però.....
			//bigD per calcolo importo totale
			BigDecimal totaleFlussoNdp = result.getImportoTotalePagamenti();
			String idRegolamento = result.getIdentificativoUnivocoRegolamento();
			XMLGregorianCalendar dataReg = result.getDataRegolamento();
			int numeroEsiti = result.getNumeroTotalePagamenti().intValue();
			
			//mittente
			String codiceIdentificativoUnivocoMittente = result.getIstitutoMittente().getIdentificativoUnivocoMittente().getCodiceIdentificativoUnivoco();									
			
			//scrivo nella table rendicontazioni
			Rendicontazioni rct = insertRendicontazione(casellarioInfo, result);
			logger.info(" RENDICONTAZIONE INSERITA "
					+ "	\n (id = " + rct.getId() + ")"  
					+ " \n (stato = " + rct.getStato()
					+ " \n (flag = " + rct.getFlagElaborazione()
					+ " \n (importo = " + rct.getImporto());				
			
			//visualizzo i dati contenuti nel flusso interno (FlussoRiversamento)
			logger.info(" Id.Unico.Regolamento = " + result.getIdentificativoUnivocoRegolamento()  );
			logger.info(" Identificativo Flusso = " + result.getIdentificativoFlusso() );
			logger.info(" Mittente / ricevente = " + result.getIstitutoMittente() + "/" + result.getIstitutoRicevente() );
			logger.info(" Importo totale = " + result.getImportoTotalePagamenti() );
			logger.info(" Numero Totale pagamenti = " + result.getNumeroTotalePagamenti().intValue() );
			List<CtDatiSingoliPagamenti> listaPag = result.getDatiSingoliPagamenti();			
			
//			//variabili di controllo			
//			boolean somethingNotFound = false;
//			int howManyNotFound = 0;
			
			int howManyReceived = listaPag.size();			
			logger.info(" Numero Distinte di Pagamento = " + listaPag.size() );

			for (Iterator<CtDatiSingoliPagamenti> iterator = listaPag.iterator(); iterator.hasNext();) {
				
				CtDatiSingoliPagamenti ctDatiSingoliPagamenti = (CtDatiSingoliPagamenti) iterator.next();
				
				String IUV = ctDatiSingoliPagamenti.getIdentificativoUnivocoVersamento();
				String IUR = ctDatiSingoliPagamenti.getIdentificativoUnivocoRiscossione();				
				logger.info("IUV = " + IUV);
				logger.info("IURiscossione = " + IUR);
				logger.info("Importo = " + ctDatiSingoliPagamenti.getSingoloImportoPagato());
				logger.info("Esito = " + ctDatiSingoliPagamenti.getCodiceEsitoSingoloPagamento());
				
				//DEVO ESEGUIRE LA BUSINESS LOGIC....
				//CONTROLLARE SU TABLE DIST-PAG e PAGAMENTI				
								
				//scrivo nella table esiti_ndp
				EsitiNdp esitoNdp = buildEsitoNdp(rct, ctDatiSingoliPagamenti,
						IUV, IUR);  												
				//setto il collegamento con il flusso di rendicontazione
				esitoNdp.setRendicontazioni(rct);
				
				esitoNdp.setFlagRiconciliazione((short)0);
				
				manager.persist(esitoNdp);
				
			} 			
			
			//aggiorno lo stato del casel.info
			casellarioInfo.setFlagElaborazione(EnumFlagElaborazione.ELABORATO.getChiave());
			casellarioInfo.setDataElaborazione(new Timestamp(System.currentTimeMillis()));
			casellarioInfo.setOpAggiornamento("NDP_MANAGER");
			casellarioInfo.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));

//			//se non ho inserito niente... devo scrivere che l'ID esisteva già
//			if (descErroreComposto!=null && descErroreComposto.length()>2) {
//				casellarioInfo.setTipoErrore(EnumTipiErroriNdp.ERRORE_CODTRANSAZIONE_GIAELABORATO.getChiave());
//				if (casellarioInfo.getDescErrore()!=null)
//					casellarioInfo.setDescErrore(casellarioInfo.getDescErrore() + descErroreComposto);
//				else
//					casellarioInfo.setDescErrore(descErroreComposto.toString());
//			}	
			
			logger.info(" CASELLARIO AGGIORNATO "
					+ "	\n (id = " + casellarioInfo.getId() + ")"  
					+ " \n (dex errore = " + casellarioInfo.getDescErrore()
					+ " \n (flag = " + casellarioInfo.getFlagElaborazione()
					+ " \n (importo = " + rct.getImporto());					
			
			/*** Andrea Folli wrote: casellarioInfo is in detached state, merge required ***/
			manager.merge(casellarioInfo);	
			
			/*** Andrea Folli wrote: explicit flush to get suddendly the possible persistence exception ***/
			manager.flush();							
			
			
		} else {
			logger.info(" Data HAS BEEN unmarshalled towards ctFlussoRiversamento YET !!");
		}				
				
		return storicoData;			
	}

	/**
	 * 
	 * @param casellarioInfo
	 * @param result
	 * @return
	 */
	private CtFlussoRiversamento readFlussoRendicontazioneRisposta(
			CasellarioInfo casellarioInfo, CtFlussoRiversamento result) {
		try {
			Holder<FaultBean> fault = new Holder<FaultBean>();
			Holder<DataHandler> xmlRendicontazione = new Holder<DataHandler>();
			//eseguo il marshall della response ricevuta dal WS
			NodoChiediFlussoRendicontazioneRisposta resultData = null;			
			//read all the lines!!!
			StringBuffer xmlRendicontazioneReceived; 
			//DA CHIARIRE....
			//PERO' IL SIMULATORE INTERNO TAS RESTITUISCE QUESTA RISPOSTA
			//E quindi la leggiamo così
			//eseguo il marshall
			InputStream isMsg = new ByteArrayInputStream(casellarioInfo.getFlussoCbi());
			JAXBContext context = JAXBContext.newInstance(gov.telematici.pagamenti.ws.ObjectFactory.class);
			Unmarshaller um = context.createUnmarshaller();
			JAXBElement<NodoChiediFlussoRendicontazioneRisposta> unmarshal = (JAXBElement<NodoChiediFlussoRendicontazioneRisposta>)um.unmarshal(isMsg);
			resultData = unmarshal.getValue();
			logger.info(" Data HAS BEEN unmarshalled towards NodoChiediFlussoRendicontazioneRisposta  ");
			
			//la response contiene un altro dataHandler
			//che è quello che poi contiene veramente il flusso
			ByteArrayInputStream robaDaDecodare = (ByteArrayInputStream) resultData.getXmlRendicontazione().getContent();
			logger.info(" Messaggio contenuto nel DettaglioRisposta (tipo ctFlussoRiversamento) = \n " + robaDaDecodare);
			logger.info(" ------------------------ ");
			BufferedReader br = new BufferedReader(new InputStreamReader(robaDaDecodare, "UTF-8"));
			boolean readAll = true;
			xmlRendicontazioneReceived = new StringBuffer(br.readLine());
			String part = "";
			while (readAll) {
				part = br.readLine();
				if (part != null) {
					xmlRendicontazioneReceived.append(part);
				} else {
					readAll = false;
				}
			}
			logger.info(" Dati decodificati (sempre di tipo ctFlussoRiversamento) = \n "+ xmlRendicontazioneReceived);
			logger.info(" ------------------------ ");
			robaDaDecodare.reset();
			
			//.....MUCCATA.... UNA VOLTA TANTO....
			//			xmlRendicontazioneReceived
			String newXml = xmlRendicontazioneReceived.toString();
			String outputMuccato = newXml.replace("ctFlussoRiversamento","FlussoRiversamento");
			xmlRendicontazioneReceived = new StringBuffer(outputMuccato);
			logger.info(" dati fixati.... settato TAG di tipo FlussoRiversamento) = \n   "+ xmlRendicontazioneReceived.toString());
			//eseguo il marshall   
			logger.info(" Eseguo il marshall verso ctFlussoRiversamento  ");
			InputStream isMsg2 = new ByteArrayInputStream(xmlRendicontazioneReceived.toString().getBytes());
			JAXBContext context2 = JAXBContext
					.newInstance(it.tasgroup.ndpmodel.nodopagamentispc.payloads.flussoriversamento.ObjectFactory.class);
			Unmarshaller um2 = context2.createUnmarshaller();
			JAXBElement<CtFlussoRiversamento> unmarshal2 = (JAXBElement<CtFlussoRiversamento>) um2.unmarshal(isMsg2);
			result = unmarshal2.getValue();
			
		} catch (UnsupportedEncodingException e) {
			logger.error(this.getClass().getName() + " ERROR ENCODING  = " + e.getMessage());
		} catch (JAXBException e) {
			logger.error(this.getClass().getName() + " ERROR JAXBENCODING = " + e.getMessage());
		} catch (IOException e) {
			logger.error(this.getClass().getName() + " ERROR IO = " + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param rct
	 * @param ctDatiSingoliPagamenti
	 * @param IUV
	 * @param IUR
	 * @return
	 */
	private EsitiNdp buildEsitoNdp(Rendicontazioni rct,
			CtDatiSingoliPagamenti ctDatiSingoliPagamenti, String IUV,
			String IUR) {
		EsitiNdp esitoNdp = new EsitiNdp();
		esitoNdp.setImporto(ctDatiSingoliPagamenti.getSingoloImportoPagato());			
		esitoNdp.setIdRiconciliazione(IUV);
		esitoNdp.setIdRiscossione(IUR);
		esitoNdp.setDataPagamento(ctDatiSingoliPagamenti.getDataEsitoSingoloPagamento().toGregorianCalendar().getTime());
		esitoNdp.setIndicePagamento( ctDatiSingoliPagamenti.getIndiceDatiSingoloPagamento());
		rct.setOpInserimento("Esiti Ndp Manager");
		rct.setTsInserimento(new Timestamp(System.currentTimeMillis()));					
		
		String esito = ctDatiSingoliPagamenti.getCodiceEsitoSingoloPagamento();
		if ("0".equals(esito)) {
			esitoNdp.setEsitoPagamento("ESEGUITO");
			esitoNdp.setSegno("C");
		} else if ("3".equals(esito)) {
			esitoNdp.setEsitoPagamento("REVOCATO");
			esitoNdp.setSegno("D");
		} else if ("9".equals(esito)) {
			esitoNdp.setEsitoPagamento("ESEGUITO_NO_RPT");
			esitoNdp.setSegno("C");
		}
		return esitoNdp;
	}


	/**
	 * 
	 * @param casellarioInfo
	 * @param result
	 * @return
	 */
	private Rendicontazioni insertRendicontazione(
			CasellarioInfo casellarioInfo, CtFlussoRiversamento result) {
		Rendicontazioni rct = new Rendicontazioni();
		//questa.... dovrebbe funzionare...
		rct.setCasellarioInfo(casellarioInfo);
		//però altrimenti.... usiamo la solita tecnica che si usa
		//con i flussi RCT
		CasellarioInfo casellarioInfoFind = (CasellarioInfo)manager.find(CasellarioInfo.class, casellarioInfo.getId());		
		rct.setCasellarioInfo(casellarioInfoFind);				
		//altri campi
		rct.setCodRendicontazione("FR");
		rct.setDivisa("EUR");
		rct.setDataRicezione(casellarioInfo.getTsInserimento());
		rct.setUtenteCreatore("NDP");
		rct.setImporto(result.getImportoTotalePagamenti());	
		rct.setBicBancaRiversamento(result.getCodiceBicBancaDiRiversamento());
		
		if (result.getDataOraFlusso()!=null) {		
			rct.setDataCreazione(new Timestamp(result.getDataOraFlusso().toGregorianCalendar().getTimeInMillis()));
		} else {
			rct.setDataCreazione(new Timestamp(System.currentTimeMillis()));
		}				
		rct.setStato(EnumFlagRiconciliazione.DA_RICONCILIARE.getDescrizione());
		rct.setNumeroEsiti(result.getNumeroTotalePagamenti().intValue());
		rct.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		rct.setOpInserimento("Esiti NDP Manager");
		rct.setIdFlusso(result.getIdentificativoFlusso());
		rct.setIdRegolamento(result.getIdentificativoUnivocoRegolamento());
		rct.setDataRegolamento(result.getDataRegolamento().toGregorianCalendar().getTime());
				
		//idem come sopra.... usiamo la stessa tecnica collaudata degli RCT
		manager.persist(rct);
		return rct;
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String executeHtml() throws Exception {
		return null;
	}

	@Override
	public StoricoData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoricoData executeApplicationTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeHtml(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
