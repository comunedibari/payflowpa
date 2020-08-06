package it.nch.eb.common.converter.pmtreq.cdtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class Record67 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long serialVersionUID = 1L;
	
	public Record67() {
		super("/CBICdtrPmtStatusReportMsg/CBIBdyCdtrPmtStatusReport/BODY:CBIEnvelCdtrPmtStatusReport/BODY:CBICdtrPmtStatusReport/CPSR:TxInfAndSts/CPSR:OrgnlTxRef/CPSR:RmtInf/CPSR:Strd/CPSR:Invcr/CPSR:Id/CPSR:PrvtId", "67");
	}

				public final Converter filler = createFixedValue(fill1, " ");	
			public final Converter tipo_record = createGetBindingValue(fill2, TIPO_RECORD);
			public final Converter rec_count = createGetBindingValue(numb7, REC_COUNT, true);
			public final Converter alnRegnNb = createXPath(fill35, "CPSR:AlnRegnNb", true);
			public final Converter cstmrNb = createXPath(fill35, "CPSR:CstmrNb", true);
			public final Converter drvrsLicNb = createXPath(fill35, "CPSR:DrvrsLicNb", true);
			public final Converter birthDt = createXPath(date10, "CPSR:DtAndPlcOfBirth/CPSR:BirthDt", true);
			public final Converter prvcOfBirth = createXPath(fill35, "CPSR:DtAndPlcOfBirth/CPSR:PrvcOfBirth", true);
			public final Converter cityOfBirth = createXPath(fill35, "CPSR:DtAndPlcOfBirth/CPSR:CityOfBirth", true);
			public final Converter ctryOfBirth = createXPath(fill2, "CPSR:DtAndPlcOfBirth/CPSR:CtryOfBirth", true);
			public final Converter idntyCardNb = createXPath(fill35, "CPSR:IdntyCardNb", true);
			public final Converter mplyrIdNb = createXPath(fill35, "CPSR:MplyrIdNb", true);
			public final Converter id = createXPath(fill35, "CPSR:OthrId/CPSR:Id", true);
			public final Converter idTp = createXPath(fill35, "CPSR:OthrId/CPSR:IdTp", true);
			public final Converter psptNb = createXPath(fill35, "CPSR:PsptNb", true);
			public final Converter sclSctyNb = createXPath(fill35, "CPSR:SclSctyNb", true);
			public final Converter taxIdNb = createXPath(fill35, "CPSR:TaxIdNb", true);
	
	
}