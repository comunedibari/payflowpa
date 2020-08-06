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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazione;
import gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta;
import it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirpt.PagamentiTelematiciRPT;
import it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirpt.PagamentiTelematiciRPTservice;
import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.gateway.FlussiNdp;
import it.tasgroup.idp.pojo.StoricoData;
import it.tasgroup.idp.proxyndp.exception.NDPComunicationException;
import it.tasgroup.idp.proxyndp.gde.GiornaleEventiExtDTO;
import it.tasgroup.idp.proxyndp.gde.IGiornaleEventiUtils;
import it.tasgroup.idp.proxyndp.utils.NDPConstants;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.enums.EnumFlagElaborazione;
import it.tasgroup.iris2.pagamenti.CasellarioInfo;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.flussoriversamento.CtFlussoRiversamento;



@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
/**
 * Bean che permette di estrarre un flusso NDP, chiedendolo al nodo ed inserirlo nel casellario.
 * Con l'avvento del trasporto via SFTP si e' estesa l'implementazione per considerare
 * flussi che il nodo ha FTP-ato in una cartella e possono essere elaborabili subito dopo
 * la ricezione della response oppure solo in un secondo tempo
 *
 * @author RepettiS & altri
 */
public class DettaglioFlussoNdpManager implements ExecutorLocal {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;
 
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@EJB(beanName = "GiornaleEventiUtilsBean")
	private IGiornaleEventiUtils giornaleEventiUtilsBean;
	
	// lista con gli id dei flussi che sono stati trovati nella cartella SFTP ed elaborati
	private List<String> listaFlussiSFTPElaborati; 
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public StoricoData executeApplicationTransaction(Object flussoObj) {
		
		NDPConstants constants = new NDPConstants();
		StoricoData monData = new StoricoData();

		if (flussoObj == null) {
			// fase di "sistemazione" della directory SFTP: acquisizione di flussi precedentemente non elaborabili
			listaFlussiSFTPElaborati = new ArrayList<String>();
			String dirFlussiRiversamentoSftpName = constants.getDirFlussiRiversamentoSftp();
			if (dirFlussiRiversamentoSftpName != null && !dirFlussiRiversamentoSftpName.trim().isEmpty()) { // se la cartella e' stata configurata
				File dirFlussiRiversamentoSftp = new File(dirFlussiRiversamentoSftpName);
				if (dirFlussiRiversamentoSftp.isDirectory()) { // listiamo tutti i file .XML.ZIP nella directory SFTP
					File[] filesXmlZip = dirFlussiRiversamentoSftp.listFiles(new FileFilter() {
						@Override
						public boolean accept(File file) {
							return file.isFile() && file.getName().endsWith(".XML.ZIP");
						}
					});
					for (File fileXmlZip : filesXmlZip) {
						byte[] xml = readData(fileXmlZip, false);
						if (xml != null) {
							CtFlussoRiversamento ctFlussoRiversamento = unmarshall(xml);
							if (ctFlussoRiversamento != null) {
								try {
									elaboraFRNDP(ctFlussoRiversamento, ctFlussoRiversamento.getIdentificativoFlusso(), xml);
									listaFlussiSFTPElaborati.add(ctFlussoRiversamento.getIdentificativoFlusso()); // non lo facciamo riscaricare solo se e' e' stato anche scritto sul DB
								} catch (Exception e) {
									logger.error("Errore nel salvataggio dei dati del flusso FR " + fileXmlZip.getAbsolutePath() + " ricevuto via SFTP", e);
								}
							}
						}
						try {
							fileXmlZip.delete(); // comunque sia andata cancelliamo il file, se aveva problemi verra' riscaricato 
						} catch (Exception e) {
							logger.error("Errore nella cancellazione del flusso FR " + fileXmlZip.getAbsolutePath() + " ricevuto via SFTP", e);
						}
					}

				} else {
					logger.error("La cartella destinata alla ricezione dei file/flussi FR via SFTP [" + constants.getDirFlussiRiversamentoSftp() + "=" + dirFlussiRiversamentoSftpName + "] non e' valida");
				}
			}
			return monData; // uscita veloce dopo l'elaborazione di eventuali file presenti nella directory SFTP e in precedenza non elaborati
		}

		FlussiNdp flusso = (FlussiNdp) flussoObj;
		String identificativoFlusso = flusso.getIdentificativoFlusso();
		String identificativoDominio = flusso.getIdentificativoDominio();
		
		if (listaFlussiSFTPElaborati != null && listaFlussiSFTPElaborati.contains(identificativoFlusso)) {
			return monData; // uscita veloce nel caso il flusso sia appena stato elaborato perche' gia' ricevuto via SFTP in una vita precedente
		}

		
		GiornaleEventiExtDTO go = null;
		try {

			PagamentiTelematiciRPTservice ndp = new PagamentiTelematiciRPTservice();
			PagamentiTelematiciRPT port = ndp.getPagamentiTelematiciRPTPort();

			BindingProvider bindingProvider = (BindingProvider) port;

			if (!constants.isUsaProxyNdp()) {
				if (constants.getHttpBasicUser() != null && !constants.getHttpBasicUser().isEmpty()) {
					bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, constants.getHttpBasicUser());
					bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, constants.getHttpBasicPassword());
					logger.info(" ========== credenziali accesso  username =  " + constants.getHttpBasicUser() + "password = " + constants.getHttpBasicPassword());
				}
				bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, constants.getNdpWsUrl() + "nodoChiediFlussoRendicontazione");
				logger.info(" ========== URL =  " + constants.getNdpWsUrl() + "nodoChiediFlussoRendicontazione");
			} else {
				bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, constants.getNdpWsUrl());
				logger.info(" ========== URL =  " + constants.getNdpWsUrl());
			}

			NodoChiediFlussoRendicontazione inputParams = new NodoChiediFlussoRendicontazione();

			inputParams.setIdentificativoFlusso(identificativoFlusso);
			inputParams.setIdentificativoIntermediarioPA(constants.getIntermediarioPA());
			
			inputParams.setIdentificativoDominio(identificativoDominio);
			
			// inputParams.setIdentificativoPSP(identificativoPsp);
			inputParams.setIdentificativoStazioneIntermediarioPA(constants.getCodiceStazionePA());
			inputParams.setPassword(constants.getPasswordNdp());

			logger.info(" ========== Salvataggio su giornale degli eventi (richiesta) ======");
			go = giornaleEventiUtilsBean.saveGDE(inputParams, null);
			logger.info(" ========== Salvataggio su giornale degli eventi effettuato ======");

			logger.info(" ========== Elaboro il flusso, idFlusso ======== " + identificativoFlusso);
			NodoChiediFlussoRendicontazioneRisposta risposta = port.nodoChiediFlussoRendicontazione(inputParams);
			logger.info(" !! Webservice NDP called !! ");

			logger.info(" ========== Salvataggio su giornale degli eventi (risposta) ======");
			giornaleEventiUtilsBean.saveGDE(risposta, go);
			logger.info(" ========== Salvataggio su giornale degli eventi effettuato ======");
			
			if (risposta.getFault() != null) {
				throw new NDPComunicationException(risposta.getFault());
			}
			
			byte[] xml = null;
			if (risposta.getXmlRendicontazione() == null) { // spedizione flusso via SFTP
				xml = readResult(identificativoFlusso, constants.getDirFlussiRiversamentoSftp());
			} else { // spedizione flusso sulla response
				xml = readResult(risposta);
			}
			CtFlussoRiversamento ctFlussoRiversamento = unmarshall(xml);

			if (ctFlussoRiversamento == null) {
				logger.info(" Data has not been unmarshalled towards ctFlussoRiversamento  ");
			} else {
				logger.info(" Data HAS BEEN unmarshalled towards ctFlussoRiversamento  ");
				elaboraFRNDP(ctFlussoRiversamento, identificativoFlusso, xml);
			}

		} catch (Exception e) {
			logger.error("Errore nel recupero del contenuto del flusso " + identificativoFlusso, e);
			throw new RuntimeException(e); // Rollback and propaga.
		}
		return monData;

	}
	
	@Override
	public StoricoData executeApplicationTransaction(String data) {
		return null;
	}

	@Override
	public StoricoData executeApplicationTransaction() {
		return null;
	}

	@Override
	public String executeHtml() throws Exception {
		return null;
	}

	@Override
	public String executeHtml(String id) throws Exception {
		return null;
	}

	private byte[] readResult(String identificativoFlusso, String dirFlussiRiversamentoSftp) throws NDPComunicationException	 {
		
		byte[] msgNonEncoded = null;

		try {

			if (dirFlussiRiversamentoSftp == null || dirFlussiRiversamentoSftp.trim().isEmpty()) {
				String description = "Property " + NDPConstants.DIR_FLUSSI_RIVERSAMENTO_SFTP + " non valorizzata, impossibile la ricezione via SFTP";
				logger.warn(description);
				FaultBean fakeFaultBean = new FaultBean(); // impostiamo un fake FaultBean, unica uscita alternativa da questo metodo
				fakeFaultBean.setDescription(description);
				fakeFaultBean.setFaultCode("PPT_SYSTEM_ERROR");
				fakeFaultBean.setId("NodoDeiPagamentiSPC");
				throw new NDPComunicationException(fakeFaultBean);
			}

			// TODO gli diamo qualche secondo sperando che abbia finito di arrivare ?
			File zipFile = new File(dirFlussiRiversamentoSftp, identificativoFlusso + ".XML.ZIP");
			msgNonEncoded = readData(zipFile, true);
			
			String dati = "Dati non interpretabili: ";
			try {dati = new String(msgNonEncoded, "UTF8");} catch (Exception e) {dati += e.getMessage();}
			logger.debug("Data received from SFTP = \n " + dati);
			logger.info(" ------------------------- " );

		} catch (Exception e) {
			logger.error(" Errore Exception  " + e.getMessage());
		}
		
		return msgNonEncoded;
	}
	
	private byte[] readData (File zipFile, boolean delete) {
		byte[] ret = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count;
			ZipInputStream zis = null;
			try {
		        zis = new ZipInputStream(new FileInputStream(zipFile));
		        zis.getNextEntry();
		        while ((count = zis.read(buffer)) != -1) {
		            baos.write(buffer, 0, count);
		        }
			} finally {
				try {zis.closeEntry();} catch (Exception e) {}
				try {zis.close();} catch (Exception e) {}
			}
			ret = baos.toByteArray();
			if (ret.length == 0) {
				logger.error("Errore nello scompattamento (unzip) del flusso FR " + zipFile.getAbsolutePath() + " ricevuto via SFTP: nessun contenuto");
				ret = null;
			} else { // se abbiamo ottenuto qualcosa vuol dire che il file era tutto ricevuto, visto che la central directory e' in coda (e sempre che l'upload del file sia stato fatto sequenzialmente e non a chunk) quindi possiamo cancellare il file ricevuto  
				if (delete) {
					try {
						logger.info("Cancellazione del flusso FR " + zipFile.getAbsolutePath() + " ricevuto via SFTP");
						zipFile.delete();
					} catch (Exception e) {
						logger.error("Errore nella cancellazione del flusso FR " + zipFile.getAbsolutePath() + " ricevuto via SFTP", e);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Errore nello scompattamento (unzip) del flusso FR " + zipFile.getAbsolutePath() + " ricevuto via SFTP", e);
		}
		return ret;
	}
	
	private byte[] readResult(NodoChiediFlussoRendicontazioneRisposta risposta) throws NDPComunicationException	 {

		byte[] msgNonEncoded = null;

		try {
				
			logger.debug("Response (contentType/content/datasource/ds classname) = "
									+ risposta.getXmlRendicontazione().getContentType()
									+ risposta.getXmlRendicontazione().getDataSource()
									+ risposta.getXmlRendicontazione().getDataSource().getClass().getName());
			
			ByteArrayInputStream content = (ByteArrayInputStream) risposta.getXmlRendicontazione().getContent();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024];
			while ((count = content.read(buffer)) != -1) {
				baos.write(buffer, 0, count);
			}
			msgNonEncoded = baos.toByteArray();
			
			String dati = "Dati non interpretabili: ";
			try {dati = new String(msgNonEncoded, "UTF8");} catch (Exception e) {dati += e.getMessage();}
			logger.info("Data received from WS) = \n " + dati);
			logger.info(" ------------------------- " );
			
		} catch (IOException e) {
			logger.error(" Errore I/O Exception " + e.getMessage());
		} catch (Exception e) {
			logger.error(" Errore Exception  " + e.getMessage());
		}
		
		return msgNonEncoded;
	}
	
	private CtFlussoRiversamento unmarshall(byte[] xml) {

		CtFlussoRiversamento ret = null;
		
		if (xml != null) {
			try {
				// eseguo la validazione prima del marshall
		
				// eseguo il marshall
				JAXBElement<CtFlussoRiversamento> unmarshalCt = null;
				JAXBContext context = JAXBContext.newInstance(it.tasgroup.ndpmodel.nodopagamentispc.payloads.flussoriversamento.ObjectFactory.class);
				Unmarshaller um = context.createUnmarshaller();
				unmarshalCt = (JAXBElement<CtFlussoRiversamento>) um.unmarshal(new ByteArrayInputStream(xml));
				ret = unmarshalCt.getValue();
			} catch (JAXBException e) {
				logger.error(" Errore Jaxb Exception " + e.getMessage());
			} catch (Exception e) {
				logger.error(" Errore Exception  " + e.getMessage());
			}
		}
		return ret;
	}
	
	/**
	 * Scrittura dei dati del FR sul DB (insert CasellarioInfo e update FlussiNdp)
	 */
	private void elaboraFRNDP(CtFlussoRiversamento ctFlussoRiversamento, String identificativoFlusso, byte[] xml) {
		
		logger.info("RESULT DA WS/SFTP" + ctFlussoRiversamento.getIdentificativoFlusso());

		// i controlli di business sono stati superati....
		CasellarioInfo casell = new CasellarioInfo();
		// e allora inserisco
		casell.setFlussoCbi(xml);
		casell.setTipoFlusso("FR");

		// casell.setIUR(IUR);
		casell.setIUR(ctFlussoRiversamento.getIdentificativoUnivocoRegolamento());

		// casell.setMittenteVero(codiceIdentificativoUnivocoMittente)
		// casell.setMittenteVero(result.getIstitutoMittente().getIdentificativoUnivocoMittente().getCodiceIdentificativoUnivoco());
		casell.setMittenteVero(calcolaIstitutoMittente(identificativoFlusso));
		
		// casell.setRicevemteVero(codiceIdentificativoUnivocoRicevente);
		casell.setRicevemteVero(ctFlussoRiversamento.getIstitutoRicevente().getIdentificativoUnivocoRicevente().getCodiceIdentificativoUnivoco());
		casell.setNumeroRecord(ctFlussoRiversamento.getNumeroTotalePagamenti().intValue());
		casell.setDimensione(xml.length);
		casell.setNomeSupporto(identificativoFlusso);
		casell.setFlagElaborazione(EnumFlagElaborazione.DA_ELABORARE.getChiave());
		
		String idFlusso="FR"+ctFlussoRiversamento.getDataOraFlusso().getYear()+casell.getMittenteVero()+casell.getNomeSupporto();
		
		casell.setIdSupporto(idFlusso);
		
		casell.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		casell.setOpInserimento("RichiestaDettaglioNDP");
		//persist
		manager.persist(casell);
		logger.info(" Dettaglio Flusso NDP, Casellario inserito, id == " + casell.getId());
		
		//aggiorno anche i flussi... come elaborati
		//aggiornamento stato tabella pagamenti
		Query queryUpdatePaga = manager.createQuery ("Update FlussiNdp ndp " +
				" set ndp.stato = :stato  " +
				" , tsAggiornamento = :tsAggiornamento " +
				" , opAggiornamento = :opAggiornamento " +
				" WHERE ndp.identificativoFlusso = :identificativoFlusso");
		queryUpdatePaga.setParameter("stato", EnumFlagElaborazione.ELABORATO.getDescrizione());
		queryUpdatePaga.setParameter("tsAggiornamento", new Timestamp(System.currentTimeMillis()));
		queryUpdatePaga.setParameter("opAggiornamento", "RichiestaDettaglioNDP");
		queryUpdatePaga.setParameter("identificativoFlusso", identificativoFlusso);
		int pagaUpd = queryUpdatePaga.executeUpdate();
		
		logger.info(" FlussoNdp Elaborato, id == " + identificativoFlusso);
		
	}

	
	/*
	 * 7.2 Standardizzazione del dato identificativoFlusso Al fine di rendere
	 * omogenea la modalita' di composizione del dato identificativoFlusso
	 * presente nella causale standardizzata del SEPA Credit Transfer (cfr.
	 * capitolo 6) ed anche nel flusso di rendicontazione di cui al capitolo 0
	 * (cfr. Tabella 1 - Flusso per la rendicontazione - Schema dati), sara'
	 * adottata la seguente struttura
	 * <dataRegolamento><istitutoMittente>"-"<flusso>
	 *
	 * ATTENZIONE: <flusso> e' una stringa alfanumerica che, insieme alle
	 * informazioni sopra indicate, consente di individuare univocamente il
	 * flusso stesso. I caratteri ammessi all'interno della stringa sono: numeri
	 * da 0 a 9, lettere dell'alfabeto latino maiuscole e minuscole ed i
	 * seguenti caratteri.
	 * "-" meno
	 * "_" underscore
	 * Esempi: 2015-07-15xxxxxxxx-0000000001
	 *         2015-07-15xxxxxxxx-hh_mm_ss_nnn
	 *         2015-07-15xxxxxxxx-hh-mm-ss-nnn
	 */
	private String calcolaIstitutoMittente(String identificativoFlusso) {
		String istitutoMittente;
		if(identificativoFlusso == null) {
			istitutoMittente = null;
		} else if(identificativoFlusso.length() <= 10) {
			istitutoMittente = identificativoFlusso;
		} else {
			String idSenzaData = identificativoFlusso.substring(10);
			istitutoMittente = idSenzaData.substring(0, idSenzaData.indexOf('-'));
		}
		logger.info("calcolaIstitutoMittente - identificativoFlusso: " + identificativoFlusso);
		logger.info("calcolaIstitutoMittente - istitutoMittente....: " + istitutoMittente);
		return istitutoMittente;
	}

}
