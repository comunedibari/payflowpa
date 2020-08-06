/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.domain.AnagraficaCorsiDottorato;
import it.tasgroup.iris.persistence.dao.interfaces.AnagraficaCorsiDottoratoDAO;
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
@Stateless(name="AnagraficaCorsiDottoratoDaoService")
public class AnagraficaCorsiDottoratoDaoImpl extends DaoImplJpaCmtJta<AnagraficaCorsiDottorato> implements AnagraficaCorsiDottoratoDAO {

	private static final Logger LOGGER = LogManager.getLogger(AnagraficaCorsiDottoratoDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	@Override
	public AnagraficaCorsiDottorato readCorsoDottoratoById(String id) {
		AnagraficaCorsiDottorato retrieved = null;
		try {
			retrieved = loadById(AnagraficaCorsiDottorato.class, id, em, null);
		} catch (Exception e) {
			LOGGER.error("error on  readCorsoDottoratoById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<AnagraficaCorsiDottorato> readListaCorsiDottorato() {
        List<AnagraficaCorsiDottorato> l = null;
        try {
            l = (List<AnagraficaCorsiDottorato>) listByQuery("listaCorsiDottorato");
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(getClass().getName(), "listaCorsiDottorato", e.getMessage());
        }
        return l;
    }

}
