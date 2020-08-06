/**
 * 06/mag/2009
 */
package it.nch.eb.flatx.flatmodel.xpath;

/**
 * @author gdefacci
 */
public class OrXPathPositionPredicate implements XPathPositionPredicate {
	
	private final XPathPositionPredicate[] preds;

	public OrXPathPositionPredicate(XPathPositionPredicate[] preds) {
		this.preds = preds;
	}
	
	public boolean match(BaseXPathPosition pos) {
		for (int i = 0; i < preds.length; i++) {
			XPathPositionPredicate pred = preds[i];
			if (pred.match(pos)) return true;
		}
		return false;
	}

}
