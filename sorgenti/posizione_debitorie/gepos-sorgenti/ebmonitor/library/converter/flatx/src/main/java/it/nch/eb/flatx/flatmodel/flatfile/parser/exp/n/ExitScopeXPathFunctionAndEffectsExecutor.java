/**
 * Created on 03/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapPositionEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.XPathMapPositionEffectFactory;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;


/**
 * @author gdefacci
 */
public class ExitScopeXPathFunctionAndEffectsExecutor extends XPathFunctionAndEffectsExecutor {
	
	public static final XPathMapPositionEffectFactory factory(
			final XPathFunctionAndEffects[] effects) {
		return new XPathMapPositionEffectFactory() {

			public XPathMapPositionEffect apply(IBindingManager bindings) {
				return new ExitScopeXPathFunctionAndEffectsExecutor(bindings, effects);
			}

		};
	}
	
	public ExitScopeXPathFunctionAndEffectsExecutor(IBindingManager bindings, XPathFunctionAndEffects[] functionAndEffects) {
		super(bindings, functionAndEffects);
	}

	public void apply(XPathPosition pos, XPathsMapBindings xpathBindings) {
		ObjectAndEffects[] objEffcts = executeFunctions(pos, xpathBindings);
		getBindings().exitScope();
		executeEffects(pos, objEffcts);
	}

}
