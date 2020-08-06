package it.regioneveneto.mygov.payment.mypivot.controller.exception;

public class MyPivotControllerException extends Exception {

	private static final long serialVersionUID = 180063828274441383L;

	public MyPivotControllerException() {
		super();
	}

	public MyPivotControllerException(String message) {
		super(message);
	}

	public MyPivotControllerException(Throwable t) {
		super(t);
	}

	public MyPivotControllerException(String message, Throwable t) {
		super(message, t);
	}
}
