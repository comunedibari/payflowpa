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
package it.tasgroup.idp.timer;

import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.ReinoltroEsiti;
import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.util.IrisProperties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class ReinoltroEsitiTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	@EJB(beanName = "ReinoltroEsiti")
	private CommandExecutorLocal ReinoltroEsitiProxy;

	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout
	// @Interceptors(TimerInterceptor.class)
	public void handleTimeout(Timer timer) {
		if (!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;

		logger.info(this.getClass().getSimpleName() + ", calling ReinoltroEsitiProxy");
		
		// Vecchia gestione (no SmartSIL)
		if("true".equalsIgnoreCase(IrisProperties.getProperty("reinoltro.esiti.old_errori_idp.enabled"))) {
			ReinoltroEsitiProxy.executeApplicationTransaction();
		}	
		
		// nuova gestione (SmartSIL)
		if("true".equalsIgnoreCase(IrisProperties.getProperty("reinoltro.esiti.in_spedizione.enabled"))) {
			ReinoltroEsitiProxy.executeApplicationTransaction(ReinoltroEsiti.REINOLTRO_ESITI_IN_SPEDIZIONE);  //Escamotage per rispettare il pattern... ma effettuare ogni  recupero in transazione separata
		}
		
		// nuova gestione (SmartSIL)
		if("true".equalsIgnoreCase(IrisProperties.getProperty("reinoltro.esiti.non_inviati.enabled"))) {
			ReinoltroEsitiProxy.executeApplicationTransaction(ReinoltroEsiti.REINOLTRO_ESITI_NON_INVIATI); //Escamotage per rispettare il pattern... ma effettuare ogni  recupero in transazione separata
		}	

	}
}
