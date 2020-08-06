package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record30 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long serialVersionUID = 1L;
	
	public Record30() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:TxInfAndSts", "30");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter rec_count = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter stsId = createXPath(fill35, "CPSR:StsId", true);
			public final Converter orgnlInstrId = createXPath(fill35, "CPSR:OrgnlInstrId", true);
			public final Converter orgnlEndToEndId = createXPath(fill35, "CPSR:OrgnlEndToEndId", true);
			public final Converter prtry = createXPath(fill35, "CPSR:LclInstrm/CPSR:Prtry", true);
				public final Converter filler1 = createFixedValue(date10, " ");	
			public final Converter benValDt = createXPath(date10, "CPSR:BenValDt", true);
			public final Converter txSts = createXPath(fill4, "CPSR:TxSts", true);
			public final Converter bic = createXPath(fill11, "CPSR:StsRsnInf/CPSR:StsOrgtr/CPSR:Id/CPSR:OrgId/CPSR:BIC", true);
				public final Converter filler2 = createFixedValue(fill4, " ");	
				public final Converter filler3 = createFixedValue(fill35, " ");	
				public final Converter filler4 = createFixedValue(fill256, " ");	
			public final Converter cd = createXPath(fill35, "CPSR:Purp/CPSR:Cd", true);
			public final Converter prtry1 = createXPath(fill35, "CPSR:Purp/CPSR:Prtry", true);
			public final Converter accptncDtTm = createXPath(dateTime19ToXml, "CPSR:AccptncDtTm", true);
	
	
}