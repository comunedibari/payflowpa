/**
 * 19/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.common.utils.loaders.InputsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.model.HeaderModel;

/**
 * @author gdefacci
 */
public interface HeaderEffect {

	void apply(HeaderModel model, InputsFactory xmlStream);
}
