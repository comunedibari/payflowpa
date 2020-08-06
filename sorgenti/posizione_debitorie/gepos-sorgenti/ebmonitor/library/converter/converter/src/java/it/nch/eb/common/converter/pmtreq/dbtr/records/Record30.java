/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.pmtreq.dbtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;

/**
 * @author gdefacci
 */
public class Record30 extends ConversionRecord {

	private static final long	serialVersionUID	= 1096078289343704523L;
	public Record30() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:TxInfAndSts", "30");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	stsId							= createXPath(fill35, "DPSR:StsId");
	public final Converter	orgnlInstrId			= createXPath(fill35, "DPSR:OrgnlInstrId");
	public final Converter	orgnlEndToEndId		= createXPath(fill35, "DPSR:OrgnlEndToEndId");
	public final Converter	lclInstrm_Prtry		= createXPath(fill35, "DPSR:LclInstrm/DPSR:Prtry");
	public final Converter	ordValDt					= createXPath(date10, "DPSR:OrdValDt");
	public final Converter	benValDt					= createXPath(date10, "DPSR:BenValDt", true);
	public final Converter	txSts							= createXPath(fill4, "DPSR:TxSts");
	public final Converter	bic								= createXPath(fill11,"DPSR:StsRsnInf/DPSR:StsOrgtr/DPSR:Id/DPSR:OrgId/DPSR:BIC");
	public final Converter	stsRsn_Cd					= createXPath(fill4, "DPSR:StsRsnInf/DPSR:StsRsn/DPSR:Cd", true);
	public final Converter	prtry							= createXPath(fill35, "DPSR:StsRsnInf/DPSR:StsRsn/DPSR:Prtry", true);
	public final Converter	elmRfc						= createXPath(fill256, "DPSR:StsRsnInf/DPSR:StsRsn/DPSR:ElmRfc", true);
	public final Converter	purp_CD						= createXPath(fill35, "DPSR:Purp/DPSR:Cd");
	public final Converter	purp_Prtry				= createXPath(fill35, "DPSR:Purp/DPSR:Prtry", true);
	public final Converter	accptncDtTm				= createXPath(dateTime19ToXml, "DPSR:AccptncDtTm");

}
