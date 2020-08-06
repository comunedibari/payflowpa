package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record08 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 3288234640219391542L;
	public Record08() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr/BODY:CBIAdvInstr/ADIN:AdvInstrs/ADIN:CdtOnAcct/ADIN:CdtInstrs", "08");
	}

		public final Converter filler = createFixedValue(fill1, " ");	
		public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
		public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
		public final Converter iban = createXPath(fill34, "ADIN:Id/ADIN:IBAN");
		public final Converter nm = createXPath(xmlFill70, "ADIN:Nm", true);
		public final Converter assRate = createXPath(fd_number11_10, "ADIN:CdtDesc/ADIN:AssRate", true);
		public final Converter assAmt = createXPath(fd_number23_5, "ADIN:CdtDesc/ADIN:AssAmt", true);
		public final Converter ccy = createXPath(fill3, "ADIN:CdtDesc/ADIN:AssAmt/@Ccy", true);	
}