package it.tasgroup.ge.util;

import java.io.Serializable;

public class JobCountAggProf extends JobCount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int numServiziAttivati;
	public int numServiziDismessi;

	public void initialize(JobReport report) {
		super.initialize(report);
		
		numServiziAttivati = report.numInserimenti;
		numServiziDismessi = report.numCancellazioni;
	}

	public JobCount getTotalizer() {
		return new JobCountAggProf();
	}

	public void incrementCounters(JobCount countInfo) {
		super.incrementCounters(countInfo);
		
		numServiziAttivati += ((JobCountAggProf)countInfo).numServiziAttivati;
		numServiziDismessi += ((JobCountAggProf)countInfo).numServiziDismessi;
		
	}

	public String getHeader() {
		return column("#", 5) + column("t (msec)", 12) + column("PROCESSATI", 12) + column("ATTIVATI", 12) + column("DISMESSI", 12) + column("ERRORI", 12);
	}
	
	public String getLine(int index) {
		String idx = "";
		if (index > 0) {
			idx += index;
		}
		return column(idx, 5) + column((int)elapsed, 12) + column(numRecordProcessati, 12) + column(numServiziAttivati, 12) + column(numServiziDismessi, 12) + column(numErrori, 12);
	}
}
