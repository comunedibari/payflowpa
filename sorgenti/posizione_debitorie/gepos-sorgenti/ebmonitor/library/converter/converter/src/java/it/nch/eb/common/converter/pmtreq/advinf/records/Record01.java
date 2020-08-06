package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record01 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= -5707698846486687173L;
	public Record01() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr/BODY:CBIAdvInstr/ADIN:AdvInf", "01");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter bic = createXPath(fill11, "ADIN:Sndr/ADIN:BIC");
			public final Converter nm = createXPath(fill70, "ADIN:Sndr/ADIN:Nm");
			public final Converter sndrRef = createXPath(fill16, "ADIN:Sndr/ADIN:SndrRef", true);
			public final Converter relRef = createXPath(fill16, "ADIN:Sndr/ADIN:RelRef", true);
			public final Converter bic1 = createXPath(fill11, "ADIN:DbtrAgt/ADIN:BIC", true);
			public final Converter desc = createXPath(fill175, "ADIN:DbtrAgt/ADIN:Desc", true);
	
}