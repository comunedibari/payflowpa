/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionRecordWithSia;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * @author gdefacci
 */
public class RecordTestaBody extends ConversionRecordWithSia implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 2704582741770456638L;

	public RecordTestaBody() {
//		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr", "SB");
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr", "SB");
	}

	public final ExpressionConversionInfo	filler				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRecord			= createGetBindingValue(fill2, TIPO_RECORD);

	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);
	public final ExpressionConversionInfo	msgId				= createXPath(fill35, "PMRQ:MsgId");
	public final ExpressionConversionInfo	creDtTm				= createXPath(dateTime19, "PMRQ:CreDtTm");
	public final ExpressionConversionInfo	nbOfTxs				= createXPath(numb15, "PMRQ:NbOfTxs");
	public final ExpressionConversionInfo	ctrlSum				= createXPath(fd_number23_5, "PMRQ:CtrlSum");
	public final ExpressionConversionInfo	grpg				= createXPath(fill4, "PMRQ:Grpg");
	public final ExpressionConversionInfo	nm					= createXPath(fill70, "PMRQ:InitgPty/PMRQ:Nm", true);
	public final ExpressionConversionInfo	taxIdNb				= createXPath(fill35,"PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb", true);
	public final ExpressionConversionInfo	id					= createXPath(fill35, "PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	public final ExpressionConversionInfo	issr				= createXPath(fill35, "PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Issr");
	public final Converter					sia					= initSia();
	public final ExpressionConversionInfo	prtry				= createXPath(fill35, "PMRQ:FwdgAgt/PMRQ:FinInstnId/PMRQ:ClrSysMmbId/PMRQ:Prtry", true);
	public final ExpressionConversionInfo	bodyRowsNumber		= createGetBindingValue(numb7, BODY_REC_COUNT, true);

	protected Converter initSia() {
		return createGetBindingValue(fill5, SIA, true);
	}

}
