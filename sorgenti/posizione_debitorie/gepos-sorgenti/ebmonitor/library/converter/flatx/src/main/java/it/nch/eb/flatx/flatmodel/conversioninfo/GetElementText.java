/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;



/**
 * simply return the content of the given element
 * @author gdefacci
 */
public class GetElementText implements XPathsMapBindinsManagerStringFunction,
	XPathMapFunction {
	
	private final BaseXPathPosition basePath;
	private final Converter	converter;
	
	public GetElementText(String basePath) {
		this(basePath, null);
	}
	
	public GetElementText(String basePaths, Converter cnv) {
		this(XPathsParser.instance.parseXPath(basePaths), cnv );
	}
	
	public GetElementText(BaseXPathPosition basePath) {
		this(basePath, null);
	}
	
	public GetElementText(BaseXPathPosition basePaths, Converter cnv) {
		this.basePath = basePaths;
		this.converter = cnv;
	}
	
	public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
		return (String) getValue(pos, elemsMap);
	}

	public Object getValue(XPathPosition pos, IXPathMapScope elemsMap) {
		BaseXPathPosition rpos = pos.concat(basePath);
		String res = elemsMap.get(rpos);
		if (converter!=null) res = converter.encode(res);
		return res;

	}

}
