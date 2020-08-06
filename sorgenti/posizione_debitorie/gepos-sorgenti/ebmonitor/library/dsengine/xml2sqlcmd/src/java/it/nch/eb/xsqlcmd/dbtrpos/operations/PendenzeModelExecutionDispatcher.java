/**
 * 05/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelValidator;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public class PendenzeModelExecutionDispatcher extends BasePendenzeModelExecutionDispatcher implements PendenzeModelEffect {

	//set hidden
	public boolean hidden = false;
	
	public PendenzeModelExecutionDispatcher(
			PendenzeModelValidator insert,
			PendenzeModelValidator update,
			PendenzeModelValidator replace,
			PendenzeModelValidator delete,
			PendenzeModelValidator updateMassivo) {
		super(insert, update, replace, delete, updateMassivo);
	}

	public void apply(PendenzeModel model) {
		PendenzeModelValidator executor = getExecutor(model);
		
		//set hidden
		model.setHidden(hidden);
		
		executor.apply(model);
//		we ignore those errors, they have been are already managed
	}

	
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
}
