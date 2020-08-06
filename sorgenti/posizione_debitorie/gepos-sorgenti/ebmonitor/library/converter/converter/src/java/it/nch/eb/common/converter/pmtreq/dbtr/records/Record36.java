/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.pmtreq.dbtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;

/**
 * @author gdefacci
 */
public class Record36 extends ConversionRecord  {

	private static final long	serialVersionUID	= -3313211442573618977L;
	public Record36() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:TxInfAndSts/DPSR:ChrgsInf/DPSR:ChrgsAmt", "36");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	content				= createGetCurrentElementContent(fd_number18_3);
	public final Converter	ccy					= createXPath(fill3, "@Ccy"); 

}
