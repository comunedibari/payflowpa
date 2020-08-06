package it.regioneveneto.mygov.payment.mypivot.service.exception;

@SuppressWarnings("serial")
public class RtNonInRendicontazioneException extends RuntimeException {

	public RtNonInRendicontazioneException() {
		super();
	}

	public RtNonInRendicontazioneException(String message) {
		super(message);
	}

	public RtNonInRendicontazioneException(Throwable t) {
		super(t);
	}

	public RtNonInRendicontazioneException(String message, Throwable t) {
		super(message, t);
	}
}
