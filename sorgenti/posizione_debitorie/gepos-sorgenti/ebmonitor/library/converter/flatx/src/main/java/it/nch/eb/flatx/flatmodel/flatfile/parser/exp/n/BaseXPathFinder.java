/**
 * Created on 04/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public interface BaseXPathFinder {
	
	XPathPosition getBasePath(IRecordStructure recordStructure);

}
