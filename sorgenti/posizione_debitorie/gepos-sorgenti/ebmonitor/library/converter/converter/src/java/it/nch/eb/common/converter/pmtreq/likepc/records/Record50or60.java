package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

import java.util.Iterator;

/**
 * @author gdefacci
 */
public abstract class Record50or60 extends ConversionRecord implements ElementPredicate, BaseConverters, ConversionsConsts {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1356658638899920919L;

	public Record50or60(String tipoRecord) {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", tipoRecord);
	}

	public final Converter filler = createFixedValue(" ");
	public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter instrId = createXPath(numb7, "PMRQ:PmtId/PMRQ:InstrId", true);
	public final Converter ustrd = createXPath(fill90, "PMRQ:RmtInf/PMRQ:Ustrd", true);
	public final Converter filler1 = createFixedValue(fill20, " ");
			
	protected boolean addressLongerThan90Chars(XPathPosition pos, IXPathMapScope elemsMap) {
		BaseXPathPosition ustrdPos = XPathsParser.instance.parseXPath("PMRQ:Ustrd");
		StringBuffer sb = new StringBuffer();
		for (Iterator it = elemsMap.entries(); it.hasNext();) {
			IXPathMapScope.Entry entry = (IXPathMapScope.Entry) it.next();
			if (entry.getPosition().getLastSegment().match(ustrdPos.getPrefix(), ustrdPos.getName())) {
				sb.append( entry.getValue() );
			}
		}
		return sb.toString().length() >= 90;
	}

}