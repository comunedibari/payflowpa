package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.Iterator;
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
public class GestioneOTFReplaceManager implements ObjectCommandExecutor {

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
    	
    	String idPendenzaEnte = monitoringData.getIdPendenzaEnte();
    	String cdTrbEnte = monitoringData.getCdTrbEnte();
    	String idEnte = monitoringData.getIdEnte();
    	String idMittente = monitoringData.getSenderid();	
    	
		//recupero il cdEnte
		Query queryFindEnte = manager.createNamedQuery("TributiEntiByCdEnte");	
		queryFindEnte.setParameter("cdEnte", idMittente);
		List<Enti> entiList = queryFindEnte.getResultList();
		if (entiList!=null && entiList.size()==1) {
			Enti ente = entiList.get(0);			
			idEnte = ente.getIdEnte();  		
		} 	
    	
    	
		Query queryPendenza = manager.createNamedQuery("PendenzaNonAnnullata");				
		queryPendenza.setParameter("stRiga", "V");
		queryPendenza.setParameter("idPendenzaente", idPendenzaEnte);
		queryPendenza.setParameter("cdTrbEnte", cdTrbEnte);
		queryPendenza.setParameter("idEnte", idEnte);

		List<Pendenze> listaPendenza = (List) queryPendenza.getResultList();

		logger.info(" Ho trovato la pendenza ? idPendenzaente/cdTrbEnte/idEnte " + listaPendenza.size());
		
		Iterator<Pendenze> iterPendenze = listaPendenza.iterator();
		while (iterPendenze.hasNext()) {
			Pendenze pendenza = (Pendenze) iterPendenze.next();
			logger.info(" TsInserimento =  " + pendenza.getTsInserimento());
			monitoringData.setPendenzaTrovata(true);
		}
		
		return monitoringData;

	}


	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		// TODO Auto-generated method stub
		return null;
	}




}
