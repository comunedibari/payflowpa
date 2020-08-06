/**
 * Created on 02/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.IBindingManager;


/**
 * @author gdefacci
 */
public interface BeanEffectFactory {

	public BeanEffect apply(IBindingManager bm);
}
