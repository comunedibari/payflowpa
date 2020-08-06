/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.error;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author gdefacci
 */
public class DBErrorsBag {
	
	private final List/*<DBError>*/ errors = new ArrayList();

	public void add(DBError err) {
		this.errors.add(err);
	}
	
	public void addAll(DBError[] errs) {
		for (int i = 0; i < errs.length; i++) {
			this.errors.add(errs[i]);
		}
	}
	
	public void addAll(Collection/*<DBError>*/ errs) {
		this.errors.addAll(errs);
	}
	
	public List/*<DBError>*/ getErrorList() {
		return Collections.unmodifiableList(errors);
	}
	
	public DBError[] getErrors() {
		return (DBError[]) this.errors.toArray(new DBError[0]);
	}
	
	public boolean isEmpty() {
		return errors.isEmpty();
	}

}
