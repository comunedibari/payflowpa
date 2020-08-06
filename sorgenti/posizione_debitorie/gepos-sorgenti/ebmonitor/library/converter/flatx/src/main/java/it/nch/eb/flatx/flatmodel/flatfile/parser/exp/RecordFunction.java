/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;

public interface RecordFunction {
	
	boolean canApply(IRecordStructure recStruct,
			XPathPosition pos, 
			XPathsMapBindings xpathBindings, IBindingManager lbindings);
	
	Object apply(IRecordStructure recStruct,
				XPathPosition pos, 
				XPathsMapBindings xpathBindings, IBindingManager lbindings);
}