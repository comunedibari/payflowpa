/**
 * Created on 23/apr/08
 */
package it.nch.eb.common.converter;

import it.nch.eb.common.converter.common.conversioninfo.CUCDecoder;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.FillerConverter;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.RecordImpl;
import it.nch.eb.flatx.flatmodel.TipoRecordProvider;
import it.nch.eb.flatx.flatmodel.conversioninfo.ConcatFunction;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.FunctionConversionInfoAdapters;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import it.nch.eb.ubi.converter.pmtreq.records.MapDecoder;



/**
 * responsabilities:
 * - make subclass constructor as simple as possible (to leverage genaration) 
 * - exposing all conversion related consts  
 * @author gdefacci
 */
public class ConversionRecord extends RecordImpl 
	implements ConversionsConsts, BaseConverters, TipoRecordProvider {

	private static final long	serialVersionUID	= -805948171721523468L;
	
	public ConversionRecord(String xpath, String tipoRecord) {
		super(xpath);
		bindTipoRecord(tipoRecord);
	}
	
	private void bindTipoRecord(String tipoRecord) {
		bind(ConversionsConsts.TIPO_RECORD, tipoRecord);
	}
	
	public String getTipoRecord() {
		return getRecordBindings().stringValue( TIPO_RECORD );
	}

	private ExpressionConversionInfo createCUCDecoder(int pos, SizedConverter delegate, String xpath, boolean optional) {
		return new CUCDecoder(pos, delegate, xpath, optional);
	}
	
	public ExpressionConversionInfo createCUCDecoder(int pos, SizedConverter delegate, String xpath) {
		return createCUCDecoder(pos, delegate, xpath, false);
	}
	
	public ExpressionConversionInfo createCUCDecoder(SizedConverter delegate, String xpath, boolean optional) {
		return createCUCDecoder(getPos(), delegate, xpath, optional);
	}
	
	public ExpressionConversionInfo createCUCDecoder(SizedConverter delegate, String xpath) {
		return createCUCDecoder(getPos(), delegate, xpath, false);
	}
	
	public static XPathsMapBindinsManagerStringFunction concatXPathExpression(String[] xpaths, Converter[] converters) {
		return new ConcatFunction(xpaths, converters);
	}
	public static XPathsMapBindinsManagerStringFunction concatXPathExpression(String[] xpaths) {
		return new ConcatFunction(xpaths);
	}
	
	public static XPathsMapBindinsManagerStringFunction text(String text) {
		return new FunctionConversionInfoAdapters.FromString(text);
	}
	
	public static boolean includeSuffix(String suffix, XPathPosition pos, IXPathMapScope elemsMap) {
		String res = getSuffixValue(suffix, pos, elemsMap);
		return res != null;
	}

	public static String getSuffixValueOrElse(String suffix, String elseValue, XPathPosition pos, IXPathMapScope elemsMap) {
		String res = getSuffixValue(suffix, pos, elemsMap);
		if (res==null) return elseValue;
		else return res;
	}
	public static String getSuffixValue(String suffix, XPathPosition pos, IXPathMapScope elemsMap) {
		BaseXPathPosition sfx = XPathsParser.instance.parseXPathPosition(suffix);
		BaseXPathPosition rpos = null;
		try {
			rpos = pos.concat(sfx);
		} catch (NullPointerException e) {
			throw e;
		}
		String res = elemsMap.get(rpos);
		return res;
	}
}
