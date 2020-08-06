package it.tasgroup.iris.persistence.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.tasgroup.iris.persistence.dao.interfaces.StoricoSistemaEnteDAO;


@Stateless(name = "StoricoSistemaEnteDaoService")
public class StoricoSistemaEnteDaoImpl extends SistemaEnteDaoImpl implements StoricoSistemaEnteDAO {
	
	@Override
	@PersistenceContext(unitName = "StoricoPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}
