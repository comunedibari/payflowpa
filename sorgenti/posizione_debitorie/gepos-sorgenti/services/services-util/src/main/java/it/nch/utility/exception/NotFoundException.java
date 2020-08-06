package it.nch.utility.exception;

public class NotFoundException extends Exception {

	public NotFoundException(Exception ex, String codice, String messaggio)	{
	}

	public NotFoundException(Exception ex) {
		super(ex);
	}
}
