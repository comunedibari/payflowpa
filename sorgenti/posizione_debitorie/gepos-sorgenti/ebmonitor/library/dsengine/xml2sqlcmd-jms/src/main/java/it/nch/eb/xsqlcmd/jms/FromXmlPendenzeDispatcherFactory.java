/**
 * 09/lug/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.FromXmlDispatcher;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzaXPathValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;

/**
 * @author gdefacci
 */
public interface FromXmlPendenzeDispatcherFactory {
	
	FromXmlDispatcher create(PendenzeModelEffect effct, PendenzaXPathValidator pendenzaXPathValidator);

}
