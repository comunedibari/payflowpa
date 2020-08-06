package it.tasgroup.ge.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class JobSummary extends JobReport {

	private static final long serialVersionUID = 1L;

	public int totRecordDaProcessare;
	public int dimensioneLotto;
	public int numLotti;
	public int residuo;
	
	public Collection countInfos;

	public JobSummary(String des) {
		super(des);
	}

	public static JobSummary startSummary(String des) {
		JobSummary newSummary = new JobSummary(des);
		return newSummary;
	}
	
	public JobReport closeReport() {
		endTime = System.currentTimeMillis();
		return this;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb = println(sb, "\r\n" + HR1);
		
		//
		//	Dati generali
		//
		sb = println(sb, "JOB ........................ " + description);
		sb = println(sb, "Tot record ... " + totRecordDaProcessare);
		if (dimensioneLotto > 0) {
			sb = println(sb, "Dim. Lotto ... " + dimensioneLotto);
			sb = println(sb, "Lotti ........ " + numLotti);
		}
		if (residuo > 0) {
			sb = println(sb, " ** RESIDUO ** " + residuo);
		}
		sb = println(sb, HR2);
		
		//
		//	Sub cicli
		//
		if (countInfos != null && countInfos.size() > 0) {
			JobCount totalizer = null;

			int index = 0;
			for (Iterator it = countInfos.iterator(); it.hasNext(); ) {
				index++;
				JobCount countInfo = (JobCount)it.next();
				if (index == 1) {
					totalizer = countInfo.getTotalizer();
					sb = println(sb, countInfo.getHeader());
					sb = println(sb, countInfo.getHorizontalRuler());
				}
				totalizer.incrementCounters(countInfo);
				sb = println(sb, countInfo.getLine(index));
				
			}
			sb = println(sb, totalizer.getHorizontalRuler());
			sb = println(sb, totalizer.getLine(-1));
		} else {
			sb = println(sb, "Nessun sub-ciclo");
		}
		sb = println(sb, HR2);
		
		//
		//	Tempi di esecuzione
		//
		sb = println(sb, "Data/ora inizio ............ " + formatDate(startTime));
		sb = println(sb, "Data/ora fine .............. " + formatDate(endTime));
		//sb = println(sb, "Data/ora fine .............. " + formatDate(System.currentTimeMillis()));
		sb = println(sb, "Tempo trascorso ............ " + getElapsed(endTime - startTime));
		//sb = println(sb, "Tempo trascorso ............ " + getElapsed(getNowElapsed()));
		sb = println(sb, HR2);
		
		sb = println(sb, HR1);

		return sb.toString();
	}

	public static String column(int val, int fill) {
		return column("" + val, fill);
	}
	
	public static String column(String val, int fill) {
		String out = "";
		if (val.length() > fill) {
			out = val.substring(0, fill);
		} else {
			for (int i = 0; i < fill - val.length(); i ++) {
				out += " ";
			}
			out += val;
		}
		return out;
	}
	
	private String formatDate(long aLong) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(new Date(aLong));
	}
	
	public static void main(String[] args) {
		System.out.println(column("PINKO", 10) + column("PALLINO", 10) + column("PAOLINOPAPERINO", 10));
		System.out.println(column(32, 10) + column(3, 10) + column(6, 10));
		System.out.println(column(1, 10) + column(7, 10) + column(12, 10));
		System.out.println(column(33, 10) + column(10, 10) + column(18, 10));
		
		
		JobSummary summary = new JobSummary("TEST");
		summary = (JobSummary)summary.closeReport();
		System.out.println(summary.toString());
	}

	public void addSubReportInfo(JobCount jobCount, JobReport subReport) {
		if (countInfos == null) {
			countInfos = new ArrayList();
		}
		jobCount.initialize(subReport);
		countInfos.add(jobCount);
	}

}
