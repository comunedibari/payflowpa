/**
 * Created on 02/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

public final class SetSimplePropertyOnBindingManager implements BeanEffect {

	private final IBindingManager	bindings;

	public SetSimplePropertyOnBindingManager(IBindingManager bindings) {
		this.bindings = bindings;
	}

	public void apply(IRecordStructure rs, XPathPosition pos, Object value) {
		if (value!=null) {
			bindings.setOrDefine(rs.getName(), value);
		}
	}
	
	public String toString() {
		return "set simple property on BindingManager";
	}

}