package it.nch.fwk.fo.exceptions;

public class ApplicationException extends Exception {
	private static final long	serialVersionUID	= 8340429248587206369L;
	protected String m_strErrorCode = null;

	/**
     *
     * @param     strErrorCode  
     */
    public ApplicationException(String strErrorCode) 
    {
        super(strErrorCode);
        m_strErrorCode = strErrorCode;
    }

    public ApplicationException(String strErrorCode, String strErrorMessage, String strModule)
    {
        super(strErrorCode + " - " + strErrorMessage + " in " + strModule);
        m_strErrorCode = strErrorCode;
    }

    public String getErrorCode() 
    {
        return m_strErrorCode;
    }
}
