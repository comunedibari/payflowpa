/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandler;
import it.nch.eb.xsqlcmd.dbtrpos.operations.PendenzeModelExecutionDispatcher;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class PendenzeModelExecutionDispatcherFactory extends AbstractPendenzeOrchestratorFactory {

//	private final String opDefault;
//	private final PendenzeDBErrorsFactory errorsFactory = new PendenzeDBErrorsFactory();

	public PendenzeModelExecutionDispatcherFactory() {
		this(PendenzeConsts.OPERATOR_DEFAULT);
	}

	public PendenzeModelExecutionDispatcherFactory(String opDefault) {
//		this.opDefault = opDefault;
		super(opDefault,new PendenzeDBErrorsFactory());
	}

	public PendenzeModelExecutionDispatcher create(
			SqlMapClient sqlMapClient,
			TablesUidProvider keysProvider,
			DBErrorsHandler errorsHandler) {
		PendenzeModelOrchestrator insertPendenze =
			insertPendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator updatePendenze =
			updatePendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator updateMassivoPendenze =
			updateMassivoPendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator replacePendenze =
			replacePendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelOrchestrator deletePendenze =
			deletePendenzeOrchestrator(sqlMapClient, keysProvider, errorsHandler);

		PendenzeModelExecutionDispatcher execProvider =
			new PendenzeModelExecutionDispatcher(insertPendenze, updatePendenze,
					replacePendenze, deletePendenze, updateMassivoPendenze);
		return execProvider;
	}

}
