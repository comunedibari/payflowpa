package it.nch.eb.xsqlcmd.dao;


import java.sql.Connection;

/**
 * Do not use this class in production code, Replace this class with a correct timestamp 
 * UUID generator
 * 
 * @author gdefacci
 */
public final class TimeStampUidProvider implements UIdProvider {
	private long lastTs = 0;
	public synchronized long nextVal(Connection conn) {
		long res = System.currentTimeMillis();
		if (res == lastTs) {
			res += 3;
		}
		lastTs = res;
		return res;
	}
}