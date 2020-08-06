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

import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.gateway.FlussiNdp;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.proxyndp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
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
@Remote(CommandExecutor.class)
public class RichiestaDettaglioRendicontazioneNdp implements CommandExecutor, CommandExecutorLocal {
	

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;	
	
 	@EJB(beanName = "DettaglioFlussoNdpManager")
 	private ExecutorLocal dettaglioFlussoNdpManagerProxy;
	
	private final Log logger = LogFactory.getLog(this.getClass());

	public RichiestaDettaglioRendicontazioneNdp(){
		
	} 
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public MonitoringData executeApplicationTransaction() {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		try {							

			Query listaFlussiNdp = manager.createQuery ("" +
					"SELECT flussiNdp FROM FlussiNdp flussiNdp " +
					" WHERE flussiNdp.stato = :stato ");
			
			listaFlussiNdp.setParameter("stato", "DA ELABORARE" );
			List<FlussiNdp> linsNdp =  (List) listaFlussiNdp.getResultList();

			// fase iniziale di "sistemazione" di un'eventuale cartella di ingresso file/flussi FR via SFTP
			dettaglioFlussoNdpManagerProxy.executeApplicationTransaction((FlussiNdp)null); // e' il null che distingue questa chiamata da quella "normale" di ricezione file/flusso FR

			if (linsNdp==null || linsNdp.size()==0) {
				logger.info(" Non ci sono flussi da elaborare ");	
			} else {
				for (FlussiNdp flussoNdp:linsNdp) {
					logger.info(" FlussiNdp, idFlussoNdp== " + flussoNdp.getIdentificativoFlusso() + " IdPsp== " + flussoNdp.getIdentificativoPsp());
					String identificativoFlusso = flussoNdp.getIdentificativoFlusso();
					try {
						dettaglioFlussoNdpManagerProxy.executeApplicationTransaction(flussoNdp);
					} catch (Exception e) {
						logger.error("Errore nell'elaborazione del flusso NDP IDentificativo "+identificativoFlusso);
					}
				}
			}
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + " ERRORE recupero dettaglio flussi NDP = " + e.getMessage(),e);
		}
		finally {
		
		}
		
		return monData;
	}




	@Override
	public String executeHtml() throws Exception  {
		
		MonitoringData data = this.executeApplicationTransaction();
		String table = "";
	
		logger.info(this.getClass().getSimpleName() + "executeHtml, found cataloghiPSP (size=" + data.getNumRecord() );	
		table = "<br><br>";
		table += "<b>Numero Dettaglio Rendicontazione Ndp elaborati</b>";
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




	/**
	 * 
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
		return manager;
		//quando siamo su TEST JUNIT ci vuole questa riga
//			return manager;
	}

	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}


		
	

}
