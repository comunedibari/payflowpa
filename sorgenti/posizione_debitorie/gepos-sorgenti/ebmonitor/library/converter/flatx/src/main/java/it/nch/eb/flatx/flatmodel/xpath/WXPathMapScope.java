/**
 * Created on 30/mar/2009
 */
package it.nch.eb.flatx.flatmodel.xpath;


/**
 * @author gdefacci
 */
public interface WXPathMapScope extends IXPathMapScope{
	
	void put(BaseXPathPosition pos, String textValue);
	
	WXPathMapScope getWParent();

}
