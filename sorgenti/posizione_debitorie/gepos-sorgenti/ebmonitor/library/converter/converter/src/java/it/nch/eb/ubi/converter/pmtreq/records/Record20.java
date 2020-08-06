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
public class Record20 extends it.nch.eb.common.converter.pmtreq.records.Record20 implements BaseConverters, ConversionsConsts {

	private static final long	serialVersionUID	= -3702277607524103752L;

	protected Converter initSia() {
		return createCUCDecoder(fill5, "PMRQ:Id/PMRQ:OrgId/PMRQ:PrtryId/PMRQ:Id", true   );
	}
	
}
