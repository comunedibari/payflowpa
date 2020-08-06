package it.regioneveneto.mygov.payment.mypivot.service.exception;

@SuppressWarnings("serial")
public class NessunTipoDovutoAttivoException extends RuntimeException {

	public NessunTipoDovutoAttivoException() {
		super();
	}

	public NessunTipoDovutoAttivoException(String message) {
		super(message);
	}

	public NessunTipoDovutoAttivoException(Throwable t) {
		super(t);
	}

	public NessunTipoDovutoAttivoException(String message, Throwable t) {
		super(message, t);
	}
}
