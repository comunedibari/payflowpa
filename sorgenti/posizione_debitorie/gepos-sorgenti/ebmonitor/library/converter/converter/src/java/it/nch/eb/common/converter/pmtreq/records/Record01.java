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
public class Record01 extends ConversionRecordWithSia implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 8622607874679410479L;

	public Record01() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", "01");
	}

	public final ExpressionConversionInfo	fiiller				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final ExpressionConversionInfo	instrId				= createXPath(fill35, "PMRQ:PmtId/PMRQ:InstrId");
	public final ExpressionConversionInfo	endToEndId			= createXPath(fill35, "PMRQ:PmtId/PMRQ:EndToEndId");
	public final ExpressionConversionInfo	prtry				= createXPath(fill35, "PMRQ:PmtTpInf/PMRQ:SvcLvl/PMRQ:Prtry", true);
	public final ExpressionConversionInfo	lclInstrmPrtry		= createXPath(fill35, "PMRQ:PmtTpInf/PMRQ:LclInstrm/PMRQ:Prtry",	true);
	public final ExpressionConversionInfo	ctgyPurp			= createXPath(fill4, "PMRQ:PmtTpInf/PMRQ:CtgyPurp", true);
	public final ExpressionConversionInfo	instdAmt			= createXPath(fd_number18_3, "PMRQ:Amt/PMRQ:InstdAmt");
	public final ExpressionConversionInfo	instdAmtCcy			= createXPath(fill3, "PMRQ:Amt/PMRQ:InstdAmt/@Ccy");
	public final ExpressionConversionInfo	chqInstr			= createXPath(fill4, "PMRQ:ChqInstr", true);

	public final ExpressionConversionInfo	pstlAdrsNm			= createXPath(fill70, "PMRQ:UltmtDbtr/PMRQ:Nm", true);
	public final ExpressionConversionInfo	pstlAdrsAdrTp		= createXPath(fill4, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrTp", true);
	public final ExpressionConversionInfo	pstlAdrsAdrLine1		= createXPath(fill70, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrLine[1]", true);
	public final ExpressionConversionInfo	pstlAdrsAdrLine2		= createXPath(fill70, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:AdrLine[2]", true);
	public final ExpressionConversionInfo	pstlAdrsStrtNm		= createXPath(fill70, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:StrtNm", true);
	public final ExpressionConversionInfo	pstlAdrsBldgNb		= createXPath(fill16, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:BldgNb", true);
	public final ExpressionConversionInfo	pstlAdrsPstCd		= createXPath(fill16, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:PstCd", true);
	public final ExpressionConversionInfo	pstlAdrsTwnNm		= createXPath(fill35, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:TwnNm", true);
	public final ExpressionConversionInfo	pstlAdrsCtrySubDvsn	= createXPath(fill35, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:CtrySubDvsn", true);
	public final ExpressionConversionInfo	pstlAdrsCtry		= createXPath(fill2, "PMRQ:UltmtDbtr/PMRQ:PstlAdr/PMRQ:Ctry", true);

	public final ExpressionConversionInfo	srvInf				= createXPath(fill5, "PMRQ:SrvInf", true);
	public final ExpressionConversionInfo	bIC					= createXPath(fill11, "PMRQ:CdtrAgt/PMRQ:FinInstnId/PMRQ:BIC", true);
	public final ExpressionConversionInfo	iban				= createXPath(fill35, "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:IBAN", true);
	public final ExpressionConversionInfo	bban				= createXPath(fill30, "PMRQ:CdtrAcct/PMRQ:Id/PMRQ:BBAN", true);
	public final ExpressionConversionInfo	instrForDbtrAgt		= createXPath(date10, "PMRQ:InstrForDbtrAgt", true);
	public final ExpressionConversionInfo	nm					= createXPath(fill70, "PMRQ:DestCdtrRsp/PMRQ:Nm", true);
	public final ExpressionConversionInfo	prtryId_Id			= createXPath(fill35, "PMRQ:DestCdtrRsp/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	public final ExpressionConversionInfo	prtryId_Issr		= createXPath(fill35, "PMRQ:DestCdtrRsp/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Issr", true);
	public final Converter						sia					= initSia();
	public final ExpressionConversionInfo	cd					= createXPath(fill35, "PMRQ:Purp/PMRQ:Cd", true);
	public final ExpressionConversionInfo	purp_prtry			= createXPath(fill35, "PMRQ:Purp/PMRQ:Prtry", true);

	protected Converter initSia() {
		return createGetBindingValue(fill5, SIA, true);
	}

}
