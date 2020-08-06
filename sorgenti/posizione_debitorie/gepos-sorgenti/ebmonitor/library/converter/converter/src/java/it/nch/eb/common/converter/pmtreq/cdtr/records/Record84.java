package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record84 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long serialVersionUID = 1L;
	
	public Record84() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef/CPSR:UltmtCdtr", "84");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter recordCount = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter nm = createXPath(fill70, "CPSR:Nm", true);
			public final Converter adrTp = createXPath(fill4, "CPSR:PstlAdr/CPSR:AdrTp", true);
			public final Converter adrLine1 = createXPath(fill70, "CPSR:PstlAdr/CPSR:AdrLine[1]", true);
			public final Converter adrLine2 = createXPath(fill70, "CPSR:PstlAdr/CPSR:AdrLine[2]", true);
			public final Converter strtNm = createXPath(fill70, "CPSR:PstlAdr/CPSR:StrtNm", true);
			public final Converter bldgNb = createXPath(fill16, "CPSR:PstlAdr/CPSR:BldgNb", true);
			public final Converter pstCd = createXPath(fill16, "CPSR:PstlAdr/CPSR:PstCd", true);
			public final Converter twnNm = createXPath(fill35, "CPSR:PstlAdr/CPSR:TwnNm", true);
			public final Converter ctrySubDvsn = createXPath(fill35, "CPSR:PstlAdr/CPSR:CtrySubDvsn", true);
			public final Converter ctry = createXPath(fill2, "CPSR:PstlAdr/CPSR:Ctry", true);
			public final Converter bei = createXPath(fill11, "CPSR:Id/CPSR:OrgId/CPSR:BEI", true);
			public final Converter taxIdNb = createXPath(fill35, "CPSR:Id/CPSR:OrgId/CPSR:TaxIdNb", true);
			public final Converter id = createXPath(fill35, "CPSR:Id/CPSR:OrgId/CPSR:PrtryId/CPSR:Id", true);
			public final Converter issr = createXPath(fill35, "CPSR:Id/CPSR:OrgId/CPSR:PrtryId/CPSR:Issr", true);
			public final Converter taxIdNb1 = createXPath(fill35, "CPSR:Id/CPSR:PrvtId/CPSR:TaxIdNb", true);
			public final Converter id1 = createXPath(fill35, "CPSR:Id/CPSR:PrvtId/CPSR:OthrId/CPSR:Id", true);
			public final Converter idTp = createXPath(fill35, "CPSR:Id/CPSR:PrvtId/CPSR:OthrId/CPSR:IdTp", true);
	
	
}