package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record07 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= -777380504359145079L;
	public Record07() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr/BODY:CBIAdvInstr/ADIN:AdvInstrs/ADIN:CdtOnAcct/ADIN:CdtrAgt/ADIN:PrtryId", "07");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter id = createXPath(fill8, "ADIN:Id");
			public final Converter issr = createXPath(fill4, "ADIN:Issr");
	
}