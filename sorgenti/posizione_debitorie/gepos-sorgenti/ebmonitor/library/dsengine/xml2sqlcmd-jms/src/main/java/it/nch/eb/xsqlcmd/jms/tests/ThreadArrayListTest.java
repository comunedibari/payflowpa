/**
 * 28/ago/2009
 */
package it.nch.eb.xsqlcmd.jms.tests;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author gdefacci
 */
public class ThreadArrayListTest {

	private static final int ITEMS_NUMBER = 55;
	private static final int THREADS_NUMBER = 60;
	private static final UncaughtExceptionHandler SHOW_STACK_TRACE = new UncaughtExceptionHandler() {

		public void uncaughtException(Thread t, Throwable e) {
			e.printStackTrace();
		}
		
	};

	public static void main(String[] args) throws InterruptedException {
		final Random rnd = new Random();
		final List<String> list = Collections.synchronizedList(new ArrayList<String>());
		Thread[] threads = new Thread[THREADS_NUMBER];
		for (int i=0; i < THREADS_NUMBER; i++) {
			threads[i] = new Thread( new Runnable() {

				public void run() {
					for (int j=0; j < ITEMS_NUMBER; j++) {
						list.add("thread n." +  j + " " + rnd.nextLong());
					}
				}
				
			});
			threads[i] .setUncaughtExceptionHandler(SHOW_STACK_TRACE);
		}
		long start = System.currentTimeMillis();
		for (int i=0; i < THREADS_NUMBER; i++) {
			threads[i].start();
		}
		for (int i=0; i < THREADS_NUMBER; i++) {
			if (threads[i].isAlive()) {
				threads[i].join();
			}
		}
		int nullCnt = 0;
		for (String string : list) {
			if (string==null) nullCnt ++;
			System.out.println(string);
		}
		long tme = System.currentTimeMillis() - start;
		System.out.println("list size " + list.size());
		System.out.println(nullCnt + " null items");
		System.out.println("spent " + tme + "ms.");

		
	}
	
}
