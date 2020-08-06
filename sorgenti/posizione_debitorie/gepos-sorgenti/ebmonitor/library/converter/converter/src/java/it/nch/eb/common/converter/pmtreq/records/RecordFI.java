/**
 * Created on 20/feb/08
 */
package it.nch.eb.common.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class RecordFI extends ConversionRecord implements ElementPredicate, BaseConverters, ConversionsConsts {
	
	private static final long	serialVersionUID	= 7935359508856640264L;

	public RecordFI() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest", "FI");
	}
	
	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter  sgnt1	= createXPath(fill16, "BODY:CBISgnInf[1]/SGNT:Sgnt", true);
	public final Converter  sgnt2	= createXPath(fill16, "BODY:CBISgnInf[2]/SGNT:Sgnt", true);
	public final Converter  sgnt3	= createXPath(fill16, "BODY:CBISgnInf[3]/SGNT:Sgnt", true);
	public final Converter  sgnt4	= createXPath(fill16, "BODY:CBISgnInf[4]/SGNT:Sgnt", true);
	public final Converter  sgnt5	= createXPath(fill16, "BODY:CBISgnInf[5]/SGNT:Sgnt", true);
	public final Converter  sgnt6	= createXPath(fill16, "BODY:CBISgnInf[6]/SGNT:Sgnt", true);
	public final Converter  sgnt7	= createXPath(fill16, "BODY:CBISgnInf[7]/SGNT:Sgnt", true);
	public final Converter  sgnt8	= createXPath(fill16, "BODY:CBISgnInf[8]/SGNT:Sgnt", true);
	public final Converter  sgnt9	= createXPath(fill16, "BODY:CBISgnInf[9]/SGNT:Sgnt", true);
	public final Converter  sgnt10	= createXPath(fill16, "BODY:CBISgnInf[10]/SGNT:Sgnt", true);
	
	public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
		return includeSuffix("BODY:CBISgnInf[1]/SGNT:Sgnt", pos, elemsMap);
	}
	
}