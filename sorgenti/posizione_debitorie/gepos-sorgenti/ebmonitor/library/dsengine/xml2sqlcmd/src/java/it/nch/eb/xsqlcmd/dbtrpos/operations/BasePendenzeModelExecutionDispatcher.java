/**
 * 05/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelValidator;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public class BasePendenzeModelExecutionDispatcher {

	private final PendenzeModelValidator insert;
	private final PendenzeModelValidator update;
	private final PendenzeModelValidator replace;
	private final PendenzeModelValidator delete;
	private final PendenzeModelValidator updateMassivo;

	public BasePendenzeModelExecutionDispatcher(
			PendenzeModelValidator insert,
			PendenzeModelValidator update,
			PendenzeModelValidator replace,
			PendenzeModelValidator delete,
			PendenzeModelValidator updateMassivo) {
		this.insert = insert;
		this.update = update;
		this.replace = replace;
		this.delete = delete;
		this.updateMassivo = updateMassivo;
	}

	public PendenzeModelValidator getInsert() {
		return insert;
	}

	public PendenzeModelValidator getUpdate() {
		return update;
	}

	public PendenzeModelValidator getReplace() {
		return replace;
	}

	public PendenzeModelValidator getDelete() {
		return delete;
	}

	public PendenzeModelValidator getUpdateMassivo() {
		return updateMassivo;
	}

	protected PendenzeModelValidator getExecutor(PendenzeModel pendenza) {
		if (pendenza.getInsert().booleanValue()) {
			return getInsert();
		} else if (pendenza.getUpdate().booleanValue()) {
			return getUpdate();
		} else if (pendenza.getReplace().booleanValue()) {
			return getReplace();
		} else if (pendenza.getDelete().booleanValue()) {
			return getDelete();
		} else if (pendenza.getUpdateMassivo().booleanValue()) {
			return getUpdateMassivo();
		} else
			return null;
	}

}
