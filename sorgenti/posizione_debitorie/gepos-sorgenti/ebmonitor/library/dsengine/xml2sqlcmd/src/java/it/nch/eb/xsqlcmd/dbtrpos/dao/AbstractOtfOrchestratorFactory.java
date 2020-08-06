/**
 * 18/nov/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.dbtrpos.dao.insert.InsertOtfDao;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandler;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class AbstractOtfOrchestratorFactory {

	private final String opDefault;
	private final PendenzeDBErrorsFactory errorsFactory;

	public AbstractOtfOrchestratorFactory(String opDefault,
			PendenzeDBErrorsFactory errorsFactory) {
		this.opDefault = opDefault;
		this.errorsFactory = errorsFactory;
	}

	public OtfModelOrchestrator insertPendenzeOrchestrator(
			SqlMapClient sqlMapClient, TablesUidProvider keysProvider,
			DBErrorsHandler errorsHandler) {
		return new OtfModelOrchestrator(
				"Insert",
				sqlMapClient,
				null,
				new InsertOtfDao(sqlMapClient, keysProvider, null, this.opDefault),
				errorsHandler);
	}

	public PendenzeDBErrorsFactory getErrorsFactory() {
		return errorsFactory;
	}

}
