/**
 * Created on 02/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

import java.util.ArrayList;
import java.util.List;

public final class SetSequencePropertyOnBindingManager implements BeanEffect {

	private final IBindingManager	bindings;

	public SetSequencePropertyOnBindingManager(IBindingManager bindings) {
		this.bindings = bindings;
	}

	public void apply(IRecordStructure rs, XPathPosition pos, Object value) {
		if (value != null) {
			List lst = (List) bindings.get(rs.getName());
			if (lst==null) {
				lst = new ArrayList();
				bindings.setOrDefine(rs.getName(), lst);
			}
			lst.add(value);
		}
	}

	public String toString() {
		return "set sequence property on BindingManager";
	}
	
	
}