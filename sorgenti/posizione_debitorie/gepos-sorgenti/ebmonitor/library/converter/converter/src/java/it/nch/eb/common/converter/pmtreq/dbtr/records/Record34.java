/**
 * Created on 14/gen/08
 */
package it.nch.eb.common.converter.pmtreq.dbtr.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.flatx.bean.types.Converter;

/**
 * @author gdefacci
 */
public class Record34 extends ConversionRecord {

	private static final long	serialVersionUID	= 4498323811144580536L;

	public Record34() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:TxInfAndSts/DPSR:StsRsnInf/DPSR:AddtlStsRsnInf", "34");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord			= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	content				= createGetCurrentElementContent(fill105);


}
