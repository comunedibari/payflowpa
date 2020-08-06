/**
 * Created on 24/feb/2009
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;


/**
 * @author gdefacci
 */
public interface RecordModelPut {
	
	void put(IBindingManager bindingManager, IRecordStructure parser,  Object res);

}
