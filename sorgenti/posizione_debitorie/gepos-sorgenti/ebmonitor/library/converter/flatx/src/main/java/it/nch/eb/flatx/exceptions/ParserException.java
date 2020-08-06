/**
 * Created on 03/mar/08
 */
package it.nch.eb.flatx.exceptions;



/**
 * @author gdefacci
 */
public class ParserException extends RuntimeException {

	private final Object sourceValue;
	
	private int linePositionStart;
	private int linePositionEnd;
	private int lineNumber;
//	private ParsedItemInfo lastParsedItem;
	
	public ParserException(Throwable e) {
		super(e);
		sourceValue=null;
	}
	
	public ParserException(String msg, Throwable e) {
		super(msg,e);
		sourceValue=null;
	}
	
	public ParserException(Throwable e, int lineNumb) {
		super(e);
		sourceValue=null;
		this.lineNumber = lineNumb;
	}
	
	public ParserException(String msg, Throwable e, int lineNumb) {
		super(msg,e);
		sourceValue=null;
		this.lineNumber = lineNumb;
	}
	
	public ParserException(String msg, Throwable e, int lineNumb, int lineStart, int lineEnd) {
		super(msg,e);
		sourceValue=null;
		this.lineNumber = lineNumb;
		this.linePositionStart = lineStart;
		this.linePositionEnd = lineEnd;
	}
	
	public ParserException(Throwable e, int lineNumb, int lineStart, int lineEnd) {
		super(e);
		sourceValue=null;
		this.lineNumber = lineNumb;
		this.linePositionStart = lineStart;
		this.linePositionEnd = lineEnd;
	}
	
	public ParserException(String message) {
		super(message);
		this.sourceValue = null;
	}
	
	public ParserException(String message, int lineNumber) {
		super(message);
		this.sourceValue = null;
		this.lineNumber = lineNumber;
	}
	
//	public ParserException(String message, int lineNumber, ParsedItemInfo lpi) {
//		super(message);
//		this.sourceValue = null;
//		this.lineNumber = lineNumber;
//		this.lastParsedItem = lpi;
//	}

	public ParserException(Throwable e, Object sourceValue) {
		super(e);
		this.sourceValue = sourceValue;
	}
	
	public ParserException(String msg, Throwable e, Object sourceValue) {
		super(msg,e);
		this.sourceValue = sourceValue;
	}
	
	public ParserException(String msg, Object sourceValue) {
		super(msg);
		this.sourceValue = sourceValue;
	}
	
	public int getLinePositionStart() {
		return linePositionStart;
	}
	
	public void setLinePositionStart(int linePosition) {
		this.linePositionStart = linePosition;
	}
	
	public int getLinePositionEnd() {
		return linePositionEnd;
	}
	
	public void setLinePositionEnd(int linePositionEnd) {
		this.linePositionEnd = linePositionEnd;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public Object getSourceValue() {
		return sourceValue;
	}
	
//	public ParsedItemInfo getLastParsedItem() {
//		return lastParsedItem;
//	}
//	
//	public void setLastParsedItem(ParsedItemInfo lastParsedItem) {
//		this.lastParsedItem = lastParsedItem;
//	}

	public String getMessage() {
		return super.getMessage() 
			+ "\nlineNumber " + this.lineNumber 
			+ "\nlinePosition [" + this.linePositionStart + " .. " + this.linePositionEnd + "]"
			+ (this.sourceValue!=null ? "\nsourceValue " + this.sourceValue : "");
//			+ (this.lastParsedItem!=null ? "\nlast parsed item " + this.lastParsedItem : "");
	}

	public String toString() {
		return getMessage();
	}
	
	private static final long	serialVersionUID	= 7264206507255812771L;

}
