package it.tasgroup.ge.bean;

import it.tasgroup.ge.helpers.GestoreBatchEventiHelper;
import it.tasgroup.ge.helpers.GestoreEventiHelperFactory;
import it.tasgroup.ge.util.GestoreEventiConstant;
import it.tasgroup.ge.util.JobReport;
import it.tasgroup.idp.util.ServiceLocalMapper;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class GestoreBatchEventiBean implements GestoreBatchEventiInterface {
	/*** Resources ***/
	@Resource
	private EJBContext ctx;

	/*** Collaborators ***/
	@EJB(beanName = "GestoreEventiBean")
	private GestoreEventiInterface gestoreEventiProxy;

	@PersistenceContext(unitName = GestoreEventiConstant.IdpAppJpaXA)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public JobReport executeJob() {
		GestoreBatchEventiHelper helper = GestoreEventiHelperFactory.getBatchEventiHelper(manager, gestoreEventiProxy);
		JobReport jReport = helper.executeJob();
		return jReport;
	}

}
