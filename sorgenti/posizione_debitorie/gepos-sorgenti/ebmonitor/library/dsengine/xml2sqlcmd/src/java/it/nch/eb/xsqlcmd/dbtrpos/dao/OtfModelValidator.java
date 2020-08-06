/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;

/**
 * @author gdefacci
 */
public interface OtfModelValidator {
	
	
	DBError[] apply(OtfModel pendenza);

}
