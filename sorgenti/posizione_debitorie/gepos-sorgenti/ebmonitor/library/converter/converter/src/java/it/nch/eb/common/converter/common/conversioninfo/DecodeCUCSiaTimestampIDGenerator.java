/**
 * Created on 13/gen/08
 */
package it.nch.eb.common.converter.common.conversioninfo;

import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

import java.util.Map;


/**
 * @author gdefacci
 */
public class DecodeCUCSiaTimestampIDGenerator implements XPathsMapBindinsManagerStringFunction {

	private BaseXPathPosition	xpath;
	public DecodeCUCSiaTimestampIDGenerator(String xpath) {
		this.xpath = XPathsParser.instance.parseXPath(xpath);
	}

	public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
		BaseXPathPosition rpos = pos.concat(xpath);
		String cucValue = elemsMap.get(rpos);
		Map mappa = (Map) bindingManager.get(ConversionsConsts.CUC_SIA_MAP);
		String sia = decodeCuc(cucValue, mappa);
		return sia + System.currentTimeMillis();
	}
	

	public static String decodeCuc(String cucValue, Map mappa) {
		if (mappa==null || cucValue==null) {
			return " ";
		}
		String sia = (String) mappa.get(cucValue);
		if (sia==null || "".equals(sia.trim())) {
			sia = " ";
		}
		return sia;
	}

}
