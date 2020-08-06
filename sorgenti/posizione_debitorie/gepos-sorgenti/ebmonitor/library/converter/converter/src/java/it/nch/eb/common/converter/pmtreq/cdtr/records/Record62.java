package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record62 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long serialVersionUID = 1L;
	
	public Record62() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef/CPSR:RmtInf/CPSR:Strd", "62");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter rec_count = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter cd = createXPath(fill4, "CPSR:RfrdDocInf/CPSR:RfrdDocTp/CPSR:Cd", true);
			public final Converter prtry = createXPath(fill35, "CPSR:RfrdDocInf/CPSR:RfrdDocTp/CPSR:Prtry", true);
			public final Converter issr = createXPath(fill35, "CPSR:RfrdDocInf/CPSR:RfrdDocTp/CPSR:Issr", true);
			public final Converter rfrdDocNb = createXPath(fill35, "CPSR:RfrdDocInf/CPSR:RfrdDocNb", true);
			public final Converter rfrdDocRltdDt = createXPath(date10, "CPSR:RfrdDocRltdDt", true);
			public final Converter cd1 = createXPath(fill4, "CPSR:CdtrRefInf/CPSR:CdtrRefTp/CPSR:Cd", true);
			public final Converter prtry1 = createXPath(fill35, "CPSR:CdtrRefInf/CPSR:CdtrRefTp/CPSR:Prtry", true);
			public final Converter issr1 = createXPath(fill35, "CPSR:CdtrRefInf/CPSR:CdtrRefTp/CPSR:Issr", true);
			public final Converter cdtrRef = createXPath(fill35, "CPSR:CdtrRefInf/CPSR:CdtrRef", true);
			public final Converter addtlRmtInf = createXPath(fill140, "CPSR:AddtlRmtInf", true);
	
	
}