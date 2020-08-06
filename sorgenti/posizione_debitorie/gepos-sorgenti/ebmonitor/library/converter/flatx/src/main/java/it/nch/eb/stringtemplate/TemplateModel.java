/**
 * Created on 30/apr/08
 */
package it.nch.eb.stringtemplate;

import java.util.Iterator;


/**
 * @author gdefacci
 */
public interface TemplateModel {
	
	Object get(String name);
	void set(String name, Object obj);
	
	Iterator/*<String>*/ names();
}
