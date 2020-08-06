package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record11 extends ConversionRecord implements BaseConverters, ConversionsConsts {
	
	private static final long	serialVersionUID	= -2617828177184664207L;
	public Record11() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr/BODY:CBIAdvInstr/ADIN:AdvInstrs/ADIN:RgltryRptg", "11");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter dbtCdtRptgInd = createXPath(fill4, "ADIN:DbtCdtRptgInd", true);
			public final Converter cd = createXPath(fill3, "ADIN:RgltryDtls/ADIN:Cd",true);
			public final Converter ccy = createXPath(fill3, "ADIN:RgltryDtls/ADIN:Amt/@Ccy", true);
			public final Converter amt = createXPath(fd_number23_5, "ADIN:RgltryDtls/ADIN:Amt", true);
			public final Converter inf = createXPath(fill35, "ADIN:RgltryDtls/ADIN:Inf", true);
	
}