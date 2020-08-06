/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;

/**
 * @author gdefacci
 */
public interface IXmlRecord {
	
	String getName(); 
	String getBaseXPath();
//	IBindingManager getRecordBindings();
	IXPathToObjectConversionInfo[] getConversionInfos();

}