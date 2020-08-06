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
public class Record22 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 1159801056774450957L;

	public Record22() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:OrgnlGrpInfAndSts/DPSR:StsRsnInf/DPSR:AddtlStsRsnInf", "22");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	content				= createGetCurrentElementContent(fill105);

}
