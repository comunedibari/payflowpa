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
public class Record01 extends ConversionRecord implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 1842104134347901520L;
	public Record01() {
		super("/CBIDbtrPmtStatusReportMsg/CBIBdyDbtrPmtStatusReport/BODY:CBIEnvelDbtrPmtStatusReport/BODY:CBIDbtrPmtStatusReport/DPSR:OrgnlGrpInfAndSts", "01");
	}

	public final Converter	fiiller				= createFixedValue(" ");
	public final Converter	tipoRecord				= createGetBindingValue(fill2, TIPO_RECORD);
	public final Converter	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);

	public final Converter	orgnlMsgId			= createXPath(fill35, "DPSR:OrgnlMsgId");
	public final Converter	orgnlCreDtTm		= createXPath(dateTime19ToXml, "DPSR:OrgnlCreDtTm");
	public final Converter	grpSts				= createXPath(fill4, "DPSR:GrpSts");
	public final Converter	dtldNbOfTxs			= createXPath(numb15, "DPSR:NbOfTxsPerSts/DPSR:DtldNbOfTxs");
	public final Converter	dtldCtrlSum			= createXPath(fd_number23_5, "DPSR:NbOfTxsPerSts/DPSR:DtldCtrlSum");

}
