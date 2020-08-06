/**
 * 15/gen/2010
 */
package it.nch.eb.common.converter.pmtreq.dbtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;

/**
 * @author gdefacci
 */
public class EsitiRecordCoda extends ConversionRecord {
	
	private static final long	serialVersionUID	= 4814371714593757207L;
	public EsitiRecordCoda() {
		super("/CBIDbtrPmtStatusReportMsg", "EH");
	}
	public EsitiRecordCoda(String xpath, String tipoRecord) {
		super(xpath, tipoRecord);
	}
	
	public final Converter	filler			= createFixedValue(" ");
	public final Converter	tipoRecord      = createGetBindingValue(fill2, TIPO_RECORD);

	public final Converter	srvNm			= createXPath(fill30, "CBIHdrTrt/HTRT:SrvNm");
	public final Converter	idCBISndrf		= createXPath(fill8, "CBIHdrTrt/HTRT:IdCBISndrf", true);
	public final Converter	idCBIRcvrf		= createXPath(fill8, "CBIHdrTrt/HTRT:IdCBIRcvrf", true);
	public final Converter	idMsgTrt		= createXPath(fill61, "CBIHdrTrt/HTRT:IdMsgTrt", true);
	public final Converter	hdrTrtXmlCrtDt	= createXPath(dateTime19, "CBIHdrTrt/HTRT:XMLCrtDt");

	public final Converter	idE2EMsg		= createXPath(fill44, "CBIHdrSrv/HE2E:SrvInfo/HE2E:IdE2EMsg");
	public final Converter	hdrSrvXmlCrtDt	= createXPath(dateTime19, "CBIHdrSrv/HE2E:SrvInfo/HE2E:XMLCrtDt");
	public final Converter	idCBISend		= createXPath(fill8, "CBIHdrSrv/HE2E:Sender/HE2E:IdCBISend");
	public final Converter	cbiRefrRecv		= createXPath(fill8, "CBIHdrSrv/HE2E:Receiver/HE2E:IdCBIRecv");
	public final Converter	totalRowsNumber = createGetBindingValue(numb7, TOTAL_ROW_NUMBER, true);													
	

}
