package it.tasgroup.idp.bean;

public class ResultKOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object serializedResult;
    public 	ResultKOException(Object sr){
    	serializedResult=sr;
    }
	public Object getSerializedResult() {
		return serializedResult;
	}
	public void setSerializedResult(Object serializedResult) {
		this.serializedResult = serializedResult;
	}
    

}
