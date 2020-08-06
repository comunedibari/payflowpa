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

import it.tasgroup.idp.pojo.MonitoringData;

import javax.management.MXBean;

@MXBean
public interface ObjectCommandExecutor {
	public MonitoringData executeApplicationTransaction(Object data);
	
	public String executeHtml(String msg, String sendId, String sendSys);
	
}
