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

import gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazione;
import gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta;
import gov.telematici.pagamenti.ws.TipoElencoFlussiRendicontazione;
import gov.telematici.pagamenti.ws.TipoIdRendicontazione;
import it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirpt.PagamentiTelematiciRPT;
import it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirpt.PagamentiTelematiciRPTservice;
import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.gateway.FlussiNdp;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.proxyndp.exception.NDPComunicationException;
import it.tasgroup.idp.proxyndp.gde.GiornaleEventiExtDTO;
import it.tasgroup.idp.proxyndp.gde.IGiornaleEventiUtils;
import it.tasgroup.idp.proxyndp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.proxyndp.utils.NDPConstants;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@Remote(CommandExecutor.class)

@TransactionManagement(TransactionManagementType.CONTAINER)
public class RichiestaElencoRendicontazioniNdp implements CommandExecutor, CommandExecutorLocal {

	/*** Resources ***/
	@Resource
	private EJBContext ctx;

	@PersistenceContext(unitName = ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;


	@EJB(beanName = "GiornaleEventiUtilsBean")
	private IGiornaleEventiUtils giornaleEventiUtilsBean;
	
	@EJB(beanName = "PersistFlussiNdpBean")
	private IPersistFlussiNdp persistFlussiNdp;
	
	private final Log logger = LogFactory.getLog(this.getClass());

	public RichiestaElencoRendicontazioniNdp() {

	}

	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public MonitoringData executeApplicationTransaction() {
		// monitoring data
		MonitoringData monData = new MonitoringData();
		monData.setNumRecord(0);

		GiornaleEventiExtDTO go = null;
		try {

			NDPConstants constants = new NDPConstants();

			PagamentiTelematiciRPTservice ndp = new PagamentiTelematiciRPTservice();
			PagamentiTelematiciRPT port = ndp.getPagamentiTelematiciRPTPort();

			// overriding url
			BindingProvider bindingProvider = (BindingProvider) port;
			if (!constants.isUsaProxyNdp()) {
				if(constants.getHttpBasicUser() != null && !constants.getHttpBasicUser().isEmpty()) {
					bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, constants.getHttpBasicUser());
					bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, constants.getHttpBasicPassword());
					logger.info(" ========== credenziali accesso  username =  " + constants.getHttpBasicUser() + "password = " + constants.getHttpBasicPassword() );
				}
				bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, constants.getNdpWsUrl()+"nodoChiediElencoFlussiRendicontazione");
				logger.info(" ========== URL =  " + constants.getNdpWsUrl()+"nodoChiediElencoFlussiRendicontazione");
			} else {
				bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,constants.getNdpWsUrl());	 
				logger.info(" ========== URL =  " + constants.getNdpWsUrl());
			}

			boolean requestByIdDominio = constants.isRequestByIdDominioElencoFlussiRend();
			List<String> allIdDominio = null;
			if (requestByIdDominio){
			   logger.info(" ========== recupero lista idDominio degli enti attivi ======");
			   Query query = manager.createNamedQuery("findAllIdDominio");
			   allIdDominio = (List<String>) query.getResultList();
			} else {
				logger.info(" ========== effettuo una sola interrogazione con idDominio = null ======");
				allIdDominio = new ArrayList<String>();
				allIdDominio.add(null);
			}
			int totResultSize = 0;
			for (String idDominio : allIdDominio) {


				/*--- Chiamata al WS del proxy NdP ---*/
				logger.info(" calling Webservice NDP ");
				logger.info(" ========== idDominio = "+idDominio+" ======");

				NodoChiediElencoFlussiRendicontazione inputParams = new NodoChiediElencoFlussiRendicontazione();
				// Chiedo flussi per tutti gli enti di cui RT e' intermediaria
				inputParams.setIdentificativoIntermediarioPA(constants.getIntermediarioPA());
				// Chiedo flussi per tutti i psp
				inputParams.setIdentificativoPSP(null);
				inputParams.setIdentificativoStazioneIntermediarioPA(constants.getCodiceStazionePA());
				inputParams.setPassword(constants.getPasswordNdp());
				inputParams.setIdentificativoDominio(idDominio);

				try {
					logger.info(" ========== Salvataggio su giornale degli eventi (richiesta) ======");
					go = giornaleEventiUtilsBean.saveGDE(inputParams, null);
					logger.info(" ========== Salvataggio su giornale degli eventi effettuato ======");

					NodoChiediElencoFlussiRendicontazioneRisposta risposta = port.nodoChiediElencoFlussiRendicontazione(inputParams);

					logger.info(" ========== Salvataggio su giornale degli eventi (risposta) ======");
					giornaleEventiUtilsBean.saveGDE(risposta, go); 
					logger.info(" ========== Salvataggio su giornale degli eventi effettuato ======");

					boolean goOn = true;
					List<TipoIdRendicontazione> result = null;
					try {
						result = readResult(risposta);
						totResultSize = result.size();
					} catch (Exception e) {
						logger.error("=== errore elaborazione flusso psp ");
						goOn = false;
					}

					if (goOn) {
						logger.info("Lista flussi disponibili contiene: "
								+ result.size() + " flussi.");

						for (TipoIdRendicontazione tipoIdRendicontazione : result) {

							String idFlusso = tipoIdRendicontazione.getIdentificativoFlusso();
							logger.debug(" FlussoNdp found !! " + idFlusso);

							// Controllo per eccesso di scrupolo, nella risposta la
							// dataOraFlusso è obbligatoria.
							Long anno = 0L;
							if (tipoIdRendicontazione.getDataOraFlusso() != null) {
								anno = Long.valueOf(tipoIdRendicontazione.getDataOraFlusso().getYear());
							}

							Query queryIdFlusso = manager.createNamedQuery("FlussiNdp.findExistingFlussoNdp1.7");
							queryIdFlusso.setParameter("identificativoFlusso", idFlusso);

							queryIdFlusso.setParameter("anno", anno);
							List<FlussiNdp> listaFlussiNdp = (List<FlussiNdp>) queryIdFlusso.getResultList();
							logger.debug(" FLUSSI GIA' RICEVUTO ? (0=false,1=true) " + listaFlussiNdp.size());

							if (listaFlussiNdp.isEmpty()) {
								// insert into flussindp
								FlussiNdp ndpFlux = new FlussiNdp();
								ndpFlux.setIdentificativoFlusso(idFlusso);
								ndpFlux.setDataOraFlusso(new Timestamp(tipoIdRendicontazione.getDataOraFlusso().toGregorianCalendar().getTimeInMillis()));
								ndpFlux.setIdentificativoPsp("n/a");
								ndpFlux.setStato("DA ELABORARE");
								ndpFlux.setOpInserimento("Richiesta Elenco Flussi Ndp");
								ndpFlux.setTsInserimento(new Timestamp(System.currentTimeMillis()));
								ndpFlux.setAnno(anno);
								ndpFlux.setIdentificativoDominio(idDominio);
								//manager.persist(ndpFlux);
								persistFlussiNdp.insertFlussoNdp(ndpFlux);
								logger.info(" Flusso Ndp Inserito !! " + idFlusso);
							} else {
								logger.info(" Flusso Ndp NON Inserito perche' gia' presente !! " + idFlusso);
							}

						}

						logger.info(" Webservice NDP called !! ");
					}
				} catch (Exception exc) {
	            	logger.error(this.getClass().getSimpleName() + " ERROR RICHIESTA ElencoFlussiRendicontazione idDominio = "+idDominio+" exception = " + exc.getMessage());
	            }
				monData.setNumRecord(monData.getNumRecord() + totResultSize);
			}
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + " ERROR RICHIESTA ElencoFlussiRendicontazione  = " + e.getMessage());
			//ctx.setRollbackOnly();
		}
		return monData;
	}
	/**
	 * 
	 * @param fault
	 * @param elencoFlussiRendicontazine
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 * @throws NDPComunicationException
	 */
	private List<TipoIdRendicontazione> readResult(NodoChiediElencoFlussiRendicontazioneRisposta risposta)
			throws IOException, JAXBException, NDPComunicationException {

		if (risposta.getFault() != null) {
			logger.error("readResult() Fault [id =" + risposta.getFault().getId()+ ", code = " +risposta.getFault().getFaultCode()+ ", description = "+ risposta.getFault().getDescription()+"]");
			throw new NDPComunicationException(risposta.getFault());
		}

		TipoElencoFlussiRendicontazione elencoFlussiRendicontazione = risposta.getElencoFlussiRendicontazione();
		logger.debug("Response (tot rct) = " + elencoFlussiRendicontazione.getTotRestituiti());
		List<TipoIdRendicontazione> listRct = elencoFlussiRendicontazione.getIdRendicontazione();
		return listRct;
	}

	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		return null;
	}

	@Override
	public String executeHtml() throws Exception {

		MonitoringData data = this.executeApplicationTransaction();
		String table = "";

		logger.info(this.getClass().getSimpleName() + "executeHtml, found cataloghiPSP (size=" + data.getNumRecord());
		table = "<br><br>";
		table += "<b>Numero Rendicontazioni NDP trovate</b>";
		table += "<br>";
		table += "<TABLE border=\"\1\">";
		table += "<TR>";
		table += "<TD>NUMERO RendicontazioniNDP</TD>";
		table += "</TR>";

		table += "<TR>";
		table += "<TD>";
		table += data.getNumRecord();
		table += "</TD>";
		table += "</TR>";

		table += "</TABLE>";

		return table;
	}

}
