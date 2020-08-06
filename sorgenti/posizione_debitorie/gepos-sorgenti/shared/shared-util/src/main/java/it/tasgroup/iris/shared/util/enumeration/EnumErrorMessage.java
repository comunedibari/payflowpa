/**
 * 
 */
package it.tasgroup.iris.shared.util.enumeration;


/**
 * @author PazziK
 *
 */
public interface EnumErrorMessage extends MessageDescription{
	
	public EnumSeverityLevel getSeverityLevel();

	public void setSeverityLevel(EnumSeverityLevel severityLevel);

}
