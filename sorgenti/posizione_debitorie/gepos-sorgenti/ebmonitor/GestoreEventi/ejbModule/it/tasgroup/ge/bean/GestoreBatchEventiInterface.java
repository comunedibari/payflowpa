package it.tasgroup.ge.bean;

import javax.ejb.Local;

import it.tasgroup.ge.util.JobReport;

@Local
public interface GestoreBatchEventiInterface {
	public JobReport executeJob();
}
