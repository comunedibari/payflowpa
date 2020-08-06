package it.regioneveneto.mygov.payment.mypivot.service.exception;

@SuppressWarnings("serial")
public class TipoDovutoNonValidoPerUtenteException extends RuntimeException {

	public TipoDovutoNonValidoPerUtenteException() {
		super();
	}

	public TipoDovutoNonValidoPerUtenteException(String message) {
		super(message);
	}

	public TipoDovutoNonValidoPerUtenteException(Throwable t) {
		super(t);
	}

	public TipoDovutoNonValidoPerUtenteException(String message, Throwable t) {
		super(message, t);
	}
}
