package it.nch.utility.exception;

/**
 * Segnala che c'è stato un possibile tentativo di accesso a dati per i quali
 * non si possiedono le relative autorizzazioni di visione e/o modifica.
 */
public class WrongProfileException extends RuntimeException {
	
	public WrongProfileException() {
		super("Wrong Profile Exception");
	}
	
	public WrongProfileException(String message) {
		super(message);
	}
}
