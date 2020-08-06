/**
 * Created on 05/dic/08
 */
package it.nch.eb.cbi2.validator;

import java.util.Set;

import org.xml.sax.helpers.DefaultHandler;


/**
 * @author gdefacci
 */
public abstract class ErrorSetHandler extends DefaultHandler {
	
	public abstract Set getErrorSet();

}
