/**
 * Created on 01/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public interface BeanEffect {

	void apply(IRecordStructure rs, XPathPosition pos, Object value);
}
