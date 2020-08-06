/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public interface PendenzeModelValidator {
	  
	DBError[] apply(PendenzeModel pendenza);

}
