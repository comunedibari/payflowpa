/**
 * 08/giu/2009
 */
package it.nch.xml2sql.threads;

import it.nch.eb.xsqlcmd.threads.ConsumerSpawner;
import it.nch.eb.xsqlcmd.threads.Coordinator;
import it.nch.eb.xsqlcmd.threads.StackUser;

import java.util.Random;
import java.util.Stack;

/**
 * @author gdefacci
 */
public class ThreadTest {
	
	private static final class Consumer implements StackUser {

		public void apply(Stack state) {
			System.out.println("starting consumer");
			Integer counter;
			try {
				counter = (Integer) state.pop();
			} catch (Exception e) {
//				counter = new Integer(0);
				throw new RuntimeException(e);
			}
			for (int i = 0; i < counter.intValue(); i++) {
				System.out.println(i);
			}
		}
	}
	
	private static class Producer implements StackUser {
		private int count;

		public Producer(int count) {
			this.count = count;
		}

		/* @Override */
		public void apply(final Stack state) {
			System.out.println("starting producer");
			Random random = new Random();
			for (int i=0; i < this.count; i++) {
				int nextPush = Math.abs( random.nextInt(200) );
				state.push(new Integer(nextPush));
			}
		}

	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("press a key");
		System.in.read();
		
		ConsumerSpawner consumer = new ConsumerSpawner(10, new Consumer() );
		StackUser producer = new Producer(10000);
		Coordinator coordinator = new Coordinator(consumer);
		
		coordinator.runWithSharedState(producer);
		
		System.out.println("finished");
	}

}
