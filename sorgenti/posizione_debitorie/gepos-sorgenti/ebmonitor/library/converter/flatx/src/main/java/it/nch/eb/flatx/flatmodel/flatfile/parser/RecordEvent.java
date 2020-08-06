/**
 * Created on 08/mag/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;


/**
 * @author gdefacci
 */
public interface RecordEvent {

	Object getContainer();
	IParser getPropertyParser();
	Object getPropertyValue();
	String getPropertyName();
	
	
	
}