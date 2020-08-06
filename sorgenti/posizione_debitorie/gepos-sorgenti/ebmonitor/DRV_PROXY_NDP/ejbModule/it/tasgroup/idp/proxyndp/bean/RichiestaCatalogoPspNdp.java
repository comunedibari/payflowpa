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
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.telematici.pagamenti.ws.NodoChiediInformativaPSP;
import gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta;
import it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirpt.PagamentiTelematiciRPT;
import it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematicirpt.PagamentiTelematiciRPTservice;
import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.gateway.CfgGatewayPagamento;
import it.tasgroup.idp.gateway.enums.EnumFornitorePagamento;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.proxyndp.gde.GiornaleEventiExtDTO;
import it.tasgroup.idp.proxyndp.gde.IGiornaleEventiUtils;
import it.tasgroup.idp.proxyndp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.proxyndp.utils.NDPConstants;
import it.tasgroup.idp.util.ServiceLocalMapper;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Remote(CommandExecutor.class)
public class RichiestaCatalogoPspNdp implements CommandExecutor, CommandExecutorLocal {

	@Resource
	private EJBContext ctx;
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;		

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@EJB(beanName = "GiornaleEventiUtilsBean")
	private IGiornaleEventiUtils giornaleEventiUtilsBean;
	
	public RichiestaCatalogoPspNdp(){
	}
	

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction() {

		MonitoringData monData = new MonitoringData();
		
		GiornaleEventiExtDTO go = null;
		try {
			//
			// RECUPERO INFORMATIVE DAL NODO
			//
			logger.info(" calling Webservice NDP ");
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
	            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, constants.getNdpWsUrl()+"nodoChiediInformativaPSP");
	            logger.info(" ========== URL =  " + constants.getNdpWsUrl()+"nodoChiediInformativaPSP");
		    } else {
	            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,constants.getNdpWsUrl());
	            logger.info(" ========== URL =  " + constants.getNdpWsUrl());
		    }

			// recupero il valore dell'idDominio da utilizzare per effettuare la richiesta catalogo PSP
			String idDominio = constants.getIdDominioForCatalogoPsp();
			if ("".equals(idDominio)){
				idDominio=null;
			}
			logger.info(" ========== idDominio = "+ idDominio +" ======");	
			NodoChiediInformativaPSP inputParams = new NodoChiediInformativaPSP();
			//inputParams.setIdentificativoDominio(constants.getIdDominio());
			inputParams.setIdentificativoIntermediarioPA(constants.getIntermediarioPA());
			inputParams.setIdentificativoPSP(null);
			inputParams.setIdentificativoStazioneIntermediarioPA(constants.getCodiceStazionePA());
			inputParams.setPassword(constants.getPasswordNdp());
			inputParams.setIdentificativoDominio(idDominio);
			
			logger.info(" ========== Salvataggio su giornale degli eventi (richiesta) ======");
			go = giornaleEventiUtilsBean.saveGDE(inputParams, null);
			logger.info(" ========== Salvataggio su giornale degli eventi effettuato ======");
			
			try {
				logger.info(" ========== Invocazione con idDominio = "+idDominio+" ======");
				NodoChiediInformativaPSPRisposta risposta = port.nodoChiediInformativaPSP(inputParams);
				
				logger.info(" ========== Salvataggio su giornale degli eventi (risposta) ======");
				giornaleEventiUtilsBean.saveGDE(risposta, go);
				logger.info(" ========== Salvataggio su giornale degli eventi effettuato ======");
			
				CatalogoPspDelegatorFactory factory = new CatalogoPspDelegatorFactory();
			
				ICatalogoPspDelegator delegator = factory.createCatalogoPsp(risposta);

				logger.info(" Webservice NDP called !! - Numero informative restituite: "
						+ delegator.catalogoSize());
				monData.setNumRecord(delegator.catalogoSize());

				//
				// RECUPERO LISTA GATEWAY ATTUALMENTE PRESENTI SU DB
				//
				Query getPSP = manager.createNamedQuery("getCfGatewayPagamentoListByFornitoreGatewayExcludeMyAvvisoPagoPA");
				getPSP.setParameter("idFornitoreGateway", EnumFornitorePagamento.NODO_PAGAMENTI_SPC.getKey());
				List<CfgGatewayPagamento> pspList = getPSP.getResultList();
				logger.info(" executed list Gateway = " + pspList.size());

				//
				// RECUPERO LISTA GATEWAY ATTUALMENTE PRESENTI SU DB
				//
				delegator.aggiornaGatewayPagamento(pspList, factory.getCatalogoPspContent(),manager);
			} catch (Exception exc) {
				logger.error(this.getClass().getSimpleName() + " ERROR RICHIESTA CATALOGO PSP = " + exc.getMessage()+ " idDominio = "+ idDominio, exc);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getSimpleName() + " ERROR RICHIESTA CATALOGO PSP = " + e.getMessage());
		}

		return monData;
	}
	

	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeHtml() throws Exception  {
		
		MonitoringData data = this.executeApplicationTransaction();
		String table = "";

		logger.info(this.getClass().getSimpleName() + "executeHtml, found cataloghiPSP (size=" + data.getNumRecord());
		table = "<br><br>";
		table += "<b>Numero cataloghiPSP trovati</b>";
		table += "<br>";
		table += "<TABLE border=\"\1\">";
		table += "<TR>";
		table += "<TD>NUMERO cataloghiPSP</TD>";
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
