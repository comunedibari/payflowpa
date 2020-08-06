package it.nch.eb.xsqlcmd.threads;


import java.util.Stack;

/**
 * @author gdefacci
 */
public final class ConsumerSpawner implements StackUser {
	private final int consumersCount;
	private final Stack/*<Integer>*/ terminatedThreads;
	private final StackUser stackConsumer;
	
	public ConsumerSpawner(int maxCount, StackUser stackConsumer) {
		this.consumersCount = maxCount;
		this.terminatedThreads = new Stack();
		for (int i=0;i < consumersCount; i++) {
			terminatedThreads.push(new Integer(i));
		}
		this.stackConsumer = stackConsumer;
	}

	private class NotifyEndThread extends Thread {

		private Integer index;

		public NotifyEndThread(Integer idx, Runnable target) {
			super(target);
			this.index = idx;
		}

		public void run() {
			super.run();
			synchronized (terminatedThreads) {
				terminatedThreads.push(this.index);
				terminatedThreads.notify();
			}
		}
		
	}

	public void apply(final Stack state) {
		Integer idx = null;
		synchronized (terminatedThreads) {
			if (terminatedThreads.isEmpty()) {
				try {
					terminatedThreads.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				idx = (Integer) terminatedThreads.pop();
			} else {
				idx = (Integer) terminatedThreads.pop();
			}
		}
		if (idx!=null) {
			NotifyEndThread thread = new NotifyEndThread(idx, new Runnable() {

				public void run() {
					stackConsumer.apply(state);
				}
				
			});
			thread.start();
		}
	}
}