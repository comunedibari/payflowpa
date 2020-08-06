package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;

/**
 * generated 
 */
public class Record86 extends ConversionRecord {

	private static final long serialVersionUID = 1L;
	
	public Record86() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef/CPSR:DestCdtrRsp", "86");
	}

			public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter rec_count = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter nm = createXPath(fill70, "CPSR:Nm", true);
			public final Converter id = createXPath(fill35, "CPSR:Id/CPSR:OrgId/CPSR:PrtryId/CPSR:Id", true);
			public final Converter issr = createXPath(fill35, "CPSR:Id/CPSR:OrgId/CPSR:PrtryId/CPSR:Issr", true);
}