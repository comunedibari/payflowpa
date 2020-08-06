/**
 * 06/mag/2009
 */
package it.nch.eb.flatx.flatmodel.xpath;

/**
 * @author gdefacci
 */
public class AndXPathPositionPredicate implements XPathPositionPredicate {
	
	private final XPathPositionPredicate[] preds;

	public AndXPathPositionPredicate(XPathPositionPredicate[] preds) {
		this.preds = preds;
	}
	
	public boolean match(BaseXPathPosition pos) {
		for (int i = 0; i < preds.length; i++) {
			XPathPositionPredicate pred = preds[i];
			if (!pred.match(pos)) return false;
		}
		return true;
	}

}
