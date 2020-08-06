/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.pmtreq.dbtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * @author gdefacci
 */
public class RecordCodaBody extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 5660576993395206045L;

	public RecordCodaBody() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport", "EB");
	}

	public final Converter	fiiller			= createFixedValue(" ");
	public final Converter	tipoRecord			= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount		= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	msgId			= createXPath(fill35, "BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:GrpHdr/DPSR:MsgId");
	public final Converter	idE2E			= createXPath(fill44, "BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:GrpHdr/DPSR:IdE2E");
	public final Converter	msgQual			= createXPath(fill2, "BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:GrpHdr/DPSR:MsgQual");
	public final Converter	creDtTm			= createXPath(dateTime19ToXml, "BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:GrpHdr/DPSR:CreDtTm");
	public final Converter	id				= createXPath(fill35,
													"BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:GrpHdr/DPSR:InitgPty/DPSR:Id/DPSR:OrgId/DPSR:PrtryId/DPSR:Id");

	public final Converter	bodyRowsNumber			= createGetBindingValue(fill35, BODY_REC_COUNT, true);
}
