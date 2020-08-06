package it.nch.eb.ubi.converter.pmtreq.records;

import java.util.Map;

import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.FillerConverter;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.MissingXPathHandler;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

public class MapDecoder extends XPathConversionInfo implements XPathsMapBindinsManagerStringFunction {

	private static final long serialVersionUID = 1L;
	
	private final String mapName;

	public MapDecoder(int pos, String mapName, FillerConverter delegate, String xpath, boolean optional) {
		super(pos, delegate, XPathsParser.instance.parseXPath(xpath), optional);
		this.mapName=mapName;
	}

	public String getValue(XPathPosition pos, XPathsMapBindings elem,
			IBindingManager bindingManager) {
		Map ibanMap = (Map) bindingManager.get(mapName);
		String rawValue = getStringValue(pos, elem);
		String v;
		if (ibanMap == null) v = rawValue;
		else {
			v = (String) ibanMap.get(rawValue);
			if (v == null) v = rawValue;
		}
		
		return getConverter().encode(v);
	}
	

}
