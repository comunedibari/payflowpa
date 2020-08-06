/**
 * Created on 04/mar/08
 */
package it.nch.eb.flatx.exceptions;


/**
 * @author gdefacci
 */
public class IllegalRecordSequenceException extends ParserException {

	public IllegalRecordSequenceException(String message) {
		super(message);
	}

	public IllegalRecordSequenceException(Throwable e, String msg) {
		super(msg, e);
	}

	public IllegalRecordSequenceException(Throwable e) {
		super(e);
	}
	
	public IllegalRecordSequenceException(Throwable e, int lineNumb) {
		super(e, lineNumb);
	}

	public IllegalRecordSequenceException(Throwable e, String msg, int lineNumb) {
		super(msg, e, lineNumb);
	}
	
	public IllegalRecordSequenceException(String message, int lineNumber) {
		super(message, lineNumber);
	}

	private static final long	serialVersionUID	= 4856461746849811066L;
}
