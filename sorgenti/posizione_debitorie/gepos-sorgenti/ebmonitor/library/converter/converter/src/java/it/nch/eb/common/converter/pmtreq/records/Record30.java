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
public class Record30 extends ConversionRecordWithSia implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= -8643632951342472186L;

	public Record30() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:UltmtCdtr", "30");
	}

	public final ExpressionConversionInfo	fiiller				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final ExpressionConversionInfo	pstlAdrsNm			= createXPath(fill70, "PMRQ:Nm");
	public final ExpressionConversionInfo	pstlAdrsAdrTp		= createXPath(fill4, "PMRQ:PstlAdr/PMRQ:AdrTp", true);
	public final ExpressionConversionInfo	pstlAdrsAdrLine1	= createXPath(fill70, "PMRQ:PstlAdr/PMRQ:AdrLine[1]",true);
	public final ExpressionConversionInfo	pstlAdrsAdrLine2	= createXPath(fill70, "PMRQ:PstlAdr/PMRQ:AdrLine[2]",true);
//	public final ExpressionConversionInfo	pstlAdrsAdrLine3	= createXPath(fill70, "PMRQ:PstlAdr/PMRQ:AdrLine[3]",true);
//	public final ExpressionConversionInfo	pstlAdrsAdrLine4	= createXPath(fill70, "PMRQ:PstlAdr/PMRQ:AdrLine[4]",true);
//	public final ExpressionConversionInfo	pstlAdrsAdrLine5	= createXPath(fill70, "PMRQ:PstlAdr/PMRQ:AdrLine[5]",true);
	public final ExpressionConversionInfo	pstlAdrsStrtNm		= createXPath(fill70, "PMRQ:PstlAdr/PMRQ:StrtNm", true);
	public final ExpressionConversionInfo	pstlAdrsBldgNb		= createXPath(fill16, "PMRQ:PstlAdr/PMRQ:BldgNb", true);
	public final ExpressionConversionInfo	pstlAdrsPstCd		= createXPath(fill16, "PMRQ:PstlAdr/PMRQ:PstCd", true);
	public final ExpressionConversionInfo	pstlAdrsTwnNm		= createXPath(fill35, "PMRQ:PstlAdr/PMRQ:TwnNm", true);
	public final ExpressionConversionInfo	pstlAdrsCtrySubDvsn	= createXPath(fill35, "PMRQ:PstlAdr/PMRQ:CtrySubDvsn", true);
	public final ExpressionConversionInfo	pstlAdrsCtry		= createXPath(fill2, "PMRQ:PstlAdr/PMRQ:Ctry", true);

	public final ExpressionConversionInfo	bei					= createXPath(fill11, "PMRQ:Id/PMRQ:OrgId/PMRQ:BEI", true);
	public final ExpressionConversionInfo	origidTaxIdNb		= createXPath(fill35, "PMRQ:Id/PMRQ:OrgId/PMRQ:TaxIdNb", true);
	public final ExpressionConversionInfo	prtryId_Id			= createXPath(fill35, "PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	public final ExpressionConversionInfo	prtryId_Issr		= createXPath(fill35, "PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Issr", true);
	public final Converter						sia					= initSia();
	public final ExpressionConversionInfo	prvtIdTaxIdNb		= createXPath(fill35, "PMRQ:Id/PMRQ:PrvtId/PMRQ:TaxIdNb", true);
	public final ExpressionConversionInfo	othrId				= createXPath(fill35, "PMRQ:Id/PMRQ:PrvtId/PMRQ:OthrId/PMRQ:Id", true);
	public final ExpressionConversionInfo	othrIdTp			= createXPath(fill35, "PMRQ:Id/PMRQ:PrvtId/PMRQ:OthrId/PMRQ:IdTp", true);

	protected Converter initSia() {
		return createGetBindingValue(fill5, SIA, true);
	}
	
}
