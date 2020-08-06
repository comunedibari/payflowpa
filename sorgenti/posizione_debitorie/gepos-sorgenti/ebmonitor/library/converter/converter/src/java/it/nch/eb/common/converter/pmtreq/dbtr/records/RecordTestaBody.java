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
public class RecordTestaBody extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= -6895021587708808841L;

	public RecordTestaBody() {
//		super("/MSG:CBIDbtrPmtStatusReportMsg/MSG:CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:GrpHdr/DPSR:MsgId", "SB");
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:GrpHdr", "SB");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);
	
	public final Converter	msgId					= createXPath(fill35, "DPSR:MsgId");
	public final Converter	idE2E					= createXPath(fill44, "DPSR:IdE2E");
	public final Converter	msgQual				= createXPath(fill2, "DPSR:MsgQual");
	public final Converter	creDtTm				= createXPath(dateTime19ToXml, "DPSR:CreDtTm");
	public final Converter	nm						= createXPath(fill70, "DPSR:InitgPty/DPSR:Nm", true);
	public final Converter	taxIdNb				= createXPath(fill35, "DPSR:InitgPty/DPSR:Id/DPSR:OrgId/DPSR:TaxIdNb", true);
	public final Converter	id						= createXPath(fill35,"DPSR:InitgPty/DPSR:Id/DPSR:OrgId/DPSR:PrtryId/DPSR:Id");
	public final Converter	sia						= createGetBindingValue(fill5, SIA, true);
	public final Converter	issr					= createXPath(fill35,"DPSR:InitgPty/DPSR:Id/DPSR:OrgId/DPSR:PrtryId/DPSR:Issr");
	public final Converter	bic						= createXPath(fill11,"DPSR:DbtrAgt/DPSR:FinInstnId/DPSR:BIC");
	public final Converter	prtry					= createXPath(fill35,"DPSR:DbtrAgt/DPSR:FinInstnId/DPSR:ClrSysMmbId/DPSR:Prtry");
	
	public final Converter	bodyRowsNumber				= createGetBindingValue(numb7, BODY_REC_COUNT, true);


}
