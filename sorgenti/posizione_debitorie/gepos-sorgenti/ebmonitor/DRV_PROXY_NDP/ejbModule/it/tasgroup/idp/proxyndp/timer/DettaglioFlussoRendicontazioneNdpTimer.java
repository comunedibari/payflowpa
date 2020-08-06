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
package it.tasgroup.idp.proxyndp.timer;

import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.monitoring.TimerInterceptor;
import it.tasgroup.idp.timer.AbstractTimer;
import it.tasgroup.idp.timer.ITimerLocal;
import it.tasgroup.idp.timer.ITimerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class DettaglioFlussoRendicontazioneNdpTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	@EJB(beanName = "RichiestaDettaglioRendicontazioneNdp")
	private CommandExecutorLocal richiestaDettaglioRendicontazioneProxy;

	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout
	// @Interceptors(TimerInterceptor.class)
	public void handleTimeout(Timer timer) {
		if (!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;
		logger.info(this.getClass().getSimpleName() + ", calling richiestaDettaglioRendicontazioneProxy");
		richiestaDettaglioRendicontazioneProxy.executeApplicationTransaction();
	}

}
