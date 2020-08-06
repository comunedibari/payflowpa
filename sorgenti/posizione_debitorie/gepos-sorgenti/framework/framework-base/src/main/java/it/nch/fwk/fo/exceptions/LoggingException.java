package it.nch.fwk.fo.exceptions;

import org.apache.log4j.Logger;

public class LoggingException extends GenericException
{
	/**
	 * Logger for this class
	 */
	private static final Logger	logger	=
		Logger.getLogger(LoggingException.class);

	/** [IOLI2_Web] LoggingException.java
	 * =============================================
	 * it.nch.fwk.fo.exceptions.LoggingException.java
	 *		.serialVersionUID [long]
	 * =============================================
	 * @since	:30-nov-2005
	 * @author	:GennaroR2005
	 */
	private static final long	serialVersionUID	= - 5689235052149611415L;

	/** [IOLI2_Web]:
	 * it.nch.fwk.fo.exceptions.LoggingException CONSTRUCTOR
	 * =============================================
	 *
	 * @since	:30-nov-2005
	 * @author	:GennaroR2005
	 */
	public LoggingException()
	{
		super(new Exception());
		logger.error(this);
	}

	/** [IOLI2_Web]:
	 * it.nch.fwk.fo.exceptions.LoggingException CONSTRUCTOR
	 * =============================================
	 * @param message
	 *
	 * @since	:30-nov-2005
	 * @author	:GennaroR2005
	 */
	public LoggingException(String message)
	{
		super(new Exception(message));
		logger.error(message, this);
	}

	/** [IOLI2_Web]:
	 * it.nch.fwk.fo.exceptions.LoggingException CONSTRUCTOR
	 * =============================================
	 * @param message
	 * @param cause
	 *
	 * @since	:30-nov-2005
	 * @author	:GennaroR2005
	 */
	public LoggingException(String message, Throwable cause)
	{
		super(new Exception(message, cause));
		logger.error(message, cause);
	}

	/** [IOLI2_Web]:
	 * it.nch.fwk.fo.exceptions.LoggingException CONSTRUCTOR
	 * =============================================
	 * @param cause
	 *
	 * @todo	:TODO
	 * @since	:30-nov-2005
	 * @author	:GennaroR2005
	 */
	public LoggingException(Throwable cause)
	{
		super(new Exception(cause));
		logger.error(cause.getMessage(), cause);
	}

}
