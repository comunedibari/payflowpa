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
public class Record80 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 9197980602263774672L;
	public Record80() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:TxInfAndSts/DPSR:OrgnlTxRef", "80");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	nm						= createXPath(fill70, "DPSR:UltmtDbtr/DPSR:Nm");
	public final Converter	adrTp					= createXPath(fill4, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:AdrTp");
	public final Converter	adrLine1			= createXPath(fill70, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:AdrLine[1]");
	public final Converter	adrLine2			= createXPath(fill70, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:AdrLine[2]");
	public final Converter	strtNm				= createXPath(fill70, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:StrtNm");
	public final Converter	bldgNb				= createXPath(fill16, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:BldgNb");
	public final Converter	pstCd					= createXPath(fill16, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:PstCd");
	public final Converter	twnNm					= createXPath(fill35, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:TwnNm");
	public final Converter	ctrySubDvsn			= createXPath(fill35, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:CtrySubDvsn");
	public final Converter	ctry				= createXPath(fill2, "DPSR:UltmtDbtr/DPSR:PstlAdr/DPSR:Ctry");

	public final Converter	orgnlTxRef_Nm		= createXPath(fill70, "DPSR:Dbtr/DPSR:Nm");
	public final Converter	orgnlTxRef_Id		= createXPath(fill35, "DPSR:Dbtr/DPSR:Id/DPSR:OrgId/DPSR:PrtryId/DPSR:Id", true);
	public final Converter	orgnlTxRef_Issr		= createXPath(fill35, "DPSR:Dbtr/DPSR:Id/DPSR:OrgId/DPSR:PrtryId/DPSR:Issr", true);
	public final Converter	orgnlTxRef_TaxIdNb	= createXPath(fill16, "DPSR:Dbtr/DPSR:Id/DPSR:OrgId/DPSR:TaxIdNb");
	public final Converter	orgnlTxRef_Iban		= createXPath(fill35, "DPSR:DbtrAcct/DPSR:Id/DPSR:IBAN");
	

}
