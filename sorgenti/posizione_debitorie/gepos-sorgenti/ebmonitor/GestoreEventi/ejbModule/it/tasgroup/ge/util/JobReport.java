package it.tasgroup.ge.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class JobReport implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String HR1 = "===================================================================================";
	public static final String HR2 = "----------------------------------------------";
	public static final String HR3 = "-----------------------";
	public static final String HR4 = "-----------------------------------------------------------------------------------";

	protected long startTime;
	protected long endTime;
	protected String description;
	
	private Collection messages;

	public int numRecordProcessati;
	public int numInserimenti;
	public int numAggiornamenti;
	public int numCancellazioni;
	
	public int numErrori;
	
	public int numSkipped;

	public JobReport(String des) {
		startTime = System.currentTimeMillis();
		description = des;
	}

	public static JobReport startReport(String des) {
		JobReport newReport = new JobReport(des);
		return newReport;
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
		sb = println(sb, HR2);
		
		//
		//	Tempi di esecuzione
		//
		sb = println(sb, "Data/ora inizio ............ " + formatDate(startTime));
		sb = println(sb, "Data/ora fine .............. " + formatDate(endTime));
		sb = println(sb, "Tempo trascorso ............ " + getElapsed(endTime - startTime));
		sb = println(sb, HR2);
		
		//
		//	Messaggi
		//
		if (messages != null) {
			for (Iterator it = messages.iterator(); it.hasNext(); ) {
				String msg = (String)it.next();
				sb = println(sb, msg);
			}
		} else {
			sb = println(sb, "Nessun messaggio");
		}
		
		sb = println(sb, HR1);

		return sb.toString();
	}

	private String formatDate(long aLong) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(new Date(aLong));
	}
	
	public long getRawElapsed() {
		return endTime - startTime;
	}
	
	public long getNowElapsed() {
		long now = System.currentTimeMillis();
		long elpsd = now - startTime;
		return elpsd;
	}
	
	protected static String getElapsed(long diff) {
		String out = "";
		
		int hours = (int)(diff / (1000 * 60 * 60));
		if (hours > 0) {
			String unit = " ora ";
			if (hours > 1) unit = " ore ";
			out += hours + unit;
		}
		
		diff = diff - (hours * (1000 * 60 * 60));
		int minutes = (int)(diff / (1000 * 60));
		if (minutes > 0) {
			String unit = " minuto ";
			if (minutes > 1) unit = " minuti ";
			out += minutes + unit;
		}
		
		diff = diff - (minutes * (1000 * 60));
		int seconds = (int)(diff / (1000));
		if (seconds > 0) {
			String unit = " secondo ";
			if (seconds > 1) unit = " secondi ";
			out += seconds + unit;
		}
		
		diff = diff - (seconds * (1000));
		int milliseconds = (int)(diff);
		if (milliseconds > 0) {
			String unit = " millisecondo ";
			if (milliseconds > 1) unit = " millisecondi ";
			out += milliseconds + unit;
		}
		
		if (diff == 0) {
			out = "Tempo non rilevabile";
		}
		
		return out;
	}
	
	public static void main(String[] args) {
		viewElapsed();
	}

	private static void viewElapsed() {
		long oneSecond = 1000;
		long oneMinute = 60 * oneSecond;
		long oneHour = 60 * oneMinute;
		System.out.println(getElapsed(300 * oneHour + 20 * oneMinute + 45 * oneSecond));
		System.out.println(getElapsed(3 * oneHour + 20 * oneMinute + 45 * oneSecond));
		System.out.println(getElapsed(3 * oneHour + 20 * oneMinute));
		System.out.println(getElapsed(20 * oneMinute));
		System.out.println(getElapsed(1 * oneMinute + 1 * oneSecond));
	}

	protected StringBuffer println(StringBuffer sb, String string) {
		sb.append(string + "\r\n");
		return sb;
	}

	public void addMessage(String msg) {
		if (messages == null) {
			messages = new ArrayList();
		}
		messages.add(msg);
	}

	public void addError(String err) {
		if (messages == null) {
			messages = new ArrayList();
		}
		messages.add(" <!> " + err);
	}

}
