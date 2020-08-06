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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class PendenzeListDbPerformer implements IPendenzeListDbPerformer {

	private static final org.slf4j.Logger log = ResourcesUtil.createLogger(PendenzeListDbPerformer.class);

	private final SqlMapClient sqlMapClient;
	private final OrchestratorPendenzeModelExecutionDispatcher pendenzeModelExecutionDispatcher;
	private final String consumerName;
	
	private final MapOfLists<String, PendenzeModel> postPonedPendenzeMap = new MapOfLists<String, PendenzeModel>();
//	private final Set<String> failedPendenze;
	
	public PendenzeListDbPerformer( 
			String nm, 
			SqlMapClient sqlMapClient,
			OrchestratorPendenzeModelExecutionDispatcher pendenzeModelExecutionDispatcher) {
//			Set<String> failedPendenze) {
		this.consumerName = nm;
		this.sqlMapClient = sqlMapClient;
		this.pendenzeModelExecutionDispatcher = pendenzeModelExecutionDispatcher;
//		this.failedPendenze = failedPendenze;
	}

	public PendenzeOperationOutcome apply(List<PendenzeModel> lst) {
		log.debug("received list of pendenze :");
		if (lst!=null && !lst.isEmpty()) {
			
			Set<String> postPonedKeys = new TreeSet<String>();
			List<PendenzeModel> postPoned = postPonedPendenzeMap.pop();
			List<PendenzeModel> execLst = new ArrayList<PendenzeModel>();
			execLst.addAll(postPoned);
			for (PendenzeModel ppnd : postPoned) {
				postPonedKeys.add(PendenzeUtils.pendenzaId(ppnd));
			}
			for (PendenzeModel pend : lst) {
				String key = PendenzeUtils.pendenzaId(pend);
				postPonedKeys.add(key);
			}
			
			for (PendenzeModel pend : lst) {
				String key = PendenzeUtils.pendenzaId(pend);
				if (postPonedKeys.contains(key)) {
					postPonedPendenzeMap.put(key, pend);
				} else {
					execLst.add(pend);
				}
			}
			return performDbOperations(execLst);
		} else {
			return null;
		}
	}

	public void flush() {
		while (!this.postPonedPendenzeMap.isEmpty()) {
			List<PendenzeModel> lst = postPonedPendenzeMap.pop();
			if (lst == null || lst.isEmpty()) throw new IllegalStateException("bug");
			performDbOperations(lst);
		}
	}

	private PendenzeOperationOutcome performDbOperations(List<PendenzeModel> lst) {
		PendenzeOperationOutcome outcome = new PendenzeOperationOutcome();
		
		if (lst.size() < 1) return outcome;
		
		Set<String> performedPendenze = new HashSet<String>();

		try {
			log.debug(consumerName + " is performing " + lst.size() + " operations");
			
			sqlMapClient.startTransaction();
			for (PendenzeModel pend : lst) {
				
				String pendenzaKey = key(pend);
				log.debug("pendenza " + pendenzaKey);
				PendenzeModelOrchestrator orch = pendenzeModelExecutionDispatcher.getOrchestrator(pend);
				String idPendenza = PendenzeUtils.pendenzaId(pend);
				
				if (performedPendenze.contains(idPendenza)) {
					postPonedPendenzeMap.put(idPendenza, pend);
				} else {
					performedPendenze.add(idPendenza);
					
					log.debug(consumerName + " validating " + pendenzaKey);
					DBError[] errsv = orch.validate(pend, sqlMapClient);
					if (errsv != null && errsv.length > 0) {

						String errMsgPrfx = consumerName + " pendenza ("+ pendenzaKey + ") operation "+ pend.getOperationName() + " is not valid\n";
						
						for (int i = 0; i < errsv.length; i++) {
							DBError ierr = errsv[i];
							Exception cause = ierr.getCause();
							if (cause == null) {
								log.error(errMsgPrfx + " errorId "+ ierr.getErrorId());
							} else {
								String msg = cause.getMessage();
								log.error(errMsgPrfx+ " errorId "+ ierr.getErrorId()+ "\n"+ (msg.length() > 80 ? msg.substring(0,80) : msg));
							}
						}
						outcome.failure(pend, errsv);
						orch.processErrors(errsv, pend, sqlMapClient);

					} else {

						DBError[] errs = orch.writeDB(pend, sqlMapClient);
						if (errs != null && errs.length > 0) {
							outcome.failure(pend, errs);
							String errPrfx = consumerName + " error inserting " + pendenzaKey + " cause \n";
							for (DBError error : errs) {
								log.debug(errPrfx + " error "+ error.toString());
							}
							orch.processErrors(errs, pend, sqlMapClient);
						} else {
							orch.processErrors(null, pend, sqlMapClient);
							outcome.success(pend);
							log.info(consumerName + " performed db operation " + pend.getOperationName() + " pendenza : " + pendenzaKey);
						}
					}

				}

			}
			sqlMapClient.getCurrentConnection().commit();
			sqlMapClient.commitTransaction();
			sqlMapClient.endTransaction();
			
			StringBuilder sb = new StringBuilder();
			for (PendenzeModel pend : lst) {
				sb.append(PendenzeUtils.pendenzaDescId(pend));
				sb.append("\n");
			}
			log.debug(consumerName + " COMMITED " +  lst.size() +" operations \n" + sb.toString());
			
		} catch (Exception e) {
			endTransaction(sqlMapClient);
			outcome = new PendenzeOperationOutcome();
			for (PendenzeModel pendenzeModel : lst) {
				outcome.failure(pendenzeModel, null ); // fake error, we just want to track failing pendenze
			}
			log.error("error while writing the db",e);
		} finally {
		}

		return outcome;
	}

	private void endTransaction(SqlMapClient sqlSession) {
		try {
			sqlSession.endTransaction();
		} catch (SQLException e) {
			log.error("errror closing transaction ", e);
		}
	}

	private String key(PendenzeModel pendenzeModel) {
		return PendenzeUtils.pendenzaDescId(pendenzeModel);
	}

	@Override
	public String toString() {
		return PendenzeListDbPerformer.class.getName();
	}
	
}
