/**
 * Created on 14/gen/08
 */
package it.nch.eb.ubi.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;

/**
 * @author gdefacci
 */
public class Record30 extends it.nch.eb.common.converter.pmtreq.records.Record30 implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= 5538870876139822196L;

	protected Converter initSia() {
		return createCUCDecoder(fill5, "PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true);
	}
	
}
