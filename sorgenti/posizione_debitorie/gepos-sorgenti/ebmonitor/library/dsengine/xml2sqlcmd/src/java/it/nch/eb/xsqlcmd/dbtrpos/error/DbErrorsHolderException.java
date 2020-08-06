/**
 * 02/lug/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.error;


public class DbErrorsHolderException extends RuntimeException {

	private static final long serialVersionUID = -666L;
	
	private final DBError[] errors;

	public DbErrorsHolderException(DBError err) {
		this(new DBError[] { err });
	}
	public DbErrorsHolderException(DBErrorsBag errs) {
		this(errs.getErrors());
	}
	public DbErrorsHolderException(DBError[] errors) {
		super("DBError[] holder exeception");
		this.errors = errors;
	}
	
	public DBError[] getErrors() {
		return errors;
	}
	
	public String getMessage() {
		StringBuffer sb = new StringBuffer("DBErrors : ");
		if (errors.length == 0) {
			new RuntimeException("bug fix ").printStackTrace();
		} else {
			sb.append(errors[0].toString());
			for (int i = 1; i < errors.length; i++) {
				DBError err = errors[i];
				sb.append(", " + err.toString());
			}
		}
		return sb.toString();
	}
	
}