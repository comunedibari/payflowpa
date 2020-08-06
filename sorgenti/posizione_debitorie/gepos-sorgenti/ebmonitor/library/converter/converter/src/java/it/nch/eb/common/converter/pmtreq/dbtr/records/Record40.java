/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.pmtreq.dbtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;

/**
 * @author gdefacci
 */
public class Record40 extends ConversionRecord {

	private static final long	serialVersionUID	= 5722173895230198143L;
	public Record40() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:TxInfAndSts/DPSR:OrgnlTxRef", "40");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	amt					= createXPath(fd_number18_3, "DPSR:Amt");
	public final Converter	ccy					= createXPath(fill3, "DPSR:Amt/@Ccy");
	public final Converter	reqdExctnDt			= createXPath(date10, "DPSR:ReqdExctnDt");
	public final Converter	SvcLvl_prtry		= createXPath(fill35, "DPSR:PmtTpInf/DPSR:SvcLvl/DPSR:Prtry");
	public final Converter	LclInstrm_prtry		= createXPath(fill35, "DPSR:PmtTpInf/DPSR:LclInstrm/DPSR:Prtry");
	public final Converter	ctgyPurp			= createXPath(fill4, "DPSR:PmtTpInf/DPSR:CtgyPurp");
	public final Converter	pmtMtd				= createXPath(fill3, "DPSR:PmtMtd");

	public final Converter	blank1				= createFixedValue(fill35, " ");
	public final Converter	blank2				= createFixedValue(fill4, " ");
	public final Converter	blank3				= createFixedValue(fill256, " ");
	                                          
}
