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

import it.tasgroup.idp.bean.AbstractExecutorRemote;
import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.bean.ExecutorRemote;
import it.tasgroup.idp.bean.SpazioCommandExecutor;
import it.tasgroup.idp.pojo.StoricoData;
import it.tasgroup.idp.proxyndp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.enums.EnumFlagElaborazione;
import it.tasgroup.iris2.pagamenti.CasellarioInfo;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ElaboraDettaglioRendicontazioneNdp extends AbstractExecutorRemote implements ExecutorLocal, ExecutorRemote {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;	

	private final Log logger = LogFactory.getLog(this.getClass());
	
 	@EJB(beanName = "EsitiNdpBlobInputManager")
 	private ExecutorLocal esitiNdpBlobInputManagerProxy;

	public ElaboraDettaglioRendicontazioneNdp(){
		
	} 
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public StoricoData executeApplicationTransaction() {
		//monitoring data
		StoricoData monData = new StoricoData();
		List<CasellarioInfo> linsCasell = null;
				
	        //recupero il casellario di tipo FR			
			Query listaCasellarioInfo = manager.createQuery ("" +
					"SELECT casellarioInfo FROM CasellarioInfo casellarioInfo " +
					" WHERE casellarioInfo.flagElaborazione = :stato "
					+ " AND casellarioInfo.tipoFlusso = :tipoFlusso");
			listaCasellarioInfo.setParameter("stato", EnumFlagElaborazione.DA_ELABORARE.getChiave() );
			listaCasellarioInfo.setParameter("tipoFlusso", "FR" );
			linsCasell =  (List) listaCasellarioInfo.getResultList();
			Iterator<CasellarioInfo> iterCasell = linsCasell.iterator();
			 
			//ciclo sulla lista dei flussi
			while (iterCasell.hasNext()) {
				CasellarioInfo casellarioInfo = (CasellarioInfo) iterCasell
						.next();
				logger.info(" CasellarioInfo, NomeSupporto == " + casellarioInfo.getNomeSupporto() );
				logger.info(" CasellarioInfo, Mittente / Ricevente == " + casellarioInfo.getMittenteVero() + "/" + casellarioInfo.getRicevemteVero() );

				try {					
					//delego al bean dedicato
					esitiNdpBlobInputManagerProxy.executeApplicationTransaction(casellarioInfo);
					
				} catch (Exception e) {
					logger.error(this.getClass().getSimpleName() + " ERROR ELABORAZIONE FLUSSO NDP = " + e.getMessage());
				}
				finally {
				}
								
			}
		
		//set num
		monData.setNumRecord(linsCasell.size());
		
		return monData;
	}

	@Override
	public StoricoData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeHtml() throws Exception  {
		
		StoricoData data = this.executeApplicationTransaction();
		String table = "";
	
		logger.info(this.getClass().getSimpleName() + "executeHtml, found flussi NDP (size=" + data.getNumRecord() );	
		table = "<br><br>";
		table += "<b>Numero Flussi NDP elaborati</b>";
		table += "<br>";			
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
		table+="<TD>NUMERO Dettagli Rendicontazione </TD>";							
		table+="</TR>";				
		
		table+="<TR>";
		table+="<TD>";
		table+=data.getNumRecord();			
		table+="</TD>";					
		table+="</TR>";		
		
		table+="</TABLE>";

		return table;
	}



}
