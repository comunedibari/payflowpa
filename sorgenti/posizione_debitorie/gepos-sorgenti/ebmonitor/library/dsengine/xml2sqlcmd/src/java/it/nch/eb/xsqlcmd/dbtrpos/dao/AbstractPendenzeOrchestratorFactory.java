/**
 * 18/nov/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.dbtrpos.dao.delete.DeletePendenzaDataIntegratorEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.delete.DeletePendenzeDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.insert.InsertPendenzeBusinessValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.insert.InsertPendenzeDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.replace.ReplacePendenzeDataIntegratorEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.update.UpdatePendenzeDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.update.UpdatePendenzeIntegratorEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.update.UpdatePendenzeValidator;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandler;
import it.nch.eb.xsqlcmd.dbtrpos.updateMassivo.UpdateMassivoPendenzeDao;
import it.nch.eb.xsqlcmd.dbtrpos.updateMassivo.UpdateMassivoPendenzeValidator;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class AbstractPendenzeOrchestratorFactory {

	private final String opDefault;
	private final PendenzeDBErrorsFactory errorsFactory;

	public AbstractPendenzeOrchestratorFactory(String opDefault,
			PendenzeDBErrorsFactory errorsFactory) {
		this.opDefault = opDefault;
		this.errorsFactory = errorsFactory;
	}

	public PendenzeModelOrchestrator deletePendenzeOrchestrator(
			SqlMapClient sqlMapClient, TablesUidProvider keysProvider,
			DBErrorsHandler errorsHandler) {
		return new PendenzeModelOrchestrator(
				"Delete",
				sqlMapClient,
				new UpdatePendenzeValidator(sqlMapClient, errorsFactory),
				new DeletePendenzeDao(sqlMapClient, keysProvider,
						new DeletePendenzaDataIntegratorEffect(sqlMapClient, errorsFactory, opDefault), opDefault),
				errorsHandler);
	}

	public PendenzeModelOrchestrator replacePendenzeOrchestrator(
			SqlMapClient sqlMapClient, TablesUidProvider keysProvider,
			DBErrorsHandler errorsHandler) {
		return new PendenzeModelOrchestrator(
				"Replace",
				sqlMapClient,
				new UpdatePendenzeValidator(sqlMapClient, errorsFactory),
				new InsertPendenzeDao(sqlMapClient, keysProvider,
						new ReplacePendenzeDataIntegratorEffect(sqlMapClient, errorsFactory, opDefault),
						opDefault),
				errorsHandler);
	}

	public PendenzeModelOrchestrator updatePendenzeOrchestrator(
			SqlMapClient sqlMapClient, TablesUidProvider keysProvider,
			DBErrorsHandler errorsHandler) {
		return new PendenzeModelOrchestrator(
				"Update",
				sqlMapClient,
				new UpdatePendenzeValidator(sqlMapClient, errorsFactory),
				new UpdatePendenzeDao(sqlMapClient, keysProvider,
						new UpdatePendenzeIntegratorEffect(sqlMapClient, errorsFactory), opDefault),
				errorsHandler);
	}

	public PendenzeModelOrchestrator updateMassivoPendenzeOrchestrator(
			SqlMapClient sqlMapClient, TablesUidProvider keysProvider,
			DBErrorsHandler errorsHandler) {
		return new PendenzeModelOrchestrator(
				"UpdateMassivo",
				sqlMapClient,
				new UpdateMassivoPendenzeValidator(sqlMapClient, errorsFactory),
				new UpdateMassivoPendenzeDao(sqlMapClient, keysProvider,
						null, opDefault),
				errorsHandler);
	}

	public PendenzeModelOrchestrator insertPendenzeOrchestrator(
			SqlMapClient sqlMapClient, TablesUidProvider keysProvider,
			DBErrorsHandler errorsHandler) {
		return new PendenzeModelOrchestrator(
				"Insert",
				sqlMapClient,
				new InsertPendenzeBusinessValidator(sqlMapClient, errorsFactory),
				new InsertPendenzeDao(sqlMapClient, keysProvider, null, this.opDefault),
				errorsHandler);
	}

	public PendenzeDBErrorsFactory getErrorsFactory() {
		return errorsFactory;
	}

}
