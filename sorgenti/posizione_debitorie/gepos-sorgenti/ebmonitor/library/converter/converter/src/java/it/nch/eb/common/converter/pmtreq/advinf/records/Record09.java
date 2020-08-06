package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record09 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 8714255119283823545L;
	public Record09() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr/BODY:CBIAdvInstr/ADIN:AdvInstrs/ADIN:ChrgsAcct", "09");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter id = createXPath(fill8, "ADIN:CdtrAgt/ADIN:PrtryId/ADIN:Id");
			public final Converter issr = createXPath(fill4, "ADIN:CdtrAgt/ADIN:PrtryId/ADIN:Issr");
			public final Converter iban = createXPath(fill34, "ADIN:Id/ADIN:IBAN", true);
			public final Converter nm = createXPath(fill70, "ADIN:Nm", true);
	
}