/**
 * Created on 06/mag/07
 */
package it.nch.fwk.checks.errors;

import it.nch.fwk.checks.Check;
import it.nch.fwk.checks.context.CheckContext;

/**
 * @author gdefacci
 */
public interface ErrorCollectorCheck extends ErrorCollector, Check {

	void setCheckContext(CheckContext context);
	CheckContext context();
	ErrorCollector checkAndCollectAllErrors(ErrorCollectorCheck qc);
	QualifiedError checkAndCollectError(String detail, Check qc);

}
