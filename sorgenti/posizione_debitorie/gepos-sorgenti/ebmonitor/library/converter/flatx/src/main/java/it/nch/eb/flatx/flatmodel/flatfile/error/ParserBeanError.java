/**
 * Created on 04/mar/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.error;

import java.io.Serializable;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.verifiers.ErrorsDeaults;
import it.nch.eb.flatx.flatmodel.verifiers.IBeanError;


/**
 * @author gdefacci
 */
public class ParserBeanError implements IBeanError, Serializable {
	
	private static final long	serialVersionUID	= 2502474512061059881L;
	private static final String	PARSER_ERROR		= "ParserErrorId";
	private static final int	PARSER_ERROR_TYPE	= 6;
	
	private final Object targetObject; 
	private final String propertyName; 
	private final Object propertySourceValue; 
	
	private final Exception e; 
	private final Converter cnv; 
	private final String line; 
	
	private final int lineNumber;
	private final int lineStartPos; 
	private final int lineEndPos;
	
	public ParserBeanError(Object targetObject, String propertyName, Object propertySourceValue, Exception e,
			Converter cnv, String line, int lineNumber, int lineStartPos, int lineEndPos) {
		this.targetObject = targetObject;
		this.propertyName = propertyName;
		this.propertySourceValue = propertySourceValue;
		this.e = e;
		this.cnv = cnv;
		this.line = line;
		this.lineNumber = lineNumber;
		this.lineStartPos = lineStartPos;
		this.lineEndPos = lineEndPos;
	}

	public Exception getException() {
		return e;
	}

	public Object getExpectedValue() {
		return ErrorsDeaults.NO_EXPECTATION_PROVIDED;
	}

	public Object getValue() {
		return propertySourceValue;
	}

	public String getErrorId() {
		return PARSER_ERROR;
	}

	public int getErrorType() {
		return PARSER_ERROR_TYPE;
	}
	
	public Object getTargetObjectValue() {
		return targetObject;
	}
	
	public Object getPropertySourceValue() {
		return propertySourceValue;
	}
	
	public Converter getConverter() {
		return cnv;
	}
	
	public String getLine() {
		return line;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public int getStartOffset() {
		return lineStartPos;
	}

	public int getEndOffset() {
		return lineEndPos;
	}
	
	
	public int getLineNumber() {
		return lineNumber;
	}

	public int compareTo(Object o) {
		if (o instanceof ParserBeanError) {
			ParserBeanError pbe = (ParserBeanError) o;
			int res = line.compareTo(pbe.getLine());
			if (res==0) res = propertyName.compareTo(pbe.getPropertyName());
			if (res==0) res = e.getMessage().compareTo(pbe.getException().getMessage());
			return res;
		}
		return 1;
	}

	public String toString() {
		return StringUtils.toString(this);
	}
	
}
