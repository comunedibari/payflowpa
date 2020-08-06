package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class RecordTestaBody extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 4067197598092221363L;
	public RecordTestaBody() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr", "SB");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter advInstrTId = createXPath(numb9, "@AdvInstrTId", true);
			public final Converter msgId = createXPath(fill35, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:MsgId");
			public final Converter creDtTm = createXPath(dateTime19ToXml, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:CreDtTm");
			public final Converter txNb = createXPath(fill35, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:TxNb");
			public final Converter id = createXPath(fill8, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:Rcpt/ADIN:PrtryId/ADIN:Id");
			public final Converter issr = createXPath(fill4, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:Rcpt/ADIN:PrtryId/ADIN:Issr");
				public final Converter filler1 = createFixedValue(fill5, " ");	
			public final Converter nm = createXPath(fill70, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:Rcpt/ADIN:Nm");
			public final Converter id1 = createXPath(fill8, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:CdtrAgt/ADIN:PrtryId/ADIN:Id");
			public final Converter issr1 = createXPath(fill4, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:CdtrAgt/ADIN:PrtryId/ADIN:Issr");
			public final Converter bic = createXPath(fill11, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:CdtrAgt/ADIN:BIC", true);
			public final Converter clrSysMmbId = createXPath(fill5, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:CdtrAgt/ADIN:ClrSysMmbId", true);
			public final Converter bodyRowsNumber = createGetBindingValue(numb7, TOTAL_ROW_NUMBER, true);
	
}