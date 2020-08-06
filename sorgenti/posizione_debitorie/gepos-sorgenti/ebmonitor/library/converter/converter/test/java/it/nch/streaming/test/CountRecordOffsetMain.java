/**
 * Created on 03/ott/08
 */
package it.nch.streaming.test;

import org.slf4j.Logger;

import it.nch.eb.common.converter.pmtreq.records.Record20;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.RecordImpl;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathConversionInfo;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import it.nch.eb.ubi.converter.pmtreq.records.Record30;


/**
 * @author gdefacci
 */
public class CountRecordOffsetMain {
	
	private static final Logger log = ResourcesUtil.createLogger(CountRecordOffsetMain.class);
	
	static class InputPair {
		public final RecordImpl rec;
		public final int offset;
		
		public InputPair(final RecordImpl rec, final int offset) {
			this.rec = rec;
			this.offset = offset;
		}
		
	}
	
	public static void main(String[] args) {
		
//		InputPair input = new InputPair(new Record01(), 649);
//		InputPair input = new InputPair(new Record30(), 537);
//		InputPair input = new InputPair(new Record05(), 162);
//		InputPair input = new InputPair(new Record10(), 75);
//		InputPair input = new InputPair(new Record20(),410);
		InputPair input = new InputPair(new Record20(),410);
//		InputPair input = new InputPair(new Sepa2000RecordTesta(), 19);
		ExpressionConversionInfo res = getConversionInfo(input);
		BaseXPathPosition fullxpath = fullPath(res, input);
		
		System.out.println(res.toString());
		System.out.println(fullxpath.toString());
		
	}
	
	static BaseXPathPosition fullPath(ExpressionConversionInfo eci, InputPair input) {
		if (eci instanceof XPathConversionInfo) {
			XPathConversionInfo xci = (XPathConversionInfo) eci;
			XPathPosition bxp = XPathsParser.instance.parseXPathPosition( input.rec.getBaseXPath() );
			return bxp.concat(xci.getXPath());
		} else {
			return null;
		}
	}

	static ExpressionConversionInfo getConversionInfo(InputPair input) {
		ExpressionConversionInfo[] cis = input.rec.getConversionInfos();
		int ofst = 0; 
		ExpressionConversionInfo res = null;
		for (int i = 0; i < cis.length && res==null; i++) {
			ExpressionConversionInfo ci = cis[i];
			ofst += ci.getLength().intValue();
			if (ofst>= input.offset) {
				res = ci;
			}
		}
		return res;
	}

}
