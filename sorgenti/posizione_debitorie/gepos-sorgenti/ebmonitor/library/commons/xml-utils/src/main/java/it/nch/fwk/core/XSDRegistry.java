/**
 * Created on 10/ago/07
 */
package it.nch.fwk.core;

/**
 * resolve the schema urn in a phisical resource (probably in the classpath)
 * @author gdefacci
 */
public interface XSDRegistry {
	
	String getLocation(String schemaId);

}
