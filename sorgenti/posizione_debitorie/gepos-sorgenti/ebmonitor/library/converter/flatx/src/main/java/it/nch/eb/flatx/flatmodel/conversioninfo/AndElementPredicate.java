/**
 * 29/mag/2009
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class AndElementPredicate implements ElementPredicate {

	private final ElementPredicate[] predicates;
	public AndElementPredicate(ElementPredicate[] predicates) {
		this.predicates = predicates;
	}
	
	public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
		for (int i = 0; i < predicates.length; i++) {
			ElementPredicate pred = predicates[i];
			if (!pred.match(pos, elemsMap)) return false;
		}
		return true;
	}


}
