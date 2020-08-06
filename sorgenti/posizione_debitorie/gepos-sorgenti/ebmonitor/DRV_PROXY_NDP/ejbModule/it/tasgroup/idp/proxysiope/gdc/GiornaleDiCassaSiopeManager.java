package it.tasgroup.idp.proxysiope.gdc;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.pojo.StoricoData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.pagamenti.CasellarioInfo;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GiornaleDiCassaSiopeManager implements ExecutorLocal {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;
 
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public StoricoData executeApplicationTransaction(Object obj) {

		CasellarioInfo casellarioInfo = (CasellarioInfo)obj;
		manager.persist(casellarioInfo);
		logger.info("Giornale di Cassa inserito nel Casellario. Id [" + casellarioInfo.getId() + "]");
		
		return null;

	}
	
	@Override
	public StoricoData executeApplicationTransaction(String data) {
		return null;
	}

	@Override
	public StoricoData executeApplicationTransaction() {
		return null;
	}

	@Override
	public String executeHtml() throws Exception {
		return null;
	}

	@Override
	public String executeHtml(String id) throws Exception {
		return null;
	}

}
