/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;

/**
 * @author gdefacci
 */
public interface IRecord {
	
	String getName(); 
	String getBaseXPath();
	IBindingManager getRecordBindings();
	ExpressionConversionInfo[] getConversionInfos();

}