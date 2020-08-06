/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import java.io.Serializable;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.CoreConverters;
import it.nch.eb.flatx.flatmodel.MappedXmlRecord;
import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.BaseXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.StableBaseXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.StableXPathMapFunction;
import it.nch.eb.flatx.flatmodel.conversioninfo.TypedXPathMapFunction;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapFunction;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;


/**
 * @author gdefacci
 */
public class XPathToObjectConvertBuilder implements Serializable {
	
	private static final long serialVersionUID = 4462045639118917820L;

	private static final boolean defaultOptional = true; 
		
	private int pos = 1;
	protected XPathsParser xpathParser = XPathsParser.instance;
	
	protected int getPosAndInc() {
		int res = pos;
		pos ++;
		return res;
	}
	
	public IXPathToObjectConversionInfo create(BaseXPathPosition xpath) {
		return new XPathToObjectConversionInfo(getPosAndInc(), CoreConverters.IDENTITY, xpath, defaultOptional);
	}
	public IXPathToObjectConversionInfo create(BaseXPathPosition xpath, boolean optional) {
		return new XPathToObjectConversionInfo(getPosAndInc(), CoreConverters.IDENTITY, xpath, optional);
	}
	
	public IXPathToObjectConversionInfo create(Converter delegate, BaseXPathPosition xpath) {
		return new XPathToObjectConversionInfo(getPosAndInc(), delegate, xpath, defaultOptional);
	}
	
	public IXPathToObjectConversionInfo create(Converter delegate, BaseXPathPosition xpath, boolean optional) {
		return new XPathToObjectConversionInfo(getPosAndInc(), delegate, xpath, optional);
	}
	
	public IXPathToObjectConversionInfo create(ToObjectConverter cnv, BaseXPathPosition xpath) {
		return new XPathToObjectConversionInfo(getPosAndInc(), CoreConverters.IDENTITY, cnv, xpath, defaultOptional);
	}
	
	public IXPathToObjectConversionInfo create(Converter delegate, ToObjectConverter cnv, BaseXPathPosition xpath) {
		return new XPathToObjectConversionInfo(getPosAndInc(), delegate, cnv, xpath, defaultOptional);
	}
	
	public IXPathToObjectConversionInfo create(ToObjectConverter cnv, BaseXPathPosition xpath,
			boolean optional) {
		return new XPathToObjectConversionInfo(getPosAndInc(), CoreConverters.IDENTITY, cnv, xpath, optional);
	}
	
	public IXPathToObjectConversionInfo create(Converter delegate, ToObjectConverter cnv, BaseXPathPosition xpath,
			boolean optional) {
		return new XPathToObjectConversionInfo(getPosAndInc(), delegate, cnv, xpath, optional);
	}
	
	public IXPathToObjectConversionInfo create(TypedXPathMapFunction ci) {
		return baseXPathToObjectConversionInfo(getPosAndInc(), ci.getTargetClass(), ci);
	}

	protected BaseXPathToObjectConversionInfo baseXPathToObjectConversionInfo(
			int ps, Class tc, XPathMapFunction xpmf) {
		if (xpmf instanceof StableXPathMapFunction) {
			return new StableBaseXPathToObjectConversionInfo(ps, tc, xpmf);
		} else {
			return new BaseXPathToObjectConversionInfo(ps, tc, xpmf);
		}
	}
	
	public IXPathToObjectConversionInfo create(String sxpth) {
		BaseXPathPosition xpath = xpathParser.parseXPath(sxpth);
		return create(xpath);
	}
	
	public IXPathToObjectConversionInfo create(String sxpth, boolean optional) {
		BaseXPathPosition xpath = xpathParser.parseXPath(sxpth);
		return create(xpath, optional);
	}
	
	public IXPathToObjectConversionInfo create(Converter delegate, String sxpth) {
		BaseXPathPosition xpath = xpathParser.parseXPath(sxpth);
		return create(delegate, xpath );
	}
	
	public IXPathToObjectConversionInfo create(Converter delegate, String sxpth, boolean optional) {
		BaseXPathPosition xpath = xpathParser.parseXPath(sxpth);
		return create(delegate, xpath, optional);
	}
	
	public IXPathToObjectConversionInfo create(Converter delegate, ToObjectConverter cnv, String sxpth) {
		BaseXPathPosition xpath = xpathParser.parseXPath(sxpth);
		return create(delegate, cnv, xpath);
	}
	
	public IXPathToObjectConversionInfo create(Converter delegate, ToObjectConverter cnv, String sxpth,
			boolean optional) {
		BaseXPathPosition xpath = xpathParser.parseXPath(sxpth);
		return create(delegate, cnv, xpath, optional);
	}
	
	public IXPathToObjectConversionInfo create(ToObjectConverter cnv, String sxpath) {
		BaseXPathPosition xpath = xpathParser.parseXPath(sxpath);
		return create(cnv, xpath);
	}
	
	public IXPathToObjectConversionInfo create(ToObjectConverter cnv, String sxpath,
			boolean optional) {
		BaseXPathPosition xpath = xpathParser.parseXPath(sxpath);
		return create(cnv, xpath, optional);
	}
	
	public IXPathToObjectConversionInfo create(MappedXmlRecord rec) {
		return new MappedRecordXPathConversionInfo(getPosAndInc(), rec);
	}
	
	public IXPathToObjectConversionInfo create(XmlRecord rec, Class klass) {
		return create(rec, new NewInstanceObjectBuilder(klass));
	}
	
	public IXPathToObjectConversionInfo create(XmlRecord rec, ObjectBuilder objBldr) {
		return new RecordXPathConversionInfo(getPosAndInc(), rec, objBldr);
	}
	
	public IXPathToObjectConversionInfo createSeq(XmlRecord rec, Class kls) {
		return new RecordSeqXPathConversionInfo(getPosAndInc(), rec, new NewInstanceObjectBuilder(kls));
	}
	public IXPathToObjectConversionInfo createSeq(XmlRecord rec, ObjectBuilder objBldr) {
		return new RecordSeqXPathConversionInfo(getPosAndInc(), rec, objBldr);
	}
	
	public IXPathToObjectConversionInfo create(Class trgtClass, XPathMapFunction xpathMapFunction) {
		return baseXPathToObjectConversionInfo(getPosAndInc(), trgtClass, xpathMapFunction);
	}
	
	public IXPathToObjectConversionInfo create(XPathMapFunction xpathMapFunction) {
		return baseXPathToObjectConversionInfo(getPosAndInc(), null, xpathMapFunction);
	}
	
	public IXPathToObjectConversionInfo createSeq(String itemXPath, 
			XPathMapFunction xpathMapFunction) {
		return createSeq(XPathsParser.instance.parseXPathPosition(itemXPath), xpathMapFunction);
	}
	
	public IXPathToObjectConversionInfo createSeq(XPathPosition itemXPath, 
			XPathMapFunction xpathMapFunction) {
		TypedXPathMapFunction seqXpathMapFunction = new XPathMapFunctionSeq(itemXPath, xpathMapFunction);
		return baseXPathToObjectConversionInfo(getPosAndInc(), null, seqXpathMapFunction);
	}

}
