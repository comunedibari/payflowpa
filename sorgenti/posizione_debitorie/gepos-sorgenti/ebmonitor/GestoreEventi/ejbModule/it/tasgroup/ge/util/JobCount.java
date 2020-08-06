package it.tasgroup.ge.util;

import java.io.Serializable;

public abstract class JobCount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int numRecordProcessati;
	public int numErrori;
	public int numSkipped;
	
	public long elapsed;
	
	public void initialize(JobReport report) {
		numRecordProcessati = report.numRecordProcessati;
		elapsed = report.getNowElapsed();
		numErrori = report.numErrori;
		numSkipped = report.numSkipped;
	}
	
	public abstract JobCount getTotalizer();

	public void incrementCounters(JobCount countInfo) {
		numRecordProcessati += countInfo.numRecordProcessati;
		elapsed += countInfo.elapsed;
		numErrori += countInfo.numErrori;
		numSkipped = countInfo.numSkipped;
	}
	
	public static String column(String val, int fill) {
		return JobSummary.column(val, fill);
	}
	
	public static String column(int val, int fill) {
		return JobSummary.column(val, fill);
	}
	
	public abstract String getHeader();
	public String getHorizontalRuler() {
		String tmpString = getHeader();
		String hr = "";
		for (int i = 0; i < tmpString.length(); i++) {
			hr += "-";
		}
		return hr;
	}
	public abstract String getLine(int index);
}
