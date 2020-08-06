package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record05 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= -4276662266352633211L;
	public Record05() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr/BODY:CBIAdvInstr/ADIN:AdvInf", "05");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter nmAndAdr = createXPath(fill140, "ADIN:Cdtr/ADIN:NmAndAdr", true);
			public final Converter bei = createXPath(fill11, "ADIN:Cdtr/ADIN:BEI", true);
			public final Converter id = createXPath(fill8, "ADIN:Cdtr/ADIN:PrtryId/ADIN:Id", true);
			public final Converter issr = createXPath(fill4, "ADIN:Cdtr/ADIN:PrtryId/ADIN:Issr", true);
			public final Converter iban = createXPath(fill34, "ADIN:CdtrAcct/ADIN:Id/ADIN:IBAN", true);
			public final Converter bban = createXPath(fill30, "ADIN:CdtrAcct/ADIN:Id/ADIN:BBAN", true);
			public final Converter id1 = createXPath(fill35, "ADIN:CdtrAcct/ADIN:DmstAcct/ADIN:Id", true);
			public final Converter tp = createXPath(fill4, "ADIN:CdtrAcct/ADIN:DmstAcct/ADIN:Tp", true);
	
}