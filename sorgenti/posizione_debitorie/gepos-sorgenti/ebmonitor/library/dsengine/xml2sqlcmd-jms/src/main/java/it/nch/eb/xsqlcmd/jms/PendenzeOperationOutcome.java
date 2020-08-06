/**
 * 03/set/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdefacci
 */
public class PendenzeOperationOutcome {
	
	private final List<PendenzaWithErrors> failingPendenze = new ArrayList<PendenzaWithErrors>();
	private final List<PendenzeModel> successfullPendenze = new ArrayList<PendenzeModel>();
	
	public List<PendenzaWithErrors> getFailingPendenze() {
		return failingPendenze;
	}
	public List<PendenzeModel> getSuccessfullPendenze() {
		return successfullPendenze;
	}
	public void success(PendenzeModel pm) {
		this.successfullPendenze.add(pm);
	}
	public void failure(PendenzeModel pm, DBError[] errs ) {
		this.failingPendenze.add(new PendenzaWithErrors(pm, errs ));
	}

}
