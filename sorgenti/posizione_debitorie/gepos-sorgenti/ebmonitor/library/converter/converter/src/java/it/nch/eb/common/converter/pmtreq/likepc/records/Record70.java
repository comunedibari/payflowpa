package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;

/**
 * generated 
 */
public class Record70 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6024505494795726931L;
	public Record70() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", "70");
	}

	public final Converter	filler		= createFixedValue(" ");
	public final Converter	tipoRecord	= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	instrId		= createXPath(numb7, "PMRQ:PmtId/PMRQ:InstrId", true);
	public final Converter	blank		= createGetBindingValue(fill59, BLANK);
	public final Converter	pmtMtd		= create(fill1, new XPathsMapBindinsManagerStringFunction() {

		public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
			String value = getSuffixValue("../PMRQ:PmtMtd", pos, elemsMap);
			if ("TRF".equals(value)) return " ";
			if ("TRA".equals(value) || "CHK".equals(value)) return "3";
			throw new IllegalStateException();
		}

	});

	public final Converter	endToEndId	= createXPath(fill30, "PMRQ:PmtId/PMRQ:EndToEndId", true);
	public final Converter	blank1		= createGetBindingValue(fill20, BLANK);
	
}