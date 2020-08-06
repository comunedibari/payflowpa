package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class Record50 extends Record50or60 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2345654124710365602L;

	public Record50() {
		super("50");
	}
	
	public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
		return !addressLongerThan90Chars(pos, elemsMap);
	}
}