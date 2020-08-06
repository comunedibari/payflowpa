/**
 * Created on 05/mar/2009
 */
package it.nch.streaming.services.impl.exp;

import it.nch.eb.flatx.flatmodel.conversioninfo.BindingManagerEffect;
import it.nch.eb.ubi.converter.pmtreq.parser.UbiPmtreqParserFactory;
import it.nch.fwk.core.NamespacesInfos;


/**
 * @author gdefacci
 */
public class UbiPmtreqToFlatConversionServiceNew extends PmtreqToFlatConversionServiceNew {

	private static final long	serialVersionUID	= -6149564990146879458L;

	public UbiPmtreqToFlatConversionServiceNew(String lineTerminator, NamespacesInfos qnss,
			BindingManagerEffect[] bmEffects) {
		super(new UbiPmtreqParserFactory(), lineTerminator, qnss, bmEffects);
	}

	public UbiPmtreqToFlatConversionServiceNew(String lineTerminator, NamespacesInfos queriesNss2) {
		super(new UbiPmtreqParserFactory(), lineTerminator, queriesNss2, new BindingManagerEffect[] {});
	}

}
