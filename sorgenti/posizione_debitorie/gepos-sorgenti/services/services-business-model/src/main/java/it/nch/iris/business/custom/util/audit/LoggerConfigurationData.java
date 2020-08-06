package it.nch.iris.business.custom.util.audit;

import java.util.Collection;

public class LoggerConfigurationData {
	private boolean logAllParams = false;
	private Collection commonLogParameter;
	private Collection businessLogParameter;
	private String methodName;
	
	public Collection getCommonLogParameter() {
		return commonLogParameter;
	}
	public void setCommonLogParameter(Collection commonLogParameter) {
		this.commonLogParameter = commonLogParameter;
	}
	public Collection getBusinessLogParameter() {
		return businessLogParameter;
	}
	public void setBusinessLogParameter(Collection businessLogParameter) {
		this.businessLogParameter = businessLogParameter;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public boolean isLogAllParams() {
		return logAllParams;
	}
	public void setLogAllParams(boolean logAllParams) {
		this.logAllParams = logAllParams;
	}
	
}
