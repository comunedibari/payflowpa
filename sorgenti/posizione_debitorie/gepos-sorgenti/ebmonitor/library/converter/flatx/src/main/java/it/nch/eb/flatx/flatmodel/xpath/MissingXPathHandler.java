/**
 * Created on 28/ago/08
 */
package it.nch.eb.flatx.flatmodel.xpath;




/**
 * @author gdefacci
 */
public interface MissingXPathHandler {
	
	String missingXPath(BaseXPathPosition pos, IXPathMapScope elem);

}
