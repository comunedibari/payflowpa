package it.tasgroup.iris.payment.helper;

public class PagamentoException extends Exception {

	private static final long serialVersionUID = 1L;

	private String messageKey;
	private Object[] params;

	PagamentoException(String messageKey, String message) {
		super(message);
		this.messageKey = messageKey;
	} 
	
	PagamentoException(String messageKey, Object[] params, String message) {
		super(message);
		this.messageKey = messageKey;
		this.params = params;
	} 
	
	PagamentoException(String messageKey, String message, Throwable cause) {
		super(message, cause);
		this.messageKey = messageKey;
	}

	PagamentoException(String messageKey, Object[] params, String message, Throwable cause) {
		super(message, cause);
		this.messageKey = messageKey;
		this.params = params;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getParams() {
		return params;
	}
	
}
