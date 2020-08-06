package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record40 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long serialVersionUID = 1L;
	
	public Record40() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef", "40");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter rec_count = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter amt = createXPath(fd_number18_3, "CPSR:Amt", true);
			public final Converter ccy = createXPath(fill3, "CPSR:Amt/@Ccy", true);
			public final Converter reqdExctnDt = createXPath(date10, "CPSR:ReqdExctnDt", true);
			public final Converter prtry = createXPath(fill35, "CPSR:PmtTpInf/CPSR:SvcLvl/CPSR:Prtry", true);
			public final Converter prtry1 = createXPath(fill35, "CPSR:PmtTpInf/CPSR:LclInstrm/CPSR:Prtry", true);
			public final Converter ctgyPurp = createXPath(fill4, "CPSR:PmtTpInf/CPSR:CtgyPurp", true);
			public final Converter pmtMtd = createXPath(fill3, "CPSR:PmtMtd", true);
			public final Converter rmtId = createXPath(fill35, "CPSR:RltdRmtInf/CPSR:RmtId", true);
			public final Converter rmtLctnMtd = createXPath(fill4, "CPSR:RltdRmtInf/CPSR:RmtLctnMtd", true);
			public final Converter rmtLctnElctrncAdr = createXPath(fill256, "CPSR:RltdRmtInf/CPSR:RmtLctnElctrncAdr", true);
	
	
}