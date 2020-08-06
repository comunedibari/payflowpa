/**
 * Created on 15/gen/08
 */
package it.nch.eb.common.converter.common.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.ubi.converter.pmtreq.records.MapDecoder;


/**
 * @author gdefacci
 */
public class RecordCoda extends ConversionRecord {

	private static final long	serialVersionUID	= 4814371714593757207L;
	public RecordCoda() {
		super("/MSG:CBIPaymentRequestMsg", "EH");
	}
	
	public final Converter	filler			= createFixedValue(" ");
	public final Converter	tipoRecord      = createGetBindingValue(fill2, TIPO_RECORD);

	public final Converter	srvNm			= createXPath(fill30, "MSG:CBIHdrTrt/HTRT:SrvNm");
	public final Converter	idCBISndrf		= createXPath(fill8, "MSG:CBIHdrTrt/HTRT:IdCBISndrf", true);
	public final Converter	idCBIRcvrf		= createXPath(fill8, "MSG:CBIHdrTrt/HTRT:IdCBIRcvrf", true);
	public final Converter	idMsgTrt		= createXPath(fill61, "MSG:CBIHdrTrt/HTRT:IdMsgTrt", true);
	public final Converter	hdrTrtXmlCrtDt	= createXPath(dateTime19, "MSG:CBIHdrTrt/HTRT:XMLCrtDt");

	public final Converter	idE2EMsg		= createXPath(fill44, "MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:IdE2EMsg");
	public final Converter	hdrSrvXmlCrtDt	= createXPath(dateTime19, "MSG:CBIHdrSrv/HE2E:SrvInfo/HE2E:XMLCrtDt");
	public final Converter	idCBISend		= createXPath(fill8, "MSG:CBIHdrSrv/HE2E:Sender/HE2E:IdCBISend");
	public final Converter	cbiRefrRecv		= createIdCBIRecvConversion();
	public final Converter	totalRowsNumber = createGetBindingValue(numb7, TOTAL_ROW_NUMBER, true);													
	
	protected Converter createIdCBIRecvConversion() {
		return new MapDecoder(getPos(),ConversionsConsts.CUC_CUC_LOGIC_DEST_MAP,fill8, "MSG:CBIHdrSrv/HE2E:Receiver/HE2E:IdCBIRecv",false);
	}
}
