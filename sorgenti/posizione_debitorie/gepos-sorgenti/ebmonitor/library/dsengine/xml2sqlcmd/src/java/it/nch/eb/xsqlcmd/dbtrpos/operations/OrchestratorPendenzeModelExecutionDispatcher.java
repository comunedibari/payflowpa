/**
 * 05/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelOrchestrator;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public class OrchestratorPendenzeModelExecutionDispatcher {

	private final PendenzeModelOrchestrator insert;
	private final PendenzeModelOrchestrator update;
	private final PendenzeModelOrchestrator replace;
	private final PendenzeModelOrchestrator delete;
	private final PendenzeModelOrchestrator updateMassivo;

	public OrchestratorPendenzeModelExecutionDispatcher(
			PendenzeModelOrchestrator insert,
			PendenzeModelOrchestrator update,
			PendenzeModelOrchestrator replace,
			PendenzeModelOrchestrator delete,
			PendenzeModelOrchestrator updateMassivo) {
		this.insert = insert;
		this.update = update;
		this.replace = replace;
		this.delete = delete;
		this.updateMassivo = updateMassivo;
	}

	public PendenzeModelOrchestrator getInsert() {
		return insert;
	}

	public PendenzeModelOrchestrator getUpdate() {
		return update;
	}

	public PendenzeModelOrchestrator getReplace() {
		return replace;
	}

	public PendenzeModelOrchestrator getDelete() {
		return delete;
	}

	public PendenzeModelOrchestrator getUpdateMassivo() {
		return updateMassivo;
	}

	public PendenzeModelOrchestrator getOrchestrator(PendenzeModel pendenza) {
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
