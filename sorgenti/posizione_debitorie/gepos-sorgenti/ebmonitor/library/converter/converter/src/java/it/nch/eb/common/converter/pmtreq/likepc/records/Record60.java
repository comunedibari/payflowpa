package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class Record60 extends Record50or60 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6241675061943509503L;

	public Record60() {
		super("60");
	}
	
	public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
		return addressLongerThan90Chars(pos, elemsMap);
	}
	
}