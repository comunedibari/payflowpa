/**
 * Created on 29/feb/08
 */
package it.nch.eb.flatx.flatmodel.verifiers;

import it.nch.fwk.checks.errors.QualifiedError;


/**
 * @author gdefacci
 */
public interface ErrorsDeaults {

	public static final IBeanError NO_CAUSE_ERROR = new SimpleError(QualifiedError.NO_ERROR, null) {
	
		private static final long	serialVersionUID	= 5702164910717824508L;

		public String toString() {
			return "'no cause error provided'";
		}
		
	};
	public static final Object NO_VALUE_PROVIDED = new Object() {
	
		public String toString() {
			return "'no provided value'";
		}
		
	};
	public static final Object NO_EXPECTATION_PROVIDED = new Object() {
	
		public String toString() {
			return "'no provided expectation'";
		}
		
	};
	public static final Exception NO_EXCEPTION_PROVIDED = new Exception("No exception") {
	
		private static final long	serialVersionUID	= 5459278290300423213L;
	
		public String toString() {
			return "'no exception has been provided'";
		}
		
	};

}
