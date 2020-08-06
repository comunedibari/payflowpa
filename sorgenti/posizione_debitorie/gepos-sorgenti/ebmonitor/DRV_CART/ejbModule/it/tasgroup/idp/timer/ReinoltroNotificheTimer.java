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

import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.ReinoltroNotifiche;
import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.monitoring.TimerInterceptor;
import it.tasgroup.idp.util.IrisProperties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class ReinoltroNotificheTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	@EJB(beanName = "ReinoltroNotifiche")
	private CommandExecutorLocal ReinoltroNotificheProxy;

	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout
	// @Interceptors(TimerInterceptor.class)
	public void handleTimeout(Timer timer) {
		if (!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;

		logger.info(this.getClass().getSimpleName() + ", calling ReinoltroNotificheProxy");
		
		// VECCHIA GESTIONE NO SMARTSIL
		if("true".equalsIgnoreCase(IrisProperties.getProperty("reinoltro.notifiche.old_errori_idp.enabled"))) {
			ReinoltroNotificheProxy.executeApplicationTransaction(); 
		}	
		
		// NUOVA GESTIONE SMARTSIL ( e internal)
		if("true".equalsIgnoreCase(IrisProperties.getProperty("reinoltro.notifiche.in_spedizione.enabled"))) {
			ReinoltroNotificheProxy.executeApplicationTransaction(ReinoltroNotifiche.REINOLTRO_NOTIFICHE_IN_SPEDIZIONE);
		}	

		// NUOVA GESTIONE SMARTSIL ( e internal)
		if("true".equalsIgnoreCase(IrisProperties.getProperty("reinoltro.notifiche.non_inviate.enabled"))) {
			ReinoltroNotificheProxy.executeApplicationTransaction(ReinoltroNotifiche.REINOLTRO_NOTIFICHE_NON_INVIATE);
		}	

	}
}
