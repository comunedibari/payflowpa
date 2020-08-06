/**
 * 22/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.updateMassivo;

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
public class UpdateMassivoPendenzeValidator extends PendenzeValidator implements PendenzeModelValidator {

	private PendenzeDataDecoder decoder;
	private DBErrorsId errIds;

	public UpdateMassivoPendenzeValidator(SqlMapClient sqlMapClient, PendenzeDBErrorsFactory dbErrorsFactory ) {
		super(sqlMapClient, dbErrorsFactory);
		this.decoder = new PendenzeDataDecoder(sqlMapClient, dbErrorsFactory);
		errIds = dbErrorsFactory.errorIds;
	}

	public DBError[] apply(PendenzeModel pendenza) {
		DBErrorsBag errs = new DBErrorsBag();
		decodeEnteTributo(pendenza, errs);
		if (errs.isEmpty()) {
			checkPendenza(pendenza, errs);
		}
		return errs.getErrors();
	}

	public void decodeEnteTributo(PendenzeModel pendenza, DBErrorsBag errs) {
		String idEnte = decoder.decodeIdEnte(pendenza, errs);

//		18/07/2011
//		non occorre controllare se la pendenza esiste gia
//		in caso di updatemassivo idPendenzaEnte puo' anche essere gia presente in base dati
//		if (idEnte != null && decoder.checkPendenzaExists(pendenza)){
//			errs.add( decoder.getDbErrorsFactory().error(errIds.pendenzaDuplicata, pendenza) );
//		}

		if (errs.isEmpty()) {
			decoder.decodeIdTributo(pendenza, errs);
		}
	}

//	public DBError[] apply(PendenzeModel pendenza) {
//		DBErrorsBag errs = new DBErrorsBag();

//		decoder.decodeIdEnte(pendenza, errs);
//		decoder.decodeIdTributo(pendenza, errs);
//
//		if (!decoder.checkPendenzaExists(pendenza)) {
//			errs.add( decoder.getDbErrorsFactory().error(errIds.pendezaToUpdateDoesNotExists, pendenza));
//		}
//
//		if (errs.isEmpty()) {
//			checkPendenza(pendenza, errs);
//		}
//
//		return errs.getErrors();
//	}

}
