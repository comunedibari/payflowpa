package it.nch.fwk.fo.dto;

public class DTOInfoSeverity  {

	   public static  boolean isValidSeverity(int severity) {
	        return ((severity == DTOInfoInterface.SEVERITY_NO_ERROR ) || 
	                (severity == DTOInfoInterface.SEVERITY_WARNING) ||
	                (severity == DTOInfoInterface.SEVERITY_INFO)  || 
	                (severity == DTOInfoInterface.SEVERITY_ERROR)  ||
	                (severity == DTOInfoInterface.SEVERITY_ROLLBACK) || 
	                (severity == DTOInfoInterface.SEVERITY_GENERIC));
	    }
	
}

