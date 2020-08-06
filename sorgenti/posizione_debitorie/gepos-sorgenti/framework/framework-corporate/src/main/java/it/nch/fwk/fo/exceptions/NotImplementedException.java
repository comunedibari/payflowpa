package it.nch.fwk.fo.exceptions;

public class NotImplementedException extends GenericException{
	private static final long	serialVersionUID	= 5438297881400103315L;

	/** [Bancaintesa_Common]:
	 * =============================================
	 * @param cause
	 *
	 */
	public NotImplementedException(Exception cause)
	{ super(cause); }

	/**
	 *  [Bancaintesa_Common]:
	 * =============================================
	 * @param message
	 *
	 */
	public NotImplementedException(String message)
	{ super(new Exception(message)); }

}
