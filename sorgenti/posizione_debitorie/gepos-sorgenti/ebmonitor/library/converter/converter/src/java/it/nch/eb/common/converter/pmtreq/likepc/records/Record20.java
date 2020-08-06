package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record20 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3024029939806314787L;
	public Record20() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", "20");
	}
	
	public final Converter	filler		= createFixedValue(" ");
	public final Converter	tipoRecord	= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	instrId		= createXPath(numb7, "PMRQ:PmtId/PMRQ:InstrId", true);
	public final Converter	nm			= createXPath(fill30, "../PMRQ:Dbtr/PMRQ:Nm", true);
	public final Converter	blank		= createGetBindingValue(fill60, BLANK);
	public final Converter	taxIdNb		= createXPath(fill16, "../PMRQ:Dbtr/PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb", true);
	public final Converter	blank1		= createGetBindingValue(fill4, BLANK);
	
}