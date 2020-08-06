/**
 * 19/nov/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.utils.InputsFactoryDispatcher;

/**
 * @author gdefacci
 */
public interface IJMSStreamDispatcherFactory {

	InputsFactoryDispatcher create(PendenzeInclusions pendenzeInclusions);

}