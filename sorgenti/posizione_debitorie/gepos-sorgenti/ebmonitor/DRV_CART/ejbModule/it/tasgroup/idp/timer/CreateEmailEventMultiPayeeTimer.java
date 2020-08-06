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

import java.util.List;

import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.monitoring.TimerInterceptor;
import it.tasgroup.idp.util.ServiceLocalMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class CreateEmailEventMultiPayeeTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;
	
	@EJB(beanName = "CreateEmailEventMultiPayeeBean")
	private CommandExecutorLocal createEmailEventMultiPayeeProxy;

	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout	
	public void handleTimeout(Timer timer) {
		if(!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;
		logger.info(this.getClass().getSimpleName() + ", handleTimeout() BEGIN");
		
		List<String> idGruppoList = getListDistinteMultibeneficiario();
		
		for (String idGruppo: idGruppoList) {
			try {
				logger.info(this.getClass().getSimpleName() + ", elaborazione idGruppo = " + idGruppo);
			    createEmailEventMultiPayeeProxy.executeApplicationTransaction(idGruppo);
			    logger.info(this.getClass().getSimpleName() + ", elaborazione idGruppo OK ");
			} catch (Throwable t) {
				logger.error(this.getClass().getSimpleName() + ", elaborazione idGruppo KO ");
				logger.error(t);
			}
		}		
		
		logger.info(this.getClass().getSimpleName() + ", handleTimeout() END");
	}	
    private List<String> getListDistinteMultibeneficiario() {
    	Query q =manager.createNamedQuery("getIdGruppoMultiPayeeMailToSend");
    	List<String> l = (List<String>)q.getResultList();
    	return l;
    }
}
