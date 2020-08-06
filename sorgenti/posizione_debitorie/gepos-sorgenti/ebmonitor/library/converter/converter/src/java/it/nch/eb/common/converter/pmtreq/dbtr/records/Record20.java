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
public class Record20 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 2947423190883179110L;
	public Record20() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:OrgnlGrpInfAndSts/DPSR:StsRsnInf", "20");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	bic					= createXPath(fill11, "DPSR:StsOrgtr/DPSR:Id/DPSR:OrgId/DPSR:BIC", true);
	public final Converter	cd					= createXPath(fill4, "DPSR:StsRsn/DPSR:Cd", true);
	public final Converter	prtry				= createXPath(fill35, "DPSR:StsRsn/DPSR:Prtry", true);
	public final Converter	elmRfc				= createXPath(fill256, "DPSR:StsRsn/DPSR:ElmRfc", true);

}
