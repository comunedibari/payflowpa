/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public interface XPathMapFunction {

	public Object getValue(XPathPosition pos, IXPathMapScope/*<String,Object>*/ elem);
}
