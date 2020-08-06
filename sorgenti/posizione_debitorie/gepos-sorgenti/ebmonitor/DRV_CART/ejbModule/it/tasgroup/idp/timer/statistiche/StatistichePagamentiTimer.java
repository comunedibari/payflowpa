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
package it.tasgroup.idp.timer.statistiche;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.ReportStatistiche;
import it.tasgroup.idp.monitoring.TimerActivationStatControllerLocal;
import it.tasgroup.idp.timer.AbstractTimer;
import it.tasgroup.idp.timer.ITimerLocal;
import it.tasgroup.idp.timer.ITimerRemote;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.pagamenti.LogElaborazioneBatch;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StatistichePagamentiTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	private final Log logger = LogFactory.getLog(StatistichePagamentiTimer.class);
	protected static String ESITO_OK = "OK";
	protected static String ESITO_KO = "KO";
	
	protected static String ELABORAZIONE_PAGAMENTI = "Statistiche Pagamenti";
	protected static String ELABORAZIONE_POSIZIONI = "Statistiche Posizioni";
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	@EJB(beanName = "ReportStatistiche")
	private ReportStatistiche reportStatistiche;

	@EJB(beanName = "TimerActivationStatController")
	private TimerActivationStatControllerLocal activationController;

	@Timeout
	@Asynchronous
	public void handleTimeout(Timer timer) {
		
		if (!activationController.isTimerActive(this.getClass().getSimpleName())) {
			logger.info("StatistichePagamentiTimer - Nessuna esecuzione");
			return;
		}
		logger.info("StatistichePagamentiTimer - START");
		LogElaborazioneBatch logElab = new LogElaborazioneBatch(ELABORAZIONE_PAGAMENTI);
		try {
			logger.info("Inizio elaborazione report statistiche pagamenti");
			List<Object[]> report = reportStatistiche.buildReportPagamenti();	
			logger.info("Fine elaborazione report statistiche [Risultato select : "+ report.size() + "]");
			
			logger.info("Inizio popolamento dati tabella statistiche pagamenti");
			int tot = reportStatistiche.insertUpdateStatistichePagamentiMese(report);
			logger.info("Fine popolamento dati tabella statistiche [Risultato Insert/Update: "+ tot + "]");
			
			logger.info("Elaborazione Statistiche Pagamenti OK");
			logElab.setEsito(ESITO_OK);
		} catch (Throwable t) {
			logElab.setEsito(ESITO_KO + " " + (t.getMessage().length() > 250 ? t.getMessage().substring(0, 250) : t.getMessage()));
			logger.error("Elaborazione Statistiche Pagamenti KO", t);
		} finally {
			logElab.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			try {
				logger.info("Inizio aggiornamento tabella di log elaborazione batch");
				reportStatistiche.saveReportElaborazione(logElab);
				logger.info("Fine aggiornamento tabella di log elaborazione batch");
			} catch (Throwable t) {
				logger.error("Errore durante l'aggiornamento della tabella log elaborazione batch", t);
			}
		}
		logger.info("StatistichePagamentiTimer - END");
	}
	
	
}
