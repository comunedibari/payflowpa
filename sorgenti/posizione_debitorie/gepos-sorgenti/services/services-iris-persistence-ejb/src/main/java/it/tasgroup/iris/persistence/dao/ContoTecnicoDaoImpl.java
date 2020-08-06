/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.persistence.dao.interfaces.ContoTecnicoDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author PazziK 
 *
 */
@Stateless(name="ContoTecnicoDao")
public class ContoTecnicoDaoImpl extends DaoImplJpaCmtJta<ContoTecnico> implements ContoTecnicoDAO {

	private static final Logger LOGGER = LogManager.getLogger(ContoTecnicoDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	@Override
	public ContoTecnico readUniqueContoTecnicoAttivo(String intestatario) {
		
		ContoTecnico contoTecnicoAttivo = null;
		
		try {		
			
			Map params = new HashMap<String, Object>();
			
			params.put("intestatario", intestatario);
			
			contoTecnicoAttivo = (ContoTecnico)uniqueResultByQuery("getUniqueContoTecnicoAttivo", params ,em);
		
		} catch (Exception e) {
			
			LOGGER.error("error on readUniqueContoTecnicoAttivo ", e);		
			
			throw new DAORuntimeException(e);
			
		}
		
		return contoTecnicoAttivo;
	}

	

}
