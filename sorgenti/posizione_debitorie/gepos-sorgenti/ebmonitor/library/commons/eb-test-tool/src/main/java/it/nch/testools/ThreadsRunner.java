/**
 * Created on 12/feb/2009
 */
package it.nch.testools;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import junit.framework.Assert;


/**
 * @author gdefacci
 */
public class ThreadsRunner {
	
	public void runThreads(ThreadGroupAndThreadsListPair groupAndThreads) {
		runThreads(groupAndThreads.getGroup(), groupAndThreads.getThreads());	
	}
	
	public void runThreads(TrackFailingThreadsThreadGroup threadGroup, List/*<Thread>*/ threads) {
		runThreads(threadGroup, (Thread[])threads.toArray(new Thread[0]));
	}
	
	public void runThreads(TrackFailingThreadsThreadGroup threadGroup, Thread[] threads) {
		for (int i=0; i<10 && threadGroup.isOk(); i++) {
			for (int j = 0; j < threads.length; j++) {
				Thread thrd = threads[j];
//				if (!thrd.isAlive()) {
					thrd.run();
//				}
			}
			for (int j = 0; j < threads.length; j++) {
				Thread thrd = threads[j];

				if (thrd.isAlive()) {
					try {
						thrd.join();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		if (!threadGroup.isOk()) {
			StringBuffer sb = new StringBuffer("the floowing threads failed \n");
			for (Iterator it = threadGroup.failingThreads().entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				sb.append(entry.getValue() );
				sb.append("\n");
			}
			Assert.fail(sb.toString());
		}
	}
	
	public void runThreads(ThreadGroupAndThreadsListPair[] threadGroups) throws InterruptedException {
		for (int i = 0; i < threadGroups.length; i++) {
			ThreadGroupAndThreadsListPair tgs = threadGroups[i];
			for (Iterator it = tgs.getThreads().iterator(); it.hasNext();) {
				Thread thrd = (Thread) it.next();
				thrd.start();
			}
		}
		
		for (int i = 0; i < threadGroups.length; i++) {
			ThreadGroupAndThreadsListPair tgs = threadGroups[i];
			for (Iterator it = tgs.getThreads().iterator(); it.hasNext();) {
				Thread thrd = (Thread) it.next();
				if (thrd.isAlive()) thrd.join();
			}
		}
		
		for (int i = 0; i < threadGroups.length; i++) {
			ThreadGroupAndThreadsListPair tgs = threadGroups[i];
			if (!tgs.getGroup().isOk()) {
				StringBuffer sb = new StringBuffer("the following threads failed \n");
				for (Iterator it = tgs.getGroup().failingThreads().entrySet().iterator(); it.hasNext();) {
					Entry entry = (Entry) it.next();
					sb.append(entry.getValue() );
					sb.append("\n");
				}
				Assert.fail(i + ")" + sb.toString());				
			}
		}
		
	}


}
