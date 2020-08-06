package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class RecordCodaBody extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 3464157970110420996L;
	public RecordCodaBody() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr", "EB");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter advInstrTId = createXPath(fill9, "@AdvInstrTId", true);
			public final Converter msgId = createXPath(fill35, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:MsgId");
			public final Converter creDtTm = createXPath(dateTime19ToXml, "BODY:CBIAdvInstr/ADIN:GrpHdr/ADIN:CreDtTm");
			public final Converter bodyRowsNumber = createGetBindingValue(numb7, BODY_REC_COUNT, true);
	
}