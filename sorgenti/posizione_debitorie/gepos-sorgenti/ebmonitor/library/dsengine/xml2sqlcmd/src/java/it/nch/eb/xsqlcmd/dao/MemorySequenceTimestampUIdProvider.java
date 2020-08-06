/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dao;

import java.sql.Connection;

/**
 * do not use this class in production code. Not thread safe, no db synch. just use it for tests.
 * @author gdefacci
 */
public class MemorySequenceTimestampUIdProvider implements UIdProvider {
	
	final static long initial = System.currentTimeMillis();
	
	private long next = 0;
	private int increment;
	
	public MemorySequenceTimestampUIdProvider() {
		this(initial,2000);
	}
	
	public MemorySequenceTimestampUIdProvider(long next, int incValue) {
		this.next = next;
		this.increment = incValue;
	}

	public synchronized long nextVal(Connection conn) {
		long res = next;
		next += increment;
		return res;
	}

}
