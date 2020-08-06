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
public class Record40 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 7839384874862875654L;
	public Record40() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:RltdRmtInf", "40");
	}

	public final ExpressionConversionInfo	fiiller				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final ExpressionConversionInfo rmtId 				= createXPath(fill35, "PMRQ:RmtId", true	);
	public final ExpressionConversionInfo rmtLctnMtd 			= createXPath(fill4, "PMRQ:RmtLctnMtd",true );
	public final ExpressionConversionInfo rmtLctnElctrncAdr 	= createXPath(fill256, "PMRQ:RmtLctnElctrncAdr",true	);

}
