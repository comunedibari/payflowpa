/**
 * 09/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;


/**
 * @author gdefacci
 */
public interface DBErrorsHandler {

	void onError(DBError[] errors, PendenzeModel pendenza);
	
	void onError(DBError[] errors, OtfModel otf);
	
}
