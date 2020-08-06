package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public interface PendenzeModelEffect {

	void apply(PendenzeModel model);
}
