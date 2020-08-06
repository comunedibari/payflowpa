/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * @author gdefacci
 */
public class Record60 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 8740956876689175390L;
	public Record60() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:RmtInf/PMRQ:Strd", "60");
	}

	public final ExpressionConversionInfo	fiiller				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);
	
	public final ExpressionConversionInfo	cd					= createXPath(fill4, "PMRQ:RfrdDocInf/PMRQ:RfrdDocTp/PMRQ:Cd");
	public final ExpressionConversionInfo	prtry				= createXPath(fill35, "PMRQ:RfrdDocInf/PMRQ:RfrdDocTp/PMRQ:Prtry", true);
	public final ExpressionConversionInfo	issr				= createXPath(fill35, "PMRQ:RfrdDocInf/PMRQ:RfrdDocTp/PMRQ:Issr", true);
	public final ExpressionConversionInfo	rfrdDocNb			= createXPath(fill35, "PMRQ:RfrdDocInf/PMRQ:RfrdDocNb");
	public final ExpressionConversionInfo	rfrdDocRltdDt		= createXPath(fill10, "PMRQ:RfrdDocRltdDt", true);
	public final ExpressionConversionInfo	cdtrRefTp_Cd		= createXPath(fill4, "PMRQ:RfrdDocInf/PMRQ:CdtrRefInf/PMRQ:CdtrRefTp/PMRQ:Cd", true);
	public final ExpressionConversionInfo	cdtrRefTp_Prtry		= createXPath(fill35, "PMRQ:RfrdDocInf/PMRQ:CdtrRefInf/PMRQ:CdtrRefTp/PMRQ:Prtry", true);
	public final ExpressionConversionInfo	cdtrRefTp_Issr		= createXPath(fill35, "PMRQ:RfrdDocInf/PMRQ:CdtrRefInf/PMRQ:CdtrRefTp/PMRQ:Issr", true);
	public final ExpressionConversionInfo	cdtrRef				= createXPath(fill35, "PMRQ:RfrdDocInf/PMRQ:CdtrRefInf/PMRQ:CdtrRef", true);
	public final ExpressionConversionInfo	addtlRmtInf			= createXPath(fill140, "PMRQ:RfrdDocInf/PMRQ:AddtlRmtInf", true);


}
