/**
 * Created on 14/gen/08
 */
package it.nch.eb.ubi.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.common.converter.common.conversioninfo.CUCDecoder;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.FillerConverter;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathConversionInfo;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * @author gdefacci
 */
public class Record00 extends it.nch.eb.common.converter.pmtreq.records.Record00 implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 3326264297448513816L;

	protected Converter initSia() {
		return createCUCDecoder(fill5, "PMRQ:Dbtr/PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	}
	
	public MapDecoder createIBANDecoder(FillerConverter delegate, String xpath, boolean optional) {
		return new MapDecoder(getPos(),ConversionsConsts.IBAN_MAP, delegate, xpath, optional);
	}

	protected XPathConversionInfo initIbanConversion() {
		return createIBANDecoder(fill35, "PMRQ:DbtrAcct/PMRQ:Id/PMRQ:IBAN", false);
	}
 
	protected ExpressionConversionInfo initIdIstConversion() {
//		return createXPath(fill35,"PMRQ:DbtrAgt/PMRQ:FinInstnId/PMRQ:ClrSysMmbId/PMRQ:Prtry");
		return new MapDecoder(getPos(), ConversionsConsts.INST_ID_MAP ,fill35,"PMRQ:DbtrAgt/PMRQ:FinInstnId/PMRQ:ClrSysMmbId/PMRQ:Prtry", false);
	}

}
