/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.error;

import it.nch.fwk.checks.errors.QualifiedErrorImpl;

/**
 * FIXME: this class shuold be ranemd, it's used not only for db errors, but also for business
 * errors and other kind of validation.
 * @author gdefacci
 */
public class DBError extends QualifiedErrorImpl  {

	private static final long serialVersionUID = -5139262358357762881L;
	public static final int DB_ERROR_TYPE = 22;
	
	private final Object object;
	private final Exception cause;
	private final String xmlInfos;
	
	public DBError(String errorId, Object failingObject) {
		this(errorId, failingObject, null, null);
	}
	
	public DBError(String errorId, Object failingObject, String xmlInfos) {
		this(errorId, failingObject, null, xmlInfos);
	}
	
	public DBError(String errorId, Object failingObject, Exception cause) {
		this(errorId, failingObject, cause, null);
	}
	
	public DBError(String errorId, Object failingObject, Exception cause, String xmlInfos) {
		super(errorId, DB_ERROR_TYPE );
		this.object = failingObject;
		this.cause = cause;
		this.xmlInfos = xmlInfos;
	}

	public Object getObject() {
		return object;
	}
	
	public Exception getCause() {
		return cause;
	}
	
	public String getXmlInfos() {
		return xmlInfos;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("DbError\n");
		sb.append(super.toString());
		if (this.cause!=null) {
			sb.append("cause :");
			sb.append(this.cause.getMessage());
			sb.append("\n");
			sb.append(this.cause.getCause());
		}
		if (xmlInfos!=null) {
			sb.append("\n");
			sb.append(xmlInfos);
		}
//		sb.append("\nfailing object:\n");
//		sb.append(this.object.toString());
		return sb.toString();
	}
	
}
