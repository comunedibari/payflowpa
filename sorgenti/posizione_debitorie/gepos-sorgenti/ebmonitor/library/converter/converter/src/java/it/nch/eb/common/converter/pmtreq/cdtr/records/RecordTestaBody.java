package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class RecordTestaBody extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long serialVersionUID = 1L;
	
	public RecordTestaBody() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:GrpHdr", "SB");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter rec_count = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter msgId = createXPath(fill35, "CPSR:MsgId", true);
			public final Converter idE2E = createXPath(fill44, "CPSR:IdE2E", true);
			public final Converter msgQual = createXPath(fill2, "CPSR:MsgQual", true);
			public final Converter creDtTm = createXPath(dateTime19ToXml, "CPSR:CreDtTm", true);
			public final Converter nm = createXPath(fill70, "CPSR:InitgPty/CPSR:Nm", true);
			public final Converter taxIdNb = createXPath(fill35, "CPSR:InitgPty/CPSR:Id/CPSR:OrgId/CPSR:TaxIdNb", true);
			public final Converter id = createXPath(fill35, "CPSR:InitgPty/CPSR:Id/CPSR:OrgId/CPSR:PrtryId/CPSR:Id", true);
			public final Converter sia = createGetBindingValue(fill5, SIA);
			public final Converter issr = createXPath(fill35, "CPSR:InitgPty/CPSR:Id/CPSR:OrgId/CPSR:PrtryId/CPSR:Issr", true);
			public final Converter bic = createXPath(fill11, "CPSR:DbtrAgt/CPSR:FinInstnId/CPSR:BIC", true);
			public final Converter prtry = createXPath(fill35, "CPSR:DbtrAgt/CPSR:FinInstnId/CPSR:ClrSysMmbId/CPSR:Prtry", true);
	
	
}