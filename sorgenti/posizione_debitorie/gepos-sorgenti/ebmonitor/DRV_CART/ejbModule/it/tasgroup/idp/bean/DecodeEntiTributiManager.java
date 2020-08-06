package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DecodeEntiTributiManager implements ObjectCommandExecutor {

	@Resource
	private EJBContext ctx;
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;
	
	private final Log logger = LogFactory.getLog(this.getClass());


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object otf) {
		// Monitoring data
    	MonitoringData monitoringData = (MonitoringData)otf;    	
    	
    	String cdTrbEnte = monitoringData.getCdTrbEnte();
    	String idMittente = monitoringData.getSenderid();	
    	
		boolean result = false;
		//recupero il cdEnte
		Query queryFindEnte = manager.createNamedQuery("TributiEntiByCdEnte");	
		queryFindEnte.setParameter("cdEnte", idMittente);
		String idEnte = "";
		List<Enti> entiList = queryFindEnte.getResultList();
		if (entiList!=null && entiList.size()==1) {
			Enti ente = entiList.get(0);			
			idEnte = ente.getIdEnte(); 

			//ok ho trovato 
			Query queryFindEnteTributo = manager.createNamedQuery("TributiEntiByIdEnteCdTrbEnte");	
			queryFindEnteTributo.setParameter("idEnte", idEnte);
			queryFindEnteTributo.setParameter("cdTrbEnte", cdTrbEnte);
		
			List<TributiEnti> entiTributoList = queryFindEnteTributo.getResultList();
			logger.info(" Certo Ente id = " + idMittente + " Trb " + cdTrbEnte);	
			if (entiTributoList!=null && entiTributoList.size()==1) {
				TributiEnti enteTributo = entiTributoList.get(0);			
				if ("Y".equals(enteTributo.getFlReplaceOTF())) {
					result = true;
				}
			} 		
		} 		
		monitoringData.setTributoReplaceable(result);
		return monitoringData;
	}


	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		// TODO Auto-generated method stub
		return null;
	}




}
