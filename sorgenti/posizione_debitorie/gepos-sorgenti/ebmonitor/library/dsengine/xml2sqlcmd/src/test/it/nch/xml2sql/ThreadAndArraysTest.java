/**
 * 05/giu/2009
 */
package it.nch.xml2sql;

/**
 * @author gdefacci
 */
public class ThreadAndArraysTest {
	
	static class Consumer {
		int arraySize = 50;
		int[] array = new int[arraySize];
		int idx = 0;
		public void collect(int numb) {
			array[idx] = numb;
			idx ++;
			if (idx >= arraySize) {
				Thread thrd = newThread(array);
				thrd.start();
				array = new int[arraySize];
				idx = 0;
			}
		}
		
		private Thread newThread(final int[] array2) {
			return new Thread(new Runnable() {
				
				public void run() {
					for (int i=0; i < array2.length; i++) {
						System.out.println(array2[i]);
					}
				}
				
			}) {

				public synchronized void start() {
					System.out.println("starting thread");
					super.start();
				}
				
			};
		}
	}
	
	public static void main(String[] args) {
		Consumer consumer = new Consumer();
		
		for (int i=0; i<50000; i++) {
			consumer.collect(i);
		}
	}

}
