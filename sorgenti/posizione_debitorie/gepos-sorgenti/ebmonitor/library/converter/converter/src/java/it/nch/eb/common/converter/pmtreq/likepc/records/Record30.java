package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated
 */
public class Record30 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6202440335267875073L;
	public Record30() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", "30");
	}

	public final Converter	filler		= createFixedValue(" ");
	public final Converter	tipoRecord	= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	instrId		= createXPath(numb7, "PMRQ:PmtId/PMRQ:InstrId", true);
	public final Converter	nm			= createXPath(fill70, "PMRQ:Cdtr/PMRQ:Nm", true);
	public final Converter	blank		= createGetBindingValue(fill20, BLANK);
	public final Converter	taxIdNb		= createXPath(fill16, "PMRQ:Cdtr/PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb", true);
	public final Converter	blank1		= createGetBindingValue(fill4, BLANK);

}