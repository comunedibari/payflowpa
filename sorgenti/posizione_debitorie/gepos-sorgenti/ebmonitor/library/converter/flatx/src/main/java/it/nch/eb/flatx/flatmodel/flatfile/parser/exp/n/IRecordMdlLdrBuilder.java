/**
 * Created on 04/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BaseParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BeanParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.LineParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.SequenceParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BaseRecordFunctions;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;


/**
 * @author gdefacci
 */
public class IRecordMdlLdrBuilder extends MdlLoadrBuilder {

	public IRecordMdlLdrBuilder() {
		super(new BaseRecordFunctions());
	}

	public XPathPosition getBasePath(IRecordStructure rec) {
		if (rec instanceof IParser) {
			return BaseParser.findBaseXPath((IParser) rec);
		} else {
			throw new IllegalStateException("Not a IParser " + rec);
		}
	}
	
	public void preserveXPath(IRecordStructure recStruct) {
		if (recStruct instanceof IParser) {
			if (recStruct instanceof LineParser) preserveXPath(((LineParser)recStruct).getIRecord());
			else if (recStruct instanceof SequenceParser) preserveXPath(((SequenceParser)recStruct).getItemStrucuture());
			else if (recStruct instanceof BeanParser) {
				BeanParser bstr = (BeanParser) recStruct;
				for (int i = 0; i < bstr.getItemStructures().length; i++) {
					IRecordStructure innerRec = bstr.getItemStructures()[i];
					preserveXPath(innerRec);
				}
			} else {
				throw new IllegalStateException("not a valid IParser ");
			}
		} else {
			throw new IllegalStateException("Not a IParser " + recStruct);
		}
	}
	
	public void preserveXPath(IRecord head) {
		XPathPosition base = (XPathPosition) XPathsParser.instance.parseXPath(head.getBaseXPath());
		ExpressionConversionInfo[] cis = head.getConversionInfos();
		for (int i = 0; i < cis.length; i++) {
			ExpressionConversionInfo eci = cis[i];
			if (eci instanceof XPathConversionInfo) {
				XPathConversionInfo xci = (XPathConversionInfo) eci;
				BaseXPathPosition rpos = base.concat(xci.getXPath());
				preserveXPath(rpos);
			}
		}
	}

}
