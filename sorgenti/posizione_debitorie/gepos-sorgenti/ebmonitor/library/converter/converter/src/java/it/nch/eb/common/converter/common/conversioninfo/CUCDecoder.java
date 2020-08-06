/*
 * Created on Jan 18, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.eb.common.converter.common.conversioninfo;

import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

import java.util.Map;

/**
 * @author bastiap
 *
 */
public class CUCDecoder extends XPathConversionInfo implements XPathsMapBindinsManagerStringFunction {
	
	private static final long	serialVersionUID	= -6804314375731077986L;

	protected static BaseXPathPosition parse(String xpath) {
		return XPathsParser.instance.parseXPath(xpath);
	}

	public CUCDecoder(int pos, SizedConverter delegate, String xpath, boolean optional) {
		this(pos, delegate, null, xpath, optional);
	}

	public CUCDecoder(int pos, SizedConverter delegate, String name, String xpath, boolean optional) {
		super(pos, delegate, name, parse(xpath), optional, fakeMissingXPathHandler);
	}

	public String getValue(XPathPosition pos, XPathsMapBindings elem, IBindingManager bindingManager) {
		String cucValue = getStringValue(pos, elem);
		
		Map mappa = (Map) bindingManager.get(ConversionsConsts.CUC_SIA_MAP);
		String sia = decodeCuc(cucValue, mappa);
		return getConverter().encode(sia);
	}

	public static String decodeCuc(String cucValue, Map mappa) {
		if (mappa==null || cucValue==null) {
//			throw new IllegalStateException("cant find the mandatory key [" + ConversionsConsts.CUC_SIA_MAP + "]");
			return " ";
		}
		String sia = (String) mappa.get(cucValue);
		if (sia==null || "".equals(sia.trim())) {
			sia = " ";
		}
		return sia;
	}

	

}
