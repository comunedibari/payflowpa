/**
 * Created on 22/mag/07
 */
package it.nch.fwk.checks.errors;

import it.nch.fwk.checks.errors.extras.ExtendedErrorInfo;

import java.util.Comparator;

/**
 * @author gdefacci
 */
public class ComparatorsFactory {
	
	public static final int	EQUAL	= 0;
	public static final int	LESS	= -1;
	public static final int	GREATER  = 1;
	public static final int	NOT_COMPARABLE	= -2;
	
	static abstract class QualifiedErrorComparator extends SafeComparator {
		
		protected int typeSpecificCompare(Object arg1, Object arg2) {
			QualifiedError ei1 = (QualifiedError) arg1;
			QualifiedError ei2 = (QualifiedError) arg2;
			return compareTo(ei1, ei2);
		}

		public abstract int compareTo(QualifiedError qe, QualifiedError qe2);
		
	}
	
	public static abstract class ErrorInfoComparator extends SafeComparator {
		
		protected int typeSpecificCompare(Object arg1, Object arg2) {
			ErrorInfo ei1 = (ErrorInfo) arg1;
			ErrorInfo ei2 = (ErrorInfo) arg2;
			return compareTo(ei1, ei2);
		}

		public abstract int compareTo(ErrorInfo qe, ErrorInfo qe2);
	};
	
	public static abstract class ExtendedErrorInfoComparator implements Comparator {
		
		public int compare(Object o1, Object o2) {
			if (o1 instanceof QualifiedError && o2 instanceof QualifiedError) {
				if (o1 instanceof ExtendedErrorInfo && o2 instanceof ExtendedErrorInfo) {
					return compareTo((ExtendedErrorInfo)o1, (ExtendedErrorInfo)o2);
				} else if (o1 instanceof ExtendedErrorInfo) {
					return 1;
				} else if (o2 instanceof ExtendedErrorInfo) {
					return -1;
				} else {
					return QUALIFIED_ERROR_IDENTITITY_COMPARATOR.compare(o1, o2);
				}
			} else {
				return -1;
			}
		}
		
		public abstract int compareTo(ExtendedErrorInfo qe, ExtendedErrorInfo qe2);
	};
	
	/**
	 * match the <code>QualifiedError</code>'s which have same 
	 * <code>errorId</code> and same <code>errorType</code>
	 */
	public static final Comparator QUALIFIED_ERROR_IDENTITITY_COMPARATOR = new QualifiedErrorComparator() {
		public int compareTo(QualifiedError qe1, QualifiedError qe2) {
			int outcome = new Long(qe1.getErrorType()).compareTo(new Long(qe2.getErrorType()));
			if (outcome==0) 
				outcome = qe1.getErrorId().compareTo(qe2.getErrorId());
			return outcome;
		}
	};
	
	
	/**
	 * ErrorInfo qe1 and ErrorInfo qe2 match if properties errorId errorType path 
	 * severity are equals or unspecified (null)
	 */
	public static final Comparator ERRORINFO_IDENTITITY_COMPARATOR = new ErrorInfoComparator() {
		public int compareTo(ErrorInfo qe1, ErrorInfo qe2) {
			int outcome = QUALIFIED_ERROR_IDENTITITY_COMPARATOR.compare(qe1, qe2);
			if (outcome == 0) {
				outcome = qe1.getSeverity().compareTo(qe2.getSeverity());
				if (outcome == 0) {
					outcome = qe1.getSeverity().compareTo(qe2.getSeverity());
					if (outcome == 0) {
					
					}
				}
			}
			return outcome;
		}

	};

	public static final Comparator EXTENDED_ERRORINFO_IDENTITITY_COMPARATOR = new ExtendedErrorInfoComparator() {

		public int compareTo(ExtendedErrorInfo qe1, ExtendedErrorInfo qe2) {
			int outcome = QUALIFIED_ERROR_IDENTITITY_COMPARATOR.compare(qe1, qe2);
			if (outcome==0) {
				outcome = qe1.getPath().compareTo(qe2.getPath());
				return outcome;
			} else 
				return outcome;
		}
		
	};
}
