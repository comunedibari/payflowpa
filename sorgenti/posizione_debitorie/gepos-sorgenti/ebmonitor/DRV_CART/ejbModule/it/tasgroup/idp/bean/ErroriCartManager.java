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
package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.nio.charset.Charset;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
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
public class ErroriCartManager implements CommandExecutor, CommandExecutorLocal {	
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	public EntityManager manager;
	@Resource
	public SessionContext ctx;		
	@Resource 
	private EJBContext ejbCtx;	
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(String entity) {
		//monitoring data
		MonitoringData monData = new MonitoringData();			   
		
		ErroriCart cart = buildObject(entity);
		logger.info(" BEFORE FIRST PERSIST");
		try {
			
			manager.persist(cart);
			
		} catch (Exception e1) {
			logger.info(" ERROR Inside ErroriCartManager = " + e1.getMessage());
			throw new RuntimeException();
		}
		
		return monData;
	}


	/**
	 * 
	 * @param entity
	 * @return
	 */
	private ErroriCart buildObject(String entity) {
		ErroriCart cart = new ErroriCart();
		
		//dimensione della tabella
		Query query = manager.createQuery ("SELECT COUNT(erroriCart.statoErrore) FROM ErroriCart erroriCart");
		Long num = (Long) query.getResultList().get(0);
		logger.info(" NEXT NUMBER = " + num);
		
		//xml da inserire
		// why????? che senso ha???
//		String message = (String)entity;
		
		cart.setIdErroreCart(Long.toString(num));
		if (entity != null) {
			cart.setMessaggioXml(entity.getBytes(Charset.forName("US-ASCII")));						
		}
		cart.setStatoErrore(StatoEnum.DA_ELABORARE);
		cart.setTipoMessaggio("NON DEFINITO");
		cart.setOpInserimento("IDP");
		cart.setTsInserimento(new Timestamp(System.currentTimeMillis()));		
		return cart;
	}

	@Override
	public MonitoringData executeApplicationTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeHtml() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
