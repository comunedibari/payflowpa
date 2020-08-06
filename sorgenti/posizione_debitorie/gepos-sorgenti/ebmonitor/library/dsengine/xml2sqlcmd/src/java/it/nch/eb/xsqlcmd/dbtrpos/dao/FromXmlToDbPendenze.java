/**
 * 02/lug/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.FromXmlDispatcher;
import it.nch.eb.flatx.flatmodel.sax.FirstElemSaxElementHandler;
import it.nch.eb.flatx.flatmodel.sax.ModelLoader;
import it.nch.eb.flatx.flatmodel.sax.SaxElementHandler;
import it.nch.eb.flatx.flatmodel.xpath.ChildAwareXPathBuilder;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.utils.InputsFactoryDispatcher;
import it.nch.fwk.core.NamespacesInfos;

/**
 * @author gdefacci
 */
public class FromXmlToDbPendenze extends FromXmlDispatcher implements InputsFactoryDispatcher {

	public FromXmlToDbPendenze(
			HeaderEffect headerEffect,
			OtfEffect otfEffect,
			PendenzeModelEffect executor,
			PendenzaXPathValidator pendenzaXPathValidator,
			NamespacesInfos qnss) {
		this(headerEffect, otfEffect, executor, pendenzaXPathValidator, PendenzeInclusions.EXCLUDE_NONE, qnss);
	}
	
	public FromXmlToDbPendenze(HeaderEffect headerEffect, OtfEffect otfEffect,
			PendenzeModelEffect executor,
			PendenzaXPathValidator pendenzaXPathValidator,
			PendenzeInclusions pendenzeInclusions, 
			NamespacesInfos qnss) {
		super(qnss, new PendenzeModelLoaderBuilderFactory(headerEffect, otfEffect, executor, pendenzaXPathValidator, pendenzeInclusions ));
	}

	protected SaxElementHandler createSaxHandler(ModelLoader leafElementHandler) {
		return new FirstElemSaxElementHandler(leafElementHandler, getQueryNamespaces(), new ChildAwareXPathBuilder());
	}
	
}
