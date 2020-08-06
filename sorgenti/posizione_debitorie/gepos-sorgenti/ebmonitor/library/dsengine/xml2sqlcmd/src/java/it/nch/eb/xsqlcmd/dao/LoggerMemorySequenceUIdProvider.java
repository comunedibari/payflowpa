/**
 * 23/nov/2009
 */
package it.nch.eb.xsqlcmd.dao;

import java.sql.Connection;

/**
 * @author gdefacci
 */
public class LoggerMemorySequenceUIdProvider extends MemorySequenceUIdProvider {

	private final String name;

	public LoggerMemorySequenceUIdProvider() {
		this(1,1);
	}

	public LoggerMemorySequenceUIdProvider(long next, int incValue) {
		this("", next, incValue);
	}
	
	public LoggerMemorySequenceUIdProvider(String name, long next, int incValue) {
		super(next, incValue);
		this.name = name;
		System.out.println("*** new LoggerMemorySequenceUIdProvider");
	}

	public synchronized long nextVal(Connection conn) {
		long res = super.nextVal(conn);
		System.out.println("*** [" + name + "] next key " + res);
		return res;
	}

}
