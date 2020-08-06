package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.SubstringExtractorConverter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * generated 
 */
public class Record17 extends ConversionRecord implements BaseConverters, ConversionsConsts, ElementPredicate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -353306388182333323L;


	public Record17() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", "17");
	}
	
	public final Converter filler = createFixedValue(" ");
	public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter instrId = createXPath(numb7, "PMRQ:PmtId/PMRQ:InstrId", true);
	public final Converter iban =  createXPath(new SubstringExtractorConverter(0,2), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter iban1 = createXPath(new SubstringExtractorConverter(2,4), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter iban2 = createXPath(new SubstringExtractorConverter(4,5), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter iban3 = createXPath(new SubstringExtractorConverter(5,10), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter iban4 = createXPath(new SubstringExtractorConverter(10,15), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter iban5 = createXPath(new SubstringExtractorConverter(15,27), "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final Converter blank = createGetBindingValue(fill7, BLANK);
	public final Converter blank1 = createGetBindingValue(fill76, BLANK);
	

	public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
		return includeSuffix("PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", pos, elemsMap);
	}
}