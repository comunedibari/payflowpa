/**
 * Created on 12/feb/2009
 */
package it.nch.testools;

import java.util.ArrayList;
import java.util.List;

public class ThreadGroupAndThreadsListPair {
	private final TrackFailingThreadsThreadGroup group;
	private final List/*<Thread>*/ threads;
	
	public ThreadGroupAndThreadsListPair(TrackFailingThreadsThreadGroup group) {
		this(group, new ArrayList());
	}
	public ThreadGroupAndThreadsListPair(TrackFailingThreadsThreadGroup group, List threads) {
		this.group = group;
		this.threads = threads;
	}
	public TrackFailingThreadsThreadGroup getGroup() {
		return group;
	}
	public List/*<Thread>*/ getThreads() {
		return threads;
	}
}