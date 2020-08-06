/**
 * Created on 04/mar/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.error;

import java.io.Serializable;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.AbstractRecord;
import it.nch.eb.flatx.flatmodel.verifiers.IBeanError;



/**
 * @author gdefacci
 */
public class ParserErrorsFactory implements Serializable {

	private static final long	serialVersionUID	= -7849269254980810225L;

	public IBeanError createError(Object targetObject, String propertyName, Object propertySourceValue, 
			String line, Exception e, Converter converter, int lineNumber, int lineStartPos, int lineEndPos, AbstractRecord rec) {
		return new ParserBeanError(targetObject, propertyName, propertySourceValue, 
				e, converter, line,  lineNumber, lineStartPos, lineEndPos);
	}
	
	
}
