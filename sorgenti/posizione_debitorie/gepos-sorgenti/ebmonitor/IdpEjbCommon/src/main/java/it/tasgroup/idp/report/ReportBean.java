package it.tasgroup.idp.report;

import java.io.Serializable;

public class ReportBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String report;
    private boolean hasError;
    
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
    
}
