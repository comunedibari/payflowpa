/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.domain.NazioneCittadinanza;
import it.tasgroup.iris.persistence.dao.interfaces.NazioneCittadinanzaDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * 
 */
@Stateless(name="NazioneCittadinanzaDaoService")
public class NazioneCittadinanzaDaoImpl extends DaoImplJpaCmtJta<NazioneCittadinanza> implements NazioneCittadinanzaDAO {

	private static final Logger LOGGER = LogManager.getLogger(NazioneCittadinanzaDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	@Override
	public NazioneCittadinanza readNazioneCittadinanzaById(String id) {
		NazioneCittadinanza retrieved = null;
		try {
			retrieved = loadById(NazioneCittadinanza.class, id, em, null);
		} catch (Exception e) {
			LOGGER.error("error on  readNazioneCittadinanzaById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<NazioneCittadinanza> readListaNazioniCittadinanza() {
        List<NazioneCittadinanza> l = null;
        try {
            l = (List<NazioneCittadinanza>) listByQuery("listaNazioniCittadinanza");
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(getClass().getName(), "listaNazioniCittadinanza", e.getMessage());
        }
        return l;
    }

}
