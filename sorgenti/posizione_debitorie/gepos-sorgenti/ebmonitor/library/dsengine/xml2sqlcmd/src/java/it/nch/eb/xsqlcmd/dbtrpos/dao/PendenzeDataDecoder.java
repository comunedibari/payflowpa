/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.commands.db.BaseIbatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBErrorsBag;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory.DBErrorsId;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel;
import it.nch.eb.xsqlcmd.model.ConfigurazioneTributoEnteModel;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author gdefacci
 */
public class PendenzeDataDecoder extends BaseIbatisDao {

	private final PendenzeDBErrorsFactory dbErrorsFactory;
	private final DBErrorsId errIds;

	public PendenzeDataDecoder(SqlMapClient sqlMapClient, PendenzeDBErrorsFactory dbErrorsFactory) {
		super(sqlMapClient);
		this.dbErrorsFactory = dbErrorsFactory;
		this.errIds = this.dbErrorsFactory.errorIds; 
	}

	public void decodeIdTributo(PendenzeModel model, DBErrorsBag errs) {
		ConfigurazioneTributoEnteModel confTributo=(ConfigurazioneTributoEnteModel)queryForObject("selectConfigTributo", model);
		
		if (confTributo!=null) {
			model.setIdTributo(confTributo.getIdTributo());
			model.setSpontaneo(ConfigurazioneTributoEnteModel.FLAG_INIZIATIVA_SPONTANEO.equals(confTributo.getFlagIniziativa()));
		} else {
			errs.add( dbErrorsFactory.error(errIds.cantDecodeTributoID, model) );
		}
		
		
	}

	public String decodeIdEnte(PendenzeModel model, DBErrorsBag errs) {
		String idEnte=(String)queryForObject("selectIdEnte", model);
		if (idEnte!=null) {
			model.setIdEnte(idEnte);
		} else {
			errs.add( dbErrorsFactory.error(errIds.cantDecodeIdEnte, model) );
		}
		return idEnte;
	}
	
	public PendenzeModel selectPendenzaToUpdate(PendenzeModel pendenza, DBErrorsBag errs) {
		PendenzeModel updPendenza = 
			(PendenzeModel) queryForObject("selectPendenzaForUpdate", pendenza);
		if (updPendenza==null) {
			errs.add( dbErrorsFactory.error(errIds.pendezaToUpdateDoesNotExists, pendenza) );
			return null;
		} else {
			return updPendenza;
		}
	}

	public boolean checkPendenzaExists(PendenzeModel pendenza) {
		String idPendenza = (String) queryForObject("checkPendenzaExists", pendenza);
		if (idPendenza==null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkIdPagamentoExists(CondizioniPagamentoModel cp) {
		String idPendenza = (String) queryForObject("checkIdPagamentoExists", cp);
		if (idPendenza==null) {
			return false;
		} else {
			return true;
		}
	}

	public PendenzeDBErrorsFactory getDbErrorsFactory() {
		return dbErrorsFactory;
	}

}
