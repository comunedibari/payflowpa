/**
 * 08/lug/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelOrchestrator;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.operations.OrchestratorPendenzeModelExecutionDispatcher;
import it.nch.eb.xsqlcmd.utils.PendenzeUtils;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class PendenzeListDbPerformerOnebyOne implements IPendenzeListDbPerformer {

	private static final org.slf4j.Logger log = ResourcesUtil.createLogger(PendenzeListDbPerformerOnebyOne.class);

	private final OrchestratorPendenzeModelExecutionDispatcher pendenzeModelExecutionDispatcher;
	private final String consumerName;
	
	
	public PendenzeListDbPerformerOnebyOne( 
			String nm, 
			SqlMapClient sqlMapClient,
			OrchestratorPendenzeModelExecutionDispatcher pendenzeModelExecutionDispatcher) {
//			Set<String> failedPendenze) {
		this.consumerName = nm;
		this.pendenzeModelExecutionDispatcher = pendenzeModelExecutionDispatcher;
//		this.failedPendenze = failedPendenze;
	}

	public PendenzeOperationOutcome apply(List<PendenzeModel> lst) {
		PendenzeOperationOutcome res = new PendenzeOperationOutcome();
		if (lst.size()>0) {
			for (PendenzeModel pendenzeModel : lst) {
				log.debug(consumerName + " processing pendenza " + PendenzeUtils.pendenzaDescId(pendenzeModel));
				PendenzeModelOrchestrator orch = pendenzeModelExecutionDispatcher.getOrchestrator(pendenzeModel);
				DBError[] errs = orch.apply(pendenzeModel);
				if (errs != null && errs.length > 0) {
					res.failure(pendenzeModel, errs);
				} else {
					res.success(pendenzeModel);
				}
				
			}
		}
		return res;
	}

	public void flush() {
		
	}

	@Override
	public String toString() {
		return PendenzeListDbPerformerOnebyOne.class.getName();
	}
	
}
