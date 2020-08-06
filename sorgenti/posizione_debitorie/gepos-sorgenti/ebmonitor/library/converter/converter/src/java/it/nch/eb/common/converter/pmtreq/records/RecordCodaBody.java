/**
 * Created on 15/gen/08
 */
package it.nch.eb.common.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;


/**
 * @author gdefacci
 */
public class RecordCodaBody extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= -3177904961700051986L;
	
	public RecordCodaBody() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:GrpHdr/", "EB");
//		super("/MSG:CBIPaymentRequestMsg", "EB");
	}
	
	public final ExpressionConversionInfo	fiiller				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);
	
	public final ExpressionConversionInfo msgid = createXPath(fill35 , "PMRQ:MsgId");																					
	public final ExpressionConversionInfo creDtTm = createXPath(dateTime19 , "PMRQ:CreDtTm");																					
	public final ExpressionConversionInfo id =createXPath(fill35 , "PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id");
	public final ExpressionConversionInfo bodyRowsNumber = createGetBindingValue(numb7  , BODY_REC_COUNT, true);
		
}
