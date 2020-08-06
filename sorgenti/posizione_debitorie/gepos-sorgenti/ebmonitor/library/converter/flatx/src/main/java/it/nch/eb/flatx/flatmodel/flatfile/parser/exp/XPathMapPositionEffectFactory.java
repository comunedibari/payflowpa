/**
 * Created on 01/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapPositionEffect;


/**
 * @author gdefacci
 */
public interface XPathMapPositionEffectFactory {
	
	XPathMapPositionEffect apply(IBindingManager bindings);

}
