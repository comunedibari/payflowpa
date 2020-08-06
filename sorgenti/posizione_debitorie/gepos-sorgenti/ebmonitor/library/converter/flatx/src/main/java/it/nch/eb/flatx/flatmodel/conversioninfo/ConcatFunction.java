/**
 * Created on 14/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.CoreConverters;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;


/**
 * @author gdefacci
 */
public class ConcatFunction implements XPathsMapBindinsManagerStringFunction,
	XPathMapFunction {

	final BaseXPathPosition[] xpaths;
	final Converter[] converters;
	
	public ConcatFunction(BaseXPathPosition[] xpaths, Converter[] converters) {
		if (xpaths.length!=converters.length) throw new IllegalStateException();
		this.converters = converters;
		this.xpaths = xpaths;
	}

	public ConcatFunction(String[] xpaths, Converter[] converters) {
		this( parsePaths( xpaths ), converters);
	}
	
	private static BaseXPathPosition[] parsePaths(String[] xpaths2) {
		BaseXPathPosition[] res = new BaseXPathPosition[xpaths2.length];
		for (int i = 0; i < xpaths2.length; i++) {
			String xpthStr = xpaths2[i];
			BaseXPathPosition pos = XPathsParser.instance.parseXPath(xpthStr);
			res[i] = pos;
		}
		return res;
	}

	public ConcatFunction(String[] xpaths) {
		this(xpaths, identityConverters(xpaths.length));
	}
	
	static Converter[] identityConverters(int len) {
		Converter[] res = new Converter[len];
		for (int i = 0; i < res.length; i++) {
			res[i] = CoreConverters.IDENTITY;
		}
		return res;
	}

	public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
		return (String) getValue(pos, elemsMap);
	}

	public Object getValue(XPathPosition pos, IXPathMapScope elemsMap) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.xpaths.length; i++) {
			BaseXPathPosition sfx = this.xpaths[i];
			BaseXPathPosition realPos = pos.concat(sfx);
			String str = elemsMap.get(realPos);
			Converter converter = converters[i];
			sb.append( converter.encode(str) );
		}
		return sb.toString();
	}

}
