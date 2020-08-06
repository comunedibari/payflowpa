package it.regioneveneto.mygov.payment.mypivot.dao.exception;

public class DaoException extends RuntimeException {
	private static final long serialVersionUID = 2233358440469013814L;

	public DaoException() {
		super();
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable t) {
		super(t);
	}

	public DaoException(String message, Throwable t) {
		super(message, t);
	}
}
