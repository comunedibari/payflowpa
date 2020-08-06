/*
 * File: UncheckedException.java
 * Package: com.etnoteam.service.handler.errorhandler
 *
 * Revision: $Revision: 1.1.1.1 $
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $
 * Created on: 15-set-03 - 15.36.14
 * Created by: finsaccanebbia (Etnoteam)
 */
package it.nch.fwk.fo.exceptions;

/**
 *
 * <br>
 * La classe UncheckedException e' una eccezione che non e'
 * obbligatorio dichiarare e "catch-are" in modo esplicito. E' da
 * utilizzare solo nei casi in cui non e' possibile utilizzare una
 * eccezione "normale" o quando si vogliono evidenziare dei casi
 * teoricamente "impossibili" a meno di gravi errori di un altro
 * strato SW (e' possibile usarlo come una "assert").
 *
**/
public class UncheckedException extends RuntimeException{
	
	private static final long	serialVersionUID	= 864701588147367980L;

	protected String m_strErrorCode = null;

	/**
     *
     * @param     strErrorCode
     */
    public UncheckedException(String strErrorCode)
    {
        super(strErrorCode);
        m_strErrorCode = strErrorCode;
    }

    public UncheckedException(String strErrorCode, String strErrorMessage, String strModule)
    {
        super(strErrorCode + " - " + strErrorMessage + " in " + strModule);
        m_strErrorCode = strErrorCode;
    }

    public String getErrorCode()
    {
        return m_strErrorCode;
    }

}
