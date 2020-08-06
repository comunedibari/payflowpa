package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record40 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6307164658061579385L;
	public Record40() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", "40");
	}

	public final Converter	filler		= createFixedValue(" ");
	public final Converter	tipoRecord	= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	instrId		= createXPath(numb7, "PMRQ:PmtId/PMRQ:InstrId", true);
	public final Converter	or			= create(fill30, or(
			new String[] { "PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:AdrLine ",
							"PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:StrtNm", }));

	public final Converter	pstCd		= createXPath(fill5, "PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:PstCd", true);
	public final Converter	twnNm		= createXPath(fill25, "PMRQ:Cdtr/PMRQ:PstlAdr/PMRQ:TwnNm", true);
	public final Converter	blank		= createGetBindingValue(fill50, BLANK);

}