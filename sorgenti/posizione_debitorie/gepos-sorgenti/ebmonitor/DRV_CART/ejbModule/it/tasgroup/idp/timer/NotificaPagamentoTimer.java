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

import it.tasgroup.idp.bean.NotificationCommandExecutor;
import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.monitoring.TimerInterceptor;
import it.tasgroup.idp.util.StatoEnum;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class NotificaPagamentoTimer extends AbstractTimer  implements ITimerLocal, ITimerRemote {

	@EJB(beanName = "NotificaPagamento")
	private NotificationCommandExecutor NotificaPagamentoProxy;

	@EJB(beanName = "NotificaSenderBean")
	private NotificationCommandExecutor NotificaSenderProxy;
	
	@EJB(beanName = "EstrazionePagamentiNotifica")
	private NotificationCommandExecutor EstrazioneNotificheProxy;	

	private final Log logger = LogFactory.getLog(this.getClass());


	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout
	// @Interceptors(TimerInterceptor.class)
	public void handleTimeout(Timer timer) {
		if(!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;

		logger.info(this.getClass().getSimpleName() + ", calling EstrazioneNotificheProxy" + StatoEnum.NOTIFICHE_ESEGUITO);
		//chiamata in sequenza delle tre notifiche
		EstrazioneNotificheProxy.executeApplicationTransaction(StatoEnum.NOTIFICHE_ESEGUITO);
		logger.info(this.getClass().getSimpleName() + ", calling EstrazioneNotificheProxy" + StatoEnum.NOTIFICHE_REGOLATO);
		EstrazioneNotificheProxy.executeApplicationTransaction(StatoEnum.NOTIFICHE_REGOLATO);
		logger.info(this.getClass().getSimpleName() + ", calling EstrazioneNotificheProxy" + StatoEnum.NOTIFICHE_REGOLATO_FLAG2);
		EstrazioneNotificheProxy.executeApplicationTransaction(StatoEnum.NOTIFICHE_REGOLATO_FLAG2);
		logger.info(this.getClass().getSimpleName() + ", calling EstrazioneNotificheProxy" + StatoEnum.NOTIFICHE_NOTIFICATO);
		EstrazioneNotificheProxy.executeApplicationTransaction(StatoEnum.NOTIFICHE_NOTIFICATO);
		
		logger.info(this.getClass().getSimpleName() + ", calling NotificaPagamentoProxy" + StatoEnum.NOTIFICHE_CREATO_PER_ESEGUITO);
		NotificaPagamentoProxy.executeApplicationTransaction(StatoEnum.NOTIFICHE_CREATO_PER_ESEGUITO);
		logger.info(this.getClass().getSimpleName() + ", calling NotificaPagamentoProxy" + StatoEnum.NOTIFICHE_CREATO_PER_REGOLATO);
		NotificaPagamentoProxy.executeApplicationTransaction(StatoEnum.NOTIFICHE_CREATO_PER_REGOLATO);
		logger.info(this.getClass().getSimpleName() + ", calling NotificaPagamentoProxy" + StatoEnum.NOTIFICHE_CREATO_PER_INCASSO);
		NotificaPagamentoProxy.executeApplicationTransaction(StatoEnum.NOTIFICHE_CREATO_PER_INCASSO);

		logger.info(this.getClass().getSimpleName() + ", calling NotificaSenderProxy");
		NotificaSenderProxy.executeApplicationTransaction();
	}




}
