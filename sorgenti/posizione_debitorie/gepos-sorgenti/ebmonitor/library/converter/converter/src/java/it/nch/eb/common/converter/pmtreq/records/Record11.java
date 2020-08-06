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
public class Record11 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 4572254115901490217L;
	public Record11() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:RgltryRptg", "11");
	}

	public final ExpressionConversionInfo	fiiller			= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec			= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount		= createGetBindingValue(numb7, REC_COUNT, true);

	public final ExpressionConversionInfo	dbtCdtRptgInd	= createXPath(fill4, "PMRQ:DbtCdtRptgInd", true);
	public final ExpressionConversionInfo	cd				= createXPath(fill3, "PMRQ:RgltryDtls/PMRQ:Cd",true);
	public final ExpressionConversionInfo	amtCcy			= createXPath(fill3, "PMRQ:RgltryDtls/PMRQ:Amt/@Ccy", true);
	public final ExpressionConversionInfo	rgltryDtlsAmt	= createXPath(fd_number23_5, "PMRQ:RgltryDtls/PMRQ:Amt", true);
	public final ExpressionConversionInfo	rgltryDtlsInf	= createXPath(fill35, "PMRQ:RgltryDtls/PMRQ:Inf", true);
	
}
