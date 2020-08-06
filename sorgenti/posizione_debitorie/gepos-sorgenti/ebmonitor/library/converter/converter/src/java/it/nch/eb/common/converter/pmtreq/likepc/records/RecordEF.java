package it.nch.eb.common.converter.pmtreq.likepc.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * @author gdefacci
 */
public class RecordEF extends ConversionRecord implements BaseConverters, ConversionsConsts {
	
	private static final long	serialVersionUID	= 180958184601858363L;
	public RecordEF() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest", "EF");
	}

	public final Converter	filler			= createFixedValue(fill1, " ");
	public final Converter	tipoRecord		= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	id				= createCUCDecoder(fill5,
													"BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:InitgPty/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	public final Converter	prtry			= createXPath(fill5,
													"BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:DbtrAgt/PMRQ:FinInstnId/PMRQ:ClrSysMmbId/PMRQ:Prtry", true);
	public final Converter	creDtTm			= createXPath(dateTo_ddMMyy, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CreDtTm", true);
	public final Converter	timestamp		= createGetBindingValue(fill20, TIMESTAMP);
	public final Converter	blank			= createGetBindingValue(fill6, BLANK);
	public final Converter	nbOfTxs			= createXPath(numb7, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:NbOfTxs", true);
	public final Converter	filler1			= createFixedValue(numb15, "0");
	public final Converter	ctrlSum			= createXPath(fd_number15_2, "BODY:CBIPaymentRequest/PMRQ:GrpHdr/PMRQ:CtrlSum", true);
	public final Converter	bodyRowsNumber	= createGetBindingValue(numb7, ConversionsConsts.BODY_REC_COUNT, true);
	
	public final Converter	blank1			= createGetBindingValue(fill24, BLANK);
	public final Converter	filler2			= createFixedValue(fill1, "E");
	public final Converter	blank2			= createGetBindingValue(fill6, BLANK);

}