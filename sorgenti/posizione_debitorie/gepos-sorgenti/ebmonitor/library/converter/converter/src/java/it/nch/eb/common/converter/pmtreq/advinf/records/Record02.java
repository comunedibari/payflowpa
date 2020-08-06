package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record02 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= -6517825381378231804L;
	public Record02() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr/BODY:CBIAdvInstr/ADIN:AdvInf/ADIN:Dbtr", "02");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter nmAndAdr = createXPath(fill140, "ADIN:NmAndAdr");
			public final Converter bei = createXPath(fill11, "ADIN:BEI", true);
			public final Converter ctry = createXPath(fill2, "ADIN:Ctry", true);
			public final Converter acctNb = createXPath(fill34, "ADIN:AcctNb", true);
	
}