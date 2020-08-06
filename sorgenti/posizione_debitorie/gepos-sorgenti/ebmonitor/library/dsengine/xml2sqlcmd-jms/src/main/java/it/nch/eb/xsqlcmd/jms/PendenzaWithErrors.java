/**
 * 08/lug/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

public class PendenzaWithErrors {
	private final PendenzeModel pendenza;
	private final DBError[] errors;
	
	public PendenzaWithErrors(PendenzeModel pendenza, final DBError[] errors) {
		this.pendenza = pendenza;
		this.errors = errors;
	}

	public PendenzeModel getPendenza() {
		return pendenza;
	}

	public DBError[] getErrors() {
		return errors;
	}
}