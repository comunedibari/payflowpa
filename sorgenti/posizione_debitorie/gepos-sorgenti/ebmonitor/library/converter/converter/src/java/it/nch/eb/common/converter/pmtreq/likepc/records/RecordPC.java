package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class RecordPC extends ConversionRecord implements BaseConverters, ConversionsConsts {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8759333903262390847L;
	public RecordPC() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest", "PC");
	}
	public final Converter filler = createFixedValue(" ");;
	public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter id = createCUCDecoder(fill5, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	public final Converter prtry = createXPath(fill5, "BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:DbtrAgt/PMRQ:FinInstnId/PMRQ:ClrSysMmbId/PMRQ:Prtry", true);
	public final Converter creDtTm = createXPath(xsDateTo_ddMMyy, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CreDtTm", true);
	public final Converter timestamp = createGetBindingValue(fill20, TIMESTAMP);
	public final Converter blank = createGetBindingValue(fill6, BLANK);
	public final Converter msgId = createXPath(fill35, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:MsgId", true);
	public final Converter creDtTm1 = createXPath(dateTime19, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CreDtTm", true);
	public final Converter blank1 = createGetBindingValue(fill14, BLANK);
	public final Converter filler1 = createFixedValue(fill1, "E");	
	public final Converter blank2 = createGetBindingValue(fill6, BLANK);
	
}