package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;

/**
 * generated 
 */
public class Record32 extends ConversionRecord {

	private static final long serialVersionUID = 1L;
	
	public Record32() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:TRN", "32");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter rec_count = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter trn = createGetCurrentElementContent(fill35);

	
	
}