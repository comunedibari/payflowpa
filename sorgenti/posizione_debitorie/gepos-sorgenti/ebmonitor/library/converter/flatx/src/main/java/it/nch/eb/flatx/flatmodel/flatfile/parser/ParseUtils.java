/**
 * Created on 01/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.flatx.exceptions.IRecoverableParserException;
import it.nch.eb.flatx.exceptions.ParserException;


/**
 * @author gdefacci
 */
public class ParseUtils {
	
	public static boolean isFatal(Exception e) {
		Throwable pex = ReflectionUtils.getExceptionOrCause(ParserException.class, e);
//		if (pex instanceof RecoverableParserException) return false;
		if (pex instanceof IRecoverableParserException) return false;
		return true;
	}

}
