/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dao;

import java.sql.Connection;

/**
 * do not use this class in production code. Not thread safe, no db synch. just use it for tests.
 * @author gdefacci
 */
public class MemorySequenceUIdProvider implements UIdProvider {
	
	private long next = 0;
	private int increment;
	
	public MemorySequenceUIdProvider() {
		this(1,1);
	}
	
	public MemorySequenceUIdProvider(long next, int incValue) {
		this.next = next;
		this.increment = incValue;
	}

	public synchronized long nextVal(Connection conn) {
		long res = next;
		next += increment;
		return res;
	}

}
