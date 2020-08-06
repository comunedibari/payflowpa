/**
 * Created on 09/mag/07
 */
package it.nch.fwk.checks.errors;

import java.io.Serializable;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;

public class QualifiedErrorImpl implements QualifiedError, Comparable, Serializable {

	private static final long	serialVersionUID	= 8517047748726630191L;
	private int	errorType = UNQUALIFIED_ERROR_TYPE;
	private String	errorId;
	
	public QualifiedErrorImpl(String errorId) {
		this(errorId,UNQUALIFIED_ERROR_TYPE);
	}

	public QualifiedErrorImpl(String errorId, int errorType) {
		this.errorId = errorId;
		this.errorType = errorType;
	}

	public String getErrorId() {
		return errorId;
	}

	public int getErrorType() {
		return errorType;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		columnsPrint(sb, "error ID" , this.errorId );
		if (errorType!=NO_ERROR_TYPE) columnsPrint(sb, "errorType", new Integer(this.errorType));
		return sb.toString();
	}
	
	public int hashCode() {
		return errorType * 7 + errorId.hashCode() * 11;
	}
	
	public boolean equals(Object obj) {
		return compareTo(obj) == 0;
	}

	public int compareTo(Object o) {
		return ComparatorsFactory.QUALIFIED_ERROR_IDENTITITY_COMPARATOR.compare(this, o);
	}

	protected void columnsPrint(StringBuffer accumulator, String label, Object value) {
		if (label==null) throw new NullPointerException();
		if (value!=null)
			accumulator.append( columnsPrint(label, value) );
	}

	protected String columnsPrint(String label, Object value) {
		return "\n" 
			+ RegexpUtils.fill(label, 32, " ", Alignments.LEFT)
			+ " : "
			+ value.toString();
	}
	
}