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

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.idp.gateway.FlussiNdp;
import it.tasgroup.idp.util.ServiceLocalMapper;


@Stateless

@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersistFlussiNdpBean implements IPersistFlussiNdp  {

	
	@PersistenceContext(unitName = ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;

	public PersistFlussiNdpBean() {

	}

	/* (non-Javadoc)
	 * @see it.tasgroup.idp.proxyndp.bean.IPersistFlussiNdp#insertFlussoNdp(it.tasgroup.idp.gateway.FlussiNdp)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void insertFlussoNdp(FlussiNdp flusso) {
		manager.persist(flusso);
	}	
}
