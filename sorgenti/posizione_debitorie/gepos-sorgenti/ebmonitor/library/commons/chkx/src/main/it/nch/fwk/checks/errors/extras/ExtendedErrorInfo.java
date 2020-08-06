package it.nch.fwk.checks.errors.extras;

import it.nch.fwk.checks.errors.ErrorInfo;

/**
 * @author FG@2007
 */
public interface ExtendedErrorInfo extends ErrorInfo {

//	IServiceInfo getServiceInfo();
	String getErrorDetail();
	String getName();			
	String getPath();			
	String getNameSpaceURI();	
	String getValue();
	
	String toXml();
	
}
