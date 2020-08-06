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

import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.TimerData;

public interface TimerCommandExecutor {

	public MonitoringData executeApplicationTransaction(TimerData timer);

	public MonitoringData executeApplicationTransaction(String data);

	public MonitoringData executeApplicationTransaction();

	public boolean isInitialized();

	public String executeHtml() throws Exception;

	public String executeHtml(String timerName, String intervallo, String periodo, String operazione);

}
