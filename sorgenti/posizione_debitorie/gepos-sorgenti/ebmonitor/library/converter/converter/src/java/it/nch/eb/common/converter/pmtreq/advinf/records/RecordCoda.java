package it.nch.eb.common.converter.pmtreq.advinf.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * generated 
 */
public class RecordCoda extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 8718365347229632218L;
	public RecordCoda() {
		super("/MSG:CBIAdvInstrMsg", "EH");
	}

	public final Converter filler = createFixedValue(fill1, " ");	
	
	public final Converter tipoRecord = createGetBindingValue(fill2, TIPO_RECORD);
	
			public final Converter srvNm = createXPath(fill30, "MSG:CBIHdrTrt/HTRT:SrvNm");
			public final Converter idCBISndrf = createXPath(fill8, "MSG:CBIHdrTrt/HTRT:IdCBISndrf", true);
			public final Converter idCBIRcvrf = createXPath(fill8, "MSG:CBIHdrTrt/HTRT:IdCBIRcvrf", true);
			public final Converter idMsgTrt = createXPath(fill61, "MSG:CBIHdrTrt/HTRT:IdMsgTrt", true);
			public final Converter xMLCrtDt = createXPath(fill19, "MSG:CBIHdrTrt/HTRT:XMLCrtDt", true);
			public final Converter idE2EMsg = createXPath(fill44, "MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:IdE2EMsg", true);
			public final Converter xMLCrtDt1 = createXPath(dateTime19ToXml, "MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:XMLCrtDt", true);
			public final Converter idCBISend = createXPath(fill8, "MSG:CBIHdrSrv/HE2E:Sender/HE2E:IdCBISend", true);
			public final Converter idCBIRecv = createXPath(fill8, "MSG:CBIHdrSrv/HE2E:Receiver/HE2E:IdCBIRecv", true);
			public final Converter totalRowsNumber = createGetBindingValue(numb7, TOTAL_ROW_NUMBER, true);
	
}