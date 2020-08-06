package it.nch.is.fo.ping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import it.nch.fwk.fo.dto.business.Pojo;

public class ProbeVo implements Pojo {
	
	public final static String STATUS_OK = "OK"; 
	public final static String STATUS_KO = "KO"; 
	public final static String TEST_TYPE_IRIS_FE = "IRIS_FE"; 
	public final static String TEST_TYPE_IRIS_BE = "IRIS_BE"; 
	public final static String TEST_TYPE_PDP = "PDP"; 
	public final static String TEST_TYPE_FLOWMANAGER_IMPORT = "IMPORT"; 
	public final static String TEST_TYPE_FLOWMANAGER_SPED = "SPED"; 
	
	public String status;
	public String message;
	public String serverName;
	public String testType;
	public long responseTimeInMsec;
	
	public Collection fmImportProbes;
	public Collection fmSpedProbes;

	private int _noOfOpenSessions;

	public Long getId() {
		return new Long(0L);
	}
	public void setId(Long arg0) {
		
	}
	
	public int getNoOfOpenSessions() {
		return _noOfOpenSessions;
	}
	
	public void setNoOfOpenSessions(int ofOpenSessions) {
		_noOfOpenSessions = ofOpenSessions;
	}
	
	public void addFMProbe(ProbeVo fmProbe, String type) {
		fmProbe.testType = type;
		if (TEST_TYPE_FLOWMANAGER_IMPORT.equals(type)) {
			if (fmImportProbes == null) {
				fmImportProbes = new ArrayList();
			}
			fmImportProbes.add((fmProbe));
		} else if (TEST_TYPE_FLOWMANAGER_SPED.equals(type)) {
			if (fmSpedProbes == null) {
				fmSpedProbes = new ArrayList();
			}
			fmSpedProbes.add((fmProbe));
		} else {
			throw new RuntimeException("Type " + type + " not recognized");
		}
	}
	
}
