package it.tasgroup.ge.helpers;

import it.tasgroup.ge.util.JobReport;

public interface GestoreBatchEventiHelper {
	public static final String STATO_EVENTO_IN_ATTESA ="01.IN_ATTESA";
	public static final String STATO_EVENTO_IN_ESECUZIONE ="02.IN_ESECUZIONE";
	public static final String STATO_EVENTO_NOTIFICATO ="03.NOTIFICATO";
	public static final String STATO_EVENTO_IN_ERRORE="04.IN_ERRORE";
	public static final String STATO_EVENTO_ABORTITO ="05.ABORTITO";
	
	public JobReport executeJob();
	
	
}
