/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import java.util.List;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.domain.Province;
import it.tasgroup.iris.persistence.dao.interfaces.ProvinceDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author PazziK
 * 
 */
@Stateless(name="ProvinceDaoService")
public class ProvinceDaoImpl extends DaoImplJpaCmtJta<Province> implements ProvinceDAO {

	private static final Logger LOGGER = LogManager.getLogger(ProvinceDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public Province readProvinciaById(String id) {
		
		Province retrieved = null;
		
		try {
			
			retrieved = loadById(Province.class,id,em,null);
			
		} catch (Exception e) {
			
			LOGGER.error("error on  readProvinciaById, ID = " + id, e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return retrieved;
	}
	
	@Override
    public List<Province> readListaProvince() {
		
        List<Province> l = null;
        
        try {
        	
            l = (List<Province>) listByQuery("provinceAttive");
            
        } catch (Exception e) {
        	
            e.printStackTrace();
            
            Tracer.error(getClass().getName(), "readListaProvince", e.getMessage());
            
        }
        
        return l;
    }

}
