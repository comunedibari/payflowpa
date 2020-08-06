package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record03 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 2245085783336640005L;
	public Record03() {
		super("/MSG:CBIAdvInstrMsg/MSG:CBIBdyAdvInstr/BODY:CBIEnvelAdvInstr/BODY:CBIAdvInstr/ADIN:AdvInf", "03");
	}

	        public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter ccy = createXPath(fill3, "ADIN:InstdAmt/@Ccy", true);
			public final Converter instdAmt = createXPath(fd_number23_5, "ADIN:InstdAmt", true);
			public final Converter ccy1 = createXPath(fill3, "ADIN:EqvtAmt/ADIN:Amt/@Ccy", true);
			public final Converter amt = createXPath(fd_number23_5, "ADIN:EqvtAmt/ADIN:Amt", true);
			public final Converter xcghRate = createXPath(fd_number21_10, "ADIN:EqvtAmt/ADIN:XcghRate", true);
			public final Converter chrgBr = createXPath(fill3, "ADIN:ChrgBr", true);
			public final Converter vlDt = createXPath(date10, "ADIN:VlDt", true);
			public final Converter rmtInf = createXPath(fill140, "ADIN:RmtInf", true);
			public final Converter cd = createXPath(fill4, "ADIN:TxDtls/ADIN:Cd", true);
			public final Converter inf = createXPath(fill206, "ADIN:TxDtls/ADIN:Inf", true);
	
}