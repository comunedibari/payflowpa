/**
 * Created on 04/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapPositionEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffectFactory;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdefacci
 */
public class XPathFunctionAndEffectsExecutor implements XPathMapPositionEffect {

	private final XPathFunctionAndEffects[] functionAndEffects;
	private final IBindingManager	bindings;
	
	public XPathFunctionAndEffectsExecutor(IBindingManager bindings, XPathFunctionAndEffects[] functionAndEffects) {
		super();
		if (functionAndEffects != null) this.functionAndEffects = functionAndEffects;
		else this.functionAndEffects = new XPathFunctionAndEffects[0];
		this.bindings = bindings;
	}

	protected static class ObjectAndEffects {
		private final Object object;
		private final XPathFunctionAndEffects	functionEffects;
		public ObjectAndEffects(Object object, XPathFunctionAndEffects functionEffects) {
			this.object = object;
			this.functionEffects = functionEffects;
		}
		public Object getObject() {
			return object;
		}
		public XPathFunctionAndEffects getFunctionEffects() {
			return functionEffects;
		}
		
	}

	public void apply(XPathPosition pos, XPathsMapBindings xpathBindings) {
		ObjectAndEffects[] objEffcts = executeFunctions(pos, xpathBindings);
		executeEffects(pos, objEffcts);
	}

	protected void executeEffects(XPathPosition pos, ObjectAndEffects[] objEffcts) {
		for (int i = 0; i < objEffcts.length; i++) {
			ObjectAndEffects objEfct = objEffcts[i];
			BeanEffectFactory[] beanEffects = objEfct.getFunctionEffects().getBeanEffects();
			for (int j = 0; j < beanEffects.length; j++) {
				BeanEffect efct = beanEffects[j].apply(bindings);
				efct.apply(objEfct.getFunctionEffects().getRecordStructure(), pos, objEfct.getObject());
			}
		}
	}

	protected ObjectAndEffects[] executeFunctions(XPathPosition pos, XPathsMapBindings xpathBindings) {
//		ObjectAndEffects[] objEffcts = new ObjectAndEffects[functionAndEffects.length];
		List res = new ArrayList();
		for (int i = 0; i < functionAndEffects.length; i++) {
			XPathFunctionAndEffects fEffcts = functionAndEffects[i];
			if (fEffcts.getRecordFunction().canApply(fEffcts.getRecordStructure(), pos, xpathBindings, bindings)) {
				Object obj = fEffcts.getRecordFunction().apply(fEffcts.getRecordStructure(), pos, xpathBindings, bindings);
				if (obj == null) throw new NullPointerException();
				res.add(new ObjectAndEffects(obj, fEffcts));
			}
		}
		return (ObjectAndEffects[]) res.toArray(new ObjectAndEffects[0]);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("XPathFunctionAndEffectsExecutor effects :");
		for (int i = 0; i < functionAndEffects.length; i++) {
			XPathFunctionAndEffects fae = functionAndEffects[i];
			sb.append("\n");
			sb.append(i);
			sb.append(")");
			sb.append(fae);
		}
		return sb.toString();
	}

	public IBindingManager getBindings() {
		return bindings;
	}

}