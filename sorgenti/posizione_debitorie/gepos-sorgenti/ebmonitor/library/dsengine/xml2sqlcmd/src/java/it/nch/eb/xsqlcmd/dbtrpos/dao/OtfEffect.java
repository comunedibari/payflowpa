/**
 * 19/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.common.utils.loaders.InputsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public interface OtfEffect {

	void apply(OtfModel model);
	
}
