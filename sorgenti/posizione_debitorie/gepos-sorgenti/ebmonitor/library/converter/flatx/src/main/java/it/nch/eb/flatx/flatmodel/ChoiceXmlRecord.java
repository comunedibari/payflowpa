/**
 * 29/apr/2009
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapFunction;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class ChoiceXmlRecord implements XPathMapFunction {
	
	private final PredicateXmlRecordPair[] avaiableXmlRecordsPredicate;
	
	public ChoiceXmlRecord(PredicateXmlRecordPair[] avaiableXmlRecordsPredicate) {
		this.avaiableXmlRecordsPredicate = avaiableXmlRecordsPredicate;
	}
	
	public PredicateXmlRecordPair[] getXmlRecordsPredicate() {
		return avaiableXmlRecordsPredicate;
	}

	/* @Override */
	public Object getValue(XPathPosition pos, IXPathMapScope elem) {
		IMappedXMLRecord xmlrec = null;
		for (int i = 0; i < avaiableXmlRecordsPredicate.length && (xmlrec == null); i++) {
			PredicateXmlRecordPair pair = avaiableXmlRecordsPredicate[i];
			if (pair.getElementPredicate().match(pos, elem)) {
				xmlrec = pair.getXmlRecord();
			}
		}
		if (xmlrec==null) return null;
		else return xmlrec.getValue(pos, elem);
	}
}
