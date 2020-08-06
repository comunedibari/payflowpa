package it.regioneveneto.mygov.payment.mypivot.service.exception;

@SuppressWarnings("serial")
public class MyPivotServiceException extends RuntimeException {

	public MyPivotServiceException() {
		super();
	}

	public MyPivotServiceException(String message) {
		super(message);
	}

	public MyPivotServiceException(Throwable t) {
		super(t);
	}

	public MyPivotServiceException(String message, Throwable t) {
		super(message, t);
	}
}
