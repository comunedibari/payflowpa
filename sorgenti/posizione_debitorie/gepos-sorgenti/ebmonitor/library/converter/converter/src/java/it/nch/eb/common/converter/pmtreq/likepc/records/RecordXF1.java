package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * generated 
 */
public class RecordXF1 extends ConversionRecord implements BaseConverters, ConversionsConsts, ElementPredicate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5313196169041727540L;

	public RecordXF1() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest", "XF1");
	}
	
	public final Converter	filler			= createFixedValue(fill1, " ");
	public final Converter	tipoRecord		= createGetBindingValue(fill3, TIPO_RECORD);
	
	public final Converter sgnt1 = createXPath(fill16, "BODY:CBISgnInf[1]/SGNT:Sgnt" );
	public final Converter sgnt2 = createXPath(fill16, "BODY:CBISgnInf[2]/SGNT:Sgnt", true );
	public final Converter sgnt3 = createXPath(fill16, "BODY:CBISgnInf[3]/SGNT:Sgnt", true );
	public final Converter sgnt4 = createXPath(fill16, "BODY:CBISgnInf[4]/SGNT:Sgnt", true );
	public final Converter sgnt5 = createXPath(fill16, "BODY:CBISgnInf[5]/SGNT:Sgnt", true );

	public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
		return includeSuffix("BODY:CBISgnInf[1]/SGNT:Sgnt", pos, elemsMap);
	}
}
