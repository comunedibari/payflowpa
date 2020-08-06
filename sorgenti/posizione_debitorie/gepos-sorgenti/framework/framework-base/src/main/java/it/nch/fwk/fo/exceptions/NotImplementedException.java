package it.nch.fwk.fo.exceptions;

public class NotImplementedException extends GenericException{

	private static final long	serialVersionUID	= 5438297881400103315L;

	/** [Bancaintesa_Common]:
	 * it.nch.fwk.fo.exceptions.NotImplementedException CONSTRUCTOR
	 * =============================================
	 * @param cause
	 *
	 * @since	:13-dic-2005
	 * @author	:GennaroR2005
	 */
	public NotImplementedException(Exception cause)
	{ super(cause); }

	/**
	 *  [Bancaintesa_Common]:
	 * it.nch.fwk.fo.exceptions.NotImplementedException CONSTRUCTOR
	 * =============================================
	 * @param message
	 *
	 * @since	:13-dic-2005
	 * @author	:GennaroR2005
	 */
	public NotImplementedException(String message)
	{ super(new Exception(message)); }

}
