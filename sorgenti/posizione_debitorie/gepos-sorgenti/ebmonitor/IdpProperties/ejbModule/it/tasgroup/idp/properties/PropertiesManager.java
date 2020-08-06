package it.tasgroup.idp.properties;

import it.tasgroup.idp.domain.PropertiesIris;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)// Bean?!
public class PropertiesManager implements SimpleExecutor {
	
	//TODO rimuovere in caso di bean managed
//	/*** Resources ***/
	@Resource
	private EJBContext ctx;
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;		

	private final Log logger = LogFactory.getLog(this.getClass());

	public PropertiesManager(){
		
	}
		
	

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String executeApplicationTransaction(String nome) {

		String value = null;
		
		try {
			
			String timersManager = System.getProperty("it.tasgroup.properties.manager");	
			logger.debug(" PropertiesManager " + timersManager );		
			//se la property indica 'database' allora estraggo i timers dalla tabella
			if (timersManager!=null && "database".equals(timersManager)) {		
			
				//RECUPERO PROPERTIES DA TABELLA
				logger.debug(" ESTRAZIONE PROPERTIES DA TABELLA (CACHED)");
				String activator = "false";
						
				Query queryFindGw = manager.createNamedQuery("ListaPropertiesIris");
				queryFindGw.setParameter("nome", nome);
				
				List<PropertiesIris> timers = queryFindGw.getResultList();
				for (Iterator iterator = timers.iterator(); iterator.hasNext();) {
					PropertiesIris timer = (PropertiesIris) iterator.next();
					logger.info(" Key / Value / Description = " + timer.getNome() + " / " + timer.getValore() + " / " + timer.getDescrizione());
													
					if (nome.equals(timer.getNome()) ) {
						//url ok
						logger.info(" Value della property " + nome  + " = " + timer.getValore());
						value = timer.getValore();
					}										
	 			}
				//FINE RECUPERO PROPERTYES DA TABELLA		
			} 
			else {
			//altrimenti li recupero da fileSystem 			
				value = IrisProperties.getProperty(nome);
			}				

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getSimpleName() + e.getMessage());
		}
		finally {
			
		}		
		
		
		return value;
	}	

}
