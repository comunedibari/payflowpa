/**
 * Created on 22/mag/07
 */
package it.nch.fwk.checks.errors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * it.nch.fwk.xom.checks.errors.BaseErrorCollector helper classs, which provide
 * some error querying capabilities
 * @author gdefacci
 */
public class ErrorCollectorQueryImpl implements ErrorCollectorQuery {

	private Set/*ErrorInfo*/	errorsSet;

	public ErrorCollectorQueryImpl(ErrorCollector queriesTarget) {
		this.errorsSet = queriesTarget.getErrorSet();
	}
	
	protected ErrorCollectorQueryImpl(Set errorSet) {
		this.errorsSet = errorSet;
	}

	public boolean containsError(String detail) {
		return this.errorsSet.contains(detail);
	}

	public boolean containsSomeErrors(String[] details) {
		for (int i = 0; i < details.length; i++) {
			//			return true after first match
			if (containsError(details[i])) return true;
		}
		return false;
	}

	public boolean containsAllErrors(String[] details) {
		for (int i = 0; i < details.length; i++) {
//			return false after first match
			if (!containsError(details[i])) return false;
		}
		return true;
	}

	public ErrorInfo getError(QualifiedError errorId) {
		List lst = getErrors(errorId, ComparatorsFactory.QUALIFIED_ERROR_IDENTITITY_COMPARATOR);
		ErrorInfo res = lst.size()<1 ? null : (ErrorInfo)lst.get(0);
		return res;
	}
	
	public List getErrors(QualifiedError errorId) {
		List lst = getErrors(errorId, ComparatorsFactory.QUALIFIED_ERROR_IDENTITITY_COMPARATOR);
		return lst;
	}

	public List getErrors(ErrorInfo errorSample) {
		return getErrors(errorSample, ComparatorsFactory.ERRORINFO_IDENTITITY_COMPARATOR);
	}
	
	public List getErrors(Class errorClass) {
		List res = new ArrayList();
		for (Iterator it = this.errorsSet.iterator(); it.hasNext();) {
			QualifiedError ei = (QualifiedError) it.next();
			if (ei.getClass().isAssignableFrom(errorClass))  {
				res.add(ei);
			}
		}
		return res;
	}

	public List getErrors(QualifiedError errorSample, Comparator comparator) {
		List res = new ArrayList();
		for (Iterator it = this.errorsSet.iterator(); it.hasNext();) {
			QualifiedError ei = (QualifiedError) it.next();
			if (comparator.compare(errorSample, ei)==0) {
				res.add(ei);
			}
		}
		return res;
	}
	
	public List getErrors(ErrorInfo errorSample, Comparator comparator) {
		List res = new ArrayList();
		for (Iterator it = this.errorsSet.iterator(); it.hasNext();) {
			ErrorInfo ei = (ErrorInfo) it.next();
			if (comparator.compare(errorSample, ei)==0) {
				res.add(ei);
			}
		}
		return res;
	}

	public Set getErrorSet() {
		return Collections.unmodifiableSet(this.errorsSet);
	}

	public boolean isEmpty() {
		return this.errorsSet.isEmpty();
	}
	
	

}
