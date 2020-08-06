/**
 * Created on 18/mag/07
 */
package it.nch.fwk.checks.errors.processing;

import java.util.Map;

/**
 * @author gdefacci
 */
public interface ITemplateProvider {
	
	String getTemplateValue(String id, Map templateContext);

}
