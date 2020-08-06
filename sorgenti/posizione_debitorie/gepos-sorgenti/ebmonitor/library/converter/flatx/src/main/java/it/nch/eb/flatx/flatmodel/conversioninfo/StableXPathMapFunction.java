/**
 * 08/mag/2009
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;


/**
 * marker interfaces that states that that  
 * public Object getValue(XPathPosition pos, IXPathMapScope<String,Object> elem);
 * always returns the same value (alias, doesnt care about params) 
 * @author gdefacci
 */
public interface StableXPathMapFunction extends XPathMapFunction {

	
}
