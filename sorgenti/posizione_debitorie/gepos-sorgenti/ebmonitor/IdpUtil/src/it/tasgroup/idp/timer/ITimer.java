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

import javax.ejb.Timer;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public interface ITimer {

	public String checkStatus();

	public void startTimer();

	public void stopTimer();

	public void startTimer(int startFrom, int interval);

	public void scheduleTimer(Date expiration, Serializable info);

	public void scheduleTimer(Date initialExpiration, long intervalDuration, Serializable info);

	public void scheduleTimer(long initialDuration, long intervalDuration, Serializable info);

	public void cancelTimer(Serializable info);


	Collection<Timer> getTimers();
}
