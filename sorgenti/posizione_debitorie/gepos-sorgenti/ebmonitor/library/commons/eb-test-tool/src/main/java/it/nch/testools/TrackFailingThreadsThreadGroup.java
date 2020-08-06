/**
 * Created on 10/feb/2009
 */
package it.nch.testools;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @author gdefacci
 */
public class TrackFailingThreadsThreadGroup extends ThreadGroup {
	
	private Map/*<String, Throwable>*/failingThreads = Collections.synchronizedMap( new HashMap() );

	public TrackFailingThreadsThreadGroup(String name) {
		super(name);
	}

	public TrackFailingThreadsThreadGroup(ThreadGroup parent, String name) {
		super(parent, name);
	}

	public void uncaughtException(Thread t, Throwable e) {
		failingThreads.put(t.toString(), e);
	}
	
	public boolean isOk() {
		return failingThreads.isEmpty();
	}
	
	public Map failingThreads() {
		return failingThreads;
	}
	
}
