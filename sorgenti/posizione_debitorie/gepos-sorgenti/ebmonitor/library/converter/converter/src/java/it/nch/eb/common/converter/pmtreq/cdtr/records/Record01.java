package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record01 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long serialVersionUID = 1L;
	
	public Record01() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:OrgnlGrpInfAndSts", "01");
	}

			public final Converter filler = createFixedValue(" ");
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter rec_count = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter orgnlMsgId = createXPath(fill35, "CPSR:OrgnlMsgId", true);
			public final Converter orgnlCreDtTm = createXPath(dateTime19ToXml, "CPSR:OrgnlCreDtTm", true);
				public final Converter filler1 = createFixedValue(fill4, " ");	
				public final Converter filler2 = createFixedValue(numb15, " ");	
				public final Converter filler3 = createFixedValue(fd_number18_5, " ");	
	
	
}