/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.xsqlcmd.dbtrpos.dao.AbstractPendenzeOrchestratorFactory;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelOrchestrator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandler;
import it.nch.eb.xsqlcmd.dbtrpos.operations.OrchestratorPendenzeModelExecutionDispatcher;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class OrchestratorPendenzeModelExecutionDispatcherFactory extends AbstractPendenzeOrchestratorFactory {

	public OrchestratorPendenzeModelExecutionDispatcherFactory() {
		this(PendenzeConsts.OPERATOR_DEFAULT);
	}

	public OrchestratorPendenzeModelExecutionDispatcherFactory(String opDefault) {
		super(opDefault, new PendenzeDBErrorsFactory());
	}

	public OrchestratorPendenzeModelExecutionDispatcher create(
			SqlMapClient sqlMapClient,
			TablesUidProvider keysProvider,
			DBErrorsHandler errorsHandler) {
		PendenzeModelOrchestrator insertPendenze = insertPendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator updatePendenze = updatePendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator updateMassivoPendenze = updateMassivoPendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator replacePendenze = replacePendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator deletePendenze = deletePendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator updateMassivo = updateMassivoPendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		OrchestratorPendenzeModelExecutionDispatcher execProvider =
			new OrchestratorPendenzeModelExecutionDispatcher(insertPendenze, updatePendenze,
					replacePendenze, deletePendenze, updateMassivo);
		return execProvider;
	}

}
