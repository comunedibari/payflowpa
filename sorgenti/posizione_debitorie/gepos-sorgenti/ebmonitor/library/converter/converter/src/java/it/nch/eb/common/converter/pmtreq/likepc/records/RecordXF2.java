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
public class RecordXF2 extends ConversionRecord implements BaseConverters, ConversionsConsts, ElementPredicate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6131355574504078295L;

	public RecordXF2() {
		super("/MSG:CBIPaymentRequestMsg", "XF2");
	}
	
	public final Converter	filler			= createFixedValue(fill1, " ");
	public final Converter	tipoRecord		= createGetBindingValue(fill3, TIPO_RECORD);

	public final Converter sgnt6 = createXPath(fill16, "BODY:CBISgnInf[6]/SGNT:Sgnt"     );
	public final Converter sgnt7 = createXPath(fill16, "BODY:CBISgnInf[7]/SGNT:Sgnt" ,true);
	public final Converter sgnt8 = createXPath(fill16, "BODY:CBISgnInf[8]/SGNT:Sgnt" ,true);
	public final Converter sgnt9 = createXPath(fill16, "BODY:CBISgnInf[9]/SGNT:Sgnt" ,true);
	public final Converter sgnt10 = createXPath(fill16, "BODY:CBISgnInf[10]/SGNT:Sgnt",true);

	public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
		return includeSuffix("BODY:CBISgnInf[6]/SGNT:Sgnt", pos, elemsMap);
	}

}