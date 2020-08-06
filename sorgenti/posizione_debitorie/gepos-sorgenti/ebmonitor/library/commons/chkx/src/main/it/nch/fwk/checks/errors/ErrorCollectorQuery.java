/**
 * Created on 22/mag/07
 */
package it.nch.fwk.checks.errors;

import java.util.List;
import java.util.Set;

/**
 * @author gdefacci
 */
public interface ErrorCollectorQuery {
	
	ErrorInfo getError(QualifiedError errorId);
	/**
	 * non null error sample field, are supposed to be search criteria
	 */
	List/*<ErrorInfo>*/ getErrors(ErrorInfo errorSample);
	List/*<ErrorInfo>*/ getErrors(QualifiedError errorSample);
	List/*<ErrorInfo>*/ getErrors(Class/*<ErrorInfo>*/ errorClass);
	
	Set getErrorSet();
	boolean isEmpty();
	boolean containsError(String detail);
	boolean containsSomeErrors(String[] details);
	boolean containsAllErrors(String[] details);

}
