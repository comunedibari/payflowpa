package it.nch.fwk.fo.exceptions;

public class FrontEndException extends Exception{
	private static final long	serialVersionUID	= - 7650866266164715947L;

	protected String			m_strErrorCode		= null;

	public FrontEndException(String strErrorCode)
	{
		super(strErrorCode);
		m_strErrorCode = strErrorCode;
	}

	public FrontEndException(String strErrorCode, String strErrorMessage,
			String strModule)
	{
		super(strErrorCode + " - " + strErrorMessage + " in " + strModule);
		m_strErrorCode = strErrorCode;
	}

	/** [Bancaintesa_Common]:
	 * it.nch.fwk.fo.exceptions.FrontEndException CONSTRUCTOR
	 * =============================================
	 *
	 * @since	:14-dic-2005
	 * @author	:GennaroR2005
	 */
	public FrontEndException()
	{ super(); }

	/** [Bancaintesa_Common]:
	 * it.nch.fwk.fo.exceptions.FrontEndException CONSTRUCTOR
	 * =============================================
	 * @param message
	 * @param cause
	 *
	 * @since	:14-dic-2005
	 * @author	:GennaroR2005
	 */
	public FrontEndException(String message, Throwable cause)
	{ super(message, cause); }

	/** [Bancaintesa_Common]:
	 * it.nch.fwk.fo.exceptions.FrontEndException CONSTRUCTOR
	 * =============================================
	 * @param cause
	 *
	 * @since	:14-dic-2005
	 * @author	:GennaroR2005
	 */
	public FrontEndException(Throwable cause)
	{ super(cause);
	}

	public String getErrorCode()
	{ return m_strErrorCode; }
}
