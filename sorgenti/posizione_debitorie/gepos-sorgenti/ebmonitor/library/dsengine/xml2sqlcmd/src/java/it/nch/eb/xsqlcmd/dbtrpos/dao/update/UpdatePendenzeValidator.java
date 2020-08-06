/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.update;

import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeDataDecoder;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeValidator;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBErrorsBag;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory.DBErrorsId;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class UpdatePendenzeValidator extends PendenzeValidator implements PendenzeModelValidator {

	private PendenzeDataDecoder decoder;
	private DBErrorsId errIds;

	public UpdatePendenzeValidator(SqlMapClient sqlMapClient, PendenzeDBErrorsFactory dbErrorsFactory ) {
		super(sqlMapClient, dbErrorsFactory);
		this.decoder = new PendenzeDataDecoder(sqlMapClient, dbErrorsFactory);
		errIds = dbErrorsFactory.errorIds;
	}

	public DBError[] apply(PendenzeModel pendenza) {
		DBErrorsBag errs = new DBErrorsBag();
		
		decoder.decodeIdEnte(pendenza, errs);
		decoder.decodeIdTributo(pendenza, errs);
		
		if (!decoder.checkPendenzaExists(pendenza)) {
			errs.add( decoder.getDbErrorsFactory().error(errIds.pendezaToUpdateDoesNotExists, pendenza));
		} 
		
		if (errs.isEmpty()) {
			checkPendenza(pendenza, errs);
		}
		
		return errs.getErrors();
	}

}
