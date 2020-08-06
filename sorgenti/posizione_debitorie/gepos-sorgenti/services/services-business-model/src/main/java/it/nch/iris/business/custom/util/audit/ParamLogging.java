package it.nch.iris.business.custom.util.audit;

import java.util.Collection;

public class ParamLogging {
	private boolean logAllParams = false;
	private String methodName;
	private Collection logParam;
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Collection getLogParam() {
		return logParam;
	}
	public void setLogParam(Collection logParam) {
		this.logParam = logParam;
	}
	public boolean isLogAllParams() {
		return logAllParams;
	}
	public void setLogAllParams(boolean logAllParams) {
		this.logAllParams = logAllParams;
	}
	
	

}
